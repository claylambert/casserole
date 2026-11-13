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

package org.casserole;

import org.casserole.config.CasseroleProperties;
import org.roux.RouxProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

// @aa-sdd L40 app-design.md
@SpringBootApplication
@ComponentScan(basePackages = { "org.casserole", "org.roux", "org.aetheris" })
@EnableConfigurationProperties({ CasseroleProperties.class, RouxProperties.class })
/**
 * The {@code CasseroleApplication} type is the main entry point of this program.
 * {@link #main(String[])} hands the process to Spring Boot.
 * Spring Boot is the framework that builds the application context and starts the process.
 * The component scan covers the packages {@code org.casserole}, {@code org.roux}, and {@code org.aetheris}.
 * Configuration binding is enabled for {@link CasseroleProperties} and for {@code RouxProperties}.
 * {@code RouxProperties} is the configuration type supplied by the Roux library.
 *
 * @author clayl (2026-08-17)
 */
// @aa-sdd PK-741 docs/specs/common/stages.spec.md
// @aa-sdd PK-1311 docs/specs/java/org/casserole/casserole.spec.md
// @aa-sdd PK-718 docs/specs/common/server.spec.md
public class CasseroleApplication {

    /**
     * The {@code main} method starts this program.
     * The {@code args} array is the command-line text passed to the process.
     * Those arguments are handed to {@code SpringApplication.run} together with this class.
     *
     * @param args The command-line arguments for this process.
     */
    public static void main(String[] args) {
        SpringApplication.run(CasseroleApplication.class, args);
    }
}
/*****************************************************************************************
 * End of Class: CasseroleApplication.java 081726 10:09:43 PM
 ****************************************************************************************/
