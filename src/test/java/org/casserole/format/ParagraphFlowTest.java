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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * The class checks {@link ParagraphFlow} and the route through {@link FormatRouter}.
 * A paragraph is one run of text.
 * A blank line stays the break between paragraphs.
 * // @aa-sdd PK-1075 docs/specs/common/formal-plan.spec.md
 *
 * @author clayl (2026-11-04)
 */
// @aa-sdd GK-1594 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-774 docs/specs/common/stages.spec.md
// @aa-sdd PK-696 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-654 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-655 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-774 docs/specs/common/stages.spec.md
// @aa-sdd PK-1075 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1075 docs/specs/common/formal-plan.spec.md
class ParagraphFlowTest {

    /**
     * The method arranges {@code one.} and {@code two} separated by a line break.
     * The method calls {@link ParagraphFlow#flow}.
     * A line feed becomes one space.
     * A carriage return followed by a line feed becomes that same space.
     *
     * @return The method returns nothing.
     */
    @Test
    void aSingleLineFeedBecomesOneSpace() {
        assertEquals("one. two", ParagraphFlow.flow("one.\ntwo"));
        assertEquals("one. two", ParagraphFlow.flow("one.\r\ntwo"));
    }

    /**
     * The method arranges {@code one.} and {@code two} separated by a blank line.
     * The method calls {@link ParagraphFlow#flow}.
     * The result is {@code one.}, a blank line, and {@code two}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aBlankLineStaysTheBreak() {
        assertEquals("one.\n\ntwo", ParagraphFlow.flow("one.\n\ntwo"));
    }

    /**
     * The method arranges {@code one.} and a line that already starts with a space before {@code Two}.
     * The method calls {@link ParagraphFlow#flow}.
     * The result is {@code one. Two}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aSpaceAlreadyThereIsNotDoubled() {
        assertEquals("one. Two", ParagraphFlow.flow("one.\n Two"));
    }

    /**
     * The method arranges {@code One.} and {@code Two.} with a trailing line feed.
     * The method calls {@link ParagraphFlow#flow}.
     * The result is {@code One. Two.}
     *
     * @return The method returns nothing.
     */
    @Test
    void aTrailingLineFeedAddsNoSpace() {
        assertEquals("One. Two.", ParagraphFlow.flow("One.\nTwo.\n"));
    }

    /**
     * The method arranges {@code one.} and {@code two stays} at a width of {@code 8}.
     * The method calls {@link WordWrap#wrap}.
     * The result breaks the joined run into {@code one. }, {@code two }, and {@code stays}.
     *
     * @return The method returns nothing.
     */
    @Test
    void wordWrapHardWrapsTheJoinedRun() {
        assertEquals("one. \ntwo \nstays", WordWrap.wrap("one.\ntwo stays", 8));
    }

    /**
     * The method arranges {@code One.}, {@code Two.}, a blank line, and {@code Three.}
     * The method calls {@link FormatRouter#format} with a new {@link FormatContext}.
     * The text joins the first two sentences with a space.
     * The blank line becomes a carriage return, a line feed, another carriage return, and a line feed.
     * The result ends with a carriage return and a line feed after {@code Three.}
     *
     * @return The method returns nothing.
     */
    @Test
    void identityFlowsBeforeCrlf() {
        FormatResult result = new FormatRouter().format("One.\nTwo.\n\nThree.", new FormatContext());
        assertEquals("One. Two.\r\n\r\nThree.\r\n", result.getText());
    }
}
/*****************************************************************************************
 * End of Class: ParagraphFlowTest.java 110426
 ****************************************************************************************/
