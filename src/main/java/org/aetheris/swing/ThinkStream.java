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

import java.util.IdentityHashMap;

/**
 * The {@code ThinkStream} class writes a thinking chunk to one pane.
 * The caller passes the pane by reference.
 * The pane is the canvas pane or the SSH pane.
 * A different pane keeps its own block.
 * A different cell or a different banner word starts a header on that pane.
 * The same cell and the same word append on that pane.
 * {@link #finish} closes the block and leaves the pane in the manager.
 * {@link ThinkStrip#finish} leaves the fold and the shut flag as the user set them.
 * A closed pane still receives the chunk.
 * That same pane opens for the chunk.
 * Close does not drop the pane.
 */
public final class ThinkStream {

    private final IdentityHashMap<ThinkStrip, Block> live = new IdentityHashMap<ThinkStrip, Block>();

    /**
     * The method writes one chunk on {@code pane}.
     * A null pane returns without writing.
     * A null or empty chunk returns without writing.
     * A new block calls {@link ThinkStrip#begin}.
     * An open block on that pane calls {@link ThinkStrip#append}.
     * A closed pane still receives the chunk and opens.
     *
     * @param pane the thinking pane for this context
     * @param cellId the cell that produced the chunk, or the SSH surface id
     * @param model the banner word
     * @param text the chunk
     */
    public synchronized void offer(ThinkStrip pane, String cellId, String model, String text) { // @aa-sdd PK-1455 docs/specs/java/org/aetheris/swing/swing.spec.md
        if (pane == null || text == null || text.length() == 0) {
            return;
        }
        String word = model == null ? "" : model;
        String cell = cellId == null ? "" : cellId;
        Block block = live.get(pane);
        boolean fresh = block == null || !block.live || !cell.equals(block.cell) || !word.equals(block.word);
        if (fresh) {
            Block opened = new Block();
            opened.cell = cell;
            opened.word = word;
            opened.live = true;
            live.put(pane, opened);
            pane.begin(word, text);
            return;
        }
        pane.append(text);
    }

    /**
     * The method closes the open block on {@code pane}.
     * A null pane returns without a call.
     * The pane stays open or folded as the user left it.
     * A later chunk on that pane starts a header.
     *
     * @param pane the thinking pane whose block ended
     */
    public synchronized void finish(ThinkStrip pane) {
        if (pane == null) {
            return;
        }
        Block block = live.get(pane);
        if (block != null) {
            block.live = false;
        }
        pane.finish();
    }

    /**
     * The method drops the block stored for {@code pane}.
     * A null pane returns without a change.
     * The pane is not folded and is not cleared.
     * A later reply does not call this method.
     * Close does not call this method.
     * The session end calls this method.
     *
     * @param pane the pane whose surface was destroyed
     */
    public synchronized void drop(ThinkStrip pane) {
        if (pane == null) {
            return;
        }
        live.remove(pane);
    }

    /**
     * One open block on one pane instance.
     */
    private static final class Block {
        private String cell = "";
        private String word = "";
        private boolean live;
    }
}
