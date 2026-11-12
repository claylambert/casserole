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

// @aa-sdd PK-0560 docs/specs/java/org/casserole/format/format.spec.md
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
