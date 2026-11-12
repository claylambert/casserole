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

package org.casserole.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.roux.session.RegionOwner;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

/**
 * The {@code UserAccounts} type loads the SSH password accounts for the server.
 * SSH is the secure-shell protocol a client uses to connect.
 * The load reads {@code ./users.yaml}, then {@code user.home/.casserole/users.yaml}, then the classpath resource {@code users.yaml}.
 * The last step uses the {@code casserole.ssh} username and password from {@link CasseroleProperties}.
 * The successful load stores a non-empty list of {@link UserEntry} rows and a source label.
 * The type refuses an empty file, a root that is not a map, a missing {@code users} key, a {@code users} value that is not a list, an empty list, a duplicate name, a file with no valid rows, an unreadable file, and an unusable fallback pair.
 * The load skips a non-map row, a blank name, a name {@code RegionOwner.isValid} rejects, and an empty password, and it prints each skip to standard error.
 *
 * @author clayl (2026-08-26)
 */
@Component
public class UserAccounts {

    /**
     * The loaded account rows.
     * The list is the list chosen at construction.
     */
    private final List<UserEntry> entries;

    /**
     * The label of the place those rows came from.
     */
    private final String source;

    /**
     * The constructor loads accounts through {@link #loadCascade}.
     * The stored list and the source label come from that load.
     *
     * @param properties The configuration that supplies the fallback username and password.
     * @throws IllegalStateException The chosen file or the fallback pair cannot be used.
     */
    public UserAccounts(CasseroleProperties properties) {
        Loaded loaded = loadCascade(properties);
        this.entries = loaded.entries;
        this.source = loaded.source;
    }

    /**
     * The {@code getEntries} method returns the loaded account rows.
     * The method returns the same list stored at construction.
     * A successful construction leaves that list non-empty.
     *
     * @return The loaded {@link UserEntry} rows.
     */
    public List<UserEntry> getEntries() {
        return entries;
    }

    /**
     * The {@code getSource} method returns the label of the place the rows came from.
     * The file load uses the absolute path.
     * The classpath load uses {@code classpath:users.yaml}.
     * The fallback uses {@code casserole.ssh}.
     *
     * @return The origin label.
     */
    public String getSource() {
        return source;
    }

    /**
     * The {@code authenticate} method reports whether one row matches the name and the password.
     * The method returns false when the username is null.
     * The method returns false when the password is null.
     * The match is an exact {@code equals} on the name and on the password.
     * The method returns false when no row matches.
     *
     * @param username The name to match.
     * @param password The password to match.
     * @return Whether a stored row matches both values.
     */
    public boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        for (int i = 0; i < entries.size(); i++) {
            UserEntry row = entries.get(i);
            if (username.equals(row.getName()) && password.equals(row.getPassword())) {
                return true;
            }
        }
        return false;
    }

    /**
     * The {@code firstName} method returns the first account name.
     * The banner uses that name.
     * The method returns an empty string when the list is empty.
     *
     * @return The first {@link UserEntry} name, or an empty string.
     */
    public String firstName() {
        if (entries.isEmpty()) {
            return "";
        }
        return entries.get(0).getName();
    }

    /**
     * The {@code loadCascade} method loads the first account file that exists.
     * The method reads {@code ./users.yaml} when that path is a regular file.
     * The method otherwise reads {@code user.home/.casserole/users.yaml} when that path is a regular file.
     * The method otherwise reads the classpath resource {@code users.yaml} and labels it {@code classpath:users.yaml}.
     * The method otherwise uses {@link #fallback}.
     * The classpath stream is closed after the parse.
     * The method ignores a close failure.
     *
     * @param properties The configuration that supplies the fallback username and password.
     * @return The loaded rows and their source label.
     * @throws IllegalStateException The chosen file or the fallback pair cannot be used.
     */
    private static Loaded loadCascade(CasseroleProperties properties) {
        Path cwd = Path.of("users.yaml");
        if (Files.isRegularFile(cwd)) {
            return loadFile(cwd);
        }
        String home = System.getProperty("user.home");
        Path userFile = Path.of(home, ".casserole", "users.yaml");
        if (Files.isRegularFile(userFile)) {
            return loadFile(userFile);
        }
        InputStream packed = UserAccounts.class.getClassLoader().getResourceAsStream("users.yaml");
        if (packed != null) {
            try {
                return parseStream(packed, "classpath:users.yaml");
            } finally {
                try {
                    packed.close();
                } catch (IOException ignored) {
                    // ignore
                }
            }
        }
        return fallback(properties);
    }

    /**
     * The {@code loadFile} method reads one account file and parses it.
     * The source label is the absolute path.
     * The stream is closed after the parse.
     * The method ignores a close failure.
     *
     * @param path The account file to read.
     * @return The loaded rows and that absolute path.
     * @throws IllegalStateException The file cannot be read. The message starts with {@code cannot read users.yaml:}.
     */
    private static Loaded loadFile(Path path) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(path.toFile());
            return parseStream(in, path.toAbsolutePath().toString());
        } catch (IOException ex) {
            throw new IllegalStateException("cannot read users.yaml: " + path + " — " + ex.getMessage(), ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                    // ignore
                }
            }
        }
    }

    /**
     * The {@code parseStream} method reads one YAML document into account rows.
     * YAML is the indentation text format of the file.
     * The root must be a map that holds a {@code users} list.
     * The method throws {@link IllegalStateException} when the document is empty, when the root is not a map, when {@code users} is missing, when {@code users} is not a list, when that list is empty, when a name is duplicated, or when no row is valid.
     * The thrown message includes the source label.
     * The method skips a non-map row.
     * The method skips a blank name.
     * The method skips a name that {@code RegionOwner.isValid} rejects.
     * The method skips an empty password.
     * The method prints each skip to standard error.
     * The {@link #stringOf} method trims a kept name and password.
     * The method stores the first copy of a name as a {@link UserEntry}.
     *
     * @param in The YAML bytes to read.
     * @param source The label stored with the rows and written into failure messages.
     * @return The valid rows and the same source label.
     * @throws IllegalStateException The document has no usable account list.
     */
    private static Loaded parseStream(InputStream in, String source) {
        Yaml yaml = new Yaml();
        Object root = yaml.load(in);
        if (root == null) {
            throw new IllegalStateException("users.yaml empty: " + source);
        }
        if (!(root instanceof Map)) {
            throw new IllegalStateException("users.yaml must be a map: " + source);
        }
        @SuppressWarnings("unchecked")
        Map<Object, Object> map = (Map<Object, Object>) root;
        Object usersObj = map.get("users");
        if (usersObj == null) {
            throw new IllegalStateException("users.yaml missing users: " + source);
        }
        if (!(usersObj instanceof List)) {
            throw new IllegalStateException("users.yaml users must be a list: " + source);
        }
        @SuppressWarnings("unchecked")
        List<Object> rows = (List<Object>) usersObj;
        if (rows.isEmpty()) {
            throw new IllegalStateException("users.yaml users list empty: " + source);
        }
        List<UserEntry> out = new ArrayList<UserEntry>();
        Set<String> seen = new HashSet<String>();
        for (int i = 0; i < rows.size(); i++) {
            Object rowObj = rows.get(i);
            if (!(rowObj instanceof Map)) {
                System.err.println("users.yaml skip non-map row in " + source);
                continue;
            }
            @SuppressWarnings("unchecked")
            Map<Object, Object> row = (Map<Object, Object>) rowObj;
            String name = stringOf(row.get("name"));
            String password = stringOf(row.get("password"));
            if (name.length() == 0) {
                System.err.println("users.yaml skip blank name in " + source);
                continue;
            }
            if (!RegionOwner.isValid(name)) {
                System.err.println("users.yaml skip invalid name '" + name + "' in " + source);
                continue;
            }
            if (password.length() == 0) {
                System.err.println("users.yaml skip empty password for " + name + " in " + source);
                continue;
            }
            if (seen.contains(name)) {
                throw new IllegalStateException("users.yaml duplicate name: " + name + " (" + source + ")");
            }
            seen.add(name);
            out.add(new UserEntry(name, password));
        }
        if (out.isEmpty()) {
            throw new IllegalStateException("users.yaml has no valid users: " + source);
        }
        return new Loaded(out, source);
    }

    /**
     * The {@code fallback} method builds one account from {@code casserole.ssh}.
     * The method uses an empty name and an empty password when the SSH section is null.
     * The method uses an empty password when the stored password is null.
     * The method throws {@link IllegalStateException} when {@code RegionOwner.isValid} rejects the name or the password is empty.
     * The message is {@code casserole.ssh fallback user/password is not usable}.
     * The source label is {@code casserole.ssh}.
     *
     * @param properties The configuration that supplies the username and password.
     * @return One {@link UserEntry} and the source label {@code casserole.ssh}.
     * @throws IllegalStateException The fallback name or password cannot be used.
     */
    private static Loaded fallback(CasseroleProperties properties) {
        CasseroleProperties.Ssh ssh = properties.getSsh();
        String name = ssh == null ? "" : ssh.getUsername();
        String password = ssh == null || ssh.getPassword() == null ? "" : ssh.getPassword();
        if (!RegionOwner.isValid(name) || password.length() == 0) {
            throw new IllegalStateException("casserole.ssh fallback user/password is not usable");
        }
        List<UserEntry> out = new ArrayList<UserEntry>();
        out.add(new UserEntry(name, password));
        return new Loaded(out, "casserole.ssh");
    }

    /**
     * The {@code stringOf} method turns one YAML value into trimmed text.
     * The method returns an empty string when the value is null.
     * The method returns {@code String.valueOf} trimmed for any other value.
     *
     * @param value The YAML scalar to read.
     * @return The trimmed text, or an empty string.
     */
    private static String stringOf(Object value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value).trim();
    }

    /**
     * The {@code Loaded} type is one finished account load.
     * The type holds the rows and the source label.
     */
    private static final class Loaded {
        /**
         * The account rows from this load.
         */
        private final List<UserEntry> entries;

        /**
         * The label of the place those rows came from.
         */
        private final String source;

        /**
         * The constructor stores the rows and the source label.
         *
         * @param entries The account rows to keep.
         * @param source The origin label to keep.
         */
        private Loaded(List<UserEntry> entries, String source) {
            this.entries = entries;
            this.source = source;
        }
    }
}
/*****************************************************************************************
 * End of Class: UserAccounts.java 082626
 ****************************************************************************************/
