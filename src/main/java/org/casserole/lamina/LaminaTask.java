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

package org.casserole.lamina;

// @aa-sdd L40 app-design.md
/**
 * The {@code LaminaTask} type is one named unit of work for a {@link Lane}.
 * A lane is one named pool of worker threads.
 * The task holds the lane, a name, and the {@code Runnable} body that the pool will run.
 * A {@code Runnable} is the Java object whose {@code run} method is that body.
 *
 * @author clayl (2026-08-30)
 */
// @aa-sdd PK-353 docs/specs/java/org/casserole/lamina/lamina.spec.md
// @aa-sdd PK-354 docs/specs/java/org/casserole/lamina/lamina.spec.md
public class LaminaTask {

    private final Lane lane;
    private final String name;
    private final Runnable work;

    /**
     * The constructor stores the lane, the task name, and the body.
     * If {@code lane} is {@code null}, the constructor throws {@code IllegalArgumentException} with the message {@code lane}.
     * If {@code name} is {@code null} or empty, the constructor throws {@code IllegalArgumentException} with the message {@code name}.
     * If {@code work} is {@code null}, the constructor throws {@code IllegalArgumentException} with the message {@code work}.
     *
     * @param lane The live {@link Lane} whose pool will run the body.
     * @param name The task name, which must contain at least one character.
     * @param work The body the pool will run.
     * @throws IllegalArgumentException The lane, the name, or the body was missing.
     */
    public LaminaTask(Lane lane, String name, Runnable work) {
        if (lane == null) {
            throw new IllegalArgumentException("lane");
        }
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("name");
        }
        if (work == null) {
            throw new IllegalArgumentException("work");
        }
        this.lane = lane;
        this.name = name;
        this.work = work;
    }

    /**
     * The {@code getLane} method returns the pool this task is assigned to.
     *
     * @return The {@link Lane} stored by the constructor.
     */
    public Lane getLane() {
        return lane;
    }

    /**
     * The {@code getName} method returns the task name.
     *
     * @return The name stored by the constructor.
     */
    public String getName() {
        return name;
    }

    /**
     * The {@code getWork} method returns the body the pool will run.
     *
     * @return The {@code Runnable} stored by the constructor.
     */
    public Runnable getWork() {
        return work;
    }
}
/*****************************************************************************************
 * End of Class: LaminaTask.java 083026
 ****************************************************************************************/
