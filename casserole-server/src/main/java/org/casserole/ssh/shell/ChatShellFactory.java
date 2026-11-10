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

// @aa-sdd PK-3545 PK-3575 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
/**
 * The {@code ChatShellFactory} class builds one {@link ChatShell} for each SSH connection.
 * That shell gets its own {@link org.casserole.chat.ChatSession} and its own {@link org.casserole.chat.cmd.CommandProcessor}. A refused login does not open stored chat memory for the user.
 * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
 * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
 * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
 * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
 * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
 * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
 * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
 * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
 * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
 * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
 * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
 * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
 * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
 * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
 * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
 * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
 * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
 * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
 * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
 *
 * @author clayl (2026-08-24)
 */
@Component
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
