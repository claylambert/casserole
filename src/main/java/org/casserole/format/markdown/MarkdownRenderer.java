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

import org.casserole.format.FormatContext;
import org.casserole.format.FormatRenderer;
import org.casserole.format.FormatResult;
import org.casserole.format.WordWrap;
import org.commonmark.node.BlockQuote;
import org.commonmark.node.BulletList;
import org.commonmark.node.Code;
import org.commonmark.node.Emphasis;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.HardLineBreak;
import org.commonmark.node.Heading;
import org.commonmark.node.Link;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;

// @aa-sdd L40 aetheris-stage-2-finessing.001.md
/**
 * The {@code MarkdownRenderer} type walks a CommonMark tree and writes plain VT100 text.
 * CommonMark is the markdown dialect this renderer parses.
 * VT100 is the terminal control set the chat shell paints with.
 * Emphasis markers are dropped, and the text inside them is kept.
 * Headings, lists, quotes, and fenced code keep their structure.
 * Prose is wrapped to the column width.
 *
 * @author clayl (2026-08-25)
 */
public class MarkdownRenderer implements FormatRenderer {

    private final Parser parser;

    /**
     * The constructor builds the default CommonMark parser.
     */
    public MarkdownRenderer() {
        this.parser = Parser.builder().build();
    }

    /**
     * The {@code render} method parses the reply and writes each top-level block.
     * If {@code ctx} is {@code null}, the wrap width is {@code 80}.
     * Otherwise the wrap width is {@link FormatContext#getColumns}.
     * {@link #emitBlock} writes each top-level child in order.
     * The result has body color on.
     *
     * @param raw The original assistant text.
     * @param ctx The render settings, or {@code null} to wrap at {@code 80} columns.
     * @return The plain VT100 text.
     */
    @Override
    public FormatResult render(String raw, FormatContext ctx) {
        int width = ctx == null ? 80 : ctx.getColumns();
        Node doc = parser.parse(raw);
        StringBuilder out = new StringBuilder();
        Node child = doc.getFirstChild();
        while (child != null) {
            emitBlock(child, out, width);
            child = child.getNext();
        }
        return new FormatResult(out.toString(), true);
    }

    /**
     * The {@code emitBlock} method writes one top-level block.
     * A heading or a paragraph is flattened by {@link #inlineText}, wrapped by {@link WordWrap#wrap}, and followed by two line feeds.
     * A fenced code block copies its literal, adds a line feed when the literal does not already end with one, and then adds another line feed.
     * A {@code null} code literal writes only the extra line feed.
     * A bullet list or an ordered list writes each item as a hanging hyphen.
     * The item body is trimmed and wrapped to {@code width} minus two.
     * The first wrapped line starts with {@code - }, and later lines start with two spaces.
     * A blank line follows the list.
     * A block quote wraps to {@code width} minus two and prefixes every line with {@code > }, then adds a blank line.
     * Any other block writes nothing.
     *
     * @param node The block to write.
     * @param out The destination.
     * @param width The column width.
     */
    private void emitBlock(Node node, StringBuilder out, int width) {
        if (node instanceof Heading) {
            String text = inlineText(node);
            out.append(WordWrap.wrap(text, width)).append('\n').append('\n');
            return;
        }
        if (node instanceof Paragraph) {
            String text = inlineText(node);
            out.append(WordWrap.wrap(text, width)).append('\n').append('\n');
            return;
        }
        if (node instanceof FencedCodeBlock) {
            FencedCodeBlock code = (FencedCodeBlock) node;
            String literal = code.getLiteral();
            if (literal != null) {
                out.append(literal);
                if (!literal.endsWith("\n")) {
                    out.append('\n');
                }
            }
            out.append('\n');
            return;
        }
        if (node instanceof BulletList || node instanceof OrderedList) {
            Node item = node.getFirstChild();
            while (item != null) {
                if (item instanceof ListItem) {
                    String body = inlineText(item).trim();
                    String wrapped = WordWrap.wrap(body, width - 2);
                    String[] parts = wrapped.split("\n", -1);
                    for (int p = 0; p < parts.length; p++) {
                        if (p == 0) {
                            out.append("- ").append(parts[p]).append('\n');
                        } else {
                            out.append("  ").append(parts[p]).append('\n');
                        }
                    }
                }
                item = item.getNext();
            }
            out.append('\n');
            return;
        }
        if (node instanceof BlockQuote) {
            String text = inlineText(node);
            String wrapped = WordWrap.wrap(text, width - 2);
            String[] parts = wrapped.split("\n", -1);
            for (int p = 0; p < parts.length; p++) {
                out.append("> ").append(parts[p]).append('\n');
            }
            out.append('\n');
        }
    }

    /**
     * The {@code inlineText} method flattens one subtree to plain text.
     * Emphasis markers are not copied.
     * A link contributes its label, not its destination.
     *
     * @param node The subtree to flatten.
     * @return The plain text collected by {@link #collectInline}.
     */
    private String inlineText(Node node) {
        StringBuilder sb = new StringBuilder();
        collectInline(node, sb);
        return sb.toString();
    }

    /**
     * The {@code collectInline} method appends the plain text of one subtree.
     * If the node has no children, a text node or a code node appends its literal, and a soft or hard line break appends one space.
     * Any other childless node appends nothing.
     * A text child appends its literal.
     * A code child appends its literal.
     * A soft or hard line break appends one space.
     * A link, emphasis, strong emphasis, paragraph, list item, or any other child is visited the same way.
     * The link destination is not appended.
     *
     * @param node The subtree to walk.
     * @param sb The destination for the plain text.
     */
    private void collectInline(Node node, StringBuilder sb) {
        Node child = node.getFirstChild();
        if (child == null) {
            if (node instanceof Text) {
                sb.append(((Text) node).getLiteral());
            } else if (node instanceof Code) {
                sb.append(((Code) node).getLiteral());
            } else if (node instanceof SoftLineBreak || node instanceof HardLineBreak) {
                sb.append(' ');
            }
            return;
        }
        while (child != null) {
            if (child instanceof Text) {
                sb.append(((Text) child).getLiteral());
            } else if (child instanceof Code) {
                sb.append(((Code) child).getLiteral());
            } else if (child instanceof SoftLineBreak || child instanceof HardLineBreak) {
                sb.append(' ');
            } else if (child instanceof Link) {
                collectInline(child, sb);
            } else if (child instanceof Emphasis || child instanceof StrongEmphasis) {
                collectInline(child, sb);
            } else if (child instanceof Paragraph || child instanceof ListItem) {
                collectInline(child, sb);
            } else {
                collectInline(child, sb);
            }
            child = child.getNext();
        }
    }
}
/*****************************************************************************************
 * End of Class: MarkdownRenderer.java 082526
 ****************************************************************************************/
