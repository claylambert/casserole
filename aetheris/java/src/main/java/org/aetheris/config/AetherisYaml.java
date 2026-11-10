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

package org.aetheris.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * The {@code AetherisYaml} class loads {@code aetheris.yaml} and holds the values the shell reads.
 * The file uses spaces for indent.
 * A tab in the file makes the load throw {@link IllegalStateException}.
 * A key this class does not recognize is ignored.
 * The class does not use SnakeYAML.
 * // @aa-sdd PK-0001 PK-0002 docs/specs/java/org/aetheris/config/config.spec.md
 *
 * The {@link #load} method looks for a file in a fixed order.
 * The first place is the path after {@code --aetheris-yaml}.
 * The second place is {@code aetheris.yaml} in the working directory.
 * The third place is {@code .aetheris.yaml} in the home directory.
 * A missing file leaves the defaults from the constructor.
 * {@link #ensureHomeFile} writes that home file when it is absent.
 * An existing home file is left as it stands.
 *
 * @author clayl (2026-08-30)
 */
public class AetherisYaml {

    public static final String DEFAULT_TITLE = "Aetheris Shell";
    public static final String MODE_RAM = "ram";
    public static final String MODE_DISK = "disk";

    private String title;
    private String vaultMode;
    private String vaultPath;
    private int poolMax;
    private boolean airGap;
    /**
     * The flag stays false until the file contains {@code network.air-gap}.
     * A missing key is unset.
     * Unset is not a stored false that would allow a cloud target.
     */
    private boolean airGapKnown;
    private boolean amwpEnabled;
    /**
     * The value is the {@code notebook.mood-shape} id.
     * An empty id means the Ollama model already in use.
     */
    private String moodShape;
    /**
     * The flag is {@code notebook.burner-key}.
     * A missing key denies a cloud hop for a burner.
     * A burner is an ephemeral request.
     * The flag is not an API key.
     */
    private boolean burnerCloud;

    /**
     * The text is the home file written when {@code .aetheris.yaml} is absent.
     * {@code network.air-gap} is explicit {@code true}.
     * The Rust client writes these same bytes.
     * The text is not copied from a repository file or from an install folder.
     */
    private static final String HOME_DEFAULTS =
            "# Aetheris home configuration. The application writes this file when it is absent.\n"
            + "aetheris:\n"
            + "  ui:\n"
            + "    title: Aetheris\n"
            + "  vault:\n"
            + "    mode: disk\n"
            + "    path: ${user.home}/.aetheris/${user}/vault\n"
            + "    pool-max: 4\n"
            + "  # air-gap true refuses a cloud target. air-gap false allows that target.\n"
            + "  network:\n"
            + "    air-gap: true\n"
            + "  amwp:\n"
            + "    enabled: true\n"
            + "  # mood-shape holds the shaper id. Empty means the Ollama model in use.\n"
            + "  # burner-key allows a burner to hop to a cloud target. Missing or false denies.\n"
            + "  notebook:\n"
            + "    mood-shape: \"\"\n"
            + "    burner-key: false\n";

    /**
     * The constructor stores the defaults used when no file is present.
     * The title is {@code Aetheris Shell}.
     * The vault mode is {@code disk}.
     * The vault path is empty.
     * The pool maximum is {@code 4}.
     * The air-gap flag is false and is not yet known from a file.
     * The AMWP flag starts enabled.
     * AMWP is the framed protocol on the connection.
     * The mood-shape id is empty.
     * The burner-key flag is false.
     */
    public AetherisYaml() {
        this.title = DEFAULT_TITLE;
        this.vaultMode = MODE_DISK;
        this.vaultPath = "";
        this.poolMax = 4;
        this.airGap = false;
        this.amwpEnabled = true;
        this.moodShape = "";
        this.burnerCloud = false;
    }

    /**
     * The method returns the window title stored on this object.
     *
     * @return The return value is the title string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * The method stores the window title.
     * A null title becomes {@code Aetheris Shell}.
     * A title whose trimmed length is zero becomes {@code Aetheris Shell}.
     * Any other title is stored after trim.
     *
     * @param title The title is the window text from the file, and it may be null.
     */
    public void setTitle(String title) {
        if (title == null || title.trim().length() == 0) {
            this.title = DEFAULT_TITLE;
        } else {
            this.title = title.trim();
        }
    }

    /**
     * The method returns the vault mode stored on this object.
     * The mode is {@code ram} or {@code disk}.
     *
     * @return The return value is that mode token.
     */
    public String getVaultMode() {
        return vaultMode;
    }

    /**
     * The method returns the vault path template from {@code vault.path}.
     * An empty string means the file did not set a path.
     *
     * @return The return value is the path template.
     */
    public String getVaultPath() {
        return vaultPath;
    }

    /**
     * The method returns the stored maximum size of the Hikari connection pool.
     * Hikari is the pool {@link org.aetheris.vault.VaultStore} opens for the vault database.
     * The constructor stores {@code 4}.
     *
     * @return The return value is that maximum.
     */
    public int getPoolMax() {
        return poolMax;
    }

    /**
     * The method returns the stored air-gap flag.
     * The flag stays false when {@code network.air-gap} is missing.
     * A caller that needs the refusal uses {@link #refusesCloud}.
     *
     * @return The return value is the stored flag.
     */
    public boolean isAirGap() {
        return airGap;
    }

    /**
     * The method returns the air-gap word a caller can pass onward.
     * A missing {@code network.air-gap} key returns {@code unset}.
     * A stored true returns {@code true}.
     * A stored false returns {@code false}.
     *
     * @return The return value is {@code true}, {@code false}, or {@code unset}.
     */
    public String airGapWord() {
        if (!airGapKnown) {
            return "unset";
        }
        return airGap ? "true" : "false";
    }

    /**
     * The method reports whether a cloud target is refused.
     * An explicit true refuses the target.
     * A missing key is unset and also refuses the target.
     * An explicit false allows the target.
     *
     * @return The return value is true when the key is missing or the stored flag is true.
     */
    public boolean refusesCloud() {
        return !airGapKnown || airGap;
    }

    /**
     * The {@code grokDoor} method reports whether this Grok case skips the air-gap refusal.
     * The provider token must be {@code grok}.
     * Text must follow the slash, so {@code modelPresent} is true.
     * The burner flag must be false.
     * A burner is an ephemeral request.
     * The word must be exactly {@code true}.
     * The word {@code unset} returns false.
     * The word {@code false} returns false.
     * Any other provider returns false.
     * The method does not write the word.
     * A false result leaves the caller on {@link #refusesCloud}.
     * The notebook and {@link org.aetheris.ssh.AmwpFacade} both call this method.
     *
     * @param provider The provider is the token before {@code /}.
     * @param modelPresent The flag is true when text follows {@code /}.
     * @param burner The flag is true for an ephemeral request, and true does not skip the refusal.
     * @param word The word is {@code true}, {@code false}, or {@code unset}.
     * @return The return value is true only for that one Grok case.
     */
    public static boolean grokDoor(String provider, boolean modelPresent, boolean burner, String word) {
        if (!"grok".equals(provider)) {
            return false;
        }
        if (!modelPresent) {
            return false;
        }
        if (burner) {
            return false;
        }
        return "true".equals(word);
    }

    /**
     * The {@code poolDoor} method reports whether this pool case skips the air-gap refusal.
     * The provider token must be {@code pool}.
     * The method does not require a model id.
     * The burner flag must be false.
     * The word must be exactly {@code true}.
     * The word {@code unset} returns false.
     * The word {@code false} returns false.
     * The method does not write the word.
     *
     * @param provider The provider is the token before {@code /}.
     * @param burner The flag is true for an ephemeral request, and true does not skip the refusal.
     * @param word The word is {@code true}, {@code false}, or {@code unset}.
     * @return The return value is true only for that pool case.
     */
    public static boolean poolDoor(String provider, boolean burner, String word) {
        if (!"pool".equals(provider)) {
            return false;
        }
        if (burner) {
            return false;
        }
        return "true".equals(word);
    }

    /**
     * The method reports whether a burner may use a cloud target.
     * A burner is an ephemeral request.
     * The value comes from {@code notebook.burner-key}.
     * A missing key leaves the flag false.
     * A line that does not start with {@code burner-key:} is ignored.
     * The words {@code true}, {@code yes}, and {@code 1} set the flag.
     * The flag is not the Grok API key.
     * {@link #refusesCloud} still refuses a cloud target on its own.
     *
     * @return The return value is true when the file allows the burner hop.
     */
    public boolean burnerCloudAllowed() {
        return burnerCloud;
    }

    /**
     * The method returns the shaper id from {@code notebook.mood-shape}.
     * A line that does not start with {@code mood-shape:} is ignored.
     * A missing key returns an empty string.
     * A null field also returns an empty string.
     * An empty id means the Ollama model already in use.
     *
     * @return The return value is the id, or empty when the key is absent.
     */
    public String moodShapeId() {
        if (moodShape == null) {
            return "";
        }
        return moodShape;
    }

    /**
     * The method reads {@code .aetheris.yaml} from the process user's home directory.
     * This path is the server copy.
     * It is not the lookup {@link #load} uses for the shell.
     * A missing file returns a new object with the constructor defaults.
     * A present file is parsed by {@link #readFile}.
     *
     * @return The return value is the parsed file, or the defaults when the file is missing.
     * @throws IllegalStateException The method throws this when the file contains a tab.
     */
    public static AetherisYaml readProcessHome() {
        File home = homeFile();
        if (home.isFile()) {
            return readFile(home);
        }
        return new AetherisYaml();
    }

    /**
     * The method writes {@code .aetheris.yaml} in the home directory when that file is absent.
     * An existing file is left as it stands.
     * The bytes are {@link #HOME_DEFAULTS}.
     * {@code network.air-gap} in that text is explicit {@code true}.
     * The method does not copy a repository file.
     * The method does not copy an install-folder file.
     * A file created by another start is left as it stands.
     * A write error prints {@code Unable to write } plus the path and the exception message.
     * {@link #readProcessHome} still reads this path.
     */
    public static void ensureHomeFile() {
        File home = homeFile();
        if (home.isFile()) {
            return;
        }
        try {
            Files.write(home.toPath(), HOME_DEFAULTS.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        } catch (FileAlreadyExistsException ignored) {
            return;
        } catch (IOException ex) {
            System.err.println("Unable to write " + home.getAbsolutePath() + ": " + ex.getMessage());
        }
    }

    /**
     * The method returns {@code .aetheris.yaml} in the process user's home directory.
     *
     * @return The return value is that file.
     */
    private static File homeFile() {
        return new File(System.getProperty("user.home"), ".aetheris.yaml");
    }

    /**
     * The method reports whether the shell may send an AMWP Init frame.
     * AMWP is the framed protocol on the connection.
     * The constructor starts the flag enabled.
     *
     * @return The return value is the stored flag.
     */
    public boolean isAmwpEnabled() {
        return amwpEnabled;
    }

    /**
     * The method reports whether the vault mode is {@code ram}.
     * The {@code ram} mode is the in-memory vault used for a test.
     * The default mode is {@code disk}.
     * The comparison ignores case.
     *
     * @return The return value is true when the stored mode is {@code ram}.
     */
    public boolean isRamVault() {
        return MODE_RAM.equalsIgnoreCase(vaultMode);
    }

    /**
     * The method loads the shell file from the first path that exists.
     * The first path is the file named after {@code --aetheris-yaml} when that flag is present and the file exists.
     * The second path is {@code aetheris.yaml} in the working directory.
     * The third path is {@code .aetheris.yaml} in the home directory.
     * A null argument array skips the flag and continues with the two files.
     * A missing file at every path returns the constructor defaults.
     *
     * @param args The array is the {@code main} argument list, and it may be null.
     * @return The return value is the loaded file, or the defaults when no file exists.
     * @throws IllegalStateException The method throws this when the chosen file contains a tab.
     */
    public static AetherisYaml load(String[] args) {
        File explicit = parseFlag(args);
        if (explicit != null && explicit.isFile()) {
            return readFile(explicit);
        }
        File cwd = new File("aetheris.yaml");
        if (cwd.isFile()) {
            return readFile(cwd);
        }
        File home = homeFile();
        if (home.isFile()) {
            return readFile(home);
        }
        return new AetherisYaml();
    }

    /**
     * The method finds the path after {@code --aetheris-yaml}.
     * A null array returns null.
     * A flag with no following argument returns null.
     * A missing flag returns null.
     *
     * @param args The array is the {@code main} argument list, and it may be null.
     * @return The return value is the file named by the flag, or null when the flag is absent.
     */
    private static File parseFlag(String[] args) {
        if (args == null) {
            return null;
        }
        for (int i = 0; i < args.length; i++) {
            if ("--aetheris-yaml".equals(args[i]) && i + 1 < args.length) {
                return new File(args[i + 1]);
            }
        }
        return null;
    }

    /**
     * The method parses one {@code aetheris.yaml} file.
     * A line that contains a tab throws {@link IllegalStateException}.
     * The exception message names the absolute path.
     * A blank line is skipped.
     * A line whose trimmed text starts with {@code #} is skipped.
     * Keys are read only under a top-level {@code aetheris:} line.
     * Another top-level key turns that section off.
     * A line indented two spaces that ends with {@code :} and contains no space is a section name.
     * The known section names are {@code ui}, {@code vault}, {@code network}, {@code amwp}, and {@code notebook}.
     * A key line is indented at least four spaces.
     * A line indented less than four spaces, and not a section, is skipped.
     * An unknown key is ignored.
     * An {@link IOException} while reading returns a new object with the constructor defaults.
     * That failure discards any keys already applied.
     *
     * @param file The file is the yaml file to read.
     * @return The return value is the parsed object, or the defaults when the read fails.
     * @throws IllegalStateException The method throws this when a line contains a tab.
     */
    static AetherisYaml readFile(File file) {
        AetherisYaml yaml = new AetherisYaml();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            boolean inAetheris = false;
            String section = "";
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf('\t') >= 0) {
                    throw new IllegalStateException("aetheris.yaml: tabs are not allowed (G-20). File: "
                            + file.getAbsolutePath());
                }
                String trimmed = line.trim();
                if (trimmed.length() == 0 || trimmed.startsWith("#")) {
                    continue;
                }
                int indent = indentOf(line);
                if (indent == 0 && trimmed.startsWith("aetheris:")) {
                    inAetheris = true;
                    section = "";
                    continue;
                }
                if (indent == 0) {
                    inAetheris = false;
                    section = "";
                    continue;
                }
                if (!inAetheris) {
                    continue;
                }
                if (indent == 2 && trimmed.endsWith(":") && trimmed.indexOf(' ') < 0) {
                    section = trimmed.substring(0, trimmed.length() - 1);
                    continue;
                }
                if (indent < 4) {
                    continue;
                }
                applyKey(yaml, section, trimmed);
            }
        } catch (IllegalStateException tabs) {
            throw tabs;
        } catch (IOException ignored) {
            return new AetherisYaml();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignoredClose) {
                }
            }
        }
        return yaml;
    }

    /**
     * The method applies one known key in the current section.
     * {@code ui.title} is stored through {@link #setTitle}.
     * {@code vault.mode} {@code ram} stores {@code ram}.
     * Any other vault mode stores {@code disk}.
     * {@code vault.path} stores the unquoted path.
     * {@code vault.pool-max} stores the integer.
     * A pool maximum that is not an integer stores {@code 4}.
     * A pool maximum below 1 stores {@code 1}.
     * {@code network.air-gap} marks the flag known and stores the boolean.
     * {@code amwp.enabled} stores the boolean.
     * {@code notebook.mood-shape} stores the unquoted id.
     * {@code notebook.burner-key} stores the boolean.
     * Any other line is ignored.
     *
     * @param yaml The yaml object receives the value.
     * @param section The section is the current two-space name.
     * @param trimmed The trimmed line is the key line with its indent removed.
     */
    private static void applyKey(AetherisYaml yaml, String section, String trimmed) {
        if ("ui".equals(section) && trimmed.startsWith("title:")) {
            yaml.setTitle(unquote(trimmed.substring("title:".length()).trim()));
            return;
        }
        if ("vault".equals(section) && trimmed.startsWith("mode:")) {
            String mode = unquote(trimmed.substring("mode:".length()).trim()).toLowerCase();
            if (MODE_RAM.equals(mode)) {
                yaml.vaultMode = MODE_RAM;
            } else {
                yaml.vaultMode = MODE_DISK;
            }
            return;
        }
        if ("vault".equals(section) && trimmed.startsWith("path:")) {
            yaml.vaultPath = unquote(trimmed.substring("path:".length()).trim());
            return;
        }
        if ("vault".equals(section) && trimmed.startsWith("pool-max:")) {
            try {
                yaml.poolMax = Integer.parseInt(unquote(trimmed.substring("pool-max:".length()).trim()));
            } catch (NumberFormatException ignored) {
                yaml.poolMax = 4;
            }
            if (yaml.poolMax < 1) {
                yaml.poolMax = 1;
            }
            return;
        }
        if ("network".equals(section) && trimmed.startsWith("air-gap:")) {
            yaml.airGapKnown = true;
            yaml.airGap = parseBool(unquote(trimmed.substring("air-gap:".length()).trim()));
            return;
        }
        if ("amwp".equals(section) && trimmed.startsWith("enabled:")) {
            yaml.amwpEnabled = parseBool(unquote(trimmed.substring("enabled:".length()).trim()));
            return;
        }
        if ("notebook".equals(section) && trimmed.startsWith("mood-shape:")) {
            yaml.moodShape = unquote(trimmed.substring("mood-shape:".length()).trim());
            return;
        }
        if ("notebook".equals(section) && trimmed.startsWith("burner-key:")) {
            yaml.burnerCloud = parseBool(unquote(trimmed.substring("burner-key:".length()).trim()));
        }
    }

    /**
     * The method reads a boolean token.
     * {@code true} and {@code yes} match in any letter case.
     * The token {@code 1} matches only that exact character.
     * Any other token returns false.
     *
     * @param raw The raw value is the text after the colon.
     * @return The return value is true for {@code true}, {@code yes}, or {@code 1}.
     */
    private static boolean parseBool(String raw) {
        return "true".equalsIgnoreCase(raw) || "yes".equalsIgnoreCase(raw) || "1".equals(raw);
    }

    /**
     * The method strips one pair of double quotes from a value.
     * The quotes are removed only when the value starts and ends with {@code "} and has length at least 2.
     * Any other value is returned unchanged.
     * Escape sequences are not decoded.
     *
     * @param value The value is the text after the colon.
     * @return The return value is the value with that one pair removed, or the original text.
     */
    private static String unquote(String value) {
        if (value.startsWith("\"") && value.endsWith("\"") && value.length() >= 2) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    /**
     * The method counts the leading spaces on a line.
     * A tab is not counted as indent.
     *
     * @param line The line is one file line, including its leading spaces.
     * @return The return value is the number of leading spaces.
     */
    private static int indentOf(String line) {
        int n = 0;
        while (n < line.length() && line.charAt(n) == ' ') {
            n++;
        }
        return n;
    }
}
/*****************************************************************************************
 * End of Class: AetherisYaml.java 083026
 ****************************************************************************************/
