/*
 * Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
 * All rights reserved.
 *
 * Author: Clay Lambert
 *
 * Licensed under the MIT License.
 * If you use or modify this source, you must include this copyright notice
 * and the MIT License permission notice in all copies or substantial portions
 * of the software, including modifications.
 */

package org.casserole.ssh.shell;

import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.shell.ShellFactory;
import org.casserole.chat.ChatSession;
import org.casserole.chat.cmd.CommandProcessor;
import org.casserole.chat.cmd.CommandProcessorFactory;
import org.casserole.config.CasseroleProperties;
import org.casserole.format.FormatRouter;
import org.casserole.format.FormatRouterFactory;
import org.casserole.lamina.Lamina;
import org.aetheris.ssh.AmwpAwareShell;
import org.aetheris.ssh.AmwpFacade;
import org.casserole.llm.LlmRouter;
import org.casserole.llm.LlmService;
import org.roux.session.OpenResult;
import org.roux.session.RegionOwner;
import org.roux.spring.RouxChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// @aa-sdd PK-1054 docs/specs/common/formal-plan.spec.md
/**
 * The {@code ChatShellFactory} class builds one {@link ChatShell} for each SSH connection.
 * That shell gets its own {@link org.casserole.chat.ChatSession} and its own {@link org.casserole.chat.cmd.CommandProcessor}. A refused login does not open stored chat memory for the user.
 * // @aa-sdd L40 aa-sdd-source-prose.md#ChatShellFactory
 *
 * @author clayl (2026-08-24)
 */
@Component
// @aa-sdd PK-414 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-416 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-781 docs/specs/common/stages.spec.md
// @aa-sdd PK-429 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-452 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-457 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-816 docs/specs/common/roux.spec.md
// @aa-sdd PK-823 docs/specs/common/roux.spec.md
// @aa-sdd PK-466 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
public class ChatShellFactory implements ShellFactory {

    private final CasseroleProperties properties;
    private final CommandProcessorFactory commandProcessorFactory;
    private final FormatRouterFactory formatRouterFactory;
    private final LlmService llmService;
    private final LlmRouter llmRouter;
    private final RouxChatMemory rouxMemory;
    private final Lamina lamina;
    private final AmwpFacade amwpFacade;
    private final String modelName;

    /**
     * The constructor stores the settings, the factories, the model services, and the shared turn path.
     * The model name is the configured chat model.
     *
     * @param properties The properties are the casserole settings.
     * @param commandProcessorFactory The command processor factory builds one command registry per session.
     * @param formatRouterFactory The format router factory builds the reply formatter.
     * @param llmService The language-model service is the Ollama boundary.
     * @param llmRouter The router is the map of language-model providers.
     * @param rouxMemory The memory is the stored chat memory.
     * @param lamina The lamina is the server thread model.
     * @param amwpFacade The facade is the shared path for a console turn and a cell turn.
     * @param modelName The model name is the configured chat model.
     */
    public ChatShellFactory(
            CasseroleProperties properties,
            CommandProcessorFactory commandProcessorFactory,
            FormatRouterFactory formatRouterFactory,
            LlmService llmService,
            LlmRouter llmRouter,
            RouxChatMemory rouxMemory,
            Lamina lamina,
            AmwpFacade amwpFacade,
            @Value("${spring.ai.ollama.chat.options.model:qwen2.5-coder:7b}") String modelName) {
        this.properties = properties;
        this.commandProcessorFactory = commandProcessorFactory;
        this.formatRouterFactory = formatRouterFactory;
        this.llmService = llmService;
        this.llmRouter = llmRouter;
        this.rouxMemory = rouxMemory;
        this.lamina = lamina;
        this.amwpFacade = amwpFacade;
        this.modelName = modelName;
    }

    /**
     * The method builds the shell for one channel, or a {@link RejectShell} when the login cannot open memory.
     * The username comes from the server session. A missing session or a read failure uses an empty name. {@link org.roux.session.RegionOwner#sanitize} rejects an illegal name with {@link RejectShell} and the text {@code invalid username}.
     *
     * Ram-only memory builds a {@link org.casserole.chat.ChatSession} without opening a stored region. Any other memory opens the user. A refused open returns {@link RejectShell} with that reason. A failure while building the session closes the open and throws again.
     *
     * The method then builds a command processor and a format router. The router follows the smart-format setting. The return value is an {@link org.aetheris.ssh.AmwpAwareShell} around the new {@link ChatShell}. A runtime failure prints its stack and throws again.
     *
     * @param channel The channel is the SSH channel that needs a shell.
     * @return The return value is the shell command for that channel.
     */
    @Override
    public Command createShell(ChannelSession channel) {
        try {
            String rawUser = "";
            try {
                ServerSession serverSession = channel.getServerSession();
                if (serverSession != null && serverSession.getUsername() != null) {
                    rawUser = serverSession.getUsername();
                }
            } catch (Exception ignored) {
                rawUser = "";
            }
            String user;
            try {
                user = RegionOwner.sanitize(rawUser);
            } catch (IllegalArgumentException ex) {
                return new RejectShell("invalid username");
            }
            String systemPrompt = properties.getChat().getSystemPrompt();
            ChatSession session;
            if (rouxMemory.isRamOnly()) {
                session = new ChatSession(systemPrompt, rouxMemory);
            } else {
                OpenResult open = rouxMemory.open(user);
                if (open.getKind() == OpenResult.REFUSED) {
                    return new RejectShell(open.getReason());
                }
                try {
                    session = ChatSession.forUser(user, open, systemPrompt, rouxMemory);
                } catch (RuntimeException ex) {
                    rouxMemory.persistClose(user);
                    throw ex;
                }
            }
            CommandProcessor processor = commandProcessorFactory.create();
            FormatRouter router = formatRouterFactory.create(properties.getChat().isSmartFormat());
            ChatShell shell = new ChatShell(session, processor, llmService, properties, modelName, router, lamina,
                    llmRouter, amwpFacade);
            return new AmwpAwareShell(shell, amwpFacade, lamina, user);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
/*****************************************************************************************
 * End of Class: ChatShellFactory.java 082626
 ****************************************************************************************/
