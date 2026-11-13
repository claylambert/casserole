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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.roux.session.RegionOwner;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

// @aa-sdd PK-1067 docs/specs/common/formal-plan.spec.md FINESSE:f-225
/**
 * The {@code ThinkSeats} class keeps one thinking pane per user and per scope.
 * A scope with a group id is that user's merge group, and the group shares one pane.
 * A scope with no group id is that one cell, and the cell does not share.
 * The glass is still one strip. This seat decides which text that strip shows.
 * Each user is one file under {@code ~/.casserole/aetheris-thinking/}.
 * A clear of one user rewrites that file only.
 * The legacy {@code aetheris-thinking.txt} file is not read and not written here.
 */
// @aa-sdd PK-914 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-667 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-665 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-667 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-664 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-665 docs/specs/common/formal-plan.spec.md
final class ThinkSeats {

    /** Splits the user from the scope inside a map key. */
    static final char SEP = '\u0001';

    private final File dir;
    private final HashMap<String, ThinkPane> panes = new HashMap<String, ThinkPane>();
    private final HashMap<String, String> cellKey = new HashMap<String, String>();
    private final HashMap<String, String[]> faces = new HashMap<String, String[]>();
    private final HashSet<String> loaded = new HashSet<String>();
    private final HashSet<String> blocked = new HashSet<String>();

    /**
     * The constructor stores the directory that holds one yaml file per user.
     *
     * @param dir the directory, created on the first save
     */
    ThinkSeats(File dir) {
        this.dir = dir;
    }

    /**
     * The method returns the scope token for one cell.
     * A non-empty group id returns {@code g:} plus that id.
     * Any other cell returns {@code c:} plus the cell id.
     *
     * @param groupId the merge group, or empty when the cell stands alone
     * @param cellId the cell id
     * @return the scope token
     */
    static String scope(String groupId, String cellId) {
        String group = groupId == null ? "" : groupId;
        if (group.length() > 0) {
            return "g:" + group;
        }
        String cell = cellId == null ? "" : cellId;
        return "c:" + cell;
    }

    /**
     * The method returns the map key for one user and one scope.
     * The user and the scope are joined with {@link #SEP}.
     *
     * @param user the sanitized user
     * @param groupId the merge group, or empty
     * @param cellId the cell id
     * @return the key
     */
    static String key(String user, String groupId, String cellId) {
        String name = user == null ? "" : user;
        return name + SEP + scope(groupId, cellId);
    }

    /**
     * The method turns a model id into the banner word.
     * A null id is empty.
     * A newline, a carriage return, and a box-drawing character are dropped.
     * A blank result returns {@code model}.
     * A word longer than 80 characters keeps the first 80.
     *
     * @param model the model id, or the short name
     * @return the word painted between the box-drawing marks
     */
    static String word(String model) {
        String raw = model == null ? "" : model;
        StringBuffer kept = new StringBuffer();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c == '\n' || c == '\r' || c == '\u2500') {
                continue;
            }
            kept.append(c);
        }
        String text = kept.toString().trim();
        if (text.length() == 0) {
            return "model";
        }
        if (text.length() > 80) {
            return text.substring(0, 80);
        }
        return text;
    }

    /**
     * The method opens a block on stored text the same way the strip opens one.
     * A pane that already has text gets a blank line first.
     * The banner is two box-drawing characters, the word, and two more.
     * The chunk follows the banner.
     *
     * @param text the pane text before this chunk
     * @param banner the banner word
     * @param chunk the first chunk of the block
     * @return the pane text after the block opens
     */
    static String beginText(String text, String banner, String chunk) {
        String body = text == null ? "" : text;
        StringBuffer out = new StringBuffer(body);
        if (out.length() > 0) {
            out.append('\n');
        }
        out.append('\u2500');
        out.append('\u2500');
        out.append(' ');
        out.append(banner == null ? "model" : banner);
        out.append(' ');
        out.append('\u2500');
        out.append('\u2500');
        out.append('\n');
        if (chunk != null && chunk.length() > 0) {
            out.append(chunk);
        }
        return out.toString();
    }

    /**
     * The method applies one chunk to a stored pane.
     * A different cell or a different banner word starts a header.
     * The same cell and the same word append.
     * A null pane, or an empty chunk, returns without a change.
     *
     * @param pane the stored pane
     * @param cellId the cell that produced the chunk
     * @param model the banner word
     * @param chunk the chunk
     */
    static void accept(ThinkPane pane, String cellId, String model, String chunk) {
        if (pane == null || chunk == null || chunk.length() == 0) {
            return;
        }
        String next = word(model);
        String id = cellId == null ? "" : cellId;
        boolean fresh = !pane.live || !id.equals(pane.cell) || !next.equals(pane.word);
        if (fresh) {
            pane.cell = id;
            pane.word = next;
            pane.live = true;
            pane.text = beginText(pane.text, next, chunk);
            return;
        }
        pane.text = pane.text + chunk;
    }

    /**
     * The method stores the group id and the execution target for a cell.
     * A later chunk can seat that cell when its canvas is not on screen.
     *
     * @param user the sanitized user
     * @param cellId the cell id
     * @param groupId the merge group, or empty
     * @param target the execution target
     */
    synchronized void rememberFace(String user, String cellId, String groupId, String target) {
        if (user == null || cellId == null || cellId.length() == 0) {
            return;
        }
        String group = groupId == null ? "" : groupId;
        String stored = target == null ? "" : target;
        faces.put(user + SEP + cellId, new String[] { group, stored });
    }

    /**
     * The method returns the stored group id and execution target.
     *
     * @param user the sanitized user
     * @param cellId the cell id
     * @return a pair of group id then target, or null when this cell was not remembered
     */
    synchronized String[] face(String user, String cellId) {
        if (user == null || cellId == null) {
            return null;
        }
        String[] held = faces.get(user + SEP + cellId);
        if (held == null) {
            return null;
        }
        return new String[] { held[0], held[1] };
    }

    /**
     * The method returns the pane for one user and one scope, loading that user's file on first touch.
     * The cell id is recorded so a later pause can close this pane.
     *
     * @param user the sanitized user
     * @param groupId the merge group, or empty
     * @param cellId the cell id
     * @return the pane
     */
    synchronized ThinkPane pane(String user, String groupId, String cellId) {
        String name = user == null ? "" : user;
        load(name);
        String id = cellId == null ? "" : cellId;
        String mapKey = key(name, groupId, id);
        ThinkPane one = panes.get(mapKey);
        if (one == null) {
            one = new ThinkPane();
            panes.put(mapKey, one);
        }
        if (id.length() > 0) {
            cellKey.put(name + SEP + id, mapKey);
        }
        return one;
    }

    /**
     * The method returns a pane that is already in memory.
     * A missing key returns null and does not load a file.
     *
     * @param mapKey the key from {@link #key}
     * @return the pane, or null
     */
    synchronized ThinkPane paneIfPresent(String mapKey) {
        if (mapKey == null) {
            return null;
        }
        return panes.get(mapKey);
    }

    /**
     * The method closes the open block for the pane that last took this cell.
     * A cell that was never seated is left alone.
     *
     * @param user the sanitized user
     * @param cellId the cell whose run paused
     */
    synchronized void finishCell(String user, String cellId) {
        if (user == null || cellId == null) {
            return;
        }
        String mapKey = cellKey.get(user + SEP + cellId);
        if (mapKey == null) {
            return;
        }
        ThinkPane one = panes.get(mapKey);
        if (one != null) {
            one.live = false;
        }
    }

    /**
     * The method writes one user's panes.
     * A blocked file is left alone.
     * An empty pane is omitted, so a clear drops that scope and leaves the others.
     *
     * @param user the sanitized user
     */
    synchronized void saveUser(String user) {
        if (user == null || user.length() == 0 || !RegionOwner.isValid(user)) {
            return;
        }
        if (blocked.contains(user)) {
            return;
        }
        load(user);
        if (blocked.contains(user)) {
            return;
        }
        if (dir != null && !dir.isDirectory()) {
            dir.mkdirs();
        }
        ArrayList<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        String prefix = user + SEP;
        Iterator<Map.Entry<String, ThinkPane>> it = panes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ThinkPane> entry = it.next();
            if (entry.getKey() == null || !entry.getKey().startsWith(prefix)) {
                continue;
            }
            ThinkPane one = entry.getValue();
            if (one == null || one.text == null || one.text.length() == 0) {
                continue;
            }
            String scopeToken = entry.getKey().substring(prefix.length());
            LinkedHashMap<String, Object> row = new LinkedHashMap<String, Object>();
            row.put("scope", scopeToken);
            row.put("text", one.text);
            row.put("cell", one.cell == null ? "" : one.cell);
            row.put("model", one.word == null ? "" : one.word);
            rows.add(row);
        }
        LinkedHashMap<String, Object> root = new LinkedHashMap<String, Object>();
        root.put("version", Integer.valueOf(1));
        root.put("panes", rows);
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file(user)), StandardCharsets.UTF_8);
            new Yaml(options).dump(root, writer);
        } catch (Exception ex) {
            return;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception ignored) {
                    // a missed close still left the bytes already written
                }
            }
        }
    }

    /**
     * The method loads one user's file the first time that user is touched.
     * A missing file stays an empty seat.
     * A version other than 1 blocks later writes and loads nothing.
     * A loaded block is not live, so the next launch starts a header.
     *
     * @param user the sanitized user
     */
    private void load(String user) {
        if (user == null || user.length() == 0 || loaded.contains(user) || blocked.contains(user)) {
            return;
        }
        if (!RegionOwner.isValid(user)) {
            loaded.add(user);
            return;
        }
        File file = file(user);
        if (file == null || !file.isFile()) {
            loaded.add(user);
            return;
        }
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            Object parsed = new Yaml().load(reader);
            if (!(parsed instanceof Map)) {
                blocked.add(user);
                return;
            }
            Map<?, ?> root = (Map<?, ?>) parsed;
            if (!versionOne(root.get("version"))) {
                blocked.add(user);
                return;
            }
            Object raw = root.get("panes");
            if (raw instanceof List) {
                List<?> list = (List<?>) raw;
                for (int i = 0; i < list.size(); i++) {
                    if (!(list.get(i) instanceof Map)) {
                        continue;
                    }
                    Map<?, ?> row = (Map<?, ?>) list.get(i);
                    String scopeToken = text(row.get("scope"));
                    if (scopeToken.length() == 0 || scopeToken.indexOf(SEP) >= 0) {
                        continue;
                    }
                    ThinkPane one = new ThinkPane();
                    one.text = text(row.get("text"));
                    one.cell = text(row.get("cell"));
                    one.word = text(row.get("model"));
                    one.live = false;
                    String mapKey = user + SEP + scopeToken;
                    panes.put(mapKey, one);
                    if (one.cell.length() > 0) {
                        cellKey.put(user + SEP + one.cell, mapKey);
                    }
                }
            }
            loaded.add(user);
        } catch (Exception ex) {
            blocked.add(user);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                    // the map keeps what it already read
                }
            }
        }
    }

    /**
     * The method returns the yaml file for one user.
     *
     * @param user the sanitized user
     * @return that file
     */
    private File file(String user) {
        return new File(dir, user + ".yaml");
    }

    /**
     * The method reports whether a yaml version is 1.
     *
     * @param value the version field
     * @return true for 1
     */
    private static boolean versionOne(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue() == 1;
        }
        return value != null && "1".equals(String.valueOf(value).trim());
    }

    /**
     * The method returns a stored yaml value as text.
     * A null value returns an empty string.
     *
     * @param value the yaml value
     * @return the text
     */
    private static String text(Object value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }

    /**
     * One stored thinking pane.
     * {@code live} is true while a block is still receiving chunks.
     * {@code cell} and {@code word} are the cell and the banner of that open block.
     */
    static final class ThinkPane {
        String text = "";
        String cell = "";
        String word = "";
        boolean live;

        /**
         * The method drops the text and closes the open block.
         */
        void clear() {
            text = "";
            cell = "";
            word = "";
            live = false;
        }
    }
}
