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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;

/**
 * The {@code RejectShell} class is an SSH command that writes one red line and then ends the channel.
 * The factory returns this command when a login is refused. The command does not open stored chat memory for the user.
 *
 * The constructor stores the reason text. {@link #start} writes that text in red. The method then asks the exit callback to leave with status {@code 1}.
 * // @aa-sdd L40 aa-sdd-source-prose.md#RejectShell
 *
 * @author clayl (2026-08-26)
 */
// @aa-sdd PK-820 docs/specs/common/roux.spec.md
// @aa-sdd PK-821 docs/specs/common/roux.spec.md
// @aa-sdd PK-474 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
public class RejectShell implements Command {

    private final String reason;
    private OutputStream out;
    private ExitCallback callback;

    /**
     * The constructor stores the reason text that {@link #start} will write.
     * The constructor stores {@code refused} when the reason is null.
     *
     * @param reason The reason is the text written in red.
     */
    public RejectShell(String reason) {
        this.reason = reason == null ? "refused" : reason;
    }

    /**
     * The method accepts the input stream and does not store it.
     * This command never reads keystrokes.
     *
     * @param in The input stream is the SSH input for this channel.
     */
    @Override
    public void setInputStream(InputStream in) {
    }

    /**
     * The method stores the output stream that {@link #start} writes to.
     *
     * @param out The output stream is the SSH output for this channel.
     */
    @Override
    public void setOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * The method accepts the error stream and does not store it.
     * The red line goes to the output stream, not to this stream.
     *
     * @param err The error stream is the SSH error output for this channel.
     */
    @Override
    public void setErrorStream(OutputStream err) {
    }

    /**
     * The method stores the exit callback that {@link #start} calls.
     *
     * @param callback The callback is the hook that ends the channel.
     */
    @Override
    public void setExitCallback(ExitCallback callback) {
        this.callback = callback;
    }

    /**
     * The method writes the reason in red and then ends the channel with status {@code 1}.
     * A missing output stream skips the write. A write failure is ignored. A missing exit callback skips the status.
     *
     * @param channel The channel is the SSH channel that started this command.
     * @param env The environment is the channel environment. The method does not read it.
     * @throws IOException The signature declares this checked exception.
     */
    @Override
    public void start(ChannelSession channel, Environment env) throws IOException {
        if (out != null) {
            try {
                out.write((Ansi.red(reason) + "\r\n").getBytes(StandardCharsets.UTF_8));
                out.flush();
            } catch (IOException ignored) {
                // hangup anyway
            }
        }
        if (callback != null) {
            callback.onExit(1);
        }
    }

    /**
     * The method is the channel destroy hook.
     * The method does no work.
     *
     * @param channel The channel is the SSH channel being closed.
     */
    @Override
    public void destroy(ChannelSession channel) {
    }
}
/*****************************************************************************************
 * End of Class: RejectShell.java 082626
 ****************************************************************************************/
