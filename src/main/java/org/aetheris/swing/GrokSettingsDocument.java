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
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

// @aa-sdd PK-0598 PK-0721 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0640 docs/specs/java/org/casserole/llm/llm.spec.md
/**
 * The {@code GrokSettingsDocument} class reads and writes {@code aetheris-grok.yaml}.
 * The file lives in {@code .casserole} under the home directory.
 * The file is not the air-gap file.
 * The file is not the client account file.
 * A version other than 1 is left unread and is not overwritten.
 * A key found in the file is not written back.
 *
 * @author clayl (2026-10-22)
 */
public final class GrokSettingsDocument {

    /**
     * The method lists the effort rows for one model id.
     * {@code grok-4.7}, {@code grok-4.6}, and {@code grok-4.20-multi-agent-0309} list {@code low}, {@code medium}, {@code high}, and {@code xhigh}.
     * {@code grok-4.5} lists {@code low}, {@code medium}, and {@code high}.
     * An empty id lists {@code high} only.
     * Every other id lists {@code high} only.
     * The list never contains {@code omit}.
     *
     * @param model The model is the model id.
     * @return The return value is the row labels.
     */
    static String[] effortRows(String model) {
        if ("grok-4.7".equals(model) || "grok-4.6".equals(model)
                || "grok-4.20-multi-agent-0309".equals(model)) {
            return new String[] { "low", "medium", "high", "xhigh" };
        }
        if ("grok-4.5".equals(model)) {
            return new String[] { "low", "medium", "high" };
        }
        return new String[] { "high" };
    }

    /**
     * The method returns the factory effort.
     * The model id is not read.
     * The factory effort is always {@code high}.
     *
     * @param model The model is the model id, and it is ignored.
     * @return The return value is {@code high}.
     */
    static String effortStart(String model) {
        return "high";
    }

    /**
     * The method reports whether a fresh cell sends {@code reasoning_effort}.
     * {@code grok-4.20-0309-reasoning} returns false.
     * {@code grok-4.20-0309-non-reasoning} returns false.
     * {@code grok-build-0.1} returns false.
     * Every other id returns true.
     * A stored {@code send_reasoning_effort} wins over this start when the file contains that key.
     *
     * @param model The model is the model id.
     * @return The return value is false for the ids that refuse the effort field.
     */
    static boolean sendsEffort(String model) {
        return !"grok-4.20-0309-reasoning".equals(model)
                && !"grok-4.20-0309-non-reasoning".equals(model)
                && !"grok-build-0.1".equals(model);
    }

    /**
     * The method keeps a stored effort only when it is still a row for the model.
     * An empty value is not a row.
     * {@code omit} is not a row.
     * A value that is not in {@link #effortRows} is replaced by {@link #effortStart}.
     *
     * @param model The model is the model id.
     * @param value The value is the stored level, and it may be null.
     * @return The return value is the stored level when it is still a row, and otherwise {@code high}.
     */
    static String normalizeEffort(String model, String value) {
        String[] rows = effortRows(model);
        if (value != null) {
            for (int i = 0; i < rows.length; i++) {
                if (rows[i].equals(value)) {
                    return value;
                }
            }
        }
        return effortStart(model);
    }

    private final File file;
    /**
     * The flag is true when the file version is not 1.
     * A blocked document returns empty reads.
     * A blocked document does not write the file.
     */
    private boolean blocked;
    private Entry standing;
    private final LinkedHashMap<String, Entry> cells = new LinkedHashMap<String, Entry>();

    /**
     * The constructor stores the file path and does not read it yet.
     *
     * @param file The file is {@code aetheris-grok.yaml}.
     */
    private GrokSettingsDocument(File file) {
        this.file = file;
    }

    /**
     * The method opens the user document and loads it.
     * The path is {@code .casserole/aetheris-grok.yaml} under the home directory.
     * A missing file has no standing default.
     *
     * @return The return value is the loaded document.
     */
    static GrokSettingsDocument open() {
        File home = new File(System.getProperty("user.home"), ".casserole");
        GrokSettingsDocument doc = new GrokSettingsDocument(new File(home, "aetheris-grok.yaml"));
        doc.load();
        return doc;
    }

    // @aa-sdd PK-0599 PK-0722 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0641 docs/specs/java/org/casserole/llm/llm.spec.md
    /**
     * The {@code Entry} class is one settings object.
     * The API starts as {@code chat}.
     * The model starts as {@code grok-4.3}.
     * The reasoning effort starts as {@code high}.
     * Stream starts off.
     * The burner-cloud flag starts off.
     * The temperature starts at {@code 1.0}.
     * The completion cap starts at {@code 128000}.
     */
    static final class Entry {
        String api = "chat";
        String model = "grok-4.3";
        String reasoningEffort = "high";
        /**
         * The flag is false when {@code reasoning_effort} stays off the wire.
         * A missing file key uses {@link GrokSettingsDocument#sendsEffort}.
         */
        boolean sendReasoningEffort = true;
        boolean stream;
        boolean burnerCloud;
        double temperature = 1.0d;
        int maxCompletionTokens = 128000;

        /**
         * The method returns a separate entry with the same values.
         * A null or empty API becomes {@code chat}.
         * A null model becomes empty.
         * The effort is passed through {@link GrokSettingsDocument#normalizeEffort}.
         *
         * @return The return value is the copied entry.
         */
        Entry copy() {
            Entry next = new Entry();
            next.api = api == null || api.length() == 0 ? "chat" : api;
            next.model = model == null ? "" : model;
            next.reasoningEffort = normalizeEffort(next.model, reasoningEffort);
            next.sendReasoningEffort = sendReasoningEffort;
            next.stream = stream;
            next.burnerCloud = burnerCloud;
            next.temperature = temperature;
            next.maxCompletionTokens = maxCompletionTokens;
            return next;
        }
    }

    /**
     * The method returns a new factory entry.
     * The model is {@code grok-4.3}.
     * The effort is {@code high}.
     * Stream is off.
     * The burner-cloud flag is off.
     *
     * @return The return value is that entry.
     */
    static Entry factory() {
        return new Entry();
    }

    /**
     * The method reports whether the file was left unread.
     *
     * @return The return value is true when the version is not 1 or the load failed.
     */
    boolean blocked() {
        return blocked;
    }

    /**
     * The method returns a copy of the standing default.
     * A blocked file returns null.
     * A file with no standing default returns null.
     *
     * @return The return value is that copy, or null.
     */
    Entry standing() {
        if (blocked || standing == null) {
            return null;
        }
        return standing.copy();
    }

    /**
     * The method returns the standing Grok row for a console whose Grok id is still empty.
     * Index 0 is the model id.
     * Index 1 is the effort.
     * Index 2 is {@code true} or {@code false} for sending the effort.
     * Index 3 is the stream flag.
     * Index 4 is the burner-cloud flag.
     * A missing file, a blocked file, or an empty model uses the factory row.
     * That factory model is {@code grok-4.3}.
     * The factory effort is {@code high}.
     * The factory send flag is on.
     * The factory stream flag is off.
     * The factory burner-cloud flag is off.
     * No API key is returned.
     * The file is not written.
     *
     * @return The return value is those five strings, and index 0 is never empty.
     */
    public static String[] consoleSeed() {
        Entry entry = open().standing();
        if (entry == null || entry.model == null || entry.model.trim().length() == 0) {
            entry = factory();
        }
        String effort = entry.reasoningEffort == null ? "high" : entry.reasoningEffort;
        return new String[] {
            entry.model.trim(),
            effort,
            entry.sendReasoningEffort ? "true" : "false",
            entry.stream ? "true" : "false",
            entry.burnerCloud ? "true" : "false"
        };
    }

    /**
     * The {@code entry} method returns a copy of one stored cell.
     * A blocked document returns null.
     * A null cell id returns null.
     * A cell id that is not stored returns null.
     *
     * @param cellId The cell id is the vault cell id.
     * @return The return value is that copy, or null.
     */
    Entry entry(String cellId) {
        if (blocked || cellId == null) {
            return null;
        }
        Entry found = cells.get(cellId);
        if (found == null) {
            return null;
        }
        return found.copy();
    }

    /**
     * The {@code burnerCloud} method reports the burner-cloud flag for one cell.
     * The lookup uses {@link #entry}.
     * A missing entry returns false.
     *
     * @param cellId The cell id is the vault cell id.
     * @return The return value is true when that cell stores the flag as on.
     */
    boolean burnerCloud(String cellId) {
        Entry found = entry(cellId);
        return found != null && found.burnerCloud;
    }

    /**
     * The {@code write} method stores one cell and writes the file.
     * A blocked document returns without writing.
     * A null cell id returns without writing.
     * An empty cell id returns without writing.
     * A null entry returns without writing.
     * A null model is stored as empty.
     * An empty model never becomes the standing default.
     * A non-empty model becomes the standing default when {@code alsoDefault} is true.
     * A non-empty model also becomes the standing default when no standing default exists.
     *
     * @param cellId The cell id is the vault cell id.
     * @param entry The entry holds the dialog values.
     * @param alsoDefault The flag is true when this entry also becomes the standing default.
     */
    void write(String cellId, Entry entry, boolean alsoDefault) {
        if (blocked || cellId == null || cellId.length() == 0 || entry == null) {
            return;
        }
        Entry stored = entry.copy();
        if (stored.model == null) {
            stored.model = "";
        }
        cells.put(cellId, stored);
        if (stored.model.length() > 0 && (alsoDefault || standing == null)) {
            standing = stored.copy();
        }
        save();
    }

    /**
     * The {@code copyOnto} method copies one entry onto a cell.
     * A null source returns without writing.
     * The copy calls {@link #write} with the standing flag false.
     * An empty model does not become the standing default.
     * A non-empty model becomes the standing default when none exists.
     *
     * @param cellId The cell id is the vault cell id.
     * @param source The source is the nearest cell or the standing default.
     */
    void copyOnto(String cellId, Entry source) {
        if (source == null) {
            return;
        }
        write(cellId, source, false);
    }

    /**
     * The {@code forgetAll} method drops cell entries that left the canvas.
     * A blocked document returns without writing.
     * A null list returns without writing.
     * An empty list returns without writing.
     * The file is written only when at least one stored id was removed.
     *
     * @param ids The ids are the cell ids that left the canvas.
     */
    void forgetAll(java.util.List<String> ids) {
        if (blocked || ids == null || ids.size() == 0) {
            return;
        }
        boolean changed = false;
        for (int i = 0; i < ids.size(); i++) {
            if (cells.remove(ids.get(i)) != null) {
                changed = true;
            }
        }
        if (changed) {
            save();
        }
    }

    /**
     * The {@code load} method reads the file into this document.
     * A null file returns without reading.
     * A path that is not a file returns without reading.
     * A root that is not a map sets the blocked flag.
     * A version other than 1 sets the blocked flag.
     * A default map becomes standing only when its model is non-empty.
     * A cells map stores each map value under its key.
     * A null key is skipped.
     * A value that is not a map is skipped.
     * An exception sets the blocked flag.
     * That same exception clears the standing default.
     * That same exception clears the cells.
     * The reader is closed afterward.
     */
    private void load() {
        if (file == null || !file.isFile()) {
            return;
        }
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            Object loaded = new Yaml().load(reader);
            if (!(loaded instanceof Map)) {
                blocked = true;
                return;
            }
            Map<?, ?> root = (Map<?, ?>) loaded;
            if (!versionOne(root.get("version"))) {
                blocked = true;
                return;
            }
            Object def = root.get("default");
            if (def instanceof Map) {
                Entry parsed = parseEntry((Map<?, ?>) def);
                if (parsed.model != null && parsed.model.length() > 0) {
                    standing = parsed;
                }
            }
            Object rawCells = root.get("cells");
            if (rawCells instanceof Map) {
                Map<?, ?> map = (Map<?, ?>) rawCells;
                for (Map.Entry<?, ?> one : map.entrySet()) {
                    if (one.getKey() == null || !(one.getValue() instanceof Map)) {
                        continue;
                    }
                    cells.put(String.valueOf(one.getKey()), parseEntry((Map<?, ?>) one.getValue()));
                }
            }
        } catch (Exception ex) {
            blocked = true;
            standing = null;
            cells.clear();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * The {@code save} method writes version 1 in block style.
     * A blocked document returns without writing.
     * A missing parent directory is created.
     * The standing default is written only when it exists.
     * Every stored cell is written.
     * A write failure is ignored.
     * The writer is closed afterward.
     */
    private void save() {
        if (blocked) {
            return;
        }
        File parent = file.getParentFile();
        if (parent != null && !parent.isDirectory()) {
            parent.mkdirs();
        }
        LinkedHashMap<String, Object> root = new LinkedHashMap<String, Object>();
        root.put("version", Integer.valueOf(1));
        if (standing != null) {
            root.put("default", mapOf(standing));
        }
        LinkedHashMap<String, Object> outCells = new LinkedHashMap<String, Object>();
        for (Map.Entry<String, Entry> one : cells.entrySet()) {
            outCells.put(one.getKey(), mapOf(one.getValue()));
        }
        root.put("cells", outCells);
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            new Yaml(options).dump(root, writer);
        } catch (Exception ignored) {
            return;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception ignoredClose) {
                }
            }
        }
    }

    /**
     * The {@code versionOne} method reports whether a version value is 1.
     * A number matches when its int value is 1.
     * Any other non-null value matches when its trimmed text is {@code 1}.
     * A null value returns false.
     *
     * @param value The value is the version field from the file.
     * @return The return value is true when the version is 1.
     */
    private static boolean versionOne(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue() == 1;
        }
        return value != null && "1".equals(String.valueOf(value).trim());
    }

    /**
     * The {@code parseEntry} method builds an entry from one map.
     * The start is {@link #factory}.
     * An empty API becomes {@code chat}.
     * The model is the trimmed {@code model} text.
     * The effort is passed through {@link #normalizeEffort}.
     * A present {@code send_reasoning_effort} uses {@link #bool}.
     * A missing {@code send_reasoning_effort} uses {@link #sendsEffort} for the model.
     * Stream and burner-cloud use {@link #bool}.
     * A numeric {@code temperature} replaces {@code 1.0}.
     * A numeric {@code max_completion_tokens} replaces {@code 128000}.
     *
     * @param raw The raw map is one default or cell object.
     * @return The return value is the parsed entry.
     */
    private static Entry parseEntry(Map<?, ?> raw) {
        Entry entry = factory();
        String api = text(raw.get("api"));
        entry.api = api.length() == 0 ? "chat" : api;
        entry.model = text(raw.get("model"));
        String effort = text(raw.get("reasoning_effort"));
        entry.reasoningEffort = normalizeEffort(entry.model, effort);
        if (raw.containsKey("send_reasoning_effort")) {
            entry.sendReasoningEffort = bool(raw.get("send_reasoning_effort"));
        } else {
            entry.sendReasoningEffort = sendsEffort(entry.model);
        }
        entry.stream = bool(raw.get("stream"));
        entry.burnerCloud = bool(raw.get("burner_cloud"));
        if (raw.get("temperature") instanceof Number) {
            entry.temperature = ((Number) raw.get("temperature")).doubleValue();
        }
        if (raw.get("max_completion_tokens") instanceof Number) {
            entry.maxCompletionTokens = ((Number) raw.get("max_completion_tokens")).intValue();
        }
        return entry;
    }

    /**
     * The {@code mapOf} method writes one entry as a map.
     * An empty API is written as {@code chat}.
     * A null model is written as empty.
     * The effort is passed through {@link #normalizeEffort}.
     * The map writes {@code api}.
     * The map writes {@code model}.
     * The map writes {@code reasoning_effort}.
     * The map writes {@code send_reasoning_effort}.
     * The map writes {@code stream}.
     * The map writes {@code burner_cloud}.
     * The map writes {@code temperature}.
     * The map writes {@code max_completion_tokens}.
     *
     * @param entry The entry is the settings object.
     * @return The return value is that map.
     */
    private static Map<String, Object> mapOf(Entry entry) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("api", entry.api == null || entry.api.length() == 0 ? "chat" : entry.api);
        map.put("model", entry.model == null ? "" : entry.model);
        map.put("reasoning_effort", normalizeEffort(entry.model, entry.reasoningEffort));
        map.put("send_reasoning_effort", Boolean.valueOf(entry.sendReasoningEffort));
        map.put("stream", Boolean.valueOf(entry.stream));
        map.put("burner_cloud", Boolean.valueOf(entry.burnerCloud));
        map.put("temperature", Double.valueOf(entry.temperature));
        map.put("max_completion_tokens", Integer.valueOf(entry.maxCompletionTokens));
        return map;
    }

    /**
     * The {@code text} method returns trimmed text.
     * A null value returns empty.
     *
     * @param value The value is a file field.
     * @return The return value is the trimmed text, or empty.
     */
    private static String text(Object value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value).trim();
    }

    /**
     * The {@code bool} method reads a yes-or-no field.
     * A {@link Boolean} returns its own value.
     * A null value returns false.
     * The text {@code true} returns true.
     * The text {@code yes} returns true.
     * The text {@code 1} returns true.
     * {@code true} and {@code yes} ignore case.
     * {@code 1} must match exactly.
     * Every other text returns false.
     *
     * @param value The value is a file field.
     * @return The return value is that flag.
     */
    private static boolean bool(Object value) {
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        }
        if (value == null) {
            return false;
        }
        String raw = String.valueOf(value).trim();
        return "true".equalsIgnoreCase(raw) || "yes".equalsIgnoreCase(raw) || "1".equals(raw);
    }
}
/*****************************************************************************************
 * End of Class: GrokSettingsDocument.java 102226
 ****************************************************************************************/
