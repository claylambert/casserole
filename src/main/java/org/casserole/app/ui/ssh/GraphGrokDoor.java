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

package org.casserole.app.ui.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.aetheris.lcarsgraph.GraphDoor;
import org.casserole.config.CasseroleProperties;
import org.casserole.llm.GrokCallOptions;
import org.casserole.llm.GrokLlmService;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.yaml.snakeyaml.Yaml;

/**
 * The door sends one intent brief through the Grok chat the shell already uses.
 * The key is not written to a log.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1477 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
public final class GraphGrokDoor implements GraphDoor {

    /** The frontier model Model Config already names. */
    static final String FRONTIER = "grok-4.7";

    private final String model;
    private final GrokLlmService.DeltaSink sink;
    private final Runnable end;
    private final GrokCallOptions seated;

    /**
     * The constructor keeps the model id.
     * A Grok engine uses the id after the slash.
     * Any other engine uses {@link #FRONTIER}.
     * The call stays unstreamed.
     *
     * @param engine The engine is the card's execution target. The value may be empty.
     */
    public GraphGrokDoor(String engine) {
        this(engine, null, null);
    }

    /**
     * The constructor keeps the model id and a thought ear.
     * A null ear leaves the call unstreamed.
     * Kind {@code t} is forwarded. Any other kind is dropped.
     * {@code end} runs when the ask returns, including when the call throws.
     *
     * @param engine The engine is the card's execution target. The value may be empty.
     * @param sink The sink hears a reasoning chunk. The value may be null.
     * @param end The end closes the thinking block. The value may be null.
     */
    GraphGrokDoor(String engine, GrokLlmService.DeltaSink sink, Runnable end) {
        this(engine, sink, end, null);
    }

    /**
     * The constructor keeps the model id, a thought ear, and the stored options.
     * A null options value uses {@link #callOptions} for the ear.
     *
     * @param engine The engine is the card's execution target. The value may be empty.
     * @param sink The sink hears a reasoning chunk. The value may be null.
     * @param end The end closes the thinking block. The value may be null.
     * @param seated The options are the stored model options. The value may be null.
     */
    // @aa-sdd L40 aetheris-plan-f-324.001.md FINESSE:f-324
    GraphGrokDoor(String engine, GrokLlmService.DeltaSink sink, Runnable end, GrokCallOptions seated) {
        this.model = modelOf(engine);
        this.sink = sink;
        this.end = end;
        this.seated = seated;
    }

    /**
     * The method sends the brief and returns the assistant text.
     * A missing key throws {@link GrokLlmService.KeyMissingException}.
     *
     * @param brief The brief is the intent ask.
     * @return The return value is the assistant text.
     * @throws Exception The exception is a door that does not send.
     */
    @Override
    public String ask(String brief) throws Exception {
        try {
            String key = yamlKey();
            CasseroleProperties properties = null;
            if (key.length() > 0) {
                properties = new CasseroleProperties();
                CasseroleProperties.Grok grok = new CasseroleProperties.Grok();
                grok.setApiKey(key);
                properties.setGrok(grok);
            }
            GrokLlmService service = new GrokLlmService(properties);
            List<org.springframework.ai.chat.messages.Message> messages = new ArrayList<org.springframework.ai.chat.messages.Message>();
            messages.add(new SystemMessage(systemText()));
            messages.add(new UserMessage(brief == null ? "" : brief));
            GrokCallOptions options = seatedOptions();
            org.springframework.ai.chat.model.ChatResponse response = service.completeFor(
                    model, new Prompt(messages), options, null, forward());
            if (response == null || response.getResult() == null || response.getResult().getOutput() == null) {
                return "";
            }
            String text = response.getResult().getOutput().getText();
            return text == null ? "" : text;
        } finally {
            if (end != null) {
                end.run();
            }
        }
    }

    /**
     * The method builds the call options for one ask.
     * {@code sendReasoningEffort} stays false.
     * {@code maxCompletionTokens} stays 4000.
     * A thinking ask sets {@code thinkStream}.
     * A quiet ask leaves the stream off.
     *
     * @param thinking The flag is true when a thought ear is present.
     * @return The return value is those options.
     */
    /**
     * The method returns the options this door will send.
     * A stored seat wins.
     * A missing seat uses {@link #callOptions} for the ear.
     *
     * @return The return value is the call options.
     */
    GrokCallOptions seatedOptions() {
        if (seated != null) {
            return seated;
        }
        return callOptions(sink != null);
    }

    static GrokCallOptions callOptions(boolean thinking) {
        GrokCallOptions options = new GrokCallOptions();
        options.sendReasoningEffort = false;
        options.maxCompletionTokens = 4000;
        options.thinkStream = thinking;
        return options;
    }

    /**
     * The method forwards kind {@code t} when a thought ear is present.
     * A quiet door returns null, so the call stays unstreamed.
     *
     * @return The return value is the forwarding sink, or null.
     */
    private GrokLlmService.DeltaSink forward() {
        if (sink == null) {
            return null;
        }
        return new GrokLlmService.DeltaSink() {
            @Override
            public void delta(char kind, String text) throws Exception {
                if (kind == 't' && text != null && text.length() > 0) {
                    sink.delta(kind, text);
                }
            }
        };
    }

    /**
     * The method teaches the block.
     * The shape names are not aims. An aim is a thing the window holds.
     * The pool door sends this same text.
     *
     * @return The return value is the system text.
     */
    static String systemText() {
        return "You assign intent. You read the window lines. You name each aim those lines hold. "
                + "An aim is one, two, or three words. It is a conjugated context the speaker is holding. "
                + "It is not the task you were given. "
                + "Two or three words sit in brackets. One word needs no brackets. "
                + "Do not emit conversation-map or the phrase conversation map. "
                + "Do not emit intent-pass, intent-ask, or intent-repair as an aim. "
                + "The shape names in this message are not aims. Do not copy a shape unless the window says that word. "
                + "A passing block is key lines and list lines. Talk above the block is ignored. "
                + "The value on intent-pass is 1. A born line pairs with an added line. "
                + "act: none and shift: none are complete groups. "
                + "A meat line names one word, the word on, and one aim. "
                + "Write one meat line for every meat word that aim holds. "
                + "One aim may hold many meat lines. There is no cap on that count. "
                + "A relation line names one aim, one relation, and one later aim. "
                + "The relations are supports, refines, contains, and pulls against. "
                + "A meat word that belongs to two aims is written on each aim. "
                + "That shared word relates those aims. "
                + "An evidence line is exactly three tokens: one aim, the word sees, and one meat word. "
                + "A cell line is a turn label from the window and one aim. Do not invent a label. "
                + "A birth cites a window label on a cell line. "
                + "When the window holds several aims, write several born lines. "
                + "When the window holds one aim, one born line is enough. "
                + "Shape one, one aim. These names are not in the ask:\n"
                + "intent-pass: 1\n"
                + "act:\n"
                + "- kettle born inferred\n"
                + "shift:\n"
                + "- kettle added\n"
                + "meat:\n"
                + "- steam on kettle\n"
                + "evidence:\n"
                + "- kettle sees steam\n"
                + "cell:\n"
                + "- turn-1 kettle\n"
                + "Shape two, a phrase and a word. These names are not in the ask:\n"
                + "intent-pass: 1\n"
                + "act:\n"
                + "- [harbor light] born inferred\n"
                + "- ribbon born inferred\n"
                + "shift:\n"
                + "- [harbor light] added\n"
                + "- ribbon added\n"
                + "meat:\n"
                + "- rope on [harbor light]\n"
                + "- dye on ribbon\n"
                + "evidence:\n"
                + "- [harbor light] sees rope\n"
                + "- ribbon sees dye\n"
                + "cell:\n"
                + "- turn-1 [harbor light]\n"
                + "- turn-1 ribbon\n"
                + "Shape three, many words and a relation. These names are not in the ask:\n"
                + "intent-pass: 1\n"
                + "act:\n"
                + "- lantern born inferred\n"
                + "- path born inferred\n"
                + "shift:\n"
                + "- lantern added\n"
                + "- path added\n"
                + "meat:\n"
                + "- ember on lantern\n"
                + "- wick on lantern\n"
                + "- glow on lantern\n"
                + "- glow on path\n"
                + "- stone on path\n"
                + "relation:\n"
                + "- lantern supports path\n"
                + "evidence:\n"
                + "- lantern sees ember\n"
                + "- path sees stone\n"
                + "cell:\n"
                + "- turn-1 lantern\n"
                + "- turn-1 path\n"
                + "The window lines are conversation text. They are not a format to copy as prose. "
                + "A repair names a fault. Answer that repair with a full intent-pass block.";
    }

    /**
     * The method reads the model id from an engine word.
     *
     * @param engine The engine is the card's execution target.
     * @return The return value is the model id.
     */
    static String modelOf(String engine) {
        if (engine == null) {
            return FRONTIER;
        }
        String trimmed = engine.trim();
        int slash = trimmed.indexOf('/');
        if (slash > 0) {
            String family = trimmed.substring(0, slash).toLowerCase(Locale.ROOT);
            String id = trimmed.substring(slash + 1).trim();
            if ("grok".equals(family) && id.length() > 0) {
                return id;
            }
        }
        if (trimmed.toLowerCase(Locale.ROOT).startsWith("grok-")) {
            return trimmed;
        }
        return FRONTIER;
    }

    /**
     * The method reads {@code casserole.grok.api-key}.
     * A missing resource returns an empty string.
     * The value is not written to a log.
     *
     * @return The return value is the key, or an empty string.
     */
    private static String yamlKey() {
        InputStream in = GraphGrokDoor.class.getResourceAsStream("/application.yaml");
        if (in == null) {
            return "";
        }
        try {
            Object loaded = new Yaml().load(in);
            if (!(loaded instanceof Map<?, ?>)) {
                return "";
            }
            Object casserole = ((Map<?, ?>) loaded).get("casserole");
            if (!(casserole instanceof Map<?, ?>)) {
                return "";
            }
            Object grok = ((Map<?, ?>) casserole).get("grok");
            if (!(grok instanceof Map<?, ?>)) {
                return "";
            }
            Object value = ((Map<?, ?>) grok).get("api-key");
            if (!(value instanceof String)) {
                return "";
            }
            return ((String) value).trim();
        } catch (RuntimeException ex) {
            return "";
        } finally {
            try {
                in.close();
            } catch (IOException closed) {
                // The text already left the stream.
            }
        }
    }
}
