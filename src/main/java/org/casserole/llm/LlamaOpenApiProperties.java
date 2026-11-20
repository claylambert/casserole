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

package org.casserole.llm;

import org.springframework.boot.context.properties.ConfigurationProperties;

// @aa-sdd aetheris-plan-f-278.001.md
// @aa-sdd aetheris-plan-f-280.001.md
/**
 * The {@code LlamaOpenApiProperties} class binds {@code spring.ai.llama-openapi}.
 * There is no Spring AI starter for this prefix.
 * The default base URL is {@code http://127.0.0.1:3333}.
 * The default API key is blank.
 * The default alias is blank.
 * The default {@code num-ctx} is {@code 4096}.
 * {@code temperature} and {@code num-predict} stay null when those yaml keys are absent.
 *
 * @author clayl (2026-11-19)
 */
@ConfigurationProperties(prefix = "spring.ai.llama-openapi")
public class LlamaOpenApiProperties {

    /** The field is the base URL. The default is {@code http://127.0.0.1:3333}. */
    private String baseUrl = "http://127.0.0.1:3333";

    /** The field is the API key. A blank key omits {@code Authorization}. */
    private String apiKey = "";

    /** The field is the model alias. A blank alias leaves a path id unchanged. */
    private String alias = "";

    /** The field is the nested chat block. */
    private Chat chat = new Chat();

    /**
     * The {@code getBaseUrl} method returns the base URL.
     *
     * @return The return value is the base URL.
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * The {@code setBaseUrl} method stores the base URL.
     *
     * @param baseUrl The base URL is the server root.
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * The {@code getApiKey} method returns the API key.
     *
     * @return The return value is the API key.
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * The {@code setApiKey} method stores the API key.
     *
     * @param apiKey The API key is sent as {@code Authorization: Bearer} when it is non-blank.
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * The {@code getAlias} method returns the model alias.
     *
     * @return The return value is the alias.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * The {@code setAlias} method stores the model alias.
     *
     * @param alias The alias replaces a path-shaped catalog id when it is non-blank.
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * The {@code getChat} method returns the nested chat block.
     *
     * @return The return value is the chat block.
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * The {@code setChat} method stores the nested chat block.
     *
     * @param chat The chat block holds the options.
     */
    public void setChat(Chat chat) {
        this.chat = chat;
    }

    /**
     * The {@code Chat} class is the nested {@code chat} block.
     * {@link Options} sits under {@code chat.options}.
     */
    public static class Chat {

        /** The field is the nested options block. */
        private Options options = new Options();

        /**
         * The {@code getOptions} method returns the options block.
         *
         * @return The return value is the options block.
         */
        public Options getOptions() {
            return options;
        }

        /**
         * The {@code setOptions} method stores the options block.
         *
         * @param options The options block holds {@code num-ctx}, {@code temperature}, and {@code num-predict}.
         */
        public void setOptions(Options options) {
            this.options = options;
        }

        /**
         * The {@code Options} class binds {@code spring.ai.llama-openapi.chat.options}.
         * {@code numCtx} maps {@code num-ctx}.
         * {@code numPredict} maps {@code num-predict}.
         * A missing {@code temperature} stays null.
         * A missing {@code num-predict} stays null.
         */
        public static class Options {

            /** The field is {@code num-ctx}. The default is {@code 4096}. The chat body does not send it. */
            private int numCtx = 4096;

            /** The field is {@code temperature}. A missing yaml key stays null. */
            private Double temperature;

            /** The field is {@code num-predict}. A missing yaml key stays null. */
            private Integer numPredict;

            /**
             * The {@code getNumCtx} method returns {@code num-ctx}.
             *
             * @return The return value is the context length stored for the launcher match.
             */
            public int getNumCtx() {
                return numCtx;
            }

            /**
             * The {@code setNumCtx} method stores {@code num-ctx}.
             *
             * @param numCtx The number is the context length. It is not a chat-body field.
             */
            public void setNumCtx(int numCtx) {
                this.numCtx = numCtx;
            }

            /**
             * The {@code getTemperature} method returns the yaml temperature.
             *
             * @return The return value is the temperature, or null when the key is absent.
             */
            public Double getTemperature() {
                return temperature;
            }

            /**
             * The {@code setTemperature} method stores the yaml temperature.
             *
             * @param temperature The temperature is written on the body only when the cell did not store one.
             */
            public void setTemperature(Double temperature) {
                this.temperature = temperature;
            }

            /**
             * The {@code getNumPredict} method returns the yaml {@code num-predict}.
             *
             * @return The return value is the cap, or null when the key is absent.
             */
            public Integer getNumPredict() {
                return numPredict;
            }

            /**
             * The {@code setNumPredict} method stores the yaml {@code num-predict}.
             *
             * @param numPredict The cap is written as {@code max_tokens} only when the cell did not store a cap.
             */
            public void setNumPredict(Integer numPredict) {
                this.numPredict = numPredict;
            }
        }
    }
}
/*****************************************************************************************
 * End of Class: LlamaOpenApiProperties.java 111926
 ****************************************************************************************/
