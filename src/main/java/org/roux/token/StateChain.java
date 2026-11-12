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

package org.roux.token;

import java.util.ArrayList;
import java.util.List;

/**
 * The StateChain combines several {@link StateModifier} values.
 * The class is visible only inside this package.
 * A half stays when every modifier keeps it.
 * An empty chain keeps both halves.
 * A null entry in the source list is skipped.
 *
 * @aa-sdd PK-4137 docs/specs/java/org/aetheris/swing/swing.spec.md
 *
 * @author clayl (2026-11-02)
 */
final class StateChain implements StateModifier {

    private final ArrayList<StateModifier> modifiers = new ArrayList<StateModifier>();

    /**
     * The constructor copies the modifiers in list order.
     * A null list stores no modifiers.
     * A null entry is skipped.
     *
     * @param modifiers The modifiers are applied from first to last.
     */
    StateChain(List<StateModifier> modifiers) {
        if (modifiers == null) {
            return;
        }
        for (int i = 0; i < modifiers.size(); i++) {
            StateModifier one = modifiers.get(i);
            if (one != null) {
                this.modifiers.add(one);
            }
        }
    }

    /**
     * The method reports whether every modifier keeps the user half.
     * An empty chain returns true.
     *
     * @return The flag is true when the user half stays.
     */
    @Override
    public boolean keepUser() {
        for (int i = 0; i < modifiers.size(); i++) {
            if (!modifiers.get(i).keepUser()) {
                return false;
            }
        }
        return true;
    }

    /**
     * The method reports whether every modifier keeps the assistant half.
     * An empty chain returns true.
     *
     * @return The flag is true when the assistant half stays.
     */
    @Override
    public boolean keepAssistant() {
        for (int i = 0; i < modifiers.size(); i++) {
            if (!modifiers.get(i).keepAssistant()) {
                return false;
            }
        }
        return true;
    }
}
/*****************************************************************************************
 * End of Class: StateChain.java 110226
 ****************************************************************************************/
