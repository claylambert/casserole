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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.casserole.chat.ChatSession;
import org.casserole.chat.ShellMode;
import org.casserole.chat.cmd.ChatSessionCommand;
import org.casserole.chat.cmd.CommandContext;
import org.casserole.chat.cmd.CommandProcessor;
import org.casserole.chat.cmd.StatusCommand;
import org.casserole.config.CasseroleProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.roux.crypto.CryptoSink;
import org.roux.id.RouxIdGenerator;
import org.roux.model.Role;
import org.roux.session.OpenResult;
import org.roux.session.RegionManifest;
import org.roux.spill.RegionPack;
import org.roux.spring.RouxChatMemory;
import org.roux.tel.RouxCounters;
import org.roux.token.HeuristicTokenCounter;

/**
 * The class checks the named-pack shelf on one login.
 * One window holds one live pack.
 * A named folder sits beside the username folder.
 * The lock stays on the username directory.
 *
 * @author clayl (2026-11-22)
 */
class RouxShelfTest {

    private final ArrayList<Path> dirs = new ArrayList<Path>();
    private final ArrayList<RouxEngine> engines = new ArrayList<RouxEngine>();

    /**
     * The method closes every engine the test opened and deletes the temporary directories.
     *
     * @throws IOException The directory delete can fail.
     */
    @AfterEach
    void cleanup() throws IOException {
        for (int i = 0; i < engines.size(); i++) {
            engines.get(i).shutdown(true);
        }
        engines.clear();
        for (int i = 0; i < dirs.size(); i++) {
            deleteTree(dirs.get(i));
        }
        dirs.clear();
    }

    /**
     * The method checks that {@code foo} is a sibling pack with its own files.
     * The owner is the username.
     * The lock file stays on the username directory.
     *
     * @throws IOException The temporary directory can fail.
     */
    @Test
    void p1_01_namedFolderSitsBesideTheUser() throws IOException {
        Shelf shelf = shelf("p101", true, true);
        opened(shelf);
        OpenResult moved = shelf.engine.switchPack(shelf.user, "foo");
        assertEquals(OpenResult.NEW, moved.getKind());
        Path named = shelf.dir.resolve(shelf.user + "~foo");
        assertTrue(Files.isRegularFile(named.resolve("pages.bin")));
        assertTrue(Files.isRegularFile(named.resolve("manifest.bin")));
        RegionManifest man = RegionManifest.read(named);
        assertEquals(shelf.user, man.getOwner());
        assertTrue(Files.isRegularFile(shelf.dir.resolve(shelf.user).resolve(".lock")));
    }

    /**
     * The method checks a new name, a later restore, and the two banners.
     *
     * @throws IOException The temporary directory can fail.
     */
    @Test
    void p1_02_newThenRestoredBanner() throws IOException {
        Shelf shelf = shelf("p102", true, true);
        OpenResult opened = opened(shelf);
        Tape tape = tape(shelf, opened, "");
        ChatSessionCommand command = new ChatSessionCommand();
        command.execute(tape, "foo");
        assertEquals("New session foo\r\n", tape.written.toString());
        shelf.engine.appendMessage(shelf.user, Role.USER, 1710000000000L, 1, "hello");
        tape.written.setLength(0);
        command.execute(tape, "");
        assertEquals("", tape.written.toString());
        tape.written.setLength(0);
        command.execute(tape, "foo");
        assertEquals("Restored 1 messages for " + shelf.user + " session foo\r\n", tape.written.toString());
    }

    /**
     * The method checks that an empty name returns to the username pack.
     * The named sibling still holds the message that was left there.
     *
     * @throws IOException The temporary directory can fail.
     */
    @Test
    void p1_03_emptyNameReturnsToTheUser() throws IOException {
        Shelf shelf = shelf("p103", true, true);
        opened(shelf);
        shelf.engine.switchPack(shelf.user, "foo");
        shelf.engine.appendMessage(shelf.user, Role.USER, 1710000000000L, 1, "kept");
        OpenResult back = shelf.engine.switchPack(shelf.user, "");
        assertEquals(OpenResult.RESTORED, back.getKind());
        assertEquals(shelf.user, shelf.engine.liveSessionName(shelf.user));
        OpenResult again = shelf.engine.switchPack(shelf.user, "foo");
        assertEquals(OpenResult.RESTORED, again.getKind());
        assertEquals(1, again.getMessageCount());
    }

    /**
     * The method checks that a close and a later open load the username pack.
     * The named sibling stays on disk.
     * A second open of that user is refused.
     *
     * @throws IOException The temporary directory can fail.
     */
    @Test
    void p1_04_reconnectOpensTheUserPack() throws IOException {
        Shelf shelf = shelf("p104", true, true);
        opened(shelf);
        shelf.engine.appendMessage(shelf.user, Role.USER, 1710000000000L, 1, "home");
        shelf.engine.switchPack(shelf.user, "foo");
        shelf.engine.persistClose(shelf.user);
        OpenResult again = shelf.engine.openRegion(shelf.user);
        assertEquals(OpenResult.RESTORED, again.getKind());
        assertEquals(shelf.user, shelf.engine.liveSessionName(shelf.user));
        assertTrue(Files.isDirectory(shelf.dir.resolve(shelf.user + "~foo")));
        OpenResult second = shelf.engine.openRegion(shelf.user);
        assertEquals(OpenResult.REFUSED, second.getKind());
        assertTrue(second.getReason().startsWith("session already open for "));
    }

    /**
     * The method checks that clear wipes the live named pack and leaves the username bytes.
     *
     * @throws IOException The temporary directory can fail.
     */
    @Test
    void p1_05_clearWipesOnlyTheLivePack() throws IOException {
        Shelf shelf = shelf("p105", true, true);
        opened(shelf);
        shelf.engine.appendMessage(shelf.user, Role.USER, 1710000000000L, 1, "stay");
        shelf.engine.switchPack(shelf.user, "foo");
        byte[] userBytes = Files.readAllBytes(shelf.dir.resolve(shelf.user).resolve("pages.bin"));
        shelf.engine.appendMessage(shelf.user, Role.USER, 1710000000000L, 1, "gone");
        shelf.engine.clearRegion(shelf.user);
        assertArrayEquals(userBytes, Files.readAllBytes(shelf.dir.resolve(shelf.user).resolve("pages.bin")));
        assertFalse(Files.isRegularFile(shelf.dir.resolve(shelf.user + "~foo").resolve("pages.bin")));
    }

    /**
     * The method checks the live name on the username pack and on the named pack.
     *
     * @throws IOException The temporary directory can fail.
     */
    @Test
    void p1_06_statusNamesTheLivePack() throws IOException {
        Shelf shelf = shelf("p106", true, true);
        OpenResult opened = opened(shelf);
        assertEquals(shelf.user, shelf.engine.liveSessionName(shelf.user));
        shelf.engine.switchPack(shelf.user, "foo");
        assertEquals("foo", shelf.engine.liveSessionName(shelf.user));
        Tape tape = tape(shelf, opened, "");
        tape.written.setLength(0);
        new StatusCommand().execute(tape, "");
        String text = tape.written.toString();
        assertTrue(text.contains("ssh-user"));
        assertTrue(text.contains("session"));
        assertTrue(text.contains("foo"));
    }

    /**
     * The method checks that a manifest without a pack file refuses the switch.
     * The live segment stays the username pack.
     *
     * @throws IOException The temporary directory can fail.
     */
    @Test
    void p1_07_halfPresentRefuses() throws IOException {
        Shelf shelf = shelf("p107", true, true);
        opened(shelf);
        shelf.engine.appendMessage(shelf.user, Role.USER, 1710000000000L, 1, "live");
        Path bad = shelf.dir.resolve(shelf.user + "~bad");
        Files.createDirectories(bad);
        RegionManifest man = new RegionManifest();
        man.setPageBytes(512);
        man.setPageCount(1);
        man.setHotPageId(0);
        man.setOwner(shelf.user);
        man.setFormatVersion(2);
        RegionManifest.write(bad, man);
        OpenResult refused = shelf.engine.switchPack(shelf.user, "bad");
        assertEquals(OpenResult.REFUSED, refused.getKind());
        assertEquals(shelf.user, shelf.engine.liveSessionName(shelf.user));
        assertEquals(1, shelf.engine.lastMessageId(shelf.user));
    }

    /**
     * The method checks that a memory-only engine and a persist-off engine refuse the shelf.
     * Neither call creates a named sibling.
     *
     * @throws IOException The temporary directory can fail.
     */
    @Test
    void p1_08_modesWithoutAPackRefuse() throws IOException {
        Shelf ram = shelf("p108ram", false, true);
        ram.engine.openRegion(ram.user);
        OpenResult ramRefused = ram.engine.switchPack(ram.user, "foo");
        assertEquals(OpenResult.REFUSED, ramRefused.getKind());
        assertEquals("named session needs a spill pack", ramRefused.getReason());
        assertFalse(Files.exists(ram.dir.resolve(ram.user + "~foo")));

        Shelf quiet = shelf("p108off", true, false);
        quiet.engine.openRegion(quiet.user);
        OpenResult off = quiet.engine.switchPack(quiet.user, "foo");
        assertEquals(OpenResult.REFUSED, off.getKind());
        assertEquals("named session needs a spill pack", off.getReason());
        assertFalse(Files.exists(quiet.dir.resolve(quiet.user + "~foo")));
    }

    /**
     * The method checks that a single dot, the parent segment, a tilde, and a 33-byte name are refused.
     * The live segment stays the username pack.
     *
     * @throws IOException The temporary directory can fail.
     */
    @Test
    void refusedNamesLeaveTheLivePack() throws IOException {
        Shelf shelf = shelf("names", true, true);
        OpenResult first = opened(shelf);
        String parent = new String(new char[] { '.', '.' });
        String[] bad = new String[] { ".", parent, "a~b", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" };
        for (int i = 0; i < bad.length; i++) {
            OpenResult refused = shelf.engine.switchPack(shelf.user, bad[i]);
            assertEquals(OpenResult.REFUSED, refused.getKind());
            assertEquals("invalid session name", refused.getReason());
            assertEquals(shelf.user, shelf.engine.liveSessionName(shelf.user));
        }
        Tape tape = tape(shelf, first, "");
        tape.written.setLength(0);
        new ChatSessionCommand().execute(tape, "foo bar");
        assertEquals("invalid session name\r\n", tape.written.toString());
        assertEquals(shelf.user, shelf.engine.liveSessionName(shelf.user));
    }

    /**
     * The method opens a new username region.
     *
     * @param shelf The shelf holds the engine and the user.
     * @return The result is the new open.
     */
    private static OpenResult opened(Shelf shelf) {
        OpenResult opened = shelf.engine.openRegion(shelf.user);
        assertEquals(OpenResult.NEW, opened.getKind());
        return opened;
    }

    /**
     * The method builds a disk or memory engine over a temporary directory.
     *
     * @param name The name is the directory suffix.
     * @param spill The flag turns spill on or off.
     * @param persist The flag turns persist on or off.
     * @return The shelf is the engine, the user, and the directory.
     * @throws IOException The temporary directory can fail.
     */
    private Shelf shelf(String name, boolean spill, boolean persist) throws IOException {
        Path dir = Files.createTempDirectory("roux-shelf-" + name);
        dirs.add(dir);
        RouxProperties properties = new RouxProperties();
        properties.setResidentPages(4);
        properties.setDurability("commit");
        properties.setPageBytes(512);
        properties.getSpill().setEnabled(spill);
        properties.getSpill().setDir(dir.toString());
        properties.getSession().setPersist(persist);
        properties.getSession().setRestore(true);
        RouxEngine engine = new RouxEngine(properties, new RegionPack(properties), new RouxCounters(),
                new CryptoSink(properties), new RouxIdGenerator());
        engines.add(engine);
        return new Shelf(engine, "clay", dir);
    }

    /**
     * The method builds a command tape over an already open region.
     * A second open is not performed.
     *
     * @param shelf The shelf holds the open engine.
     * @param opened The result is the open that already succeeded.
     * @param prompt The prompt is the system prompt, or empty.
     * @return The tape records command output.
     */
    private static Tape tape(Shelf shelf, OpenResult opened, String prompt) {
        RouxChatMemory memory = new RouxChatMemory(shelf.engine, new HeuristicTokenCounter());
        ChatSession session = ChatSession.forUser(shelf.user, opened, prompt, memory);
        return new Tape(session, shelf.user);
    }

    /**
     * The method deletes a temporary directory and the files inside it.
     *
     * @param dir The directory is the spill root.
     * @throws IOException A file delete can fail.
     */
    private static void deleteTree(Path dir) throws IOException {
        if (dir == null || !Files.exists(dir)) {
            return;
        }
        if (Files.isDirectory(dir)) {
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
            try {
                for (Path child : stream) {
                    deleteTree(child);
                }
            } finally {
                stream.close();
            }
        }
        Files.deleteIfExists(dir);
    }

    /**
     * The class holds one engine, the username, and the spill directory.
     */
    private static final class Shelf {
        private final RouxEngine engine;
        private final String user;
        private final Path dir;

        private Shelf(RouxEngine engine, String user, Path dir) {
            this.engine = engine;
            this.user = user;
            this.dir = dir;
        }
    }

    /**
     * The class records the text a command writes.
     * The other shell methods stay unused by this test.
     */
    private static final class Tape implements CommandContext {
        private final ChatSession session;
        private final String user;
        private final StringBuilder written = new StringBuilder();

        private Tape(ChatSession session, String user) {
            this.session = session;
            this.user = user;
        }

        @Override
        public ChatSession getSession() {
            return session;
        }

        @Override
        public ShellMode getShellMode() {
            return ShellMode.COMMAND;
        }

        @Override
        public CasseroleProperties getProperties() {
            return null;
        }

        @Override
        public String getModelName() {
            return "";
        }

        @Override
        public String getConsoleThink() {
            return "";
        }

        @Override
        public String getConsoleProvider() {
            return "ollama";
        }

        @Override
        public void writeModelStatus() {
        }

        @Override
        public boolean selectConsoleFamily(String family) {
            return false;
        }

        @Override
        public String getSshUsername() {
            return user;
        }

        @Override
        public CommandProcessor getProcessor() {
            return null;
        }

        @Override
        public void write(String text) {
            written.append(text);
        }

        @Override
        public void writeError(String text) {
        }

        @Override
        public void setChatActive(boolean active) {
        }

        @Override
        public void requestExit() {
        }

        @Override
        public void setModelName(String modelName) {
        }

        @Override
        public void collectMessageEdit(long seq) {
        }
    }
}
