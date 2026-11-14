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
 * The OrdinaryStructure turns one user text and one assistant text into a turn.
 * The turn is added when a {@link StateChain} keeps either half.
 * A kept half whose text is empty still adds that turn.
 * Both halves staying out returns an empty list.
 *
 * // @aa-sdd PK-1105 docs/specs/common/formal-plan.spec.md
 *
 * @author clayl (2026-11-02)
 */
// @aa-sdd PK-1326 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-554 docs/specs/java/org/roux/token/token.spec.md
public final class OrdinaryStructure implements StructuralModifier {

    private final String user;
    private final String assistant;
    private final List<StateModifier> modifiers;

    /**
     * The constructor stores the user text, the assistant text, and the modifiers.
     * A null user text is stored as an empty string.
     * A null assistant text is stored as an empty string.
     *
     * @param user The user text is the user half.
     * @param assistant The assistant text is the assistant half.
     * @param modifiers The modifiers are the {@link StateModifier} list passed to the chain.
     */
    public OrdinaryStructure(String user, String assistant, List<StateModifier> modifiers) {
        this.user = user == null ? "" : user;
        this.assistant = assistant == null ? "" : assistant;
        this.modifiers = modifiers;
    }

    /**
     * The method returns one turn when either half stays.
     * A dropped half is stored as an empty string on that turn.
     * Both halves staying out returns an empty list.
     *
     * @return The list contains one turn, or it is empty.
     */
    @Override
    public List<Transcript.Turn> turns() {
        ArrayList<Transcript.Turn> out = new ArrayList<Transcript.Turn>();
        StateChain chain = new StateChain(modifiers);
        boolean keepUser = chain.keepUser();
        boolean keepAssistant = chain.keepAssistant();
        if (!keepUser && !keepAssistant) {
            return out;
        }
        String userHalf = keepUser ? user : "";
        String assistantHalf = keepAssistant ? assistant : "";
        out.add(new Transcript.Turn(userHalf, assistantHalf));
        return out;
    }
}
/*****************************************************************************************
 * End of Class: OrdinaryStructure.java 110226
 ****************************************************************************************/
