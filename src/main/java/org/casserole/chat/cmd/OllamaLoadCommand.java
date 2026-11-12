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

package org.casserole.chat.cmd;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * The {@code OllamaLoadCommand} type is the {@code ollama load} command.
 * The alias is {@code ollama start}.
 * The command pins the configured model in memory through Ollama's HTTP API.
 * Ollama is the program that runs a local model.
 * The request sends {@code keep_alive} as {@code -1}, which asks Ollama to keep the model loaded.
 * The command does not start an interactive {@code ollama run} prompt.
 *
 * @author clayl (2026-08-25)
 */
public class OllamaLoadCommand implements Command {

    private final String baseUrl;

    /**
     * The {@code numCtx} field is the token window from configuration.
     * A positive value is sent as {@code options.num_ctx}.
     * A value of {@code 0} or less means the request omits {@code num_ctx}.
     */
    private final int numCtx;

    /**
     * The constructor stores the Ollama HTTP base and the token window.
     * If {@code baseUrl} is {@code null} or empty, the stored base is {@code http://localhost:11434}.
     * A trailing slash on any other base is removed by {@link #trimSlash}.
     *
     * @param baseUrl The Ollama HTTP base, for example {@code http://localhost:11434}.
     * @param numCtx The token window, where {@code 0} omits {@code options.num_ctx}.
     */
    public OllamaLoadCommand(String baseUrl, int numCtx) {
        this.baseUrl = baseUrl == null || baseUrl.length() == 0
                ? "http://localhost:11434" : trimSlash(baseUrl);
        this.numCtx = numCtx;
    }

    /**
     * The {@code getName} method returns the canonical name {@code ollama load}.
     *
     * @return The text {@code ollama load}.
     */
    @Override
    public String getName() {
        return "ollama load";
    }

    /**
     * The {@code getAliases} method returns the alternate name {@code ollama start}.
     *
     * @return An array whose only entry is {@code ollama start}.
     */
    @Override
    public String[] getAliases() {
        return new String[] { "ollama start" };
    }

    /**
     * The {@code getHelp} method returns the one-line summary for {@code help}.
     * The summary says the command loads the configured model into memory with {@code keep_alive -1}.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Load the configured model into RAM (keep_alive -1).";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says the command posts to {@code /api/generate} with a tiny prompt and {@code keep_alive=-1}.
     * The text says that request avoids a hung {@code ollama run} prompt.
     * The text says {@code ollama stop} unloads the model.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "POSTs /api/generate with a tiny prompt and keep_alive=-1 so Ollama "
                + "keeps the model loaded. Avoids a hung `ollama run` REPL. "
                + "Use `ollama stop` to unload.";
    }

    /**
     * The {@code execute} method asks Ollama to load the configured model.
     * The method writes {@code Loading } plus the model name plus {@code ...} before the request.
     * The JSON body names the model, sends the prompt as one space, sets {@code stream} to false, and sets {@code keep_alive} to {@code -1}.
     * {@link #optionsJson} appends {@code num_ctx} only when the stored window is positive.
     * The client connects with a timeout of 10 seconds and posts to the stored base plus {@code /api/generate}.
     * The request timeout is 120 seconds, and the content type is {@code application/json}.
     * If the status code is below 200 or is 300 or higher, the method throws {@code Exception} with {@code Ollama HTTP }, the code, a space, and the body.
     * A status from 200 through 299 writes {@code Model loaded (keep_alive unlimited until ollama stop).}
     * The written lines end with a carriage return and a line feed.
     * The {@code args} text is ignored.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     * @throws Exception The HTTP call failed or returned a status outside 200 through 299.
     */
    @Override
    public void execute(CommandContext ctx, String args) throws Exception {
        String model = ctx.getModelName();
        ctx.write("Loading " + model + " ...\r\n");
        String body = "{\"model\":\"" + jsonEscape(model)
                + "\",\"prompt\":\" \",\"stream\":false,\"keep_alive\":-1"
                + optionsJson() + "}";
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/generate"))
                .timeout(Duration.ofSeconds(120))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new Exception("Ollama HTTP " + response.statusCode() + " " + response.body());
        }
        ctx.write("Model loaded (keep_alive unlimited until ollama stop).\r\n");
    }

    /**
     * The {@code optionsJson} method builds the optional context-window fragment.
     * If {@code numCtx} is {@code 0} or less, the method returns the empty string.
     * Otherwise the method returns a comma, the options object, and {@code num_ctx} set to that window.
     *
     * @return The JSON fragment, or the empty string when the window is off.
     */
    private String optionsJson() {
        if (numCtx <= 0) {
            return "";
        }
        return ",\"options\":{\"num_ctx\":" + numCtx + "}";
    }

    /**
     * The {@code trimSlash} method removes one trailing slash from a base URL.
     * If the text does not end with {@code /}, the method returns it unchanged.
     *
     * @param url The base URL.
     * @return The URL without a trailing slash.
     */
    private static String trimSlash(String url) {
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    /**
     * The {@code jsonEscape} method escapes a model name for a JSON string.
     * A quotation mark or a backslash is prefixed with a backslash.
     * Every other character is copied unchanged.
     *
     * @param value The raw model name.
     * @return The model name with those two characters escaped.
     */
    private static String jsonEscape(String value) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '"' || c == '\\') {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
/*****************************************************************************************
 * End of Class: OllamaLoadCommand.java 082526
 ****************************************************************************************/
