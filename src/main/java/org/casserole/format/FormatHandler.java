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

package org.casserole.format;

// @aa-sdd L40 aetheris-stage-2-finessing.001.md
/**
 * The {@code FormatHandler} type is one named detector and renderer pair.
 * {@link FormatRouter} tries the pair in registration order.
 * The short ids used by the factory are {@code json}, {@code fence}, {@code table}, {@code list}, and {@code markdown}.
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-350 docs/specs/java/org/casserole/format/format.spec.md
public class FormatHandler {

    private final String name;
    private final FormatDetector detector;
    private final FormatRenderer renderer;

    /**
     * The constructor stores the short id, the match rule, and the paint rule.
     *
     * @param name The short id, such as {@code json}, {@code fence}, {@code table}, {@code list}, or {@code markdown}.
     * @param detector The match rule.
     * @param renderer The paint rule.
     */
    public FormatHandler(String name, FormatDetector detector, FormatRenderer renderer) {
        this.name = name;
        this.detector = detector;
        this.renderer = renderer;
    }

    /**
     * The {@code getName} method returns the short id.
     *
     * @return The registry name.
     */
    public String getName() {
        return name;
    }

    /**
     * The {@code getDetector} method returns the match rule.
     *
     * @return The {@link FormatDetector} stored by the constructor.
     */
    public FormatDetector getDetector() {
        return detector;
    }

    /**
     * The {@code getRenderer} method returns the paint rule.
     *
     * @return The {@link FormatRenderer} stored by the constructor.
     */
    public FormatRenderer getRenderer() {
        return renderer;
    }
}
/*****************************************************************************************
 * End of Class: FormatHandler.java 082526
 ****************************************************************************************/
