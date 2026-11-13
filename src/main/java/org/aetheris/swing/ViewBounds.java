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

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The {@code ViewBounds} class reads and writes the View dialog rectangle for one vault user.
 * The rectangle is not a cell column.
 * The file is {@code view-bounds.txt} under {@code .aetheris} and the user name in the home directory.
 * // @aa-sdd T5 2026-10-18_1258_stage-1-tweaks.md
 *
 * @author clayl (2026-10-18)
 */
public final class ViewBounds {

    /**
     * The constructor stops a caller from creating an instance.
     * The file helpers are static.
     */
    private ViewBounds() {
    }

    /**
     * The method builds the path of the bounds file for one user.
     * A null name returns null.
     * An empty name returns null.
     * The directory is {@code .aetheris} under the user home, then the user name.
     * The file name is {@code view-bounds.txt}.
     *
     * @param username The name is the sanitized vault user, and it may be null.
     * @return The return value is that file, or null when the name is empty.
     */
    public static File fileFor(String username) {
        if (username == null || username.length() == 0) {
            return null;
        }
        File dir = new File(System.getProperty("user.home"), ".aetheris");
        dir = new File(dir, username);
        return new File(dir, "view-bounds.txt");
    }

    /**
     * The method reads a dialog rectangle from the bounds file.
     * A missing file returns null.
     * A read error returns null.
     * The file lines are {@code x}, {@code y}, {@code width}, and {@code height}.
     * A line that is not an integer is skipped.
     * Fewer than four accepted keys returns null.
     * A width below 200 returns null.
     * A height below 120 returns null.
     * A rectangle that misses every screen returns null.
     *
     * @param username The name is the sanitized vault user.
     * @return The return value is a rectangle that intersects a screen, or null.
     */
    public static Rectangle load(String username) {
        File file = fileFor(username);
        if (file == null || !file.isFile()) {
            return null;
        }
        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;
        int seen = 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                int eq = line.indexOf('=');
                if (eq > 0) {
                    String key = line.substring(0, eq).trim();
                    int value = parseInt(line.substring(eq + 1).trim());
                    if (value != Integer.MIN_VALUE) {
                        if (key.equals("x")) {
                            x = value;
                            seen++;
                        } else if (key.equals("y")) {
                            y = value;
                            seen++;
                        } else if (key.equals("width")) {
                            width = value;
                            seen++;
                        } else if (key.equals("height")) {
                            height = value;
                            seen++;
                        }
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException ex) {
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                    // leave the fallback to the caller
                }
            }
        }
        if (seen < 4 || width < 200 || height < 120) {
            return null;
        }
        Rectangle rect = new Rectangle(x, y, width, height);
        if (!onScreen(rect)) {
            return null;
        }
        return rect;
    }

    /**
     * The method writes the dialog rectangle to the bounds file.
     * A null file path returns without writing.
     * A null rectangle returns without writing.
     * A missing parent directory is created.
     * A write error is ignored, and the next open falls back to the default.
     * The lines are {@code x}, {@code y}, {@code width}, and {@code height}.
     *
     * @param username The name is the sanitized vault user.
     * @param rect The rectangle is the dialog bounds, and it may be null.
     */
    public static void save(String username, Rectangle rect) {
        File file = fileFor(username);
        if (file == null || rect == null) {
            return;
        }
        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
            writer.println("x=" + rect.x);
            writer.println("y=" + rect.y);
            writer.println("width=" + rect.width);
            writer.println("height=" + rect.height);
        } catch (IOException ex) {
            // a missed write falls back to the default on the next open
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * The method parses one integer from a file value.
     * A value that is not an integer returns {@link Integer#MIN_VALUE}.
     *
     * @param raw The raw value is the text after {@code =}.
     * @return The return value is the integer, or {@link Integer#MIN_VALUE} when parsing fails.
     */
    private static int parseInt(String raw) {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException ex) {
            return Integer.MIN_VALUE;
        }
    }

    /**
     * The method reports whether the rectangle intersects any local screen.
     * Each screen bound comes from the default configuration of that device.
     * No intersecting screen returns false.
     *
     * @param rect The rectangle is the candidate dialog bounds.
     * @return The return value is true when one screen intersects the rectangle.
     */
    private static boolean onScreen(Rectangle rect) {
        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (int i = 0; i < devices.length; i++) {
            Rectangle screen = devices[i].getDefaultConfiguration().getBounds();
            if (screen.intersects(rect)) {
                return true;
            }
        }
        return false;
    }
}
