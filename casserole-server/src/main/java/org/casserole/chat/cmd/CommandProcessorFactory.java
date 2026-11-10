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

package org.casserole.chat.cmd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// @aa-sdd PK-0842 PK-0889 PK-0936 PK-0983 PK-1030 PK-1077 PK-1124 PK-1171 PK-1218 PK-1265 PK-1312 PK-1359 PK-1406 PK-1453 PK-1500 PK-1547 PK-1595 PK-1642 PK-1689 PK-1736 PK-1783 PK-1830 PK-1877 PK-1924 PK-1971 PK-2018 PK-2066 PK-2113 PK-2160 PK-2207 PK-2255 PK-2303 PK-2351 PK-2398 PK-2445 PK-2493 PK-2540 PK-2587 PK-2634 PK-2682 PK-2729 PK-2777 PK-2824 PK-2871 PK-2918 PK-2965 PK-3012 PK-3059 PK-3106 PK-3153 PK-3201 PK-3248 PK-3295 PK-3343 PK-3390 PK-3437 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-0671 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0543 PK-0545 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
/**
 * The {@code CommandProcessorFactory} type builds one {@link CommandProcessor} for an SSH session.
 * SSH is the secure-shell protocol the client used to connect.
 * Each command is constructed in {@link #create}.
 * The command set is not discovered by a component scan.
 *
 * @author clayl (2026-08-24)
 */
@Component
public class CommandProcessorFactory {

    private final String ollamaBaseUrl;

    /**
     * The {@code ollamaNumCtx} field is the token window passed to load and swap commands.
     * A positive value is sent as {@code num_ctx}.
     * A value of {@code 0} means those requests omit {@code num_ctx}.
     */
    private final int ollamaNumCtx;

    /**
     * The constructor stores the Ollama HTTP base and the token window.
     * Ollama is the program that runs a local model.
     * The base comes from {@code spring.ai.ollama.base-url}, and the default is {@code http://localhost:11434}.
     * The window comes from {@code spring.ai.ollama.chat.options.num-ctx}, and the default is {@code 0}.
     * A window of {@code 0} means a later load request omits {@code num_ctx}.
     *
     * @param ollamaBaseUrl The Spring AI Ollama base URL.
     * @param ollamaNumCtx The {@code spring.ai.ollama.chat.options.num-ctx} value, where {@code 0} omits that parameter.
     */
    public CommandProcessorFactory(
            @Value("${spring.ai.ollama.base-url:http://localhost:11434}") String ollamaBaseUrl,
            @Value("${spring.ai.ollama.chat.options.num-ctx:0}") int ollamaNumCtx) {
        this.ollamaBaseUrl = ollamaBaseUrl;
        this.ollamaNumCtx = ollamaNumCtx;
    }

    /**
     * The {@code create} method returns a new processor with this session's command set.
     * The registered order is chat start, chat stop, help, message show, message find, message list, message lock, message unlock, and message edit.
     * The same processor then registers clear, status, exit, ollama load, ollama stop, ollama, swap, and select-model.
     * Ollama load and swap receive the stored base URL and the stored token window.
     *
     * @return A new processor that already holds that command set.
     */
    public CommandProcessor create() {
        CommandProcessor processor = new CommandProcessor();
        processor.register(new ChatStartCommand());
        processor.register(new ChatStopCommand());
        processor.register(new HelpCommand());
        processor.register(new MessageShowCommand());
        processor.register(new MessageFindCommand());
        processor.register(new MessageListCommand());
        processor.register(new MessageLockCommand());
        processor.register(new MessageUnlockCommand());
        processor.register(new MessageEditCommand());
        processor.register(new ClearCommand());
        processor.register(new StatusCommand());
        processor.register(new ExitCommand());
        processor.register(new OllamaLoadCommand(ollamaBaseUrl, ollamaNumCtx));
        processor.register(new OllamaStopCommand());
        processor.register(new OllamaExecCommand());
        processor.register(new SwapCommand(ollamaBaseUrl, ollamaNumCtx));
        processor.register(new SelectModelCommand());
        return processor;
    }
}
/*****************************************************************************************
 * End of Class: CommandProcessorFactory.java 082426
 ****************************************************************************************/
