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

package org.aetheris.swing;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The {@code ThinkPaneManager} class is the gateway to a thinking pane.
 * A caller asks with a context key and receives that context's pane.
 * The same key returns the same instance for the session.
 * One canvas has one pane, and every cell on that canvas receives it.
 * One SSH pane has one pane.
 * A different user has a different set of panes.
 * The stream receives the returned reference and writes that instance.
 * A reply ends on that pane and does not release it.
 * Close hides the pane and does not release it.
 * {@link #release} drops a pane only when that session ends.
 */
public final class ThinkPaneManager {

    private final HashMap<String, ThinkStrip> strips = new HashMap<String, ThinkStrip>();
    private final HashMap<String, String> cellKey = new HashMap<String, String>();
    private final ArrayList<ThinkStrip> canvasStrips = new ArrayList<ThinkStrip>();
    private final ThinkStream stream = new ThinkStream();

    /**
     * The method builds a canvas strip the first time a key is acquired.
     */
    interface StripSource {
        /**
         * The method builds the strip for a key that has no pane yet.
         *
         * @return the new strip
         */
        ThinkStrip create();
    }

    /**
     * The method returns the stream that writes a pane by reference.
     *
     * @return the stream
     */
    public ThinkStream stream() {
        return stream;
    }

    /**
     * The method returns the key for one SSH pane and one user.
     * The surface id is the pane instance.
     * A null user is stored as empty.
     * A null surface id is stored as empty.
     *
     * @param user the sanitized user
     * @param surfaceId the SSH pane id
     * @return the key
     */
    public static String sshKey(String user, String surfaceId) {
        String name = user == null ? "" : user;
        String id = surfaceId == null ? "" : surfaceId;
        return name + ThinkSeats.SEP + "s:" + id;
    }

    /**
     * The method returns the pane already bound to {@code key}.
     * The first call stores {@code strip} and returns it.
     * A later call returns the stored instance.
     * A null key returns {@code strip} without storing it.
     *
     * @param key the context key
     * @param strip the pane the surface built
     * @return the pane for that key
     */
    public synchronized ThinkStrip bind(String key, ThinkStrip strip) {
        if (key == null || key.length() == 0 || strip == null) {
            return strip;
        }
        ThinkStrip held = strips.get(key);
        if (held != null) {
            return held;
        }
        strips.put(key, strip);
        return strip;
    }

    /**
     * The method returns the canvas pane for {@code key}, creating it once.
     * A key that already has a pane returns that pane.
     * A null source on a missing key returns null.
     *
     * @param key the user and scope key
     * @param source the builder used the first time
     * @return the pane
     */
    synchronized ThinkStrip acquire(String key, StripSource source) {
        if (key == null || key.length() == 0) {
            return null;
        }
        ThinkStrip held = strips.get(key);
        if (held != null) {
            return held;
        }
        if (source == null) {
            return null;
        }
        ThinkStrip created = source.create();
        if (created == null) {
            return null;
        }
        strips.put(key, created);
        canvasStrips.add(created);
        return created;
    }

    /**
     * The method records which pane a cell writes.
     * Every cell on one canvas records that canvas key.
     * A later pause finds the pane through this record.
     *
     * @param user the sanitized user
     * @param cellId the cell id
     * @param key the pane key
     */
    synchronized void rememberCell(String user, String cellId, String key) {
        if (user == null || cellId == null || cellId.length() == 0 || key == null) {
            return;
        }
        cellKey.put(user + ThinkSeats.SEP + cellId, key);
    }

    /**
     * The method returns the pane that receives this cell's stream.
     * A cell that has not acquired a pane returns null.
     *
     * @param user the sanitized user
     * @param cellId the cell id
     * @return the pane, or null
     */
    synchronized ThinkStrip findCell(String user, String cellId) {
        if (user == null || cellId == null) {
            return null;
        }
        String key = cellKey.get(user + ThinkSeats.SEP + cellId);
        if (key == null) {
            return null;
        }
        return strips.get(key);
    }

    /**
     * The method drops the pane stored at {@code key}.
     * The call is the end of that session.
     * Close does not call this method.
     * A reply does not call this method.
     * A missing key returns without a change.
     * Cells that recorded this key lose that record.
     * The stream drops the block for that pane.
     * The pane object is not folded and is not cleared.
     *
     * @param key the context key
     */
    public synchronized void release(String key) { // @aa-sdd PK-1455 docs/specs/java/org/aetheris/swing/swing.spec.md
        if (key == null || key.length() == 0) {
            return;
        }
        ThinkStrip held = strips.remove(key);
        forgetKey(key);
        if (held == null) {
            return;
        }
        for (int i = canvasStrips.size() - 1; i >= 0; i--) {
            if (canvasStrips.get(i) == held) {
                canvasStrips.remove(i);
            }
        }
        stream.drop(held);
    }

    /**
     * The method drops every key that holds {@code strip}.
     * A null strip returns without a change.
     * The call is the end of an SSH session.
     * Close on the strip does not call this method.
     * A canvas pane stays until that session ends.
     *
     * @param strip the pane that is leaving
     */
    public synchronized void releaseStrip(ThinkStrip strip) {
        if (strip == null) {
            return;
        }
        ArrayList<String> keys = new ArrayList<String>();
        Iterator<Map.Entry<String, ThinkStrip>> it = strips.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ThinkStrip> entry = it.next();
            if (entry.getValue() == strip) {
                keys.add(entry.getKey());
            }
        }
        for (int i = 0; i < keys.size(); i++) {
            release(keys.get(i));
        }
    }

    /**
     * The method removes cell records that point at {@code key}.
     *
     * @param key the pane key that is leaving
     */
    private void forgetKey(String key) {
        Iterator<Map.Entry<String, String>> it = cellKey.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            if (key.equals(entry.getValue())) {
                it.remove();
            }
        }
    }

    /**
     * The method applies the body font to each canvas pane.
     * An SSH pane keeps the font its terminal set.
     *
     * @param font the monospaced body font
     */
    synchronized void applyBodyFont(Font font) {
        if (font == null) {
            return;
        }
        for (int i = 0; i < canvasStrips.size(); i++) {
            ThinkStrip strip = canvasStrips.get(i);
            if (strip != null) {
                strip.setBodyFont(font);
            }
        }
    }
}
