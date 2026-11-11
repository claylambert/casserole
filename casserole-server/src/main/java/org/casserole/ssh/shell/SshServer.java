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

import java.nio.file.Paths;
import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.apache.sshd.common.FactoryManager;
import org.apache.sshd.common.io.IoServiceFactory;
import org.apache.sshd.common.io.nio2.Nio2ServiceFactory;
import org.apache.sshd.common.io.nio2.Nio2ServiceFactoryFactory;
import org.apache.sshd.common.util.threads.CloseableExecutorService;
import org.apache.sshd.core.CoreModuleProperties;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.casserole.config.CasseroleProperties;
import org.casserole.config.UserAccounts;
import org.casserole.lamina.Lamina;
import org.casserole.lamina.LaminaHandle;
import org.casserole.lamina.LaminaTask;
import org.casserole.lamina.Lane;
import org.casserole.log.CasseroleLog;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

/**
 * The {@code SshServer} class is the Casserole SSH listener.
 * Apache MINA SSHD is the library that accepts the password, binds the port, and loads the host key from the casserole settings. Each connection asks {@link ChatShellFactory} for a shell. The listen loop runs on the SSH input-output lane.
 *
 * The bean does not start itself. {@link org.casserole.app.InitRunner} calls {@link #start}. {@link org.casserole.lamina.Lamina} receives the listen task from that call. A stop lets the failed accept land before the group executor closes.
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 *
 * @author clayl (2026-08-24)
 */
@Component
public class SshServer {

    private static final Logger LOG = CasseroleLog.get(SshServer.class);

    /**
     * The value is how long to wait so a failed accept can land after the acceptor closes.
     * Windows IOCP delivers that failure after the close. Zulu 26 runs that delivery on an {@code InnocuousThread}. The group must still be open, or {@code NoCloseExecutor} throws and the drain returns at once. The wait is {@code 1000} milliseconds.
     */
    private static final long ACCEPT_SETTLE_MS = 1000L;

    /**
     * The value is how long the group stays open so in-flight NIO work can finish.
     * The NIO2 factory waits this long inside {@code awaitTermination}. On this JDK that call returns immediately. The drain stays here while the executor still accepts the accept callback. The wait is {@code 5000} milliseconds.
     */
    private static final long GROUP_GRACE_MS = 5000L;

    private final CasseroleProperties properties;
    private final ChatShellFactory shellFactory;
    private final UserAccounts userAccounts;
    private final Lamina lamina;
    private final Object listenLock;
    private org.apache.sshd.server.SshServer sshd;
    private volatile boolean running;
    private LaminaHandle listenHandle;

    /**
     * The constructor stores the settings, the shell factory, the accounts, and the thread model.
     *
     * @param properties The properties are the casserole SSH settings.
     * @param shellFactory The shell factory builds one chat shell per connection.
     * @param userAccounts The accounts are the password rows.
     * @param lamina The lamina is the server thread model.
     */
    public SshServer(
            CasseroleProperties properties,
            ChatShellFactory shellFactory,
            UserAccounts userAccounts,
            Lamina lamina) {
        this.properties = properties;
        this.shellFactory = shellFactory;
        this.userAccounts = userAccounts;
        this.lamina = lamina;
        this.listenLock = new Object();
    }

    /**
     * The method submits the MINA listen loop onto the SSH input-output lane.
     * A listen task that is already running returns without a second submit.
     */
    public void start() {
        if (listenHandle != null && !listenHandle.isDone()) {
            return;
        }
        /**
         * The task runs {@link #listenLoop} on the SSH input-output lane.
         */
        LaminaTask task = new LaminaTask(Lane.SSH_IO, "casserole-sshd", new Runnable() {
            // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
            // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
            // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
            // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
            // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
            // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
            // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
            // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
            // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
            @Override
            public void run() {
                listenLoop();
            }
        });
        listenHandle = lamina.submit(task);
    }

    /**
     * The method binds the listener, accepts passwords, and waits until {@link #shutdown} clears the running flag.
     * The port, the idle timeout, and the host-key path come from the casserole SSH settings. A failed password is reported to {@link SshIoTrace}. The shell factory is {@link ChatShellFactory}.
     *
     * After start, the method logs a banner and prints that same banner. The loop then waits one second at a time. An interrupt clears the running flag. Any other failure is logged and printed.
     */
    private void listenLoop() {
        try {
            CasseroleProperties.Ssh ssh = properties.getSsh();
            sshd = org.apache.sshd.server.SshServer.setUpDefaultServer();
            // @aa-sdd PK-0511 docs/specs/java/org/casserole/casserole.spec.md
            sshd.setIoServiceFactoryFactory(new QuietNioFactory());
            sshd.setPort(ssh.getPort());
            applyIdleTimeout(sshd, ssh.getIdleTimeoutMs());
            sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(Paths.get(ssh.getHostKey())));
            /**
             * The authenticator asks {@link UserAccounts} whether the name and password match.
             * A refusal is reported to {@link SshIoTrace} before the method returns false.
             */
            sshd.setPasswordAuthenticator(new PasswordAuthenticator() {
                // @aa-sdd PK-0508 docs/specs/java/org/casserole/casserole.spec.md
                @Override
                public boolean authenticate(String username, String password, ServerSession session) {
                    boolean accepted = userAccounts.authenticate(username, password);
                    if (!accepted) {
                        SshIoTrace.authReject(session, username);
                    }
                    return accepted;
                }
            });
            sshd.setShellFactory(shellFactory);
            // @aa-sdd PK-0509 docs/specs/java/org/casserole/casserole.spec.md
            SshIoTrace.attach(sshd);
            sshd.start();
            running = true;
            String banner = "Casserole SSH chat started on port " + sshd.getPort()
                    + "  Auth: " + userAccounts.getSource() + " (" + userAccounts.getEntries().size()
                    + " user(s))"
                    + "  Connect with: ssh -p " + sshd.getPort() + " " + userAccounts.firstName()
                    + "@localhost";
            LOG.info(banner);
            System.out.println(banner);
            while (running) {
                try {
                    synchronized (listenLock) {
                        listenLock.wait(1000);
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
            }
        } catch (Exception ex) {
            LOG.error("SSH listen failed", ex);
            ex.printStackTrace();
        }
    }

    /**
     * The method replaces the listener idle timeout.
     * The built-in idle timeout is {@code 600} seconds unless this method stores another duration. A value of {@code 0} or less stores a zero duration, which means the timeout is not a positive wait. A positive value stores that many milliseconds.
     * //
     *
     * @param server The server is the listener being configured.
     * @param idleTimeoutMs The timeout is the idle wait in milliseconds. {@code 0} means never time out.
     */
    private void applyIdleTimeout(org.apache.sshd.server.SshServer server, long idleTimeoutMs) {
        Duration idle;
        if (idleTimeoutMs <= 0L) {
            idle = Duration.ZERO;
        } else {
            idle = Duration.ofMillis(idleTimeoutMs);
        }
        CoreModuleProperties.IDLE_TIMEOUT.set(server, idle);
    }

    /**
     * The method stops MINA and then releases the listen task.
     * The group waits inside {@code sshd.stop()}. The listen task stays until that wait returns. A stop failure is logged. The method logs when the stop begins and when it ends.
     */
    @PreDestroy
    public void shutdown() {
        LOG.info("SSH listener stopping");
        running = false;
        synchronized (listenLock) {
            listenLock.notifyAll();
        }
        if (sshd != null) {
            try {
                sshd.stop();
            } catch (Exception ex) {
                LOG.error("SSH stop failed", ex);
            }
        }
        if (listenHandle != null) {
            listenHandle.cancel(true);
        }
        LOG.info("SSH listener stopped");
    }

    /**
     * The method waits so the Windows accept failure can land before the group executor closes.
     * The wait is {@link #ACCEPT_SETTLE_MS}.
     */
    private static void settleAccept() {
        pause(ACCEPT_SETTLE_MS);
    }

    /**
     * The method holds the group open so in-flight NIO work can finish.
     * The wait is {@link #GROUP_GRACE_MS}.
     */
    private static void drainGroup() {
        pause(GROUP_GRACE_MS);
    }

    /**
     * The method sleeps for the given number of milliseconds.
     * An interrupt sets the interrupt flag again and returns.
     *
     * @param millis The count is how long to hold the executor open.
     */
    private static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    // @aa-sdd PK-0512 docs/specs/java/org/casserole/casserole.spec.md
    /**
     * The {@code QuietNioFactory} class builds the NIO2 service used by the listener.
     * The service settles the accept, and then the stock close runs.
     */
    private static final class QuietNioFactory extends Nio2ServiceFactoryFactory {

        /**
         * The method returns a {@link QuietNioService} for this manager.
         * The service uses two new executors from the factory.
         *
         * @param manager The manager is the factory manager for the listener.
         * @return The return value is the input-output service.
         */
        @Override
        public IoServiceFactory create(FactoryManager manager) {
            return new QuietNioService(manager, newExecutor(), newExecutor());
        }
    }

    // @aa-sdd PK-0513 docs/specs/java/org/casserole/casserole.spec.md
    /**
     * The {@code QuietNioService} class is the stock NIO2 service with a pause before close.
     * The accept callback still has a live executor during that pause.
     */
    private static final class QuietNioService extends Nio2ServiceFactory {

        /**
         * The constructor passes the manager and the two executors to the stock service.
         *
         * @param manager The manager is the factory manager for the listener.
         * @param service The service executor runs the input-output work.
         * @param resume The resume executor runs resumed input-output work.
         */
        private QuietNioService(
                FactoryManager manager,
                CloseableExecutorService service,
                CloseableExecutorService resume) {
            super(manager, service, resume);
        }

        /**
         * The method logs the settle, waits for the accept, drains the group, and then runs the stock close.
         */
        @Override
        protected void doCloseImmediately() {
            LOG.info("SSH accept settling before the group stops");
            settleAccept();
            LOG.info("SSH group draining");
            drainGroup();
            super.doCloseImmediately();
        }
    }
}
/*****************************************************************************************
 * End of Class: SshServer.java 083026
 ****************************************************************************************/
