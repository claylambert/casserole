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

// @aa-sdd L40 aetheris-stage-2-finessing.001.md
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
// @aa-sdd PK-346 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-350 docs/specs/java/org/casserole/format/format.spec.md
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
