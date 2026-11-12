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

package org.casserole.app;

import org.casserole.ssh.shell.SshServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

// @aa-sdd PK-0541 docs/specs/java/org/casserole/casserole.spec.md
/**
 * The {@code InitRunner} type starts services that Spring does not start by itself.
 * Spring is the application framework that calls {@link #run(ApplicationArguments)} after the context is ready.
 * The type starts the SSH chat listener.
 * SSH is the secure-shell protocol a chat client uses to connect.
 *
 * @author clayl (2026-08-24)
 */
@Component
public class InitRunner implements ApplicationRunner {

    private final SshServer sshServer;

    /**
     * The constructor stores the SSH chat server that {@link #run(ApplicationArguments)} starts.
     * The server is not started in the constructor.
     *
     * @param sshServer The {@link SshServer} chat listener, still stopped.
     */
    public InitRunner(SshServer sshServer) {
        this.sshServer = sshServer;
    }

    /**
     * The {@code run} method starts the SSH chat server.
     * Spring calls this method after the application context is ready.
     * The {@code args} value is the set of application arguments Spring collected.
     * The method does not read those arguments.
     *
     * @param args The application arguments Spring passed in.
     */
    @Override
    public void run(ApplicationArguments args) {
        sshServer.start();
    }
}
/*****************************************************************************************
 * End of Class: InitRunner.java 082426
 ****************************************************************************************/
