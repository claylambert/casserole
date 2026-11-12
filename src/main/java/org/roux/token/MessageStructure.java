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
 * The MessageStructure turns one message into a single turn.
 * The message text becomes the user half.
 * The assistant half is empty.
 * An {@link IncludedModifier} keeps both of those halves.
 *
 * @aa-sdd PK-4134 docs/specs/java/org/aetheris/swing/swing.spec.md
 *
 * @author clayl (2026-11-02)
 */
public final class MessageStructure implements StructuralModifier {

    private final String text;

    /**
     * The constructor stores the message text.
     * A null text is stored as an empty string.
     *
     * @param text The text is the message body, or null.
     */
    public MessageStructure(String text) {
        this.text = text == null ? "" : text;
    }

    /**
     * The method returns one turn.
     * The user half is the stored text.
     * The assistant half is empty.
     *
     * @return The list contains that one turn.
     */
    @Override
    public List<Transcript.Turn> turns() {
        ArrayList<StateModifier> modifiers = new ArrayList<StateModifier>();
        modifiers.add(new IncludedModifier());
        return new OrdinaryStructure(text, "", modifiers).turns();
    }
}
/*****************************************************************************************
 * End of Class: MessageStructure.java 110226
 ****************************************************************************************/
