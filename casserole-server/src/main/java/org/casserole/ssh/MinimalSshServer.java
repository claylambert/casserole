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

package org.casserole.ssh;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.shell.ShellFactory;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * The {@code MinimalSshServer} class is a standalone Apache MINA SSH listener on port {@code 2222}.
 * Apache MINA is the library that accepts the SSH connection. This class is not the Spring bean. The Spring listener is {@link org.casserole.ssh.shell.SshServer}.
 *
 * The host key file is {@code hostkey.ser} in the working directory. {@link #main} accepts only the name {@code clay} with the password {@code butter}. The shell echoes typed lines and reports escape sequences.
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
 */
public class MinimalSshServer {

    /**
     * The method starts the echo listener and waits until the process is interrupted.
     * The port is {@code 2222}. The host key file is {@code hostkey.ser}. The only accepted name is {@code clay} and the only accepted password is {@code butter}.
     *
     * The method prints the port, the connect line {@code ssh -p 2222 clay@localhost}, and the password line. An interrupt sets the interrupt flag again.
     *
     * @param args The arguments are unused.
     * @throws IOException The method throws this exception when the listener cannot start.
     */
    public static void main(String[] args) throws IOException {
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(2222);

        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(
                Paths.get("hostkey.ser")));

        /**
         * The authenticator accepts only the name {@code clay} with the password {@code butter}.
         * Any other pair returns false.
         */
        sshd.setPasswordAuthenticator(new PasswordAuthenticator() {
            // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
            // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
            // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
            // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
            // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
            // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
            // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
            // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
            // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
            // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
            // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
            // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
            // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
            // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
            // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
            // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
            // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
            // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
            // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
            // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
            // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
            // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
            // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
            // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
            // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
            // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
            // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
            /**
             * The method accepts only the name {@code clay} with the password {@code butter}.
             *
             * @param username The username is the offered name.
             * @param password The password is the offered password.
             * @param session The session is the server session. The method does not read it.
             * @return The return value is true only for that name and password.
             */
            @Override
            public boolean authenticate(String username, String password, ServerSession session) {
                return "clay".equals(username) && "butter".equals(password);
            }
        });

        sshd.setShellFactory(new EchoShellFactory());

        sshd.start();
        System.out.println("SSH Echo Server started on port " + sshd.getPort());
        System.out.println("Connect with: ssh -p 2222 clay@localhost");
        System.out.println("Password: butter");

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
    /**
     * The {@code EchoShellFactory} class builds one {@link EchoCommand} for each channel.
     */
    static class EchoShellFactory implements ShellFactory {
        /**
         * The method returns a new echo command.
         *
         * @param channel The channel is the SSH channel that needs a shell.
         * @return The return value is the echo command.
         */
        @Override
        public Command createShell(ChannelSession channel) {
            return new EchoCommand();
        }
    }

    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
    /**
     * The {@code EchoCommand} class reads the SSH input, echoes printable characters, and reports escape sequences.
     * Enter runs {@link #onCommand}. The prompt is {@code casserole> }.
     */
    static class EchoCommand implements Command, Runnable {

        private InputStream in;
        private OutputStream out;
        private OutputStream err;
        private ExitCallback callback;
        private Thread thread;

        // Line buffer for normal Enter-based commands
        private final StringBuilder lineBuffer = new StringBuilder();

        // Simple escape sequence buffer
        private final StringBuilder escapeBuffer = new StringBuilder();
        private boolean inEscapeSequence = false;

        /**
         * The method stores the input stream the read loop will use.
         *
         * @param in The input stream is the SSH input for this channel.
         */
        @Override
        public void setInputStream(InputStream in) {
            this.in = in;
        }

        /**
         * The method stores the output stream that {@link #write} uses.
         *
         * @param out The output stream is the SSH output for this channel.
         */
        @Override
        public void setOutputStream(OutputStream out) {
            this.out = out;
        }

        /**
         * The method stores the error stream.
         * The echo path does not write to this stream.
         *
         * @param err The error stream is the SSH error output for this channel.
         */
        @Override
        public void setErrorStream(OutputStream err) {
            this.err = err;
        }

        /**
         * The method stores the exit callback.
         *
         * @param callback The callback is the hook that ends the channel.
         */
        @Override
        public void setExitCallback(ExitCallback callback) {
            this.callback = callback;
        }

        /**
         * The method writes the welcome lines and starts the daemon read thread.
         * The thread name is {@code echo-shell}.
         *
         * @param channel The channel is the SSH channel that started this command.
         * @param env The environment is the channel environment. The method does not read it.
         * @throws IOException The method throws this exception when a welcome write fails.
         */
        @Override
        public void start(ChannelSession channel, Environment env) throws IOException {
            write("Welcome to Casserole Command Shell\r\n");
            write("• Type a command and press Enter\r\n");
            write("• Special keys / Escape sequences are trapped immediately\r\n");
            write("• Type 'exit' to quit\r\n\r\n");
            write("casserole> ");

            thread = new Thread(this, "echo-shell");
            thread.setDaemon(true);
            thread.start();
        }

        /**
         * The method interrupts the read thread.
         * A missing thread does nothing.
         *
         * @param channel The channel is the SSH channel being closed.
         */
        @Override
        public void destroy(ChannelSession channel) {
            if (thread != null) {
                thread.interrupt();
            }
        }

        /**
         * The method reads bytes until the input ends, and handles each byte.
         * Escape bytes collect until {@link #isCompleteEscapeSequence} accepts them. Enter trims the line, calls {@link #onCommand} when the line is not empty, and writes the prompt. Backspace deletes one buffered character and erases one screen cell. A printable byte is echoed. Other control bytes are ignored.
         *
         * A normal end calls the exit callback with status {@code 0}. Any other failure calls it with status {@code 1} and the exception message.
         */
        @Override
        public void run() {
            try {
                byte[] buffer = new byte[1024];
                int n;

                while ((n = in.read(buffer)) != -1) {
                    for (int i = 0; i < n; i++) {
                        int b = buffer[i] & 0xFF;   // unsigned
                        char c = (char) b;

                        // ------------------------------------------------
                        // 1. Always inspect every incoming character here
                        // ------------------------------------------------
                        onRawCharacter(b, c);

                        // ------------------------------------------------
                        // 2. Escape sequence handling (immediate commands)
                        // ------------------------------------------------
                        if (inEscapeSequence) {
                            escapeBuffer.append(c);

                            // Very simple detection – extend as needed
                            if (isCompleteEscapeSequence(escapeBuffer)) {
                                String seq = escapeBuffer.toString();
                                escapeBuffer.setLength(0);
                                inEscapeSequence = false;

                                onEscapeSequence(seq);
                            }
                            continue;
                        }

                        if (b == 0x1B) {          // ESC
                            inEscapeSequence = true;
                            escapeBuffer.setLength(0);
                            escapeBuffer.append(c);
                            continue;
                        }

                        // ------------------------------------------------
                        // 3. Normal character / line handling
                        // ------------------------------------------------
                        if (c == '\r' || c == '\n') {
                            String line = lineBuffer.toString().trim();
                            lineBuffer.setLength(0);

                            write("\r\n");

                            if (!line.isEmpty()) {
                                onCommand(line);          // Enter-based command
                            }

                            write("casserole> ");
                        }
                        else if (c == 127 || c == 8) {   // Backspace
                            if (lineBuffer.length() > 0) {
                                lineBuffer.setLength(lineBuffer.length() - 1);
                                write("\b \b");
                            }
                        }
                        else if (c >= 32 && c < 127) {   // Printable
                            lineBuffer.append(c);
                            write(String.valueOf(c));    // echo
                        }
                        // other control characters are ignored for now
                    }
                }

                callback.onExit(0);

            } catch (Exception e) {
                callback.onExit(1, e.getMessage());
            }
        }

        // =====================================================================
        //  Extension points – put your logic in these methods
        // =====================================================================

        /**
         * The method sees every raw byte before line editing.
         * Byte {@code 3}, which is Ctrl+C, writes {@code ^C} and the prompt. A write failure is ignored.
         *
         * @param b The byte is the unsigned value from {@code 0} to {@code 255}.
         * @param c The character is that same byte as a character.
         */
        private void onRawCharacter(int b, char c) {
            // Example: trap Ctrl+C immediately
            if (b == 3) {   // Ctrl+C
                try {
                    write("\r\n^C\r\n");
                    write("casserole> ");
                } catch (IOException ignored) {}
            }

            // You can add more single-key traps here
        }

        /**
         * The method writes the escape sequence as hex bytes and then writes the prompt.
         * The sequence fires as soon as it is complete. Enter is not required.
         *
         * @param sequence The sequence is the complete escape text, including the escape byte.
         * @throws IOException The method throws this exception when the write fails.
         */
        private void onEscapeSequence(String sequence) throws IOException {
            write("\r\n[Escape sequence received: ");
            for (char ch : sequence.toCharArray()) {
                write(String.format("\\x%02X", (int) ch));
            }
            write("]\r\n");
            write("casserole> ");

            // Example of mapping a sequence to an action:
            // if (sequence.equals("\u001B[A")) { ... }  // Up arrow
        }

        /**
         * The method handles one line after Enter.
         * {@code exit} or {@code quit}, ignoring case, writes {@code Goodbye.} and ends the channel with status {@code 0}. Any other line writes {@code Command received:} and that line.
         *
         * @param line The line is the trimmed command text.
         * @throws IOException The method throws this exception when the write fails.
         */
        private void onCommand(String line) throws IOException {
            if ("exit".equalsIgnoreCase(line) || "quit".equalsIgnoreCase(line)) {
                write("Goodbye.\r\n");
                callback.onExit(0);
                return;
            }

            write("Command received: " + line + "\r\n");
        }

        // =====================================================================
        //  Helpers
        // =====================================================================

        /**
         * The method reports whether the escape buffer looks complete.
         * A buffer shorter than {@code 2} is incomplete. A last character that is a letter or {@code ~} is complete. A two-character sequence whose second character is not {@code [} is complete. Every other buffer is incomplete.
         *
         * @param buf The buffer is the escape text collected so far.
         * @return The return value is true when the sequence should be handled now.
         */
        private boolean isCompleteEscapeSequence(StringBuilder buf) {
            // Very basic detection – good enough to get started
            if (buf.length() < 2) return false;

            // CSI sequences usually end with a letter
            char last = buf.charAt(buf.length() - 1);
            if (last >= 'A' && last <= 'Z') return true;
            if (last >= 'a' && last <= 'z') return true;
            if (last == '~') return true;          // many function keys

            // Simple single-character ESC sequences (rare)
            if (buf.length() == 2 && buf.charAt(1) != '[') return true;

            return false;
        }

        /**
         * The method writes the text as UTF-8 and flushes the output stream.
         *
         * @param text The text is the characters to send.
         * @throws IOException The method throws this exception when the write or the flush fails.
         */
        private void write(String text) throws IOException {
            out.write(text.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }
}
