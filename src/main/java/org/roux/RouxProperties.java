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

package org.roux;

import org.springframework.boot.context.properties.ConfigurationProperties;

// @aa-sdd PK-970 docs/specs/java/org/casserole/ssh/shell/shell.spec.md FINESSE:f-170
/**
 * The RouxProperties type holds the settings whose prefix is {@code roux}.
 * The page size starts at {@code 1048576} bytes.
 * The maximum frame size starts at {@code 0}.
 * The resident page cap starts at {@code 2}.
 * The durability text starts as {@code commit}.
 * A {@link Spill} object, a {@link Crypto} object, and a {@link Session} object are created with the settings.
 *
 * A caller reads a value or replaces it.
 * {@link #normalizedDurability} returns {@code hot-only}, {@code commit}, or {@code append}.
 * {@link #isRamOnly} is true for hot-only durability, a missing spill block, or a spill block that is off.
 * A missing session block keeps persist, restore, and exclusive on.
 * The setters store the value they receive.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#RouxProperties roux-design.md
 *
 * @author clayl (2026-08-25)
 */
@ConfigurationProperties(prefix = "roux")
// @aa-sdd PK-688 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-515 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-811 docs/specs/common/roux.spec.md
public class RouxProperties {

    private int pageBytes = 1048576;
    private int maxFrameBytes = 0;
    private int residentPages = 2;
    private String durability = "commit";
    private Spill spill = new Spill();
    private Crypto crypto = new Crypto();
    private Session session = new Session();

    /**
     * The method returns the stored page size.
     * The starting value is {@code 1048576} bytes.
     *
     * @return The size is the page capacity in bytes.
     */
    public int getPageBytes() {
        return pageBytes;
    }

    /**
     * The method stores the page size.
     *
     * @param pageBytes The page size is the capacity in bytes.
     */
    public void setPageBytes(int pageBytes) {
        this.pageBytes = pageBytes;
    }

    /**
     * The method returns the stored maximum frame size.
     * The starting value is {@code 0}.
     *
     * @return The size is the stored maximum, and {@code 0} is the starting value.
     */
    public int getMaxFrameBytes() {
        return maxFrameBytes;
    }

    /**
     * The method stores the maximum frame size.
     *
     * @param maxFrameBytes The size is the maximum frame size to store.
     */
    public void setMaxFrameBytes(int maxFrameBytes) {
        this.maxFrameBytes = maxFrameBytes;
    }

    /**
     * The method returns the stored resident page cap.
     * The starting value is {@code 2}.
     *
     * @return The cap is the stored resident page count.
     */
    public int getResidentPages() {
        return residentPages;
    }

    /**
     * The method stores the resident page cap.
     *
     * @param residentPages The cap is the resident page count to store.
     */
    public void setResidentPages(int residentPages) {
        this.residentPages = residentPages;
    }

    /**
     * The method returns the stored durability text.
     * The starting value is {@code commit}.
     *
     * @return The text is the durability value last stored.
     */
    public String getDurability() {
        return durability;
    }

    /**
     * The method stores the durability text.
     *
     * @param durability The text is the durability value to store.
     */
    public void setDurability(String durability) {
        this.durability = durability;
    }

    /**
     * The method returns one of three durability names.
     * A null value returns {@code commit}.
     * The stored text is trimmed and compared in lower case.
     * {@code hot-only} and {@code h0} return {@code hot-only}.
     * {@code append} and {@code h2} return {@code append}.
     * Every other value returns {@code commit}.
     *
     * @return The name is {@code hot-only}, {@code commit}, or {@code append}.
     */
    public String normalizedDurability() {
        if (durability == null) {
            return "commit";
        }
        String d = durability.trim().toLowerCase();
        if (d.equals("hot-only") || d.equals("h0")) {
            return "hot-only";
        }
        if (d.equals("append") || d.equals("h2")) {
            return "append";
        }
        return "commit";
    }

    /**
     * The method returns the spill settings.
     *
     * @return The settings are the {@link Spill} object.
     */
    public Spill getSpill() {
        return spill;
    }

    /**
     * The method stores the spill settings.
     *
     * @param spill The settings are the {@link Spill} object to store.
     */
    public void setSpill(Spill spill) {
        this.spill = spill;
    }

    /**
     * The method returns the crypto settings.
     *
     * @return The settings are the {@link Crypto} object.
     */
    public Crypto getCrypto() {
        return crypto;
    }

    /**
     * The method stores the crypto settings.
     *
     * @param crypto The settings are the {@link Crypto} object to store.
     */
    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    /**
     * The method returns the session settings.
     *
     * @return The settings are the {@link Session} object.
     */
    public Session getSession() {
        return session;
    }

    /**
     * The method stores the session settings.
     *
     * @param session The settings are the {@link Session} object to store.
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * The method reports whether these settings stay in memory.
     * Hot-only durability returns true.
     * A null spill block returns true.
     * A spill block that is not enabled returns true.
     *
     * @return The flag is true when the settings do not use a pack.
     */
    public boolean isRamOnly() {
        if ("hot-only".equals(normalizedDurability())) {
            return true;
        }
        return getSpill() == null || !getSpill().isEnabled();
    }

    /**
     * The method reports the opposite of {@link #isRamOnly}.
     *
     * @return The flag is true when {@link #isRamOnly} is false.
     */
    public boolean isDiskBacked() {
        return !isRamOnly();
    }

    /**
     * The method reports whether a close keeps the region files.
     * A null session block returns true.
     *
     * @return The flag is the persist value, and a missing session means true.
     */
    public boolean sessionPersist() {
        return session == null || session.isPersist();
    }

    /**
     * The method reports whether an open may restore a pack.
     * A null session block returns true.
     *
     * @return The flag is the restore value, and a missing session means true.
     */
    public boolean sessionRestore() {
        return session == null || session.isRestore();
    }

    /**
     * The method reports the exclusive flag.
     * A null session block returns true.
     *
     * @return The flag is the exclusive value, and a missing session means true.
     */
    public boolean sessionExclusive() {
        return session == null || session.isExclusive();
    }

    /**
     * The Spill type holds the pack settings.
     * The enabled flag starts as true.
     * The directory starts as the {@code user.home} property plus {@code /.casserole/roux}.
     * The alignment starts at {@code 4096} bytes.
     * A caller reads a value or replaces it.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxProperties.Spill roux-design.md
     */
    // @aa-sdd PK-500 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-502 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-970 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
    // @aa-sdd PK-503 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-978 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-512 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-513 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-706 docs/specs/common/roux.spec.md
    // @aa-sdd GK-2303 roux-1.1-paging.md
    // @aa-sdd PK-803 docs/specs/common/roux.spec.md
    // @aa-sdd PK-979 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-515 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-518 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-519 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-521 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-522 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-827 docs/specs/common/roux.spec.md
    // @aa-sdd PK-504 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-516 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-808 docs/specs/common/roux.spec.md
    // @aa-sdd PK-809 docs/specs/common/roux.spec.md
    // @aa-sdd PK-810 docs/specs/common/roux.spec.md
    public static class Spill {

        private boolean enabled = true;
        private String dir = System.getProperty("user.home") + "/.casserole/roux";
        private int alignBytes = 4096;

        /**
         * The method returns the enabled flag.
         * The starting value is true.
         *
         * @return The flag is true when spill is enabled.
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * The method stores the enabled flag.
         *
         * @param enabled The flag is true when spill is enabled.
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * The method returns the spill directory.
         *
         * @return The directory is the stored path.
         */
        public String getDir() {
            return dir;
        }

        /**
         * The method stores the spill directory.
         *
         * @param dir The directory is the path to store.
         */
        public void setDir(String dir) {
            this.dir = dir;
        }

        /**
         * The method returns the alignment.
         * The starting value is {@code 4096} bytes.
         *
         * @return The alignment is the stored byte multiple.
         */
        public int getAlignBytes() {
            return alignBytes;
        }

        /**
         * The method stores the alignment.
         *
         * @param alignBytes The alignment is the byte multiple to store.
         */
        public void setAlignBytes(int alignBytes) {
            this.alignBytes = alignBytes;
        }
    }

    /**
     * The Crypto type holds the encryption flag.
     * The flag starts as false.
     * An enabled flag is refused by {@link org.roux.crypto.CryptoSink}.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxProperties.Crypto roux-design.md
     */
    // @aa-sdd PK-970 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
    public static class Crypto {

        private boolean enabled = false;

        /**
         * The method returns the encryption flag.
         * The starting value is false.
         *
         * @return The flag is true when encryption is enabled.
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * The method stores the encryption flag.
         *
         * @param enabled The flag is true when encryption is enabled.
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    /**
     * The Session type holds the persist, restore, and exclusive flags.
     * Each flag starts as true.
     * A caller reads a flag or replaces it.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxProperties.Session roux-design.md
     */
    // @aa-sdd PK-515 docs/specs/java/org/roux/roux.spec.md
    public static class Session {

        private boolean persist = true;
        private boolean restore = true;
        private boolean exclusive = true;

        /**
         * The method returns the persist flag.
         * The starting value is true.
         *
         * @return The flag is true when a close keeps the region files.
         */
        public boolean isPersist() {
            return persist;
        }

        /**
         * The method stores the persist flag.
         *
         * @param persist The flag is true when a close keeps the region files.
         */
        public void setPersist(boolean persist) {
            this.persist = persist;
        }

        /**
         * The method returns the restore flag.
         * The starting value is true.
         *
         * @return The flag is true when an open may restore a pack.
         */
        public boolean isRestore() {
            return restore;
        }

        /**
         * The method stores the restore flag.
         *
         * @param restore The flag is true when an open may restore a pack.
         */
        public void setRestore(boolean restore) {
            this.restore = restore;
        }

        /**
         * The method returns the exclusive flag.
         * The starting value is true.
         *
         * @return The flag is the stored exclusive value.
         */
        public boolean isExclusive() {
            return exclusive;
        }

        /**
         * The method stores the exclusive flag.
         *
         * @param exclusive The flag is the exclusive value to store.
         */
        public void setExclusive(boolean exclusive) {
            this.exclusive = exclusive;
        }
    }
}
/*****************************************************************************************
 * End of Class: RouxProperties.java 082626
 ****************************************************************************************/
