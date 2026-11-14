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

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

// @aa-sdd L40 app-design.md
/**
 * The {@code CasseroleProperties} type is the bound {@code casserole.*} configuration.
 * The type holds the SSH listen settings, the chat settings, the Lamina settings, the Grok settings, and the pool settings.
 * SSH is the secure-shell protocol a client uses to connect.
 * Each section starts as a new object with that section's defaults.
 * Spring Boot binds the yaml values into these objects.
 * Spring Boot is the framework that fills this type at startup.
 *
 * @author clayl (2026-08-24)
 */
@ConfigurationProperties(prefix = "casserole")
// @aa-sdd PK-1299 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-682 docs/specs/common/grok.spec.md
// @aa-sdd PK-337 docs/specs/java/org/casserole/config/config.spec.md
// @aa-sdd PK-339 docs/specs/java/org/casserole/config/config.spec.md
// @aa-sdd PK-811 docs/specs/common/roux.spec.md
// @aa-sdd PK-335 docs/specs/java/org/casserole/config/config.spec.md
public class CasseroleProperties {

    private Ssh ssh = new Ssh();
    private Chat chat = new Chat();
    private Lamina lamina = new Lamina();
    private Grok grok = new Grok();
    private Pool pool = new Pool();

    /**
     * The {@code getSsh} method returns the SSH listen and password settings.
     *
     * @return The {@link Ssh} section.
     */
    public Ssh getSsh() {
        return ssh;
    }

    /**
     * The {@code setSsh} method stores the SSH section.
     *
     * @param ssh The SSH settings to store.
     */
    public void setSsh(Ssh ssh) {
        this.ssh = ssh;
    }

    /**
     * The {@code getChat} method returns the chat and prompt settings.
     *
     * @return The {@link Chat} section.
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * The {@code setChat} method stores the chat section.
     *
     * @param chat The chat settings to store.
     */
    public void setChat(Chat chat) {
        this.chat = chat;
    }

    /**
     * The {@code getLamina} method returns the Lamina settings.
     * Lamina is the named thread-pool model.
     *
     * @return The {@link Lamina} section.
     */
    public Lamina getLamina() {
        return lamina;
    }

    /**
     * The {@code setLamina} method stores the Lamina section.
     *
     * @param lamina The Lamina settings to store.
     */
    public void setLamina(Lamina lamina) {
        this.lamina = lamina;
    }

    /**
     * The {@code getGrok} method returns the Grok API settings.
     * The key text is not written to the log by this method.
     *
     * @return The {@link Grok} section.
     */
    public Grok getGrok() {
        return grok;
    }

    /**
     * The {@code setGrok} method stores the Grok section.
     *
     * @param grok The Grok API settings to store.
     */
    public void setGrok(Grok grok) {
        this.grok = grok;
    }

    /**
     * The {@code getPool} method returns the pool settings.
     * The window is the value bound from {@code casserole.pool.context-window}.
     * The host and the port name a remote {@code grok agent serve} seat. A blank host keeps the local child.
     *
     * @return The {@link Pool} section.
     */
    public Pool getPool() {
        return pool;
    }

    /**
     * The {@code setPool} method stores the pool section.
     * If {@code pool} is {@code null}, the method stores a new {@link Pool} with the default window of {@code 0}.
     *
     * @param pool The pool settings to store, or {@code null} to store a fresh section.
     */
    public void setPool(Pool pool) {
        this.pool = pool == null ? new Pool() : pool;
    }

    // @aa-sdd L40 app-design.md
    /**
     * The {@code Ssh} type holds the SSH server bind and password defaults.
     * The port starts at {@code 2222}.
     * The user name starts at {@code clay}.
     * The password starts at {@code butter}.
     * The host-key file starts at {@code hostkey.ser}.
     * The idle timeout starts at {@code 0}, which means the server does not disconnect an idle session.
     */
    public static class Ssh {

        private int port = 2222;
        private String username = "clay";
        private String password = "butter";
        private String hostKey = "hostkey.ser";
        /**
         * The {@code idleTimeoutMs} field is the idle limit in milliseconds.
         * A value of {@code 0} means the server does not disconnect an idle SSH session.
         */
        private long idleTimeoutMs = 0;

        /**
         * The {@code getPort} method returns the listen port.
         *
         * @return The port, which starts at {@code 2222}.
         */
        public int getPort() {
            return port;
        }

        /**
         * The {@code setPort} method stores the listen port.
         *
         * @param port The listen port.
         */
        public void setPort(int port) {
            this.port = port;
        }

        /**
         * The {@code getUsername} method returns the default user name.
         *
         * @return The user name, which starts at {@code clay}.
         */
        public String getUsername() {
            return username;
        }

        /**
         * The {@code setUsername} method stores the default user name.
         *
         * @param username The user name to store.
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * The {@code getPassword} method returns the default password.
         *
         * @return The password, which starts at {@code butter}.
         */
        public String getPassword() {
            return password;
        }

        /**
         * The {@code setPassword} method stores the default password.
         *
         * @param password The password to store.
         */
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * The {@code getHostKey} method returns the host-key file path.
         *
         * @return The path, which starts at {@code hostkey.ser}.
         */
        public String getHostKey() {
            return hostKey;
        }

        /**
         * The {@code setHostKey} method stores the host-key file path.
         *
         * @param hostKey The file path to store.
         */
        public void setHostKey(String hostKey) {
            this.hostKey = hostKey;
        }

        /**
         * The {@code getIdleTimeoutMs} method returns the idle limit in milliseconds.
         * A value of {@code 0} disables idle disconnect.
         *
         * @return The stored millisecond count.
         */
        public long getIdleTimeoutMs() {
            return idleTimeoutMs;
        }

        /**
         * The {@code setIdleTimeoutMs} method stores the idle limit.
         * The method stores a negative value as given.
         * A stored value of {@code 0} or less means the server does not disconnect an idle session.
         *
         * @param idleTimeoutMs The limit in milliseconds, where {@code 0} or a negative value means never.
         */
        public void setIdleTimeoutMs(long idleTimeoutMs) {
            this.idleTimeoutMs = idleTimeoutMs;
        }
    }

    // @aa-sdd PK-1252 docs/specs/java/org/casserole/config/config.spec.md
    /**
     * The {@code Chat} type holds the chat session defaults.
     * The system prompt starts empty, so a new session has no system message until a prompt is set.
     * {@code maxMessages} starts at {@code 40}.
     * {@code maxTokens}, {@code managedTokens}, and {@code headCount} start at {@code 0}.
     * {@code escapeTimeoutMs} starts at {@code 100}.
     * {@code maxPasteChars} starts at {@code 65536}.
     * The spinner starts at {@code ascii}, and the spinner interval starts at {@code 100} milliseconds.
     * Smart format starts on.
     * The table style starts at {@code ascii}.
     * {@code historySize} starts at {@code 500}.
     * {@code listPreviewChars} starts at {@code 120}.
     */
    public static class Chat {

        private String systemPrompt = "";
        private int maxMessages = 40;
        private int maxTokens = 0;
        private int managedTokens = 0;
        private int headCount = 0;
        private int escapeTimeoutMs = 100;
        private int maxPasteChars = 65536;
        private String spinner = "ascii";
        private int spinnerMs = 100;
        private boolean smartFormat = true;
        private String tableStyle = "ascii";
        private int historySize = 500;
        private int listPreviewChars = 120;

        /**
         * The {@code getSystemPrompt} method returns the system prompt for a new session.
         * The empty string leaves the system message off.
         *
         * @return The system prompt text.
         */
        public String getSystemPrompt() {
            return systemPrompt;
        }

        /**
         * The {@code setSystemPrompt} method stores the system prompt text.
         *
         * @param systemPrompt The system prompt text.
         */
        public void setSystemPrompt(String systemPrompt) {
            this.systemPrompt = systemPrompt;
        }

        /**
         * The {@code getMaxMessages} method returns the maximum number of non-system turns sent to the model on each call.
         *
         * @return The cap, which starts at {@code 40}.
         */
        public int getMaxMessages() {
            return maxMessages;
        }

        /**
         * The {@code setMaxMessages} method stores the maximum number of non-system messages in a prompt.
         *
         * @param maxMessages The cap to store.
         */
        public void setMaxMessages(int maxMessages) {
            this.maxMessages = maxMessages;
        }

        /**
         * The {@code getMaxTokens} method returns the token budget for the prompt window.
         * A value of {@code 0} means the window is not limited by tokens.
         *
         * @return The token cap.
         */
        public int getMaxTokens() {
            return maxTokens;
        }

        /**
         * The {@code setMaxTokens} method stores the token cap.
         * A value of {@code 0} means unlimited.
         *
         * @param maxTokens The token cap to store.
         */
        public void setMaxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
        }

        /**
         * The {@code getManagedTokens} method returns the sink allowance.
         * A value of {@code 0} means prompt assembly uses the message window and the token cap instead of the sink.
         *
         * @return The allowance.
         */
        public int getManagedTokens() {
            return managedTokens;
        }

        /**
         * The {@code setManagedTokens} method stores the sink allowance.
         * A value of {@code 0} leaves the allowance unset.
         *
         * @param managedTokens The allowance to store.
         */
        public void setManagedTokens(int managedTokens) {
            this.managedTokens = managedTokens;
        }

        /**
         * The {@code getHeadCount} method returns how many eligible turns may lock.
         * A value of {@code 0} means no turns are locked.
         *
         * @return The lock count.
         */
        public int getHeadCount() {
            return headCount;
        }

        /**
         * The {@code setHeadCount} method stores how many eligible turns may lock.
         * A value of {@code 0} leaves the count unset.
         *
         * @param headCount The cap to store.
         */
        public void setHeadCount(int headCount) {
            this.headCount = headCount;
        }

        /**
         * The {@code getEscapeTimeoutMs} method returns how long the shell waits after ESC before treating it as a bare mode switch.
         * ESC is the escape character.
         *
         * @return The timeout in milliseconds, which starts at {@code 100}.
         */
        public int getEscapeTimeoutMs() {
            return escapeTimeoutMs;
        }

        /**
         * The {@code setEscapeTimeoutMs} method stores the ESC wait.
         *
         * @param escapeTimeoutMs The timeout in milliseconds.
         */
        public void setEscapeTimeoutMs(int escapeTimeoutMs) {
            this.escapeTimeoutMs = escapeTimeoutMs;
        }

        /**
         * The {@code getMaxPasteChars} method returns the maximum number of characters accepted inside a bracketed paste.
         * A bracketed paste is the terminal sequence that wraps a pasted block.
         *
         * @return The paste cap, which starts at {@code 65536}.
         */
        public int getMaxPasteChars() {
            return maxPasteChars;
        }

        /**
         * The {@code setMaxPasteChars} method stores the paste cap.
         *
         * @param maxPasteChars The maximum number of characters.
         */
        public void setMaxPasteChars(int maxPasteChars) {
            this.maxPasteChars = maxPasteChars;
        }

        /**
         * The {@code getSpinner} method returns the spinner style token.
         * The token is {@code braille}, {@code ascii}, or {@code none}.
         *
         * @return The style token, which starts at {@code ascii}.
         */
        public String getSpinner() {
            return spinner;
        }

        /**
         * The {@code setSpinner} method stores the spinner style token.
         *
         * @param spinner The style token.
         */
        public void setSpinner(String spinner) {
            this.spinner = spinner;
        }

        /**
         * The {@code getSpinnerMs} method returns the animation interval.
         *
         * @return The interval in milliseconds, which starts at {@code 100}.
         */
        public int getSpinnerMs() {
            return spinnerMs;
        }

        /**
         * The {@code setSpinnerMs} method stores the animation interval.
         *
         * @param spinnerMs The interval in milliseconds.
         */
        public void setSpinnerMs(int spinnerMs) {
            this.spinnerMs = spinnerMs;
        }

        /**
         * The {@code isSmartFormat} method reports whether the format detectors run.
         * The detectors are the JSON, fence, table, list, and markdown matchers.
         *
         * @return {@code true} when those detectors run, which is the starting value.
         */
        public boolean isSmartFormat() {
            return smartFormat;
        }

        /**
         * The {@code setSmartFormat} method stores whether the format detectors run.
         *
         * @param smartFormat {@code true} to enable the detectors.
         */
        public void setSmartFormat(boolean smartFormat) {
            this.smartFormat = smartFormat;
        }

        /**
         * The {@code getTableStyle} method returns the table drawing style.
         * The style is {@code ascii} or {@code box}.
         *
         * @return The style, which starts at {@code ascii}.
         */
        public String getTableStyle() {
            return tableStyle;
        }

        /**
         * The {@code setTableStyle} method stores the table drawing style.
         *
         * @param tableStyle The style to store.
         */
        public void setTableStyle(String tableStyle) {
            this.tableStyle = tableStyle;
        }

        /**
         * The {@code getHistorySize} method returns the maximum number of persisted history lines.
         *
         * @return The cap, which starts at {@code 500}.
         */
        public int getHistorySize() {
            return historySize;
        }

        /**
         * The {@code setHistorySize} method stores the history cap.
         *
         * @param historySize The maximum number of persisted history lines.
         */
        public void setHistorySize(int historySize) {
            this.historySize = historySize;
        }

        /**
         * The {@code getListPreviewChars} method returns the terse {@code message list} body cap.
         * A value of {@code 0} means the list does not cut the text.
         *
         * @return The preview character count, which starts at {@code 120}.
         */
        public int getListPreviewChars() {
            return listPreviewChars;
        }

        /**
         * The {@code setListPreviewChars} method stores the preview cap.
         *
         * @param listPreviewChars The preview character count.
         */
        public void setListPreviewChars(int listPreviewChars) {
            this.listPreviewChars = listPreviewChars;
        }
    }

    // @aa-sdd L40 app-design.md
    /**
     * The {@code Lamina} type holds the executor settings.
     * Metrics start off.
     */
    public static class Lamina {

        private boolean metrics = false;

        /**
         * The {@code isMetrics} method reports whether lane counters are written to the log.
         *
         * @return {@code true} when the counters are on, which is not the starting value.
         */
        public boolean isMetrics() {
            return metrics;
        }

        /**
         * The {@code setMetrics} method stores whether the log counters are on.
         *
         * @param metrics {@code true} to enable the counters.
         */
        public void setMetrics(boolean metrics) {
            this.metrics = metrics;
        }
    }

    // @aa-sdd L40 app-design.md
    /**
     * The {@code Grok} type holds the API settings from {@code casserole.grok}.
     * A blank key is absent.
     * The key text is not written to the log by these methods.
     * The context-window map starts empty and keeps insertion order.
     */
    // @aa-sdd PK-367 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-851 docs/specs/common/stages.spec.md
    // @aa-sdd PK-957 docs/specs/java/org/casserole/config/config.spec.md
    // @aa-sdd PK-332 docs/specs/java/org/casserole/config/config.spec.md
    // @aa-sdd PK-958 docs/specs/java/org/casserole/config/config.spec.md
    // @aa-sdd PK-697 docs/specs/common/pre-plan.spec.md
    // @aa-sdd PK-333 docs/specs/java/org/casserole/config/config.spec.md
    // @aa-sdd PK-726 docs/specs/common/server.spec.md
    // @aa-sdd PK-338 docs/specs/java/org/casserole/config/config.spec.md
    public static class Grok {

        private String apiKey = "";
        private Map<String, Integer> contextWindows = new LinkedHashMap<String, Integer>();

        /**
         * The {@code getApiKey} method returns the trimmed API key.
         *
         * @return The trimmed key, or the empty string when the key is absent.
         */
        public String getApiKey() {
            return apiKey;
        }

        /**
         * The {@code setApiKey} method stores the API key.
         * A {@code null} value is stored as the empty string.
         * Any other value is stored with surrounding spaces removed.
         * The method does not write the text to the log.
         *
         * @param apiKey The raw property, or {@code null}.
         */
        public void setApiKey(String apiKey) {
            if (apiKey == null) {
                this.apiKey = "";
            } else {
                this.apiKey = apiKey.trim();
            }
        }

        /**
         * The {@code getContextWindows} method returns the context windows by model id.
         * The map is empty when the yaml map is absent.
         *
         * @return The map of model id to window size.
         */
        public Map<String, Integer> getContextWindows() {
            return contextWindows;
        }

        /**
         * The {@code setContextWindows} method stores a copy of the context-window map.
         * A {@code null} map is stored as an empty map.
         * Only the entries in the given map are copied.
         * The copy keeps insertion order.
         *
         * @param contextWindows The raw {@code context-windows} map, or {@code null}.
         */
        public void setContextWindows(Map<String, Integer> contextWindows) {
            LinkedHashMap<String, Integer> next = new LinkedHashMap<String, Integer>();
            if (contextWindows != null) {
                next.putAll(contextWindows);
            }
            this.contextWindows = next;
        }
    }

    // @aa-sdd L40 app-design.md
    // @aa-sdd PK-1273 docs/specs/java/org/casserole/config/config.spec.md
    /**
     * The {@code Pool} type holds the pool settings from {@code casserole.pool}.
     * The window starts at {@code 0}, which means the yaml key is absent.
     * The host starts empty, which keeps the local {@code grok agent stdio} child.
     * A set host is the machine that runs {@code grok agent serve}. The port starts at {@code 0}.
     * A port below 1 is read as the serve default 2419 when the host is set.
     * The secret is the serve token. A blank secret sends no Authorization header. The secret is not written to the log by these methods.
     */
    // @aa-sdd PK-338 docs/specs/java/org/casserole/config/config.spec.md
    public static class Pool {

        private int contextWindow = 0;
        private String host = "";
        private int port = 0;
        private String secret = "";

        /**
         * The {@code getContextWindow} method returns the pool context window.
         *
         * @return The window, or {@code 0} when the yaml key is absent.
         */
        public int getContextWindow() {
            return contextWindow;
        }

        /**
         * The {@code setContextWindow} method stores the pool context window.
         * A value below {@code 1} is stored as {@code 0}.
         *
         * @param contextWindow The raw property.
         */
        public void setContextWindow(int contextWindow) {
            if (contextWindow < 1) {
                this.contextWindow = 0;
            } else {
                this.contextWindow = contextWindow;
            }
        }

        /**
         * The {@code getHost} method returns the ACP host.
         *
         * @return The trimmed host, or the empty string when the pool stays local.
         */
        public String getHost() {
            return host;
        }

        /**
         * The {@code setHost} method stores the ACP host.
         * A {@code null} value is stored as the empty string.
         * Any other value is stored with surrounding spaces removed.
         *
         * @param host The raw {@code casserole.pool.host}, or {@code null}.
         */
        public void setHost(String host) {
            if (host == null) {
                this.host = "";
            } else {
                this.host = host.trim();
            }
        }

        /**
         * The {@code getPort} method returns the ACP port.
         *
         * @return The port, or {@code 0} when the yaml key is absent.
         */
        public int getPort() {
            return port;
        }

        /**
         * The {@code setPort} method stores the ACP port.
         * A value below {@code 0} is stored as {@code 0}. A value above 65535 is stored as {@code 0}.
         *
         * @param port The raw {@code casserole.pool.port}.
         */
        public void setPort(int port) {
            if (port < 0 || port > 65535) {
                this.port = 0;
            } else {
                this.port = port;
            }
        }

        /**
         * The {@code getSecret} method returns the serve token.
         * The text is not written to the log by this method.
         *
         * @return The trimmed token, or the empty string when the token is absent.
         */
        public String getSecret() {
            return secret;
        }

        /**
         * The {@code setSecret} method stores the serve token.
         * A {@code null} value is stored as the empty string.
         * Any other value is stored with surrounding spaces removed.
         * The method does not write the text to the log.
         *
         * @param secret The raw {@code casserole.pool.secret}, or {@code null}.
         */
        public void setSecret(String secret) {
            if (secret == null) {
                this.secret = "";
            } else {
                this.secret = secret.trim();
            }
        }
    }
}
/*****************************************************************************************
 * End of Class: CasseroleProperties.java 082626
 ****************************************************************************************/
