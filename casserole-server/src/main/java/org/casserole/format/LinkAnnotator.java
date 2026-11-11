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
 * The {@code LinkAnnotator} type marks {@code http} and {@code https} addresses so a capable terminal can open them.
 * Each accepted address is wrapped in a purple SGR sequence and an OSC 8 sequence.
 * SGR is the select-graphic-rendition sequence that sets terminal color.
 * OSC 8 is the terminal sequence that attaches a link to displayed text.
 * Text that already contains an OSC 8 opener is left unchanged.
 * An address that contains a carriage return or a line feed is left as ordinary text.
 * The type has no instances.
 *
 * @author clayl (2026-08-25)
 */
public final class LinkAnnotator {

    private static final String PURPLE = "\u001B[35m";
    private static final String RESET = "\u001B[0m";
    private static final String OSC8_OPEN_PREFIX = "\u001B]8;;";
    private static final char BEL = 0x07;

    /**
     * The constructor is private so a caller uses {@link #annotate}.
     */
    private LinkAnnotator() {
    }

    /**
     * The {@code annotate} method marks web addresses in text that already uses carriage-return line feeds.
     * A {@code null} or empty text returns the empty string.
     * If the text already contains the OSC 8 opener, the method returns the text unchanged.
     * {@link #indexOfHttp} finds the next {@code http://} or {@code https://}.
     * {@link #extendUrl} takes the run, and {@link #trimTrailingPunct} drops a trailing period, comma, semicolon, or closing bracket.
     * If that address contains a carriage return or a line feed, or if it is shorter than eight characters, the original run is copied without a link.
     * Otherwise the method writes purple, the OSC 8 opener, the address, BEL, the visible address, a closing OSC 8, and the reset sequence.
     * Punctuation removed from the address is written after the link.
     *
     * @param text The canvas text, already normalized to carriage-return line feeds.
     * @return The text with accepted addresses marked.
     */
    public static String annotate(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }
        if (text.indexOf(OSC8_OPEN_PREFIX) >= 0) {
            return text;
        }
        StringBuilder out = new StringBuilder(text.length() + 32);
        int i = 0;
        while (i < text.length()) {
            int http = indexOfHttp(text, i);
            if (http < 0) {
                out.append(text.substring(i));
                break;
            }
            out.append(text.substring(i, http));
            int end = extendUrl(text, http);
            String url = trimTrailingPunct(text.substring(http, end));
            if (url.indexOf('\r') >= 0 || url.indexOf('\n') >= 0 || url.length() < 8) {
                out.append(text.substring(http, end));
            } else {
                out.append(PURPLE);
                out.append(OSC8_OPEN_PREFIX);
                out.append(url);
                out.append(BEL);
                out.append(url);
                out.append(OSC8_OPEN_PREFIX);
                out.append(BEL);
                out.append(RESET);
            }
            i = http + url.length();
            if (i < end) {
                out.append(text.substring(i, end));
                i = end;
            }
        }
        return out.toString();
    }

    /**
     * The {@code indexOfHttp} method finds the next web address prefix.
     * The method searches for {@code https://} and for {@code http://} from {@code from}.
     * If only one prefix is present, that index is returned.
     * If both are present, the earlier index is returned.
     * If neither is present, the method returns {@code -1}.
     *
     * @param text The text being scanned.
     * @param from The index at which the scan starts.
     * @return The index of {@code http://} or {@code https://}, or {@code -1}.
     */
    private static int indexOfHttp(String text, int from) {
        int a = text.indexOf("https://", from);
        int b = text.indexOf("http://", from);
        if (a < 0) {
            return b;
        }
        if (b < 0) {
            return a;
        }
        return a < b ? a : b;
    }

    /**
     * The {@code extendUrl} method finds the end of one address run.
     * The run stops before a character whose code is {@code 32} or less.
     * The run also stops before {@code <}, {@code >}, a quotation mark, an apostrophe, or {@code )}.
     *
     * @param text The text being scanned.
     * @param start The index where the address starts.
     * @return The exclusive end index of the run.
     */
    private static int extendUrl(String text, int start) {
        int i = start;
        while (i < text.length()) {
            char c = text.charAt(i);
            if (c <= 32 || c == '<' || c == '>' || c == '"' || c == '\'' || c == ')') {
                break;
            }
            i++;
        }
        return i;
    }

    /**
     * The {@code trimTrailingPunct} method removes trailing punctuation from an address.
     * A trailing {@code .}, {@code ,}, {@code ;}, or {@code ]} is removed, and the removal repeats.
     * Any other ending is kept.
     *
     * @param url The raw address run.
     * @return The address without that trailing punctuation.
     */
    private static String trimTrailingPunct(String url) {
        int end = url.length();
        while (end > 0) {
            char c = url.charAt(end - 1);
            if (c == '.' || c == ',' || c == ';' || c == ']') {
                end--;
            } else {
                break;
            }
        }
        return url.substring(0, end);
    }
}
/*****************************************************************************************
 * End of Class: LinkAnnotator.java 082526
 ****************************************************************************************/
