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

package org.aetheris.lcarsgraph;

/**
 * One turn is the card the vault already stores and the words that turn holds.
 * The label is the protocol name. The card is the vault id.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1477 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
public final class GraphTurn {

    private final String card;
    private final String label;
    private final String words;

    /**
     * The constructor keeps the card, the protocol label, and the words.
     *
     * @param card The card is the vault id.
     * @param label The label is the protocol name, such as turn-1.
     * @param words The words are the turn text on one line.
     */
    public GraphTurn(String card, String label, String words) {
        this.card = card == null ? "" : card;
        this.label = label == null ? "" : label;
        this.words = words == null ? "" : words;
    }

    /**
     * The method returns the vault id.
     *
     * @return The card.
     */
    public String card() {
        return card;
    }

    /**
     * The method returns the protocol name.
     *
     * @return The label.
     */
    public String label() {
        return label;
    }

    /**
     * The method returns the turn text.
     *
     * @return The words.
     */
    public String words() {
        return words;
    }
}
