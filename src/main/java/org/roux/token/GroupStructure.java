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
 * The GroupStructure joins several prompts into one turn.
 * Each prompt has its own list of {@link StateModifier} values.
 * A prompt stays only when that list keeps the user half.
 * Kept prompts are joined with a newline.
 * An empty kept prompt adds no characters.
 * The reply stays only when the merge modifiers keep the assistant half.
 * No kept half returns an empty list.
 *
 * // @aa-sdd PK-1105 docs/specs/common/formal-plan.spec.md
 *
 * @author clayl (2026-11-02)
 */
// @aa-sdd PK-1326 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-545 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-554 docs/specs/java/org/roux/token/token.spec.md
public final class GroupStructure implements StructuralModifier {

    private final List<String> prompts;
    private final List<List<StateModifier>> chains;
    private final String reply;
    private final List<StateModifier> mergeChain;

    /**
     * The constructor stores the prompts, the modifier lists, the reply, and the merge modifiers.
     * A null reply is stored as an empty string.
     *
     * @param prompts The prompts are the member texts, in the order they should join.
     * @param chains The chains are one modifier list per prompt, in that same order.
     * @param reply The reply is the assistant half.
     * @param mergeChain The merge chain is the modifier list for the reply.
     */
    public GroupStructure(List<String> prompts, List<List<StateModifier>> chains, String reply,
            List<StateModifier> mergeChain) {
        this.prompts = prompts;
        this.chains = chains;
        this.reply = reply == null ? "" : reply;
        this.mergeChain = mergeChain;
    }

    /**
     * The method returns one turn, or an empty list when no half stays.
     * A kept user half stays even when every kept prompt text is empty.
     * A missing modifier list is treated as an {@link ExcludedModifier}.
     * That missing list drops the prompt.
     *
     * @return The list contains one turn, or it is empty.
     */
    @Override
    public List<Transcript.Turn> turns() {
        ArrayList<Transcript.Turn> out = new ArrayList<Transcript.Turn>();
        boolean keepUser = false;
        StringBuilder join = new StringBuilder();
        int promptCount = prompts == null ? 0 : prompts.size();
        for (int i = 0; i < promptCount; i++) {
            if (!chainAt(i).keepUser()) {
                continue;
            }
            keepUser = true;
            String text = prompts.get(i);
            if (text == null || text.length() == 0) {
                continue;
            }
            if (join.length() > 0) {
                join.append('\n');
            }
            join.append(text);
        }
        boolean keepAssistant = new StateChain(mergeChain).keepAssistant();
        if (!keepUser && !keepAssistant) {
            return out;
        }
        String userHalf = keepUser ? join.toString() : "";
        String assistantHalf = keepAssistant ? reply : "";
        out.add(new Transcript.Turn(userHalf, assistantHalf));
        return out;
    }

    /**
     * The method builds the chain for one prompt.
     * A missing list becomes a chain that holds an {@link ExcludedModifier}.
     *
     * @param index The index is the prompt position.
     * @return The chain is the modifiers for that prompt.
     */
    private StateChain chainAt(int index) {
        if (chains == null || index < 0 || index >= chains.size()) {
            ArrayList<StateModifier> excluded = new ArrayList<StateModifier>();
            excluded.add(new ExcludedModifier());
            return new StateChain(excluded);
        }
        return new StateChain(chains.get(index));
    }
}
/*****************************************************************************************
 * End of Class: GroupStructure.java 110226
 ****************************************************************************************/
