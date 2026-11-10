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

// @aa-sdd PK-0746 PK-0782 docs/specs/java/org/roux/roux.spec.md
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
 * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
 * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
 * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
 * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
 * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
 * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
 * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
 * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
 * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
 * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
 * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
 * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
 * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
 * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
 * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
 * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
 * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
 * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
 * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
 *
 * @author clayl (2026-08-25)
 */
@ConfigurationProperties(prefix = "roux")
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
     */
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
     */
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
     */
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
