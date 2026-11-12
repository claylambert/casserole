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

package org.casserole.app.ui.ssh;

/**
 * The {@code ToolsThinking} type places Show Thinking, the item under the Tools menu, on the main menu.
 * The main menu is the bar of menu names across the window.
 * The type reveals the thinking pane of the plate in front, the visible product tab.
 * The closed pane starts its rise at slide {@code 0}.
 * The fold opens.
 * The hidden text is shown again.
 * The pane that is already open keeps the slide it has.
 * The other plate keeps the same {@link Seat}.
 * The log stays as it is, because this type does not write the log.
 * The closed mark on the file stays as it is, because this type does not write that mark.
 * @aa-sdd PK-3710 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 *
 * @author clayl (2026-10-31)
 */
public final class ToolsThinking {

    /**
     * The {@code MENU} constant is the Tools name on the main menu.
     * The name sits between {@link #FILE} and {@link #HELP}.
     */
    public static final String MENU = "Tools";

    /**
     * The {@code SHOW} constant is the item under the Tools menu.
     * The text is {@code Show Thinking}.
     */
    public static final String SHOW = "Show Thinking";

    /**
     * The {@code FILE} constant is the menu that leads the bar.
     * The text is {@code File}.
     */
    public static final String FILE = "File";

    /**
     * The {@code HELP} constant is the menu that ends the bar.
     * The text is {@code Help}.
     */
    public static final String HELP = "Help";

    /**
     * The constructor is private so a caller cannot create an instance.
     * The caller uses the static methods on this type.
     */
    private ToolsThinking() {
    }

    // @aa-sdd PK-3710 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code Plate} type names which product tab is in front.
     * The index {@code 1} is the Aetheris tab, the canvas tab.
     * The other indexes are the SSH tab, the secure-shell tab.
     */
    public enum Plate {
        /**
         * The {@code SSH} constant is the secure-shell tab.
         */
        SSH,
        /**
         * The {@code AETHERIS} constant is the canvas tab.
         */
        AETHERIS
    }

    // @aa-sdd PK-3710 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code Seat} type stores the open flag, the slide count, and the fold flag for one thinking pane.
     * The slide value {@code -1} means the rise has settled.
     * The slide value {@code 0} means the rise is at its start.
     * The constructor stores the three values as they are given.
     */
    public static final class Seat {

        public final boolean open;
        public final int slide;
        public final boolean folded;

        /**
         * The constructor stores the open flag, the slide count, and the fold flag.
         *
         * @param open The pane is up for this session when the flag is true.
         * @param slide The rise count. The value {@code -1} means the rise has settled.
         * @param folded The text is hidden on the fold row when the flag is true.
         */
        public Seat(boolean open, int slide, boolean folded) {
            this.open = open;
            this.slide = slide;
            this.folded = folded;
        }
    }

    // @aa-sdd PK-3710 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code Pair} type holds both plates after Show Thinking.
     * The plate that was not in front keeps the same {@link Seat} object.
     */
    public static final class Pair {

        public final Seat ssh;
        public final Seat canvas;

        /**
         * The constructor stores the SSH pane and the Aetheris pane.
         * The SSH pane is the focused VT100 strip, the status strip on the secure-shell tab.
         * The Aetheris pane is the thinking pane on the canvas tab.
         *
         * @param ssh The focused VT100 strip.
         * @param canvas The Aetheris thinking pane.
         */
        public Pair(Seat ssh, Seat canvas) {
            this.ssh = ssh;
            this.canvas = canvas;
        }
    }

    /**
     * The front method names the product tab that is in front.
     * The method returns {@link Plate#AETHERIS} when the tab index is {@code 1}.
     * The method returns {@link Plate#SSH} for every other index.
     *
     * @param tab The selected product-tab index. The value {@code 1} is the Aetheris tab.
     * @return The return value is the plate in front.
     */
    public static Plate front(int tab) {
        if (tab == 1) {
            return Plate.AETHERIS;
        }
        return Plate.SSH;
    }

    /**
     * The bar method returns the menu names from west to east.
     * The word west means the left end of the bar.
     * The names are {@link #FILE}, {@link #MENU}, and {@link #HELP}.
     * The string {@code Token Calc} is not in the array.
     * The constant {@link Vt100TokenWindow#ITEM} is that string.
     *
     * @return The return value is a new array of File, Tools, and Help.
     */
    public static String[] bar() {
        return new String[] { FILE, MENU, HELP };
    }

    /**
     * The show method builds the {@link Seat} for Show Thinking on one pane.
     * The returned pane is open.
     * The slide stays as given when the pane is already open.
     * The slide is {@code 0} when the pane is closed, which starts the rise.
     * The fold flag is false, so the text is not hidden.
     *
     * @param open The pane is already up when the flag is true.
     * @param slide The current rise count.
     * @return The return value is an open pane, the kept or restarted slide, and a cleared fold.
     */
    public static Seat show(boolean open, int slide) {
        int next = open ? slide : 0;
        return new Seat(true, next, false);
    }

    /**
     * The reveal method applies Show Thinking to the plate in front.
     * The method calls {@link #show} on the Aetheris pane when the front plate is {@link Plate#AETHERIS}.
     * The SSH pane in that result is the same object that was passed in.
     * The method calls {@link #show} on the SSH pane for every other front plate, including a null plate.
     * The Aetheris pane in that result is the same object that was passed in.
     *
     * @param front The plate in front.
     * @param ssh The focused VT100 strip.
     * @param canvas The Aetheris thinking pane.
     * @return The return value is both panes after the click.
     */
    public static Pair reveal(Plate front, Seat ssh, Seat canvas) {
        if (front == Plate.AETHERIS) {
            return new Pair(ssh, show(canvas.open, canvas.slide));
        }
        return new Pair(show(ssh.open, ssh.slide), canvas);
    }
}
