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

package org.casserole.lamina.spring;

import org.casserole.config.CasseroleProperties;
import org.casserole.lamina.Lamina;
import org.casserole.lamina.LaminaEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PreDestroy;

// @aa-sdd PK-0540 docs/specs/java/org/casserole/casserole.spec.md
/**
 * The {@code LaminaConfiguration} type is the Spring Boot wiring for {@link Lamina}.
 * Spring Boot is the framework that calls {@link #lamina(CasseroleProperties)} while the application starts.
 * {@link LaminaEngine} itself has no Spring types.
 * {@link #shutdown()} runs when the application context stops.
 *
 * @author clayl (2026-08-30)
 */
@Configuration
public class LaminaConfiguration {

    private LaminaEngine engine;

    /**
     * The {@code lamina} method builds the single {@link LaminaEngine} for this process.
     * The {@code properties} value is the bound {@code casserole.*} configuration, including {@code lamina.metrics}.
     * If {@code properties.getLamina()} is {@code null}, metrics stay off.
     * If the lamina section is present, metrics follow {@code isMetrics()} on that section.
     * The method stores the engine so {@link #shutdown()} can stop it later.
     *
     * @param properties The bound {@link CasseroleProperties}, including the lamina section when that section is present.
     * @return The process-wide {@link Lamina} engine.
     */
    @Bean
    public Lamina lamina(CasseroleProperties properties) {
        boolean metrics = false;
        if (properties.getLamina() != null) {
            metrics = properties.getLamina().isMetrics();
        }
        engine = new LaminaEngine(metrics);
        return engine;
    }

    /**
     * The {@code shutdown} method stops the engine when Spring Boot stops the application.
     * If {@link #lamina(CasseroleProperties)} has not stored an engine, the method does nothing.
     * If an engine is stored, the method calls {@link LaminaEngine#shutdown()}.
     */
    @PreDestroy
    public void shutdown() {
        if (engine != null) {
            engine.shutdown();
        }
    }
}
/*****************************************************************************************
 * End of Class: LaminaConfiguration.java 083026
 ****************************************************************************************/
