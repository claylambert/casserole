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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.ThinkOption;
import org.springframework.util.MimeType;

import reactor.core.Exceptions;

// @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
// @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
// @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
// @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
// @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
// @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
// @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
// @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
// @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
// @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
// @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
// @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
// @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
// @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
// @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
// @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
// @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
// @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
// @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
/**
 * The class checks think pieces in {@link SpringAiOllamaLlmService}.
 * The test does not make an HTTP call.
 * The test does not start Spring.
 *
 * @author clayl (2026-10-28)
 */
class OllamaThinkStreamTest {

    /**
     * The method arranges a {@link Memo} and feeds five chunks to {@link SpringAiOllamaLlmService#acceptStreamChunk}.
     * The thinking texts are {@code Let me }, {@code see.}, and {@code Also }.
     * The kinds are three copies of {@code t}.
     * The joined content is {@code The answer.tail}.
     * The kinds do not contain {@code c}.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because {@link SpringAiOllamaLlmService#acceptStreamChunk} declares a checked failure.
     */
    @Test
    void thinkingPiecesAreKindTAndContentStaysJoined() throws Exception {
        Memo sink = new Memo();
        StringBuilder content = new StringBuilder();
        SpringAiOllamaLlmService.acceptStreamChunk(chunk("Let me ", null), content, sink);
        SpringAiOllamaLlmService.acceptStreamChunk(chunk("see.", ""), content, sink);
        SpringAiOllamaLlmService.acceptStreamChunk(chunk(null, "The "), content, sink);
        SpringAiOllamaLlmService.acceptStreamChunk(chunk("", "answer."), content, sink);
        SpringAiOllamaLlmService.acceptStreamChunk(chunk("Also ", "tail"), content, sink);
        assertEquals(List.of("t", "t", "t"), sink.kinds);
        assertEquals(List.of("Let me ", "see.", "Also "), sink.texts);
        assertEquals("The answer.tail", content.toString());
        assertFalse(sink.kinds.contains("c"));
    }

    /**
     * The method arranges a {@link Memo} and passes a null chunk, a done frame, a chunk with null texts, and a chunk with empty texts.
     * The method calls {@link SpringAiOllamaLlmService#acceptStreamChunk}.
     * The sink records no kinds.
     * The content stays empty.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because {@link SpringAiOllamaLlmService#acceptStreamChunk} declares a checked failure.
     */
    @Test
    void aQuietChunkDoesNotCallTheSink() throws Exception {
        Memo sink = new Memo();
        StringBuilder content = new StringBuilder();
        SpringAiOllamaLlmService.acceptStreamChunk(null, content, sink);
        SpringAiOllamaLlmService.acceptStreamChunk(doneFrame(), content, sink);
        SpringAiOllamaLlmService.acceptStreamChunk(chunk(null, null), content, sink);
        SpringAiOllamaLlmService.acceptStreamChunk(chunk("", ""), content, sink);
        assertTrue(sink.kinds.isEmpty());
        assertEquals("", content.toString());
    }

    /**
     * The method arranges a {@link Memo} whose stop flag is true.
     * The method calls {@link SpringAiOllamaLlmService#acceptStreamChunk} with thinking {@code working} and content {@code hidden}.
     * The call throws {@link GrokLlmService.CallStopped}.
     * The content stays empty.
     * The sink records no kinds.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because {@link SpringAiOllamaLlmService#acceptStreamChunk} declares a checked failure.
     */
    @Test
    void aStoppedReadPropagates() throws Exception {
        Memo sink = new Memo();
        sink.stop = true;
        StringBuilder content = new StringBuilder();
        try {
            SpringAiOllamaLlmService.acceptStreamChunk(chunk("working", "hidden"), content, sink);
            fail("stopped");
        } catch (GrokLlmService.CallStopped expected) {
            assertEquals("", content.toString());
            assertTrue(sink.kinds.isEmpty());
        }
    }

    /**
     * The method arranges a {@link GrokLlmService.CallStopped} wrapped by {@code Exceptions.propagate}.
     * The method calls {@link SpringAiOllamaLlmService#raiseStreamFailure}.
     * The call throws {@link GrokLlmService.CallStopped}.
     * The thrown message is not null.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} and the asserted failure is caught inside the method.
     */
    @Test
    void aWrappedStopStaysCallStopped() throws Exception {
        Throwable wrapped = null;
        try {
            throw Exceptions.propagate(new GrokLlmService.CallStopped());
        } catch (Throwable ex) {
            wrapped = ex;
        }
        try {
            SpringAiOllamaLlmService.raiseStreamFailure(wrapped);
            fail("stopped");
        } catch (GrokLlmService.CallStopped expected) {
            assertTrue(expected.getMessage() != null);
        }
    }

    /**
     * The method arranges an {@link IllegalStateException} whose message is {@code down}.
     * The method calls {@link SpringAiOllamaLlmService#raiseStreamFailure}.
     * The call throws that same {@link IllegalStateException}.
     * The message stays {@code down}.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} and the asserted failure is caught inside the method.
     */
    @Test
    void aRuntimeFailureStaysARuntime() throws Exception {
        try {
            SpringAiOllamaLlmService.raiseStreamFailure(new IllegalStateException("down"));
            fail("runtime");
        } catch (IllegalStateException expected) {
            assertEquals("down", expected.getMessage());
        }
    }

    /**
     * The method arranges a reply built by {@link SpringAiOllamaLlmService#replyOf}.
     * The method calls {@link SpringAiOllamaLlmService#thinkingText}.
     * The reply text is {@code answer}.
     * The thinking text of that reply is empty.
     * A reply built from null has empty thinking text.
     * A null response has empty thinking text.
     *
     * @return The method returns nothing.
     */
    @Test
    void aStreamReplyLeavesThinkingEmpty() {
        ChatResponse response = SpringAiOllamaLlmService.replyOf("answer");
        assertEquals("answer", response.getResult().getOutput().getText());
        assertEquals("", SpringAiOllamaLlmService.thinkingText(response));
        assertEquals("", SpringAiOllamaLlmService.thinkingText(SpringAiOllamaLlmService.replyOf(null)));
        assertEquals("", SpringAiOllamaLlmService.thinkingText(null));
    }

    /**
     * The method arranges think words and calls {@link SpringAiOllamaLlmService#thinkOptionOf}.
     * Null, empty, spaces, and {@code false} each return null.
     * {@code true} returns a {@link ThinkOption.ThinkBoolean}.
     * {@code high} returns a {@link ThinkOption.ThinkLevel}.
     * That level word is {@code high}.
     *
     * @return The method returns nothing.
     */
    @Test
    void thinkOffStaysUnset() {
        assertNull(SpringAiOllamaLlmService.thinkOptionOf(null));
        assertNull(SpringAiOllamaLlmService.thinkOptionOf(""));
        assertNull(SpringAiOllamaLlmService.thinkOptionOf("  "));
        assertNull(SpringAiOllamaLlmService.thinkOptionOf("false"));
        assertTrue(SpringAiOllamaLlmService.thinkOptionOf("true") instanceof ThinkOption.ThinkBoolean);
        ThinkOption level = SpringAiOllamaLlmService.thinkOptionOf("high");
        assertTrue(level instanceof ThinkOption.ThinkLevel);
        assertEquals("high", ((ThinkOption.ThinkLevel) level).level());
    }

    /**
     * The method arranges a text prompt, an image prompt, a tool-call prompt, and a tool-response prompt.
     * The method calls {@link SpringAiOllamaLlmService#textOnly}.
     * A system message, a user message, and an assistant message together return true.
     * A user message that carries a png media returns false.
     * An assistant message that carries a tool call returns false.
     * A tool response returns false.
     * A null prompt returns false.
     *
     * @return The method returns nothing.
     */
    @Test
    void textHistoryStreamsAndImageOrToolDoesNot() {
        Prompt text = new Prompt(List.of(
                new SystemMessage("host"),
                new UserMessage("question"),
                new AssistantMessage("reply")));
        assertTrue(SpringAiOllamaLlmService.textOnly(text));
        Media picture = new Media(MimeType.valueOf("image/png"), URI.create("file:///tmp/a.png"));
        Prompt image = new Prompt(List.of(UserMessage.builder().text("see").media(picture).build()));
        assertFalse(SpringAiOllamaLlmService.textOnly(image));
        AssistantMessage tool = AssistantMessage.builder()
                .content("")
                .toolCalls(List.of(new AssistantMessage.ToolCall("1", "function", "now", "{}")))
                .build();
        assertFalse(SpringAiOllamaLlmService.textOnly(new Prompt(List.of(tool))));
        ToolResponseMessage result = ToolResponseMessage.builder()
                .responses(List.of(new ToolResponseMessage.ToolResponse("1", "now", "ok")))
                .build();
        assertFalse(SpringAiOllamaLlmService.textOnly(new Prompt(List.of(result))));
        assertFalse(SpringAiOllamaLlmService.textOnly(null));
    }

    /**
     * The method builds an assistant chat response.
     * The method stores {@code thinking} and {@code content} on that message.
     * The other response fields stay null.
     *
     * @param thinking The text is the thinking field, and it may be null.
     * @param content The text is the content field, and it may be null.
     * @return The method returns that chat response.
     */
    private static OllamaApi.ChatResponse chunk(String thinking, String content) {
        OllamaApi.Message message = OllamaApi.Message.builder(OllamaApi.Message.Role.ASSISTANT)
                .content(content)
                .thinking(thinking)
                .build();
        return new OllamaApi.ChatResponse(null, null, message, null, null, null, null, null, null, null, null);
    }

    /**
     * The method builds a chat response with a null message.
     * The done flag is true.
     * The stop word is {@code stop}.
     *
     * @return The method returns that chat response.
     */
    private static OllamaApi.ChatResponse doneFrame() {
        return new OllamaApi.ChatResponse(null, null, null, "stop", Boolean.TRUE, null, null, null, null, null, null);
    }

    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
    /**
     * The class records each delta passed to {@link GrokLlmService.DeltaSink}.
     * A true stop flag throws {@link GrokLlmService.CallStopped} instead of recording.
     */
    private static final class Memo implements GrokLlmService.DeltaSink {

        private final ArrayList<String> kinds = new ArrayList<String>();
        private final ArrayList<String> texts = new ArrayList<String>();
        private boolean stop;

        /**
         * The method records {@code kind} and {@code text} when the stop flag is false.
         * The method throws {@link GrokLlmService.CallStopped} when the stop flag is true.
         * The method records nothing in that stopped case.
         *
         * @param kind The character is the delta kind.
         * @param text The text is the delta body.
         * @return The method returns nothing.
         * @throws Exception The method declares {@link Exception} because {@link GrokLlmService.CallStopped} is a checked failure.
         */
        @Override
        public void delta(char kind, String text) throws Exception {
            if (stop) {
                throw new GrokLlmService.CallStopped();
            }
            kinds.add(String.valueOf(kind));
            texts.add(text);
        }
    }
}
