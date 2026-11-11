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

package org.casserole.format.markdown;

import org.casserole.format.FormatDetector;
import org.commonmark.node.BlockQuote;
import org.commonmark.node.BulletList;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Heading;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.OrderedList;
import org.commonmark.parser.Parser;

// @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
// @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/**
 * The {@code MarkdownDetector} type matches CommonMark structure in a whole reply.
 * CommonMark is the markdown dialect this detector parses.
 * A heading, a fenced code block, or a block quote is structure.
 * A bullet list or an ordered list is structure only when the reply has at least two list items.
 * A single paragraph does not match, even when that paragraph contains emphasis.
 *
 * @author clayl (2026-08-25)
 */
public class MarkdownDetector implements FormatDetector {

    private final Parser parser;

    /**
     * The constructor builds the default CommonMark parser.
     */
    public MarkdownDetector() {
        this.parser = Parser.builder().build();
    }

    /**
     * The {@code matches} method parses the reply and looks for structure.
     * A {@code null} reply or a blank reply returns {@code false}.
     * A heading, a fenced code block, or a block quote sets the structure flag.
     * List items are counted across every bullet list and ordered list.
     * Two or more list items also set the structure flag.
     * The method returns {@code true} only when structure was seen and either the item count is at least two or {@link #hasHeadingFenceOrQuote} is true.
     * A list of one item with no heading, fence, or quote returns {@code false}.
     *
     * @param raw The full assistant text.
     * @return {@code true} when the reply has a heading, a fence, a quote, or at least two list items.
     */
    @Override
    public boolean matches(String raw) {
        if (raw == null || raw.trim().length() == 0) {
            return false;
        }
        Node doc = parser.parse(raw);
        boolean structure = false;
        int listItems = 0;
        Node child = doc.getFirstChild();
        while (child != null) {
            if (child instanceof Heading) {
                structure = true;
            } else if (child instanceof FencedCodeBlock) {
                structure = true;
            } else if (child instanceof BlockQuote) {
                structure = true;
            } else if (child instanceof BulletList || child instanceof OrderedList) {
                listItems += countItems(child);
                if (listItems >= 2) {
                    structure = true;
                }
            }
            child = child.getNext();
        }
        return structure && (listItems >= 2 || hasHeadingFenceOrQuote(doc));
    }

    /**
     * The {@code hasHeadingFenceOrQuote} method looks at the top-level children of the document.
     * The method returns {@code true} when one child is a heading, a fenced code block, or a block quote.
     * Nested nodes are not visited.
     *
     * @param doc The parsed document.
     * @return {@code true} when a top-level heading, fence, or quote is present.
     */
    private boolean hasHeadingFenceOrQuote(Node doc) {
        Node child = doc.getFirstChild();
        while (child != null) {
            if (child instanceof Heading || child instanceof FencedCodeBlock || child instanceof BlockQuote) {
                return true;
            }
            child = child.getNext();
        }
        return false;
    }

    /**
     * The {@code countItems} method counts the direct children that are list items.
     * A child that is not a list item is not counted.
     *
     * @param list The bullet list or ordered list node.
     * @return The number of direct list-item children.
     */
    private int countItems(Node list) {
        int n = 0;
        Node item = list.getFirstChild();
        while (item != null) {
            if (item instanceof ListItem) {
                n++;
            }
            item = item.getNext();
        }
        return n;
    }
}
/*****************************************************************************************
 * End of Class: MarkdownDetector.java 082526
 ****************************************************************************************/
