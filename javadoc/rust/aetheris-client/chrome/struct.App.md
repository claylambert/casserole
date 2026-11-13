# struct App

[Rust tree](../../index.md) · [aetheris-client](../index.md) · **chrome**

**Source** `rust/aetheris/aetheris-client/src/chrome.rs`

```rust
struct App
```

The `App` struct is the state of the client window.
It holds the SSH client settings store, the connection fields, the focused terminal, the canvas deck, and every pane that is not in front.

`door` is the [`Door`](../door/struct.Door.md) SSH client settings store. `host`, `port`, `user`, and `password` are the texts on the connection bar. `remember` is true when that password is kept for the user. `status` is the connection line or the latest canvas word. `door_note` is an optional static note stored when the settings file cannot be read or written. `save_pending` is true when those connection fields changed and a later pass should write the store.

`slide` is the open error notice. Close drops it. A later miss replaces the body. `view` is the open reply window. `view_generation` increases by one each time that window opens, and it stops at the maximum instead of wrapping. `session` is the focused SSH session. `connect_clicked` and `disconnect_clicked` are set by those buttons and consumed on a later pass.

`vault_rt` is the runtime that blocks on the vault file. `vault` is the open `aetheris.db`, or `None` while the deck stays in memory. `vault_canvas` is the first canvas id on that file. `vault_hold` is the cell ids on that canvas that this deck does not show.

`grid` is the focused terminal. `at_tail` is true when its view sits on the tail. `jump_tail` asks the next paint to sit on the tail. `console_scroll_lines` is a menu page or line motion for the focused console. A positive count moves toward the tail. `console_scroll_base` is the offset that motion starts from. `None` reads the focused scroll. `anchor` and `current` are the selection ends. `grid_focus` is true when the terminal grid holds the keyboard.

`plate` is [`Plate::Ssh`](enum.Plate.md#member-ssh) or [`Plate::Aetheris`](enum.Plate.md#member-aetheris). `restore_aetheris` is armed when the stored tab word is `aetheris` and Connect has not landed yet. `facts` is the seated canvas facts. `scope_text` is the gathered scope text.

`think` is the session thought log. Connect, Clear, and Disconnect leave it. `think_chrome` holds fold, close, and the dragged height for this window. `think_yaml` is the shared `aetheris-think.yaml`. A version other than 1 stays unread.

`vt_think` is the thought text of the focused terminal session. `vt_open` is true when that strip is showing. `vt_slide` is the rise from the top. `0` starts shut. `-1` is settled. `vt_folded` is true when the strip is folded. `vt_height` is the dragged height. Zero uses the three-line view. `vt_think_on` is true when this session's thinking is on. `vt_chrome` is the file seed for a new terminal pane. `vt_yaml` is `aetheris-vt100-think.yaml`. A version other than 1 stays unread.

`roux_used` is the Roux count for the focused pane. `None` means the reply has not been held. `roux_chars` is the character count. `roux_blocks` is the stored message count. `tokens_asked` is true after the `session_tokens` ask for the focused pane. `live_line` is the text typed on the focused pane and not yet submitted.

`mood` is the showing user's mood. A version other than 1 stays unread. `mood_owner` is the sanitized user whose record is `mood`. Empty means no user is seated yet. `mood_bank` holds other users already read. `ssh_mood` is the mood footer box for the canvas user now showing. `ssh_users` pairs each user with whether that user's SSH mood is armed. `ssh_mood_blocked` is true when an SSH mood file is not version 1. `expanded` is the per-cell Expanded View file. A settings store with no file does not read or write that home file. `mood_desk` is the mood glass. `mood_seq` counts mood wire ids and is not the card sequence.

`cards` is the canvas deck. `focus` is the focused card index. `scroll_seat` is the text pane that receives the wheel, or `None` for the canvas. `cell_seq` is the next card sequence. `edits` is the in-memory undo desk for canvas prompts. `prompt_focus` is the prompt that held focus on the previous canvas paint. `group_live` is the group id that is running. `group_rest` holds the remaining group frames. `run_pane` is the pane that carries the showing deck's run. `None` uses the focused pane. `pipe_live` is true while a pipe chain is running. `pipe_rest` is the card indexes still waiting. `scope_stash` holds scope waiting for the canvas. `paint_stash` holds terminal paint bytes waiting for the focused grid. `join_from` is the card index armed for Join, or `None`. `focus_prompt` asks the next paint to put the keyboard in the prompt. `seat_focus` is a one-shot that puts focus on the selected cell after Hide Collapsed or Show Collapsed. `land_newest` is a one-shot that scrolls to the newest card.

`canvas_weight` is this window's canvas share. `parked_share` is a share put aside by an arrow. `divider_span` is the last bar span. The settings write waits until that span is at least 32. `divider_armed` is when the share last moved. The settings write waits 400 ms. `inspector_soft_wrap` is the soft-wrap flag. It starts on and is not saved.

`wrap_columns` is the shell column count. A missing key is 80. `wrap_field` is the typed column text. `wrap_armed` is when that field last changed. A typed number waits 800 ms. `line_chars` is the inspector line length. A missing key is 80. `line_field` and `line_armed` are that field and its last change. `view_rows` is the open-cell row count. A missing key is 4. `view_field` and `view_armed` are that field and its last change.

`tools_open` is the tool bar. It starts closed and is not saved. `bars_open` is the group-tool row. It starts open and is not saved. `hide_collapsed` keeps folded cards off the deck. `concealed` is the cell ids Hide Collapsed took off the deck. `conceal_armed` is true after this deck has seeded that set. `hide_blocked` is true when a bad hide file stays unread. `hide_extras` is the other keys written back with the flag.

`control_open` is the connection bar. It starts open and is not saved. `link_up` is true after Connected until the session drops. `control_span` is the last frame size. The first sight is not a resize. `tool_needle` is the search field. `find_needle` is the committed find filter. An empty filter shows every card the conceal set still allows. `pin_on` is the pin filter. `pin_needle` is the pin words. `pin_bar_open` is the pin bar. A missing stored key starts it folded. `pin_enter_seen` is true after the pin field already reported this focus loss.

`new_clicked`, `clear_clicked`, `delete_clicked`, `run_clicked`, `pipe_clicked`, and `stop_clicked` are the canvas buttons waiting for a later pass. `console_focus` tracks which console holds the keys. `exit_after_paint` asks the loop to leave after this frame.

`grok` is the shared `aetheris-grok.yaml`. A version other than 1 stays unread. `token_open` is the token window. `ring_used` is the integer the token paint shows. `heat_chars`, `heat_blocks`, and `heat_body` are the last seated canvas beat. `canvas_heat_sig` is the last value that decides whether the canvas heatbar posts a new beat. `canvas_sig_set` is true after that value has been stored. `vt_used`, `vt_chars`, and `vt_blocks` are the terminal heat counts. `vt_heat_sig` is the last value that decides whether the terminal heatbar posts a new beat. `vt_sig_set` is true after that value has been stored. `vt_memory` is the cached terminal text. `vt_memory_gen` is the `grid_gen` that cache was built from. `grid_gen` increases when the grid is fed, cleared, resized, or swapped. `heat_seq` is the heat sequence. `heat_kind` is 0 for none, 1 for the canvas, and 2 for the terminal. `heat` is the newest-only heatbar queue. `token_pick` is the combo label while the token window is open. Empty selects the focused turn.

`model` is the one Model Config modal. `engine_label` is the focused pane's model word. `engine_asked` is true after this connection has been asked for that model. `list_slide_gen` is the model-list notice last copied onto the error slide. `shell_open` is true when the next shell answer should open the modal. `console_model_pane` is the pane that opened the console Model Config. `console_return` is the pane to focus after that modal closes. `osc_hold` holds the tail of a split `select-model` sequence. `pool_boot` is the pool boot id for this window.

`pane_id` is the focused pane. `next_pane` is the next id to assign. `panes` holds every [`ParkedPane`](struct.ParkedPane.md). `decks` holds canvas decks that are not on the glass. `canvas_user` exists only in tests and is the glass user when a test has no vault file. `tree` is the [`PaneNode`](enum.PaneNode.md) layout. `term_font` is the live font size. `last_connect` is the last connect fields for the focused pane.

`paste` is the open paste editor. `paste_in` is clipboard text waiting to open that editor. `shell_notice` is the open terminal notice. `help_open`, `about_open`, and `settings_open` are those modals. `painted_borders` is the optional cell frame. It starts off. `debug_mood` paints the invitation in the inspector when on. `tooltips_on` is the hover-tip switch. `settings_blocked` is true when a bad settings file stays unread. `settings_extras` is the other keys written back with the switches. `about_app` is the About mark, loaded on the first open.

`exit_ask` is the confirm-exit box. `exit_confirmed` is true after Yes. `new_term_ask` is the split box. `reconnect_clicked`, `clear_term_clicked`, `send_file_clicked`, `close_pane_clicked`, and `import_clicked` are those commands waiting for a later pass. `export_pane` is the pane whose Roux export was asked. `export_file` is the TOON text waiting for the save dialog. `transfer_pane` is the pane whose Roux table is copied onto the canvas. `transfer_roux` is true while the canvas copy into Roux is waiting for the reply. `pane_model_clicked` asks the focused pane to open Model Config. `focus_pane_request` is a pane id to bring forward. `saved_frame` is the restored rectangle. A maximized frame does not replace it. `open_maximized` is the open flag. A missing key keeps the maximized open.

`ctx` is the egui context. `state` is the winit integration. `painter` is the wgpu painter. `window` is the open window. Each of those three stays `None` until `open_window` succeeds.

| | |
| --- | --- |
| **Specification** | PK-1156 docs/specs/common/formal-plan.spec.md FINESSE:f-100 |
| **Specification** | PK-1155 docs/specs/common/formal-plan.spec.md FINESSE:f-101 |
| **Specification** | PK-1151 docs/specs/common/formal-plan.spec.md FINESSE:f-105 |
| **Specification** | PK-1148 docs/specs/common/formal-plan.spec.md FINESSE:f-109 |
| **Specification** | PK-1144 docs/specs/common/formal-plan.spec.md FINESSE:f-113 |
| **Specification** | PK-1141 docs/specs/common/formal-plan.spec.md FINESSE:f-116 |
| **Specification** | PK-1140 docs/specs/common/formal-plan.spec.md FINESSE:f-117 |
| **Specification** | PK-1139 docs/specs/common/formal-plan.spec.md FINESSE:f-118 |
| **Specification** | PK-1138 docs/specs/common/formal-plan.spec.md FINESSE:f-119 |
| **Specification** | PK-1134 docs/specs/common/formal-plan.spec.md FINESSE:f-125 |
| **Specification** | PK-1132 docs/specs/common/formal-plan.spec.md FINESSE:f-129 |
| **Specification** | PK-1127 docs/specs/common/formal-plan.spec.md FINESSE:f-140 |
| **Specification** | PK-1126 docs/specs/common/formal-plan.spec.md FINESSE:f-141 |
| **Specification** | PK-1124 docs/specs/common/formal-plan.spec.md FINESSE:f-143 |
| **Specification** | PK-1123 docs/specs/common/formal-plan.spec.md FINESSE:f-144 |
| **Specification** | PK-1118 docs/specs/common/formal-plan.spec.md FINESSE:f-149 |
| **Specification** | PK-1160 docs/specs/common/stages.spec.md FINESSE:f-152 |
| **Specification** | PK-1115 docs/specs/common/formal-plan.spec.md FINESSE:f-154 |
| **Specification** | PK-1159 docs/specs/common/stages.spec.md FINESSE:f-157 |
| **Specification** | PK-1106 docs/specs/common/formal-plan.spec.md FINESSE:f-173 |
| **Specification** | PK-1104 docs/specs/common/formal-plan.spec.md FINESSE:f-175 |
| **Specification** | PK-1100 docs/specs/common/formal-plan.spec.md FINESSE:f-179 |
| **Specification** | PK-1167 docs/specs/java/org/aetheris/swing/swing.spec.md FINESSE:f-211 |
| **Specification** | PK-1070 docs/specs/common/formal-plan.spec.md FINESSE:f-221 |
| **Specification** | GK-0095 aetheris-stage-2-finessing.001.md FINESSE:f-222 |
| **Specification** | PK-1018 docs/specs/common/formal-plan.spec.md FINESSE:f-77 |
| **Specification** | PK-1016 docs/specs/common/formal-plan.spec.md FINESSE:f-79 |
| **Specification** | PK-1015 docs/specs/common/formal-plan.spec.md FINESSE:f-80 |
| **Specification** | PK-1014 docs/specs/common/formal-plan.spec.md FINESSE:f-81 |
| **Specification** | PK-999 docs/specs/common/formal-plan.spec.md FINESSE:f-96 |
| **Specification** | PK-997 docs/specs/common/formal-plan.spec.md FINESSE:f-98 |
| **Specification** | PK-1180 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md |
| **Specification** | PK-1066 docs/specs/common/formal-plan.spec.md FINESSE:f-226 |

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| Door | [`door`](#member-door) |  |
| String | [`host`](#member-host) |  |
| String | [`port`](#member-port) |  |
| String | [`user`](#member-user) |  |
| String | [`password`](#member-password) |  |
| bool | [`remember`](#member-remember) |  |
| String | [`status`](#member-status) |  |
| Option<ErrorNotice> | [`slide`](#member-slide) | Open error notice. Close drops it. A later miss replaces the body. |
| Option<ViewPane> | [`view`](#member-view) | Open reply window. It floats above the canvas and takes the pointer. Close drops it. A second View waits. |
| u64 | [`view_generation`](#member-view_generation) |  |
| Option<&'static str> | [`door_note`](#member-door_note) |  |
| bool | [`save_pending`](#member-save_pending) |  |
| bool | [`connect_clicked`](#member-connect_clicked) |  |
| bool | [`disconnect_clicked`](#member-disconnect_clicked) |  |
| Option<Session> | [`session`](#member-session) |  |
| Option<tokio::runtime::Runtime> | [`vault_rt`](#member-vault_rt) | One runtime for the vault file. This thread blocks on it. It is not the SSH runtime. |
| Option<crate::vault::Vault> | [`vault`](#member-vault) | Open `aetheris.db` after connect lands. None leaves the deck in memory. |
| String | [`vault_canvas`](#member-vault_canvas) | First canvas on that file. |
| Vec<String> | [`vault_hold`](#member-vault_hold) | Ids on that canvas that this deck does not show. |
| Grid | [`grid`](#member-grid) |  |
| bool | [`at_tail`](#member-at_tail) |  |
| bool | [`jump_tail`](#member-jump_tail) |  |
| i32 | [`console_scroll_lines`](#member-console_scroll_lines) | Menu Page or Line motion for the focused console. Positive moves toward the tail. |
| Option<f32> | [`console_scroll_base`](#member-console_scroll_base) | Offset that motion starts from when the pane was parked. None reads the focused scroll. |
| Option<(usize, usize)> | [`anchor`](#member-anchor) |  |
| Option<(usize, usize)> | [`current`](#member-current) |  |
| bool | [`grid_focus`](#member-grid_focus) |  |
| Plate | [`plate`](#member-plate) |  |
| bool | [`restore_aetheris`](#member-restore_aetheris) | Armed when the stored word is `aetheris`. Connect has not landed yet. |
| Option<CanvasFacts> | [`facts`](#member-facts) |  |
| String | [`scope_text`](#member-scope_text) |  |
| ThinkLog | [`think`](#member-think) | Session thought log. Connect, Clear, and Disconnect leave it. |
| ThinkChrome | [`think_chrome`](#member-think_chrome) | Fold, Close, and the dragged height for this window. |
| ThinkYaml | [`think_yaml`](#member-think_yaml) | Shared `aetheris-think.yaml`. A version other than 1 stays unread. |
| ThinkLog | [`vt_think`](#member-vt_think) | Thought text of the focused VT100 session. Each parked session keeps its own. |
| bool | [`vt_open`](#member-vt_open) | The focused session's strip is on the plate. Each parked session keeps its own. |
| f32 | [`vt_slide`](#member-vt_slide) | Rise from the top of the focused session. `0` starts shut. `-1` is settled. |
| bool | [`vt_folded`](#member-vt_folded) | The focused session's strip is folded. Each parked session keeps its own. |
| f32 | [`vt_height`](#member-vt_height) | The focused session's dragged height. Each parked session keeps its own. |
| bool | [`vt_think_on`](#member-vt_think_on) | The focused session's thinking is on. Each parked session keeps its own. |
| Option<i32> | [`roux_used`](#member-roux_used) | Roux session count for the focused pane. None until the reply is held. |
| i32 | [`roux_chars`](#member-roux_chars) | Characters in the held Roux messages. |
| i32 | [`roux_blocks`](#member-roux_blocks) | Stored Roux messages in the held count. |
| bool | [`tokens_asked`](#member-tokens_asked) | The session_tokens ask has been sent for the focused pane. |
| String | [`live_line`](#member-live_line) | Characters typed on the focused pane and not yet submitted. |
| ThinkChrome | [`vt_chrome`](#member-vt_chrome) | File seed for a new VT100 pane and the next open. Live height stays on the session. |
| VtThinkYaml | [`vt_yaml`](#member-vt_yaml) | `aetheris-vt100-think.yaml`. A version other than 1 stays unread. |
| MoodYaml | [`mood`](#member-mood) | The showing user's mood. A version other than 1 stays unread. |
| String | [`mood_owner`](#member-mood_owner) | Sanitized user whose record is `mood`. Empty until a user is seated. |
| Vec<(String, MoodYaml)> | [`mood_bank`](#member-mood_bank) | Other users already read. A miss is that user's file, not the showing record. |
| bool | [`ssh_mood`](#member-ssh_mood) | Mood footer box for the canvas user now on the glass. The map is the store. |
| Vec<(String, bool)> | [`ssh_users`](#member-ssh_users) | SSH mood armed by canvas user. The shared mood file does not carry this. |
| bool | [`ssh_mood_blocked`](#member-ssh_mood_blocked) | An SSH mood file whose version is not 1. Left unread and not overwritten. |
| ExpandedYaml | [`expanded`](#member-expanded) | Per-cell Expanded View. A version other than 1 stays unread. |
| MoodDesk | [`mood_desk`](#member-mood_desk) | The mood glass. Wire ids stay after the dialog closes. |
| u64 | [`mood_seq`](#member-mood_seq) | Count for mood wire ids. It is not the card sequence. |
| Vec<TurnCard> | [`cards`](#member-cards) |  |
| usize | [`focus`](#member-focus) |  |
| Option<(usize, TextPane)> | [`scroll_seat`](#member-scroll_seat) | Which text pane holds the wheel. None is the canvas. The armed cell stays armed. |
| u64 | [`cell_seq`](#member-cell_seq) |  |
| crate::edit::EditDesk | [`edits`](#member-edits) | RAM undo for canvas prompts. One seat per cell. A restart drops it. |
| Option<usize> | [`prompt_focus`](#member-prompt_focus) | Prompt that held focus on the previous canvas paint. |
| u64 | [`group_live`](#member-group_live) |  |
| Vec<([u8 16], Vec<u8>)> | [`group_rest`](#member-group_rest) |  |
| Option<u64> | [`run_pane`](#member-run_pane) | Pane that carries the showing deck's run. None uses the focused pane. |
| bool | [`pipe_live`](#member-pipe_live) | Chain cards still waiting. The running card is not in this list. |
| Vec<usize> | [`pipe_rest`](#member-pipe_rest) |  |
| VecDeque<(ScopePiece, bool)> | [`scope_stash`](#member-scope_stash) | Scope waiting for the canvas. The bool is true once the console thought was offered. |
| VecDeque<Vec<u8>> | [`paint_stash`](#member-paint_stash) |  |
| Option<usize> | [`join_from`](#member-join_from) |  |
| bool | [`focus_prompt`](#member-focus_prompt) |  |
| bool | [`seat_focus`](#member-seat_focus) | One shot. Hide Collapsed and Show Collapsed seat focus on the selected cell. |
| bool | [`land_newest`](#member-land_newest) | One shot. The next canvas paint scrolls to the newest card. |
| f32 | [`canvas_weight`](#member-canvas_weight) | Canvas share for this window. A drag moves it. The door remembers it. |
| Option<f32> | [`parked_share`](#member-parked_share) | Share parked by an arrow. The other arrow brings it back. |
| f32 | [`divider_span`](#member-divider_span) | Last bar span. The door write waits until the span is at least 32. |
| Option<Instant> | [`divider_armed`](#member-divider_armed) | When the share last moved. The door write waits 400 ms. |
| bool | [`inspector_soft_wrap`](#member-inspector_soft_wrap) | Soft wrap. Swing's checkbox starts on and is not saved. |
| u32 | [`wrap_columns`](#member-wrap_columns) | Shell column count. A missing key is 80. |
| String | [`wrap_field`](#member-wrap_field) | The column field. A typed number waits 800 ms. |
| Option<Instant> | [`wrap_armed`](#member-wrap_armed) | When the column field last changed. |
| u32 | [`line_chars`](#member-line_chars) | Inspector line length. A missing key is 80. |
| String | [`line_field`](#member-line_field) | The length field. A typed number waits 800 ms. |
| Option<Instant> | [`line_armed`](#member-line_armed) | When the length field last changed. |
| u32 | [`view_rows`](#member-view_rows) | Open-cell rows. A missing key is 4. |
| String | [`view_field`](#member-view_field) | The row field. A typed number waits 800 ms. |
| Option<Instant> | [`view_armed`](#member-view_armed) | When the row field last changed. |
| bool | [`tools_open`](#member-tools_open) | Tool bar open for this window. It starts closed. The choice is not saved. |
| bool | [`bars_open`](#member-bars_open) | BB1 and BB2 showing. It starts open. The choice is not saved. |
| bool | [`hide_collapsed`](#member-hide_collapsed) | Folded cards stay off the deck. Show brings them back. The choice is saved. |
| Vec<[u8 16]> | [`concealed`](#member-concealed) | Ids Hide Collapsed took off the deck. A fold never adds an id. LOCK F-141. |
| bool | [`conceal_armed`](#member-conceal_armed) | True after this deck has seeded the conceal set from the hide seat. |
| bool | [`hide_blocked`](#member-hide_blocked) | A bad hide file stays unread and is not overwritten. |
| String | [`hide_extras`](#member-hide_extras) | Other keys in the hide file, written back with the flag. |
| bool | [`control_open`](#member-control_open) | Connection bar showing. It starts open. The choice is not saved. |
| bool | [`link_up`](#member-link_up) | True after Connected, until the session drops. A canvas line does not clear it. |
| Option<egui::Vec2> | [`control_span`](#member-control_span) | Last frame size. The first sight is not a resize. |
| String | [`tool_needle`](#member-tool_needle) | Search field for this window. It is not written. |
| String | [`find_needle`](#member-find_needle) | Committed find filter. Empty shows every card the conceal set still allows. |
| bool | [`pin_on`](#member-pin_on) | Pin filter for this window. Turning the box off keeps the words. The states stay. |
| String | [`pin_needle`](#member-pin_needle) |  |
| bool | [`pin_bar_open`](#member-pin_bar_open) | Pin bar showing. A missing door key starts folded. Folding does not change the box. |
| bool | [`pin_enter_seen`](#member-pin_enter_seen) | The pin field already reported this focus loss. The next frame does not commit. |
| bool | [`new_clicked`](#member-new_clicked) |  |
| bool | [`clear_clicked`](#member-clear_clicked) |  |
| bool | [`delete_clicked`](#member-delete_clicked) |  |
| bool | [`run_clicked`](#member-run_clicked) |  |
| bool | [`pipe_clicked`](#member-pipe_clicked) |  |
| bool | [`stop_clicked`](#member-stop_clicked) |  |
| ConsoleFocus | [`console_focus`](#member-console_focus) |  |
| bool | [`exit_after_paint`](#member-exit_after_paint) |  |
| crate::model::GrokDoc | [`grok`](#member-grok) | Shared `aetheris-grok.yaml`. A version other than 1 stays unread. |
| bool | [`token_open`](#member-token_open) | Token window. T opens it. OK and the close icon dismiss it. |
| i32 | [`ring_used`](#member-ring_used) | The integer paint_token shows. The Aetheris heatbar wears this count. |
| i32 | [`heat_chars`](#member-heat_chars) | Characters from the last seated canvas beat. |
| i32 | [`heat_blocks`](#member-heat_blocks) | Blocks from the last seated canvas beat. |
| String | [`heat_body`](#member-heat_body) | Inspector text from the last seated canvas beat. |
| u64 | [`canvas_heat_sig`](#member-canvas_heat_sig) |  |
| bool | [`canvas_sig_set`](#member-canvas_sig_set) |  |
| i32 | [`vt_used`](#member-vt_used) | The VT100 heatbar wears this count. The key does not walk the grid. |
| i32 | [`vt_chars`](#member-vt_chars) |  |
| i32 | [`vt_blocks`](#member-vt_blocks) |  |
| u64 | [`vt_heat_sig`](#member-vt_heat_sig) |  |
| bool | [`vt_sig_set`](#member-vt_sig_set) |  |
| std::sync::Arc<str> | [`vt_memory`](#member-vt_memory) |  |
| u64 | [`vt_memory_gen`](#member-vt_memory_gen) |  |
| u64 | [`grid_gen`](#member-grid_gen) | Bumped when the grid is fed, cleared, resized, or swapped. |
| u64 | [`heat_seq`](#member-heat_seq) |  |
| u8 | [`heat_kind`](#member-heat_kind) | 0 none, 1 canvas, 2 vt. A plate change posts again. |
| HeatQueue | [`heat`](#member-heat) | Newest-only heatbar queue. The walk runs off the frame. |
| String | [`token_pick`](#member-token_pick) | Combo label while the token window is open. Empty selects the focused turn. |
| crate::model::ModelSession | [`model`](#member-model) | The one Model Config modal. |
| String | [`engine_label`](#member-engine_label) | The focused pane's selected model. Empty prints as ollama. A canvas cell is not this word. |
| bool | [`engine_asked`](#member-engine_asked) | This connection has been asked for the saved model. A disconnect clears it. |
| u64 | [`list_slide_gen`](#member-list_slide_gen) | The model-list notice generation last copied onto the error slide. |
| bool | [`shell_open`](#member-shell_open) | The next shell answer opens the modal. A connect ask leaves it shut. |
| Option<u64> | [`console_model_pane`](#member-console_model_pane) | Pane that opened the console Model Config. The close returns the keyboard there. |
| Option<u64> | [`console_return`](#member-console_return) | Pane to focus after that modal closes. Taken at the end of the frame. |
| crate::model::OscHold | [`osc_hold`](#member-osc_hold) | Held tail of a split `select-model` sequence. |
| String | [`pool_boot`](#member-pool_boot) | Pool boot id for this window. |
| u64 | [`pane_id`](#member-pane_id) | Focused pane. Its grid and session stay on this struct. |
| u64 | [`next_pane`](#member-next_pane) |  |
| Vec<ParkedPane> | [`panes`](#member-panes) |  |
| Vec<ParkedDeck> | [`decks`](#member-decks) | Canvas seats that are not on the glass. Each keeps its own run. |
| String | [`canvas_user`](#member-canvas_user) | Glass user when a test has no vault file. Production leaves this off. |
| PaneNode | [`tree`](#member-tree) |  |
| i32 | [`term_font`](#member-term_font) | Live spinner. Every pane and the canvas type use it. |
| Option<ConnectSnap> | [`last_connect`](#member-last_connect) | Last connect fields for the focused pane. A new pane starts without one. |
| Option<PasteDraft> | [`paste`](#member-paste) |  |
| Option<String> | [`paste_in`](#member-paste_in) |  |
| Option<ShellNotice> | [`shell_notice`](#member-shell_notice) |  |
| bool | [`help_open`](#member-help_open) |  |
| bool | [`about_open`](#member-about_open) |  |
| bool | [`settings_open`](#member-settings_open) | Settings modal. File, Settings. |
| bool | [`painted_borders`](#member-painted_borders) | Optional Java cell frame on the canvas text panes. Starts off. |
| bool | [`debug_mood`](#member-debug_mood) | File, Settings. On paints the invitation in the inspector. Starts off. |
| bool | [`tooltips_on`](#member-tooltips_on) | Hover tips. On is the present behavior. Off shows none. |
| bool | [`settings_blocked`](#member-settings_blocked) | A bad settings file stays unread and is not overwritten. |
| String | [`settings_extras`](#member-settings_extras) | Other keys in the settings file, written back with the switch. |
| Option<egui::TextureHandle> | [`about_app`](#member-about_app) | App mark for the About box. Loaded on the first open. |
| bool | [`exit_ask`](#member-exit_ask) |  |
| bool | [`exit_confirmed`](#member-exit_confirmed) |  |
| bool | [`new_term_ask`](#member-new_term_ask) |  |
| bool | [`reconnect_clicked`](#member-reconnect_clicked) |  |
| bool | [`clear_term_clicked`](#member-clear_term_clicked) |  |
| bool | [`send_file_clicked`](#member-send_file_clicked) |  |
| bool | [`close_pane_clicked`](#member-close_pane_clicked) |  |
| bool | [`import_clicked`](#member-import_clicked) |  |
| Option<u64> | [`export_pane`](#member-export_pane) | Pane whose Roux export was asked. None means no ask is open. |
| Option<String> | [`export_file`](#member-export_file) | TOON waiting for the save dialog. |
| Option<u64> | [`transfer_pane`](#member-transfer_pane) | Pane whose Roux table is copied onto the canvas. None means no copy is open. |
| bool | [`transfer_roux`](#member-transfer_roux) | True while the canvas copy into Roux is waiting for the reply. |
| bool | [`pane_model_clicked`](#member-pane_model_clicked) |  |
| Option<u64> | [`focus_pane_request`](#member-focus_pane_request) |  |
| Option<(i32, i32, i32, i32)> | [`saved_frame`](#member-saved_frame) | Restored rectangle. A maximized frame does not replace it. |
| bool | [`open_maximized`](#member-open_maximized) | Missing key keeps the seated maximized open. |
| egui::Context | [`ctx`](#member-ctx) |  |
| Option<egui_winit::State> | [`state`](#member-state) |  |
| Option<egui_wgpu::winit::Painter> | [`painter`](#member-painter) |  |
| Option<Arc<Window>> | [`window`](#member-window) |  |

## Method Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`new`](#method-new) | The function `new` builds the client window from the SSH client settings store. |
| fn | [`open_window`](#method-open_window) | The function `open_window` creates the native window when none is open. |
| fn | [`clamp_resize`](#method-clamp_resize) | The method `clamp_resize` keeps a physical resize inside the minimum and tells the painter. |
| fn | [`ask_session_export`](#method-ask_session_export) | Ask this pane's connection for its Roux table. A raw console does not ask. |
| fn | [`hold_session_export`](#method-hold_session_export) | The method `hold_session_export` stores a Roux export reply and clears the waiting pane. |
| fn | [`finish_session_export`](#method-finish_session_export) | The save dialog. Cancel writes nothing. Tests do not call this. |
| fn | [`console_ready`](#method-console_ready) | The focused pane, or a parked pane, when that connection can carry Roux. |
| fn | [`wire_console`](#method-wire_console) | The function `wire_console` sends one frame on a pane's session. |
| fn | [`ask_transfer_from_console`](#method-ask_transfer_from_console) | OK, then the Roux table. The canvas stays until that table parses. |
| fn | [`ask_transfer_from_canvas`](#method-ask_transfer_from_canvas) | OK, then the cells. Roux stays until the server accepts the table. |
| fn | [`land_transfer_export`](#method-land_transfer_export) | The method `land_transfer_export` finishes a Roux table that was copied onto the canvas. |
| fn | [`land_transfer_replace`](#method-land_transfer_replace) | The function `land_transfer_replace` records the reply to a canvas copy into Roux. |
| fn | [`land_transfer_cards`](#method-land_transfer_cards) | The canvas becomes the copied cells. An empty list leaves one empty card. |
| fn | [`drain_parked`](#method-drain_parked) | The method `drain_parked` reads every pane that is not in front and returns whether the window should paint again. |
| fn | [`drain_link`](#method-drain_link) | The method `drain_link` reads the focused session after the parked panes and returns a [`CommitWake`]. |
| fn | [`write_door`](#method-write_door) | The method `write_door` copies the connection fields into the SSH client settings store and saves that store. |
| fn | [`connect`](#method-connect) | The method `connect` remembers the connection fields, writes the SSH client settings store, and starts a session. |
| fn | [`reconnect`](#method-reconnect) | The method `reconnect` closes the focused session and starts it again with the fields on the bar. |
| fn | [`remember_connect`](#method-remember_connect) | The method `remember_connect` stores the connection fields for the focused pane. |
| fn | [`clear_terminal`](#method-clear_terminal) | The method `clear_terminal` wipes the focused terminal and sits on the tail. |
| fn | [`open_frame`](#method-open_frame) | The method `open_frame` chooses the rectangle used to open the window. |
| fn | [`note_frame`](#method-note_frame) | The method `note_frame` records the window rectangle and the maximized flag. |
| fn | [`store_frame`](#method-store_frame) | The method `store_frame` writes the saved window rectangle into the SSH client settings store. |
| fn | [`store_font`](#method-store_font) | The method `store_font` writes the live font size into the SSH client settings store. |
| fn | [`show_shell_notice`](#method-show_shell_notice) | The method `show_shell_notice` opens a terminal notice whose body is not a thrown read. |
| fn | [`show_shell_error`](#method-show_shell_error) | The method `show_shell_error` opens a terminal notice for a thrown read. |
| fn | [`open_paste`](#method-open_paste) | The method `open_paste` opens the paste editor with normalized text. |
| fn | [`accept_paste`](#method-accept_paste) | The method `accept_paste` takes the editor body and writes it when the decision allows. |
| fn | [`insert_paste`](#method-insert_paste) | The method `insert_paste` writes bracketed paste on the focused session. |
| fn | [`shell_modal`](#method-shell_modal) | The method `shell_modal` reports whether a terminal modal is open. |
| fn | [`any_session`](#method-any_session) | The method `any_session` reports whether any pane has an SSH session. |
| fn | [`take_shell_keys`](#method-take_shell_keys) | Ctrl+Shift+D / E / W split or close on any plate. |
| fn | [`show_focused_thinking`](#method-show_focused_thinking) | The method `show_focused_thinking` opens the thinking strip on the plate that is showing. |
| fn | [`paint_menu`](#method-paint_menu) | The method `paint_menu` paints the File, Tools, Token Calc, and Help bar. |
| fn | [`note_shell_door`](#method-note_shell_door) | The method `note_shell_door` keeps the file-dialog place in the SSH client settings store. |
| fn | [`begin_send_file`](#method-begin_send_file) | The method `begin_send_file` opens a file in the paste editor. |
| fn | [`begin_console_import`](#method-begin_console_import) | The method `begin_console_import` reads a file into the focused terminal. |
| fn | [`paint_settings`](#method-paint_settings) | The settings window. The close icon and OK dismiss it. The switch applies at once. |
| fn | [`paint_shell_dialogs`](#method-paint_shell_dialogs) | The method `paint_shell_dialogs` paints the terminal modals. |
| fn | [`pane_count`](#method-pane_count) | The method `pane_count` returns how many panes the tree holds. |
| fn | [`split_focused`](#method-split_focused) | The method `split_focused` parks the focused pane and opens a new pane beside it. |
| fn | [`close_focused_pane`](#method-close_focused_pane) | The method `close_focused_pane` removes the focused pane when another pane remains. |
| fn | [`focus_pane`](#method-focus_pane) | The method `focus_pane` brings a parked pane to the front. |
| fn | [`capture_focused`](#method-capture_focused) | The method `capture_focused` moves the focused pane into a [`ParkedPane`]. |
| fn | [`install_pane`](#method-install_pane) | The method `install_pane` loads a parked pane into the focused fields. |
| fn | [`pane_for_user`](#method-pane_for_user) | The method `pane_for_user` returns the pane id whose last user matches `name`. |
| fn | [`live_seats`](#method-live_seats) | The method `live_seats` lists one [`ConnectedSeat`] for the focused pane and one for each parked pane. |
| fn | [`seat_control_status`](#method-seat_control_status) | A shell word moves the connection bar. A canvas line does not call this. |
| fn | [`deck_key`](#method-deck_key) | The user whose canvas is on the glass. Empty until a vault is open. |
| fn | [`glass_pane`](#method-glass_pane) | Pane id for the glass user's execute. None when that user has no pane. |
| fn | [`live_glass_pane`](#method-live_glass_pane) | The glass user's pane when its mux has answered. Another user's socket stays idle. |
| fn | [`land_focused_canvas`](#method-land_focused_canvas) | Seat focused canvas pieces on the user who owns this pane. |
| fn | [`note_replaced_run`](#method-note_replaced_run) | This pane's socket is about to be replaced. A run on that socket cannot continue. |
| fn | [`wire_owned`](#method-wire_owned) | Wire one frame on the pane that owns the showing run. |
| fn | [`wire_pane`](#method-wire_pane) | The method `wire_pane` writes frames on one pane's session. |
| fn | [`lift_showing`](#method-lift_showing) | Move the glass into a seat. The caller supplies the user key. |
| fn | [`plant_showing`](#method-plant_showing) | The method `plant_showing` copies a parked canvas onto the showing fields. |
| fn | [`remember_deck`](#method-remember_deck) | The method `remember_deck` stores one user's canvas in `decks`. |
| fn | [`plant_user`](#method-plant_user) | Put a parked user back on the glass. False when that seat is absent. |
| fn | [`load_user_canvas`](#method-load_user_canvas) | The method `load_user_canvas` reads one user's first canvas from the vault file. |
| fn | [`adopt_loaded`](#method-adopt_loaded) | The method `adopt_loaded` replaces the showing canvas with a loaded vault canvas. |
| fn | [`seat_canvas_user`](#method-seat_canvas_user) | The named user takes the glass. The user who was showing keeps the seat and the run. |
| fn | [`card_snap`](#method-card_snap) | The model snap for a cell on the glass or on a parked seat. |
| fn | [`deliver_scope`](#method-deliver_scope) | Apply canvas pieces to the user who owns them. |
| fn | [`close_vault`](#method-close_vault) | The method `close_vault` drops the open vault from the showing fields. |
| fn | [`store_vault`](#method-store_vault) | Write the open canvas from this thread. A missing file writes nothing. |
| fn | [`search_vault`](#method-search_vault) | The method `search_vault` searches the open vault and returns a [`SearchSeat`]. |
| fn | [`store_think_yaml`](#method-store_think_yaml) | The method `store_think_yaml` writes the shared think record. |
| fn | [`keep_card_think`](#method-keep_card_think) | The five fields for this cell. A match leaves the file alone. |
| fn | [`remember_live_expanded`](#method-remember_live_expanded) | Checked cards join the file. An unchecked card is left as it was. |
| fn | [`keep_card_expanded`](#method-keep_card_expanded) | The check for this cell. A match leaves the file alone. A memory door writes nothing. |
| fn | [`store_expanded`](#method-store_expanded) | The method `store_expanded` writes the expanded-view record. |
| fn | [`arm_conceal`](#method-arm_conceal) | Seed the conceal set once. A fold does not call this. |
| fn | [`release_deck`](#method-release_deck) | A fold clears the omission. It never adds an id. |
| fn | [`store_hide`](#method-store_hide) | Hide or Show for the next open. A memory door writes nothing. A blocked file stays. |
| fn | [`store_settings`](#method-store_settings) | Painted borders for the next open. A memory door writes nothing. A blocked file stays. |
| fn | [`aetheris_status_facts`](#method-aetheris_status_facts) | Vault, wire, air-gap, and engine. The canvas extra stays on the Aetheris strip. |
| fn | [`shell_status_facts`](#method-shell_status_facts) | Vault, mode, air-gap, and engine. AMWP stays on the Aetheris strip. |
| fn | [`aetheris_status_face`](#method-aetheris_status_face) | West notebook line and the east user name. The link chip stays on the connection bar. |
| fn | [`store_vt_yaml`](#method-store_vt_yaml) | The method `store_vt_yaml` writes the VT100 think record. |
| fn | [`store_mood_yaml`](#method-store_mood_yaml) | The method `store_mood_yaml` writes the mood record for the current owner. |
| fn | [`remember_mood`](#method-remember_mood) | The method `remember_mood` stores one user's mood record in `mood_bank`. |
| fn | [`adopt_mood_user`](#method-adopt_mood_user) | Seat `picked`. Stash the user who was showing. Claim the shared file only |
| fn | [`ensure_mood_user`](#method-ensure_mood_user) | Read a pane user's file into the bank. A missing file is not cached and is not claimed. |
| fn | [`try_open_mood`](#method-try_open_mood) | The method `try_open_mood` opens the mood dialog for one card when the gate allows it. |
| fn | [`fire_mood_send`](#method-fire_mood_send) | One shaping frame. The card's running flag stays clear. This path does not call `plan_chain`. |
| fn | [`mood_miss`](#method-mood_miss) | The method `mood_miss` closes the mood dialog and shows a notice. |
| fn | [`seal_mood`](#method-seal_mood) | The method `seal_mood` saves the mood record and closes the dialog. |
| fn | [`paint_token`](#method-paint_token) | The token window. OK and the close icon dismiss it. Tests do not call |
| fn | [`paint_mood`](#method-paint_mood) | The mood glass. The window stays inside the app. The center is the |
| fn | [`send_model_wires`](#method-send_model_wires) | The method `send_model_wires` writes each model request as a text frame on the live session. The parameter `wires` is the slice of [`ModelWire`] values to send. A session that has not answered writes no frame. |
| fn | [`land_model`](#method-land_model) | The method `land_model` applies one [`ModelEffect`] and then sends that effect's wires. The parameter `console` is true when the change belongs to the console dialog rather than a card. The parameter `cell` is the card's sixteen-byte id. The parameter `effect` is the change to apply. |
| fn | [`model_card`](#method-model_card) | The method `model_card` returns the stored target, the think snap, and the card index for one cell. The parameter `console` is true for the console dialog. The parameter `cell` is the card's sixteen-byte id. |
| fn | [`open_model`](#method-open_model) | The method `open_model` opens the model dialog on the card at `index`. An index past the deck returns without a change. |
| fn | [`ring_model`](#method-ring_model) | The method `ring_model` asks the live session for the console model list. A false `doorbell` returns without a change. |
| fn | [`ask_shell_once`](#method-ask_shell_once) | The mux asks once. The answer seats the family and the model. The modal stays shut. |
| fn | [`seat_pane_engine`](#method-seat_pane_engine) | The method `seat_pane_engine` stores one pane's model word. |
| fn | [`sync_list_slide`](#method-sync_list_slide) | The method `sync_list_slide` copies a new model-list notice onto the error slide. |
| fn | [`take_model_note`](#method-take_model_note) | The method `take_model_note` reads one model reply. `pane` is the session that carried it. `cell` is the sixteen-byte id the frame named. `body` is the raw reply. |
| fn | [`drop_plate_docs`](#method-drop_plate_docs) | The method `drop_plate_docs` forgets the Grok record for every card and closes a card model dialog. Each card cell becomes a text id. `forget_all` drops those ids. |
| fn | [`copy_new_group_docs`](#method-copy_new_group_docs) | The method `copy_new_group_docs` copies Grok records from the cards already in a group onto the cards that group just gained. The parameter `before` lists the sixteen-byte cells that belonged to the group before the copy. The parameter `source_group` is that group's number. A `source_group` of 0 returns without a copy. |
| fn | [`note_target_change`](#method-note_target_change) | The method `note_target_change` stores a target edit while the field is still changing and returns the stored text. The parameter `index` is the card's place in the deck. The parameter `text` is the field's current text. An index past the deck returns `text` unchanged. |
| fn | [`note_target_leave`](#method-note_target_leave) | The method `note_target_leave` stores a target when the field loses focus and returns the stored text. The parameter `index` is the card's place in the deck. The parameter `text` is the field text at that moment. An index past the deck returns `text` unchanged. |
| fn | [`paint_model`](#method-paint_model) | Model Config. Close leaves the cell as last written. |
| fn | [`paint_model_body`](#method-paint_model_body) | The method `paint_model_body` paints the family picker and the pane for the open model dialog. The parameter `ui` is the egui surface. The parameter `session` is the [`ModelSession`] being edited. |
| fn | [`paint_ollama_pane`](#method-paint_ollama_pane) | The method `paint_ollama_pane` paints the Ollama model fields and lands each edit. The parameter `ui` is the egui surface. The parameter `session` is the [`ModelSession`]. The console flag and the cell come from that session. |
| fn | [`paint_grok_pane`](#method-paint_grok_pane) | The method `paint_grok_pane` paints the Grok model fields and lands each edit. The parameter `ui` is the egui surface. The parameter `session` is the [`ModelSession`]. The console flag and the cell come from that session. The labels `API` and `chat` are fixed text. |
| fn | [`paint_pool_pane`](#method-paint_pool_pane) | The method `paint_pool_pane` paints the pool sentence and the thinking checkbox. The parameter `ui` is the egui surface. The parameter `session` is the [`ModelSession`]. The label is `POOL_SENTENCE`. |
| fn | [`disconnect`](#method-disconnect) | The method `disconnect` stores the account file, closes it, and drops the live session. `store_vault` runs first. `close_vault` runs next. `clear_canvas` clears the facts and the scope text. `drop_plate_docs` forgets the card model records. `clear_deck` clears the cards and the focus. |
| fn | [`note_live_chars`](#method-note_live_chars) | The unsent line on the focused pane. Enter submits it. Backspace drops one character. |
| fn | [`note_live_key`](#method-note_live_key) | Enter submits the line. Backspace drops one character. Other keys stay on the wire only. |
| fn | [`note_grid`](#method-note_grid) | The grid changed. The cached console text is stale. |
| fn | [`bump_heat`](#method-bump_heat) | The method `bump_heat` advances the heat sequence and returns the new value. The parameter `kind` is the beat kind the caller is posting. Canvas heat calls this with 1. Terminal heat calls this with 2. |
| fn | [`apply_heat`](#method-apply_heat) | The method `apply_heat` stores one finished [`HeatPaint`]. The parameter `paint` is that result. |
| fn | [`take_heat`](#method-take_heat) | The method `take_heat` applies one finished heat result or asks for another paint. The parameter `ctx` is the egui context. The parameter `posted` is true when this frame offered a new beat. |
| fn | [`post_canvas_heat`](#method-post_canvas_heat) | Post the canvas beat when the seat changed. The frame paints the last integer. |
| fn | [`post_vt_heat`](#method-post_vt_heat) | Post the VT100 beat when the line or the grid changed. The key does not walk the grid. |
| fn | [`focused_pane_user`](#method-focused_pane_user) | The focused pane's user. Empty stays empty so a quiet field is not `anon`. |
| fn | [`canvas_user`](#method-canvas_user) | The vault user on the canvas. An empty vault uses the focused pane's user. |
| fn | [`mood_line_for_user`](#method-mood_line_for_user) | The line for one pane's user. None leaves a pane that was never told alone. |
| fn | [`store_ssh_mood`](#method-store_ssh_mood) | The method `store_ssh_mood` writes the per-user mood switches. A true `ssh_mood_blocked` returns without a write. A missing home directory returns without a write. |
| fn | [`apply_strokes`](#method-apply_strokes) | The method `apply_strokes` turns console input into session bytes and into local console actions. The parameter `events` is the egui event list for this frame. The parameter `focused` is the console focus flag from before this paint. The parameters `live_shift`, `live_command`, and `live_alt` are the modifier flags for this frame. A false `focused` returns, and the method does not re-read focus. |
| fn | [`ui`](#method-ui) | The method `ui` paints one client frame and then runs the click that frame recorded. The parameter `ui` is the egui surface for that frame. |
| fn | [`relay_ssh_moods`](#method-relay_ssh_moods) | Hand the mood to every muxed pane of an armed user. A raw session is left alone. |
| fn | [`seat_mood_canvas`](#method-seat_mood_canvas) | The showing canvas. The pin map keys on this id. A missing id reads the head. |
| fn | [`land_mood_pin`](#method-land_mood_pin) | A live frame consumes a pending define. A refused frame leaves pending. |
| fn | [`try_run`](#method-try_run) | The method `try_run` starts a run for the focused card, or for the whole group when the ask says so. `seat_mood_canvas` runs first. `ask_run_doc` returns the [`RunAsk`] and any dropped mood notice. `seat_mood_slide` stores that notice. |
| fn | [`try_stop`](#method-try_stop) | The method `try_stop` cancels the focused run and may send the next group frame. `halt_run` returns the cell to cancel. A missing halt sets the status to `Nothing is running.` and returns. |
| fn | [`try_pipe`](#method-try_pipe) | The method `try_pipe` starts a chained run from the focused card. `seat_mood_canvas` runs first. `plan_chain_doc` returns the [`ChainAsk`] and any dropped mood notice. `seat_mood_slide` stores that notice. |
| fn | [`try_clear`](#method-try_clear) | The method `try_clear` wipes the deck back to one card and cancels cards that are still running. `seat_mood_canvas` runs first. When the mood is not blocked and the canvas id is not empty, the mood pin for that canvas becomes `head`. `pending` becomes false. The mood file is stored. |
| fn | [`try_delete`](#method-try_delete) | The method `try_delete` removes the planned cards and cancels any of them that are still running. `plan_delete` returns the cut. A missing plan returns without a change. |
| fn | [`flush_divider`](#method-flush_divider) | The method `flush_divider` writes the inspector split after the save delay. The parameter `ui` supplies the context used to ask for another paint. A missing `divider_armed` instant returns without a write. |
| fn | [`store_wrap_columns`](#method-store_wrap_columns) | The method `store_wrap_columns` saves the inspector wrap width. The parameter `columns` is that width. [`Door`] stores the window settings. A failed save, when `door_note` is empty, stores the text `door file could not be written`. A present note is left as it was. |
| fn | [`store_line_chars`](#method-store_line_chars) | The method `store_line_chars` saves the line-character count. The parameter `count` is that count. [`Door`] stores the window settings. A failed save, when `door_note` is empty, stores the text `door file could not be written`. A present note is left as it was. |
| fn | [`store_view_rows`](#method-store_view_rows) | The method `store_view_rows` saves the view row count. The parameter `rows` is that count. [`Door`] stores the window settings. A failed save, when `door_note` is empty, stores the text `door file could not be written`. A present note is left as it was. |
| fn | [`store_product_tab`](#method-store_product_tab) | The method `store_product_tab` saves which product tab is showing. The parameter `word` is a [`TabWord`]. [`TabWord::Aetheris`] is stored as true. Any other word is stored as false. [`Door`] stores the window settings. A failed save, when `door_note` is empty, stores the text `door file could not be written`. |
| fn | [`store_pin_bar`](#method-store_pin_bar) | The method `store_pin_bar` saves whether the pin bar is open. The parameter `open` is that flag. [`Door`] stores the window settings. A failed save, when `door_note` is empty, stores the text `door file could not be written`. A present note is left as it was. |
| fn | [`flush_wrap`](#method-flush_wrap) | The method `flush_wrap` applies a typed inspector wrap width after the typing delay. The parameter `ui` supplies the context used to ask for another paint. A missing `wrap_armed` instant returns without a change. |
| fn | [`flush_line`](#method-flush_line) | The method `flush_line` applies a typed line-character count after the typing delay. The parameter `ui` supplies the context used to ask for another paint. A missing `line_armed` instant returns without a change. |
| fn | [`flush_view`](#method-flush_view) | The method `flush_view` applies a typed view-row count after the typing delay. The parameter `ui` supplies the context used to ask for another paint. A missing `view_armed` instant returns without a change. |
| fn | [`paint_slide`](#method-paint_slide) | Body plus Close at the top of the Aetheris pane. It stays until Close. |
| fn | [`take_prompt_undo`](#method-take_prompt_undo) | Ctrl-Z and Shift-Ctrl-Z on the prompt that held focus. The terminal does not see the chord. |
| fn | [`paint_empty`](#method-paint_empty) | The method `paint_empty` paints the Aetheris canvas, the split bar, and the inspector, then applies the clicks from that paint. The parameter `ui` is the egui surface. Under test, the method clears the pin, grip, tab, and north-layer slots before it paints. |
| fn | [`saved_view_rect`](#method-saved_view_rect) | The method `saved_view_rect` reads a stored reply-window rectangle for the current user. The parameter `screen` is the rectangle the stored bounds must fit. A missing session returns `None`. |
| fn | [`open_view`](#method-open_view) | The method `open_view` opens the reply window for one card. The parameter `index` is that card's place. The parameter `screen` is the content rectangle used to fit a saved window. An open view returns without a change. |
| fn | [`store_closed_view`](#method-store_closed_view) | The method `store_closed_view` writes the reply window's last rectangle. The parameter `last` is that rectangle. A missing rectangle returns. A missing session returns. A missing home directory returns. A missing path from `view_bounds_path` returns. |
| fn | [`paint_view`](#method-paint_view) | The reply window. It is the modal above the canvas. |
| fn | [`paint_vt_strip`](#method-paint_vt_strip) | The method `paint_vt_strip` paints the terminal thinking strip and stores a close or a height change. The parameter `ui` is the egui surface. The strip uses the current pane id as its salt. `seat_vt_chrome` builds the chrome from `vt_open`, `vt_slide`, `vt_folded`, and `vt_height`. |
| fn | [`paint_shell`](#method-paint_shell) | The method `paint_shell` paints the terminal plate. The parameter `ui` is the egui surface. One pane, or none, paints `paint_vt_strip` and then `paint_grid`, and returns. |
| fn | [`paint_parked`](#method-paint_parked) | The method `paint_parked` paints one unfocused terminal pane and returns the click it saw. The parameter `ui` is the egui surface. The parameter `id` is the pane id. The parameter `rect` is that pane's rectangle. An unknown id returns `None`. |
| fn | [`paint_grid`](#method-paint_grid) | The method `paint_grid` paints the focused terminal grid, the selection, and the console menu. The parameter `ui` is the egui surface. A primary press clears `scroll_seat`. |
| fn | [`paint_frame`](#method-paint_frame) | The method `paint_frame` runs one egui frame and paints it into the window. A missing window returns. A missing `state` returns. |
| fn | [`close`](#method-close) | The method `close` stores the window and shuts the sessions down. The parameter `event_loop` is the loop that can exit. A present window is measured with `note_frame`. `store_frame` and `store_vault` run. `close_vault` runs. `close_product_tab` may store a [`TabWord`]. `restore_aetheris` becomes false. `write_door` runs. |

## Field Detail

### door

<a id="member-door"></a>

```rust
door: Door
```

### host

<a id="member-host"></a>

```rust
host: String
```

### port

<a id="member-port"></a>

```rust
port: String
```

### user

<a id="member-user"></a>

```rust
user: String
```

### password

<a id="member-password"></a>

```rust
password: String
```

### remember

<a id="member-remember"></a>

```rust
remember: bool
```

### status

<a id="member-status"></a>

```rust
status: String
```

### slide

<a id="member-slide"></a>

```rust
slide: Option<ErrorNotice>
```

Open error notice. Close drops it. A later miss replaces the body.

### view

<a id="member-view"></a>

```rust
view: Option<ViewPane>
```

Open reply window. It floats above the canvas and takes the pointer. Close drops it. A second View waits.

### view_generation

<a id="member-view_generation"></a>

```rust
view_generation: u64
```

### door_note

<a id="member-door_note"></a>

```rust
door_note: Option<&'static str>
```

### save_pending

<a id="member-save_pending"></a>

```rust
save_pending: bool
```

### connect_clicked

<a id="member-connect_clicked"></a>

```rust
connect_clicked: bool
```

### disconnect_clicked

<a id="member-disconnect_clicked"></a>

```rust
disconnect_clicked: bool
```

### session

<a id="member-session"></a>

```rust
session: Option<Session>
```

### vault_rt

<a id="member-vault_rt"></a>

```rust
vault_rt: Option<tokio::runtime::Runtime>
```

One runtime for the vault file. This thread blocks on it. It is not the SSH runtime.

### vault

<a id="member-vault"></a>

```rust
vault: Option<crate::vault::Vault>
```

Open `aetheris.db` after connect lands. None leaves the deck in memory.

### vault_canvas

<a id="member-vault_canvas"></a>

```rust
vault_canvas: String
```

First canvas on that file.

### vault_hold

<a id="member-vault_hold"></a>

```rust
vault_hold: Vec<String>
```

Ids on that canvas that this deck does not show.

### grid

<a id="member-grid"></a>

```rust
grid: Grid
```

### at_tail

<a id="member-at_tail"></a>

```rust
at_tail: bool
```

### jump_tail

<a id="member-jump_tail"></a>

```rust
jump_tail: bool
```

### console_scroll_lines

<a id="member-console_scroll_lines"></a>

```rust
console_scroll_lines: i32
```

Menu Page or Line motion for the focused console. Positive moves toward the tail.

### console_scroll_base

<a id="member-console_scroll_base"></a>

```rust
console_scroll_base: Option<f32>
```

Offset that motion starts from when the pane was parked. None reads the focused scroll.

### anchor

<a id="member-anchor"></a>

```rust
anchor: Option<(usize, usize)>
```

### current

<a id="member-current"></a>

```rust
current: Option<(usize, usize)>
```

### grid_focus

<a id="member-grid_focus"></a>

```rust
grid_focus: bool
```

### plate

<a id="member-plate"></a>

```rust
plate: Plate
```

### restore_aetheris

<a id="member-restore_aetheris"></a>

```rust
restore_aetheris: bool
```

Armed when the stored word is `aetheris`. Connect has not landed yet.

### facts

<a id="member-facts"></a>

```rust
facts: Option<CanvasFacts>
```

### scope_text

<a id="member-scope_text"></a>

```rust
scope_text: String
```

### think

<a id="member-think"></a>

```rust
think: ThinkLog
```

Session thought log. Connect, Clear, and Disconnect leave it.
The think Clear wipes this log and the saved file.

### think_chrome

<a id="member-think_chrome"></a>

```rust
think_chrome: ThinkChrome
```

Fold, Close, and the dragged height for this window.

### think_yaml

<a id="member-think_yaml"></a>

```rust
think_yaml: ThinkYaml
```

Shared `aetheris-think.yaml`. A version other than 1 stays unread.

### vt_think

<a id="member-vt_think"></a>

```rust
vt_think: ThinkLog
```

Thought text of the focused VT100 session. Each parked session keeps its own.
The closed mark stays on the file. The text is not written to the vault log.

### vt_open

<a id="member-vt_open"></a>

```rust
vt_open: bool
```

The focused session's strip is on the plate. Each parked session keeps its own.

### vt_slide

<a id="member-vt_slide"></a>

```rust
vt_slide: f32
```

Rise from the top of the focused session. `0` starts shut. `-1` is settled.

### vt_folded

<a id="member-vt_folded"></a>

```rust
vt_folded: bool
```

The focused session's strip is folded. Each parked session keeps its own.
The yaml does not store it.

### vt_height

<a id="member-vt_height"></a>

```rust
vt_height: f32
```

The focused session's dragged height. Each parked session keeps its own.
Zero uses the three-line view. The file seed is the next new pane.

### vt_think_on

<a id="member-vt_think_on"></a>

```rust
vt_think_on: bool
```

The focused session's thinking is on. Each parked session keeps its own.
The shared gate stays. Another session's empty `think:` line does not clear this.

### roux_used

<a id="member-roux_used"></a>

```rust
roux_used: Option<i32>
```

Roux session count for the focused pane. None until the reply is held.

| | |
| --- | --- |
| **Specification** | L40 aa-sdd-source-prose.md FINESSE:f-163 |

### roux_chars

<a id="member-roux_chars"></a>

```rust
roux_chars: i32
```

Characters in the held Roux messages.

### roux_blocks

<a id="member-roux_blocks"></a>

```rust
roux_blocks: i32
```

Stored Roux messages in the held count.

### tokens_asked

<a id="member-tokens_asked"></a>

```rust
tokens_asked: bool
```

The session_tokens ask has been sent for the focused pane.

### live_line

<a id="member-live_line"></a>

```rust
live_line: String
```

Characters typed on the focused pane and not yet submitted.

| | |
| --- | --- |
| **Specification** | L40 aa-sdd-source-prose.md FINESSE:f-177 |

### vt_chrome

<a id="member-vt_chrome"></a>

```rust
vt_chrome: ThinkChrome
```

File seed for a new VT100 pane and the next open. Live height stays on the session.

### vt_yaml

<a id="member-vt_yaml"></a>

```rust
vt_yaml: VtThinkYaml
```

`aetheris-vt100-think.yaml`. A version other than 1 stays unread.

### mood

<a id="member-mood"></a>

```rust
mood: MoodYaml
```

The showing user's mood. A version other than 1 stays unread.

### mood_owner

<a id="member-mood_owner"></a>

```rust
mood_owner: String
```

Sanitized user whose record is `mood`. Empty until a user is seated.

### mood_bank

<a id="member-mood_bank"></a>

```rust
mood_bank: Vec<(String, MoodYaml)>
```

Other users already read. A miss is that user's file, not the showing record.

### ssh_mood

<a id="member-ssh_mood"></a>

```rust
ssh_mood: bool
```

Mood footer box for the canvas user now on the glass. The map is the store.

### ssh_users

<a id="member-ssh_users"></a>

```rust
ssh_users: Vec<(String, bool)>
```

SSH mood armed by canvas user. The shared mood file does not carry this.

| | |
| --- | --- |
| **Specification** | L40 aa-sdd-source-prose.md FINESSE:f-176 |

### ssh_mood_blocked

<a id="member-ssh_mood_blocked"></a>

```rust
ssh_mood_blocked: bool
```

An SSH mood file whose version is not 1. Left unread and not overwritten.

### expanded

<a id="member-expanded"></a>

```rust
expanded: ExpandedYaml
```

Per-cell Expanded View. A version other than 1 stays unread.
A memory door does not read or write the home file.

### mood_desk

<a id="member-mood_desk"></a>

```rust
mood_desk: MoodDesk
```

The mood glass. Wire ids stay after the dialog closes.

### mood_seq

<a id="member-mood_seq"></a>

```rust
mood_seq: u64
```

Count for mood wire ids. It is not the card sequence.

### cards

<a id="member-cards"></a>

```rust
cards: Vec<TurnCard>
```

### focus

<a id="member-focus"></a>

```rust
focus: usize
```

### scroll_seat

<a id="member-scroll_seat"></a>

```rust
scroll_seat: Option<(usize, TextPane)>
```

Which text pane holds the wheel. None is the canvas. The armed cell stays armed.

### cell_seq

<a id="member-cell_seq"></a>

```rust
cell_seq: u64
```

### edits

<a id="member-edits"></a>

```rust
edits: crate::edit::EditDesk
```

RAM undo for canvas prompts. One seat per cell. A restart drops it.

| | |
| --- | --- |
| **Specification** | PK-1163 docs/specs/java/org/casserole/edit/edit.spec.md FINESSE:f-191 |
| **Specification** | PK-1172 docs/specs/rust/aetheris/aetheris-client/src/edit.spec.md FINESSE:f-214 |

### prompt_focus

<a id="member-prompt_focus"></a>

```rust
prompt_focus: Option<usize>
```

Prompt that held focus on the previous canvas paint.

### group_live

<a id="member-group_live"></a>

```rust
group_live: u64
```

### group_rest

<a id="member-group_rest"></a>

```rust
group_rest: Vec<([u8; 16], Vec<u8>)>
```

### run_pane

<a id="member-run_pane"></a>

```rust
run_pane: Option<u64>
```

Pane that carries the showing deck's run. None uses the focused pane.
A parked user's run stays on that user's pane.
// @aa-sdd PK-578 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-215

### pipe_live

<a id="member-pipe_live"></a>

```rust
pipe_live: bool
```

Chain cards still waiting. The running card is not in this list.

### pipe_rest

<a id="member-pipe_rest"></a>

```rust
pipe_rest: Vec<usize>
```

### scope_stash

<a id="member-scope_stash"></a>

```rust
scope_stash: VecDeque<(ScopePiece, bool)>
```

Scope waiting for the canvas. The bool is true once the console thought was offered.

### paint_stash

<a id="member-paint_stash"></a>

```rust
paint_stash: VecDeque<Vec<u8>>
```

### join_from

<a id="member-join_from"></a>

```rust
join_from: Option<usize>
```

### focus_prompt

<a id="member-focus_prompt"></a>

```rust
focus_prompt: bool
```

### seat_focus

<a id="member-seat_focus"></a>

```rust
seat_focus: bool
```

One shot. Hide Collapsed and Show Collapsed seat focus on the selected cell.

| | |
| --- | --- |
| **Specification** | PK-1079 docs/specs/common/formal-plan.spec.md FINESSE:f-204 |

### land_newest

<a id="member-land_newest"></a>

```rust
land_newest: bool
```

One shot. The next canvas paint scrolls to the newest card.

### canvas_weight

<a id="member-canvas_weight"></a>

```rust
canvas_weight: f32
```

Canvas share for this window. A drag moves it. The door remembers it.

### parked_share

<a id="member-parked_share"></a>

```rust
parked_share: Option<f32>
```

Share parked by an arrow. The other arrow brings it back.

### divider_span

<a id="member-divider_span"></a>

```rust
divider_span: f32
```

Last bar span. The door write waits until the span is at least 32.

### divider_armed

<a id="member-divider_armed"></a>

```rust
divider_armed: Option<Instant>
```

When the share last moved. The door write waits 400 ms.

### inspector_soft_wrap

<a id="member-inspector_soft_wrap"></a>

```rust
inspector_soft_wrap: bool
```

Soft wrap. Swing's checkbox starts on and is not saved.

### wrap_columns

<a id="member-wrap_columns"></a>

```rust
wrap_columns: u32
```

Shell column count. A missing key is 80.

### wrap_field

<a id="member-wrap_field"></a>

```rust
wrap_field: String
```

The column field. A typed number waits 800 ms.

### wrap_armed

<a id="member-wrap_armed"></a>

```rust
wrap_armed: Option<Instant>
```

When the column field last changed.

### line_chars

<a id="member-line_chars"></a>

```rust
line_chars: u32
```

Inspector line length. A missing key is 80.

### line_field

<a id="member-line_field"></a>

```rust
line_field: String
```

The length field. A typed number waits 800 ms.

### line_armed

<a id="member-line_armed"></a>

```rust
line_armed: Option<Instant>
```

When the length field last changed.

### view_rows

<a id="member-view_rows"></a>

```rust
view_rows: u32
```

Open-cell rows. A missing key is 4.

### view_field

<a id="member-view_field"></a>

```rust
view_field: String
```

The row field. A typed number waits 800 ms.

### view_armed

<a id="member-view_armed"></a>

```rust
view_armed: Option<Instant>
```

When the row field last changed.

### tools_open

<a id="member-tools_open"></a>

```rust
tools_open: bool
```

Tool bar open for this window. It starts closed. The choice is not saved.

### bars_open

<a id="member-bars_open"></a>

```rust
bars_open: bool
```

BB1 and BB2 showing. It starts open. The choice is not saved.

### hide_collapsed

<a id="member-hide_collapsed"></a>

```rust
hide_collapsed: bool
```

Folded cards stay off the deck. Show brings them back. The choice is saved.

### concealed

<a id="member-concealed"></a>

```rust
concealed: Vec<[u8; 16]>
```

Ids Hide Collapsed took off the deck. A fold never adds an id. LOCK F-141.

### conceal_armed

<a id="member-conceal_armed"></a>

```rust
conceal_armed: bool
```

True after this deck has seeded the conceal set from the hide seat.

### hide_blocked

<a id="member-hide_blocked"></a>

```rust
hide_blocked: bool
```

A bad hide file stays unread and is not overwritten.

### hide_extras

<a id="member-hide_extras"></a>

```rust
hide_extras: String
```

Other keys in the hide file, written back with the flag.

### control_open

<a id="member-control_open"></a>

```rust
control_open: bool
```

Connection bar showing. It starts open. The choice is not saved.

### link_up

<a id="member-link_up"></a>

```rust
link_up: bool
```

True after Connected, until the session drops. A canvas line does not clear it.

### control_span

<a id="member-control_span"></a>

```rust
control_span: Option<egui::Vec2>
```

Last frame size. The first sight is not a resize.

### tool_needle

<a id="member-tool_needle"></a>

```rust
tool_needle: String
```

Search field for this window. It is not written.

### find_needle

<a id="member-find_needle"></a>

```rust
find_needle: String
```

Committed find filter. Empty shows every card the conceal set still allows.

### pin_on

<a id="member-pin_on"></a>

```rust
pin_on: bool
```

Pin filter for this window. Turning the box off keeps the words. The states stay.

### pin_needle

<a id="member-pin_needle"></a>

```rust
pin_needle: String
```

### pin_bar_open

<a id="member-pin_bar_open"></a>

```rust
pin_bar_open: bool
```

Pin bar showing. A missing door key starts folded. Folding does not change the box.

### pin_enter_seen

<a id="member-pin_enter_seen"></a>

```rust
pin_enter_seen: bool
```

The pin field already reported this focus loss. The next frame does not commit.

### new_clicked

<a id="member-new_clicked"></a>

```rust
new_clicked: bool
```

### clear_clicked

<a id="member-clear_clicked"></a>

```rust
clear_clicked: bool
```

### delete_clicked

<a id="member-delete_clicked"></a>

```rust
delete_clicked: bool
```

### run_clicked

<a id="member-run_clicked"></a>

```rust
run_clicked: bool
```

### pipe_clicked

<a id="member-pipe_clicked"></a>

```rust
pipe_clicked: bool
```

### stop_clicked

<a id="member-stop_clicked"></a>

```rust
stop_clicked: bool
```

### console_focus

<a id="member-console_focus"></a>

```rust
console_focus: ConsoleFocus
```

### exit_after_paint

<a id="member-exit_after_paint"></a>

```rust
exit_after_paint: bool
```

### grok

<a id="member-grok"></a>

```rust
grok: crate::model::GrokDoc
```

Shared `aetheris-grok.yaml`. A version other than 1 stays unread.

### token_open

<a id="member-token_open"></a>

```rust
token_open: bool
```

Token window. T opens it. OK and the close icon dismiss it.

### ring_used

<a id="member-ring_used"></a>

```rust
ring_used: i32
```

The integer paint_token shows. The Aetheris heatbar wears this count.

### heat_chars

<a id="member-heat_chars"></a>

```rust
heat_chars: i32
```

Characters from the last seated canvas beat.

### heat_blocks

<a id="member-heat_blocks"></a>

```rust
heat_blocks: i32
```

Blocks from the last seated canvas beat.

### heat_body

<a id="member-heat_body"></a>

```rust
heat_body: String
```

Inspector text from the last seated canvas beat.

### canvas_heat_sig

<a id="member-canvas_heat_sig"></a>

```rust
canvas_heat_sig: u64
```

### canvas_sig_set

<a id="member-canvas_sig_set"></a>

```rust
canvas_sig_set: bool
```

### vt_used

<a id="member-vt_used"></a>

```rust
vt_used: i32
```

The VT100 heatbar wears this count. The key does not walk the grid.

### vt_chars

<a id="member-vt_chars"></a>

```rust
vt_chars: i32
```

### vt_blocks

<a id="member-vt_blocks"></a>

```rust
vt_blocks: i32
```

### vt_heat_sig

<a id="member-vt_heat_sig"></a>

```rust
vt_heat_sig: u64
```

### vt_sig_set

<a id="member-vt_sig_set"></a>

```rust
vt_sig_set: bool
```

### vt_memory

<a id="member-vt_memory"></a>

```rust
vt_memory: std::sync::Arc<str>
```

### vt_memory_gen

<a id="member-vt_memory_gen"></a>

```rust
vt_memory_gen: u64
```

### grid_gen

<a id="member-grid_gen"></a>

```rust
grid_gen: u64
```

Bumped when the grid is fed, cleared, resized, or swapped.

### heat_seq

<a id="member-heat_seq"></a>

```rust
heat_seq: u64
```

### heat_kind

<a id="member-heat_kind"></a>

```rust
heat_kind: u8
```

0 none, 1 canvas, 2 vt. A plate change posts again.

### heat

<a id="member-heat"></a>

```rust
heat: HeatQueue
```

Newest-only heatbar queue. The walk runs off the frame.
// @aa-sdd PK-1171 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-209

### token_pick

<a id="member-token_pick"></a>

```rust
token_pick: String
```

Combo label while the token window is open. Empty selects the focused turn.

### model

<a id="member-model"></a>

```rust
model: crate::model::ModelSession
```

The one Model Config modal.

### engine_label

<a id="member-engine_label"></a>

```rust
engine_label: String
```

The focused pane's selected model. Empty prints as ollama. A canvas cell is not this word.

### engine_asked

<a id="member-engine_asked"></a>

```rust
engine_asked: bool
```

This connection has been asked for the saved model. A disconnect clears it.

### list_slide_gen

<a id="member-list_slide_gen"></a>

```rust
list_slide_gen: u64
```

The model-list notice generation last copied onto the error slide.

### shell_open

<a id="member-shell_open"></a>

```rust
shell_open: bool
```

The next shell answer opens the modal. A connect ask leaves it shut.

### console_model_pane

<a id="member-console_model_pane"></a>

```rust
console_model_pane: Option<u64>
```

Pane that opened the console Model Config. The close returns the keyboard there.

### console_return

<a id="member-console_return"></a>

```rust
console_return: Option<u64>
```

Pane to focus after that modal closes. Taken at the end of the frame.

### osc_hold

<a id="member-osc_hold"></a>

```rust
osc_hold: crate::model::OscHold
```

Held tail of a split `select-model` sequence.

### pool_boot

<a id="member-pool_boot"></a>

```rust
pool_boot: String
```

Pool boot id for this window.

### pane_id

<a id="member-pane_id"></a>

```rust
pane_id: u64
```

Focused pane. Its grid and session stay on this struct.

### next_pane

<a id="member-next_pane"></a>

```rust
next_pane: u64
```

### panes

<a id="member-panes"></a>

```rust
panes: Vec<ParkedPane>
```

### decks

<a id="member-decks"></a>

```rust
decks: Vec<ParkedDeck>
```

Canvas seats that are not on the glass. Each keeps its own run.
// @aa-sdd PK-578 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-215

### canvas_user

<a id="member-canvas_user"></a>

```rust
canvas_user: String
```

Glass user when a test has no vault file. Production leaves this off.

### tree

<a id="member-tree"></a>

```rust
tree: PaneNode
```

### term_font

<a id="member-term_font"></a>

```rust
term_font: i32
```

Live spinner. Every pane and the canvas type use it.

### last_connect

<a id="member-last_connect"></a>

```rust
last_connect: Option<ConnectSnap>
```

Last connect fields for the focused pane. A new pane starts without one.

### paste

<a id="member-paste"></a>

```rust
paste: Option<PasteDraft>
```

### paste_in

<a id="member-paste_in"></a>

```rust
paste_in: Option<String>
```

### shell_notice

<a id="member-shell_notice"></a>

```rust
shell_notice: Option<ShellNotice>
```

### help_open

<a id="member-help_open"></a>

```rust
help_open: bool
```

### about_open

<a id="member-about_open"></a>

```rust
about_open: bool
```

### settings_open

<a id="member-settings_open"></a>

```rust
settings_open: bool
```

Settings modal. File, Settings.

### painted_borders

<a id="member-painted_borders"></a>

```rust
painted_borders: bool
```

Optional Java cell frame on the canvas text panes. Starts off.

### debug_mood

<a id="member-debug_mood"></a>

```rust
debug_mood: bool
```

File, Settings. On paints the invitation in the inspector. Starts off.

### tooltips_on

<a id="member-tooltips_on"></a>

```rust
tooltips_on: bool
```

Hover tips. On is the present behavior. Off shows none.

### settings_blocked

<a id="member-settings_blocked"></a>

```rust
settings_blocked: bool
```

A bad settings file stays unread and is not overwritten.

### settings_extras

<a id="member-settings_extras"></a>

```rust
settings_extras: String
```

Other keys in the settings file, written back with the switch.

### about_app

<a id="member-about_app"></a>

```rust
about_app: Option<egui::TextureHandle>
```

App mark for the About box. Loaded on the first open.

### exit_ask

<a id="member-exit_ask"></a>

```rust
exit_ask: bool
```

### exit_confirmed

<a id="member-exit_confirmed"></a>

```rust
exit_confirmed: bool
```

### new_term_ask

<a id="member-new_term_ask"></a>

```rust
new_term_ask: bool
```

### reconnect_clicked

<a id="member-reconnect_clicked"></a>

```rust
reconnect_clicked: bool
```

### clear_term_clicked

<a id="member-clear_term_clicked"></a>

```rust
clear_term_clicked: bool
```

### send_file_clicked

<a id="member-send_file_clicked"></a>

```rust
send_file_clicked: bool
```

### close_pane_clicked

<a id="member-close_pane_clicked"></a>

```rust
close_pane_clicked: bool
```

### import_clicked

<a id="member-import_clicked"></a>

```rust
import_clicked: bool
```

### export_pane

<a id="member-export_pane"></a>

```rust
export_pane: Option<u64>
```

Pane whose Roux export was asked. None means no ask is open.

### export_file

<a id="member-export_file"></a>

```rust
export_file: Option<String>
```

TOON waiting for the save dialog.

### transfer_pane

<a id="member-transfer_pane"></a>

```rust
transfer_pane: Option<u64>
```

Pane whose Roux table is copied onto the canvas. None means no copy is open.

### transfer_roux

<a id="member-transfer_roux"></a>

```rust
transfer_roux: bool
```

True while the canvas copy into Roux is waiting for the reply.

### pane_model_clicked

<a id="member-pane_model_clicked"></a>

```rust
pane_model_clicked: bool
```

### focus_pane_request

<a id="member-focus_pane_request"></a>

```rust
focus_pane_request: Option<u64>
```

### saved_frame

<a id="member-saved_frame"></a>

```rust
saved_frame: Option<(i32, i32, i32, i32)>
```

Restored rectangle. A maximized frame does not replace it.

### open_maximized

<a id="member-open_maximized"></a>

```rust
open_maximized: bool
```

Missing key keeps the seated maximized open.

### ctx

<a id="member-ctx"></a>

```rust
ctx: egui::Context
```

### state

<a id="member-state"></a>

```rust
state: Option<egui_winit::State>
```

### painter

<a id="member-painter"></a>

```rust
painter: Option<egui_wgpu::winit::Painter>
```

### window

<a id="member-window"></a>

```rust
window: Option<Arc<Window>>
```

## Method Detail

### new

<a id="method-new"></a>

```rust
fn new(door: Door, door_note: Option<&'static str>) -> Self
```

The function `new` builds the client window from the SSH client settings store.
The value `door` is a [`Door`](../door/struct.Door.md). The host, the port, the user, the password, and the remember flag are copied from it. `door_note` is stored as the optional note on the window.

The canvas share, the wrap width, the line length, the view rows, the pin bar, the product tab, and the font come from that store. A saved frame comes from it. The maximized word comes from it, and a missing word uses [`OPEN_MAXIMIZED`](../chrome.md#constant-open_maximized). A settings file that exists loads the expanded view, the hide list, and the window settings. A missing file leaves those three at their fresh values.
Thinking, the terminal strip, the SSH mood file, and the Grok home are loaded beside that store. The mood desk starts shut. The card list starts as one fresh card. The zoom is [`OPEN_ZOOM_STEPS`](../chrome.md#constant-open_zoom_steps). The status starts as `Disconnected`. The window, the painter, and the egui state start absent.

### open_window

<a id="method-open_window"></a>

```rust
fn open_window(&mut self, event_loop: &ActiveEventLoop)
```

The function `open_window` creates the native window when none is open.
`event_loop` is the loop that owns the window. A window that is already stored returns immediately.

The frame comes from `open_frame`. The title is [`WINDOW_CAPTION`](../chrome.md#constant-window_caption). The icon is `window_icon`. The minimum size is [`MIN_WIDTH`](../chrome.md#constant-min_width) by [`MIN_HEIGHT`](../chrome.md#constant-min_height). A physical frame uses a physical inner size. Any other frame uses a logical inner size. A saved place becomes the window position.
A failed create sets the status to `the window surface did not start` and exits the loop. A current-thread runtime then builds the wgpu painter and attaches the window. A failed runtime, or a failed attach, uses that same status and exits the loop. The runtime is dropped after the attach. The egui state, the painter, and the window are stored, and the window asks to redraw.

### clamp_resize

<a id="method-clamp_resize"></a>

```rust
fn clamp_resize(&mut self, size: PhysicalSize<u32>)
```

The method `clamp_resize` keeps a physical resize inside the minimum and tells the painter.
`size` is the new physical size. A missing window returns immediately.

The size is converted to logical pixels with the window scale. `clamp_logical` raises a side that is under [`MIN_WIDTH`](../chrome.md#constant-min_width) or [`MIN_HEIGHT`](../chrome.md#constant-min_height). A size that changed is requested as the new inner size. A missing painter returns after that request. A width of zero returns. A height of zero returns. Otherwise `on_window_resized` receives the original physical width and height.

### ask_session_export

<a id="method-ask_session_export"></a>

```rust
fn ask_session_export(&mut self, pane_id: u64)
```

Ask this pane's connection for its Roux table. A raw console does not ask.

### hold_session_export

<a id="method-hold_session_export"></a>

```rust
fn hold_session_export(&mut self, file: SessionFile)
```

The method `hold_session_export` stores a Roux export reply and clears the waiting pane.
`file` is the [`SessionFile`](enum.SessionFile.md). `export_pane` becomes `None`.

[`SessionFile::Failed`](enum.SessionFile.md#member-failed) clears `export_file` and sets the status to [`EXPORT_FAILED`](../chrome.md#constant-export_failed) plus the reason. [`SessionFile::Table`](enum.SessionFile.md#member-table) stores the table body in `export_file`.

### finish_session_export

<a id="method-finish_session_export"></a>

```rust
fn finish_session_export(&mut self)
```

The save dialog. Cancel writes nothing. Tests do not call this.

### console_ready

<a id="method-console_ready"></a>

```rust
fn console_ready(&self, pane_id: u64) -> Result<(), &'static str>
```

The focused pane, or a parked pane, when that connection can carry Roux.

### wire_console

<a id="method-wire_console"></a>

```rust
fn wire_console(&self, pane_id: u64, frame: &[u8])
```

The function `wire_console` sends one frame on a pane's session.
`pane_id` picks the pane. `frame` is the bytes already built.

The focused pane id is `self.pane_id`. An argument equal to that id uses the front session. Any other id is found among the other panes. A missing session sends nothing.

### ask_transfer_from_console

<a id="method-ask_transfer_from_console"></a>

```rust
fn ask_transfer_from_console(&mut self, pane_id: u64)
```

OK, then the Roux table. The canvas stays until that table parses.

### ask_transfer_from_canvas

<a id="method-ask_transfer_from_canvas"></a>

```rust
fn ask_transfer_from_canvas(&mut self)
```

OK, then the cells. Roux stays until the server accepts the table.

### land_transfer_export

<a id="method-land_transfer_export"></a>

```rust
fn land_transfer_export(&mut self, file: SessionFile)
```

The method `land_transfer_export` finishes a Roux table that was copied onto the canvas.
`file` is the [`SessionFile`](enum.SessionFile.md). `transfer_pane` becomes `None`.

[`SessionFile::Failed`](enum.SessionFile.md#member-failed) sets the status to `export failed:` plus the reason. [`SessionFile::Table`](enum.SessionFile.md#member-table) parses the body with `pair_session`. A parsed list calls `land_transfer_cards`. A parse error stores that error text as the status.

### land_transfer_replace

<a id="method-land_transfer_replace"></a>

```rust
fn land_transfer_replace(&mut self, result: Result<(), String>)
```

The function `land_transfer_replace` records the reply to a canvas copy into Roux.
`result` is that reply. `transfer_roux` becomes false.

`Ok` sets the status to [`TRANSFER_WORD`](../chrome.md#constant-transfer_word). `Err` sets the status to `replace failed:` plus the reason.

### land_transfer_cards

<a id="method-land_transfer_cards"></a>

```rust
fn land_transfer_cards(&mut self, pairs: &[(String, String)])
```

The canvas becomes the copied cells. An empty list leaves one empty card.

### drain_parked

<a id="method-drain_parked"></a>

```rust
fn drain_parked(&mut self) -> bool
```

The method `drain_parked` reads every pane that is not in front and returns whether the window should paint again.
A status line, a kept scope piece, a paint chunk, a revealed thought, a wired frame, or a finished export sets that flag.

Each pane flushes outbound bytes first. A pane that has not asked for tokens, and whose mux has answered, sends `session_tokens:` when that frame can be built and then sets `tokens_asked`. A status line updates `link` through `link_from_status`. A line that starts with `Disconnected` shuts the session down, sets [`PaneLink::Down`](enum.PaneLink.md#member-down), and clears the Roux counts and `tokens_asked`.

Scope is drained into one burst. A piece that `console_piece_opens` accepts sets `vt_think_on`. A token reply stores `roux_used`, `roux_chars`, and `roux_blocks`. An export file is kept for `land_transfer_export` when this pane is `transfer_pane`, or for `hold_session_export` when this pane is `export_pane`. A replace file is kept for `land_transfer_replace` only when `transfer_roux` is true. Every replace file is consumed either way. Other pieces go through `route_console_thought`. A thought log that gains text opens the strip, sets `vt_think_on`, and asks the terminal yaml to be stored when `note_vt_opened` changes.

Paint chunks are peeled through `osc_hold`. An empty peel is skipped. Other bytes feed the grid. A non-empty grid reply is written on the session. The bell is taken and ignored. The pane flushes again.

Kept scope is placed by `scope_home` for the user on `last`. [`ScopeHome::Hold`](enum.ScopeHome.md#member-hold) pushes the pieces onto that pane's stash with the offered flag true, and drops the oldest piece once the stash reaches [`SCOPE_PARK_CAP`](../chrome.md#constant-scope_park_cap). [`ScopeHome::Showing`](enum.ScopeHome.md#member-showing) and [`ScopeHome::Parked`](enum.ScopeHome.md#member-parked) run `intake_mood`. A pass joins the canvas list. A quiet piece is dropped. A resend calls `fire_mood_send`. A slide may close the mood glass, opens an [`ErrorNotice`](struct.ErrorNotice.md), and sets the status to [`MOOD_KEYWORD`](../chrome.md#constant-mood_keyword). Frames from `deliver_scope` are written with `wire_pane`.

A held export then calls `hold_session_export`. A held transfer table calls `land_transfer_export`. A held replace calls `land_transfer_replace`.

### drain_link

<a id="method-drain_link"></a>

```rust
fn drain_link(&mut self) -> CommitWake
```

The method `drain_link` reads the focused session after the parked panes and returns a [`CommitWake`](struct.CommitWake.md).
`show` is true when the window should paint again. `more` is true when scope or paint is still waiting.

`drain_parked` runs first. The focused pane sends `session_tokens:` once the mux has answered and the frame can be built. Outbound bytes are flushed. A status line goes to `seat_control_status`. Scope and paint are filled up to the per-turn caps, then the front of each stash is taken.

A token reply stores the focused Roux counts. An export file calls `land_transfer_export` when `transfer_pane` is this pane, or is held for `hold_session_export` when `export_pane` is this pane. A replace file calls `land_transfer_replace` when `transfer_roux` is true, and the file is consumed either way. `intake_mood` keeps a pass, drops a quiet piece, remembers a resend, or opens a slide and sets [`MOOD_KEYWORD`](../chrome.md#constant-mood_keyword).

`land_focused_canvas` clears the canvas list when this pane's user is not the user on the glass. A later body that is a model note calls `take_model_note`. A fault calls `seat_fault` and may open `slide`. `land_open_turn` may copy the prior card's Grok entry onto the new cell, keep that card's thought, and move focus onto the new card with `focus_prompt` and `land_newest`.

A changed notebook log reveals the strip and stores the yaml when the blocks are not empty, and `store_think_file` runs either way. A piece that `console_piece_opens` accepts sets `vt_think_on`. `route_console_thought` runs only for a piece whose offered flag is false. A terminal log that gains text opens the strip and may store the terminal yaml. `ask_shell_once` runs next.

`group_after` fails the merge and clears the group when the mux has not answered. A live mux wires the frame and calls `begin_run` on that cell. `seat_mood_canvas` runs, then `pipe_after_doc`. [`PipeWake::Quiet`](enum.PipeWake.md#member-quiet) changes nothing. [`PipeWake::Status`](enum.PipeWake.md#member-status) stores the status. [`PipeWake::Send`](enum.PipeWake.md#member-send) clears the pipe when the mux is down, and otherwise wires the frame, pins the mood, and calls `begin_run`. A mood drop goes to `seat_mood_slide`. A remembered resend calls `fire_mood_send`.

Each paint chunk is peeled. A non-empty open list calls `ring_model`. An empty peel is skipped. Other bytes feed the grid, call `note_grid`, and write a non-empty reply. The bell is taken and ignored.

A status that starts with `Disconnected`, while a session is still stored and a status line arrived, saves and closes the vault, clears the canvas, drops the plate documents, clears the deck and the edits, and resets the group, the pipe, the join, and the run pane. The mood glass closes. Both stashes clear. The session is shut down. A shutdown error sets the status to `Disconnected: read thread did not stop`. The Roux counts and `tokens_asked` clear. A session that remains flushes again. `finish_session_export` runs before the wake is returned.

### write_door

<a id="method-write_door"></a>

```rust
fn write_door(&mut self)
```

The method `write_door` copies the connection fields into the SSH client settings store and saves that store.
Host text, user text, password, and the remember flag are always copied. A port that `parse_port` accepts is copied. A rejected port leaves the stored port unchanged.

A store that cannot save returns without a write. A failed save stores the note `door file could not be written` on `door_note`. A successful save clears `door_note`.

### connect

<a id="method-connect"></a>

```rust
fn connect(&mut self)
```

The method `connect` remembers the connection fields, writes the SSH client settings store, and starts a session.
The remembered fields go through `remember_connect`. The store write goes through `write_door`.

The vault user counts as the same user when a vault is open, the user text is not empty, and the vault user equals `sanitize_user` of that text. A port that `parse_port` rejects sets the status through `seat_control_status` to `Disconnected` and returns. `Session::start` receives the host text, that port, the user text, the password, and the TERM string from `shell_term_type` of the stored connection mode.

A started session replaces a session that is already open. When that replacement is not the same vault user, `note_replaced_run` runs first. The status becomes `Connecting`. `session_opened` runs on the console focus. A different vault user then calls `seat_canvas_user` with the user text. A start error passes that error text to `seat_control_status`.

### reconnect

<a id="method-reconnect"></a>

```rust
fn reconnect(&mut self)
```

The method `reconnect` closes the focused session and starts it again with the fields on the bar.
A missing session returns immediately. The fields are remembered and the SSH client settings store is written before the close.

The taken session clears `roux_used`, `roux_chars`, `roux_blocks`, and `tokens_asked`. A shutdown that fails sets the status to `Disconnected: read thread did not stop` and returns. A port that `parse_port` rejects sets the status to `Disconnected` and returns. `Session::start` uses the same host text, port, user text, password, and TERM string as `connect`.

A started session is stored, the status becomes `Connecting`, and `session_opened` runs. This path does not call `note_replaced_run` or `seat_canvas_user`. A start error passes that error text to `seat_control_status`.

### remember_connect

<a id="method-remember_connect"></a>

```rust
fn remember_connect(&mut self)
```

The method `remember_connect` stores the connection fields for the focused pane.
`last_connect` becomes a [`ConnectSnap`](struct.ConnectSnap.md) of the current host text, port text, user text, and password.

### clear_terminal

<a id="method-clear_terminal"></a>

```rust
fn clear_terminal(&mut self)
```

The method `clear_terminal` wipes the focused terminal and sits on the tail.
The grid buffer is cleared. `note_grid` runs. `anchor` and `current` become `None`. `at_tail` and `jump_tail` become true.

### open_frame

<a id="method-open_frame"></a>

```rust
fn open_frame(&self, event_loop: &ActiveEventLoop) -> OpenFrame
```

The method `open_frame` chooses the rectangle used to open the window.
`event_loop` supplies the monitor rectangles. The saved frame, the maximized flag, and those rectangles go to `open_frame_seat`. The result is an [`OpenFrame`](struct.OpenFrame.md).

### note_frame

<a id="method-note_frame"></a>

```rust
fn note_frame(&mut self, window: &Window)
```

The method `note_frame` records the window rectangle and the maximized flag.
`window` is the open window. A missing outer position is passed as no origin. An inner width or height that cannot convert becomes 0. A minimized query that fails is treated as not minimized.

Those values, the current saved rectangle, and the current maximized flag go to `note_frame_seat`. The returned rectangle is stored in `saved_frame`. The returned maximized flag is stored in `open_maximized`.

### store_frame

<a id="method-store_frame"></a>

```rust
fn store_frame(&mut self)
```

The method `store_frame` writes the saved window rectangle into the SSH client settings store.
`save_frame` receives `saved_frame` and `open_maximized`. A failed save stores the note `door file could not be written` only when `door_note` is already empty. A successful save leaves `door_note` as it was.

### store_font

<a id="method-store_font"></a>

```rust
fn store_font(&mut self)
```

The method `store_font` writes the live font size into the SSH client settings store.
`save_font_size` receives `term_font`. A failed save stores the note `door file could not be written` only when `door_note` is already empty. A successful save leaves `door_note` as it was.

### show_shell_notice

<a id="method-show_shell_notice"></a>

```rust
fn show_shell_notice(&mut self, title: &'static str, body: impl Into<String>)
```

The method `show_shell_notice` opens a terminal notice whose body is not a thrown read.
`title` is the window title. `body` is the message. `hard` is stored as false.

### show_shell_error

<a id="method-show_shell_error"></a>

```rust
fn show_shell_error(&mut self, title: &'static str, body: impl Into<String>)
```

The method `show_shell_error` opens a terminal notice for a thrown read.
`title` is the window title. `body` is the message. `hard` is stored as true, so the label uses the red ink.

### open_paste

<a id="method-open_paste"></a>

```rust
fn open_paste(&mut self, text: &str)
```

The method `open_paste` opens the paste editor with normalized text.
`text` is the source. `terminal_normalize` turns CR LF and a bare CR into LF. An empty body returns. An editor that is already open returns. Otherwise `paste` holds a [`PasteDraft`](struct.PasteDraft.md) of that body.

### accept_paste

<a id="method-accept_paste"></a>

```rust
fn accept_paste(&mut self)
```

The method `accept_paste` takes the editor body and writes it when the decision allows.
A missing draft returns. [`PasteDecision::Quiet`](enum.PasteDecision.md#member-quiet) writes nothing. [`PasteDecision::Over`](enum.PasteDecision.md#member-over) opens a `Load File` notice with [`PASTE_OVER`](../chrome.md#constant-paste_over).

[`PasteDecision::Ready`](enum.PasteDecision.md#member-ready) opens a `Load File` notice with [`NOT_CONNECTED`](../chrome.md#constant-not_connected) when no focused session is stored, and then returns. A stored session receives `paste_wrapped` of the body.

### insert_paste

<a id="method-insert_paste"></a>

```rust
fn insert_paste(&mut self, text: &str, title: &'static str)
```

The method `insert_paste` writes bracketed paste on the focused session.
`text` is the body. `title` is the notice title used when no session is stored. That path shows [`NOT_CONNECTED`](../chrome.md#constant-not_connected) and returns. A stored session receives `paste_wrapped` of `text`.

### shell_modal

<a id="method-shell_modal"></a>

```rust
fn shell_modal(&self) -> bool
```

The method `shell_modal` reports whether a terminal modal is open.
The result is true when the paste editor, a shell notice, Help, About, the exit confirm, or the new-term box is open. The settings window is not part of this test.

### any_session

<a id="method-any_session"></a>

```rust
fn any_session(&self) -> bool
```

The method `any_session` reports whether any pane has an SSH session.
The result is true when the focused `session` is stored. It is also true when any parked pane stores a session.

### take_shell_keys

<a id="method-take_shell_keys"></a>

```rust
fn take_shell_keys(&mut self, ui: &mut egui::Ui, console: bool)
```

Ctrl+Shift+D / E / W split or close on any plate.
Ctrl+Shift+V opens the editor when the console held the keys.

### show_focused_thinking

<a id="method-show_focused_thinking"></a>

```rust
fn show_focused_thinking(&mut self)
```

The method `show_focused_thinking` opens the thinking strip on the plate that is showing.
[`Plate::Aetheris`](enum.Plate.md#member-aetheris) passes the notebook open flag and slide to `show_thinking` and stores the returned open, slide, and folded values on `think_chrome`. [`Plate::Ssh`](enum.Plate.md#member-ssh) does the same with `vt_open` and `vt_slide` and stores the result on the focused terminal strip.

### paint_menu

<a id="method-paint_menu"></a>

```rust
fn paint_menu(&mut self, ui: &mut egui::Ui)
```

The method `paint_menu` paints the File, Tools, Token Calc, and Help bar.
`ui` is the egui surface. The bar font is `term_font`. Close Pane is enabled only when `pane_count` is greater than 1.

File, New Term sets `new_term_ask`. Close Pane sets `close_pane_clicked`. Import sets `import_clicked`. Settings sets `settings_open`. Exit sets `exit_ask`. Each of those clicks also closes the menu. Tools, Show Thinking calls `show_focused_thinking` and closes the menu. Token Calc sets `token_open` and does not close a menu. Help sets `help_open`. About sets `about_open`.

### note_shell_door

<a id="method-note_shell_door"></a>

```rust
fn note_shell_door(&mut self, report: &ChooserReport)
```

The method `note_shell_door` keeps the file-dialog place in the SSH client settings store.
`report` is the [`ChooserReport`](struct.ChooserReport.md). `keep_chooser` writes that place. A failed write stores the note `door file could not be written` only when `door_note` is already empty.

### begin_send_file

<a id="method-begin_send_file"></a>

```rust
fn begin_send_file(&mut self)
```

The method `begin_send_file` opens a file in the paste editor.
A missing focused session returns without a dialog. `pick_shell_file` uses the stored chooser directory and bounds. A dialog error opens a `Load File` notice with that message and returns. `note_shell_door` runs for a report that opened. A report with no file returns.

`read_console_file` on success calls `open_paste`. A refusal opens a `Load File` notice with that word.

### begin_console_import

<a id="method-begin_console_import"></a>

```rust
fn begin_console_import(&mut self)
```

The method `begin_console_import` reads a file into the focused terminal.
A missing focused session opens an `Import` notice with [`NOT_CONNECTED`](../chrome.md#constant-not_connected) and returns. `pick_shell_file` uses the stored chooser directory and bounds. A dialog error opens an `Import` notice and returns. `note_shell_door` runs for a report that opened. A report with no file returns.

Metadata that cannot be read leaves the large-file flag false. A length above [`TERMINAL_PASTE_CAP`](../chrome.md#constant-terminal_paste_cap) sets that flag through `console_is_large`. [`ConsoleReadFail::Refuse`](enum.ConsoleReadFail.md#member-refuse) opens an `Import` notice with the static word and `hard` false. [`ConsoleReadFail::Failed`](enum.ConsoleReadFail.md#member-failed) opens an `Import` error with `hard` true. Success shows [`LARGE_FILE`](../chrome.md#constant-large_file) when the flag is set, then calls `insert_paste` with the title `Import`.

### paint_settings

<a id="method-paint_settings"></a>

```rust
fn paint_settings(&mut self, ctx: &egui::Context)
```

The settings window. The close icon and OK dismiss it. The switch applies at once.

### paint_shell_dialogs

<a id="method-paint_shell_dialogs"></a>

```rust
fn paint_shell_dialogs(&mut self, ctx: &egui::Context)
```

The method `paint_shell_dialogs` paints the terminal modals.
`ctx` is the egui context. `paint_settings` runs first.

An open paste editor copies the draft into a multiline field and writes the edit back. Ctrl+Enter or OK calls `accept_paste`. Cancel, or a closed window, drops the draft when OK was not pressed. The window title is [`PASTE_TITLE`](../chrome.md#constant-paste_title) and the default width is 480.

An open shell notice shows the body in [`SLIDE_RED_INK`](../chrome.md#constant-slide_red_ink) when `hard` is true and as plain text otherwise. OK or a closed window drops the notice.

An open new-term box offers Horizontal, Vertical, and Cancel. Any of those, or a closed window, clears `new_term_ask`. A chosen [`SplitWay`](enum.SplitWay.md) then calls `split_focused`.

An open Help window shows [`HELP_BODY`](../chrome.md#constant-help_body) in a read-only field inside a vertical scroll. OK or a closed window clears `help_open`.

An open About window loads `about_app` once through `seat_about_mark` when it is missing. A loaded texture is drawn at [`ABOUT_APP_PX`](../chrome.md#constant-about_app_px). The body is [`ABOUT_BODY`](../chrome.md#constant-about_body) in a read-only field. The layer moves to the top. OK or a closed window clears `about_open`.

An open exit confirm uses a saved position when `exit_pos_usable` accepts it for the content rectangle. After the paint, a usable area rectangle is written with `save_exit_pos`. A failed write stores the note `door file could not be written` only when `door_note` is already empty. Yes clears `exit_ask` and sets `exit_confirmed`. No, or a closed window, clears `exit_ask` only when Yes was not pressed.

### pane_count

<a id="method-pane_count"></a>

```rust
fn pane_count(&self) -> usize
```

The method `pane_count` returns how many panes the tree holds.
The count is `count_leaves` of `tree`.

### split_focused

<a id="method-split_focused"></a>

```rust
fn split_focused(&mut self, way: SplitWay)
```

The method `split_focused` parks the focused pane and opens a new pane beside it.
`way` is the [`SplitWay`](enum.SplitWay.md) passed to `graft_split`. The new id is `next_pane`, which then increases by 1 and stops at the maximum instead of wrapping.

`capture_focused` parks the old pane. The new grid uses the old column count and row count. The session, selection, scope stash, paint stash, model hold, and last connect are cleared. `at_tail` is true. `jump_tail` and `grid_focus` are false. The status becomes `Disconnected` through `seat_control_status`. The connection bar opens. `session_opened` runs on the console focus.

### close_focused_pane

<a id="method-close_focused_pane"></a>

```rust
fn close_focused_pane(&mut self)
```

The method `close_focused_pane` removes the focused pane when another pane remains.
A `pane_count` of 1 or less returns with the tree unchanged. `detach_leaf` removes the focused id. A [`Detach::Miss`](enum.Detach.md#member-miss) returns while the tree is the temporary leaf whose id is 0. A [`Detach::Hit`](enum.Detach.md#member-hit) stores the sibling as the tree.

A focused session is shut down. The Roux counts and `tokens_asked` are cleared. The grid becomes 80 by 24 and `note_grid` runs. Both stashes are cleared. `install_pane` then brings the hit's focus id forward.

### focus_pane

<a id="method-focus_pane"></a>

```rust
fn focus_pane(&mut self, id: u64)
```

The method `focus_pane` brings a parked pane to the front.
`id` is that pane. The focused id returns immediately. An id that is not in `panes` returns immediately. Otherwise the focused pane is captured, pushed onto `panes`, and `install_pane` loads `id`.

### capture_focused

<a id="method-capture_focused"></a>

```rust
fn capture_focused(&mut self) -> ParkedPane
```

The method `capture_focused` moves the focused pane into a [`ParkedPane`](struct.ParkedPane.md).
The link comes from `focused_link` of whether a session is stored, the status, and `link_up`. The grid, session, selection, scroll, scope, paint, model hold, last connect, terminal thought, Roux counts, token ask, and live line move into the result.

The focused grid is left as a fresh 80 by 24 grid. `menu_jump` on the result is false, so the focused `jump_tail` is not copied. The terminal height left behind is `vt_chrome.height`. The other terminal-strip fields left behind are shut.

### install_pane

<a id="method-install_pane"></a>

```rust
fn install_pane(&mut self, id: u64)
```

The method `install_pane` loads a parked pane into the focused fields.
`id` is that pane. A missing id returns. The pane is removed from `panes`.

The grid, session, selection, scroll, stashes, model hold, terminal strip, Roux counts, token ask, and live line become the focused values. `note_grid` runs. A true `menu_jump` sets `jump_tail`. `last_connect` is cloned. When that snap is present, its host text, port text, user text, and password replace the bar fields.

[`PaneLink::Up`](enum.PaneLink.md#member-up) sets the status to `Connected`. [`PaneLink::Opening`](enum.PaneLink.md#member-opening) sets it to `Connecting`. [`PaneLink::Down`](enum.PaneLink.md#member-down) sets it to `Disconnected` and opens the connection bar. An up link whose sanitized user text is not empty calls `seat_canvas_user`. `session_opened` runs on the console focus.

### pane_for_user

<a id="method-pane_for_user"></a>

```rust
fn pane_for_user(&self, name: &str) -> Option<u64>
```

The method `pane_for_user` returns the pane id whose last user matches `name`.
`name` is sanitized with `sanitize_user`. An empty result returns `None`. A match on the focused user text returns `pane_id`. Otherwise the first parked pane whose `last` user sanitizes to the same text returns that pane id. No match returns `None`.

### live_seats

<a id="method-live_seats"></a>

```rust
fn live_seats(&self) -> Vec<ConnectedSeat>
```

The method `live_seats` lists one [`ConnectedSeat`](struct.ConnectedSeat.md) for the focused pane and one for each parked pane.
The focused row uses `user` as `name`, `link_up` as `connected`, and the focused mux answer as `amwp`. `focused` is true only on that row.

Each parked row uses the user text from `last`, or an empty string when `last` is missing. `connected` is true when that pane's link is [`PaneLink::Up`](enum.PaneLink.md#member-up). `amwp` is true when that pane's mux has answered. `focused` is false.

### seat_control_status

<a id="method-seat_control_status"></a>

```rust
fn seat_control_status(&mut self, text: &str)
```

A shell word moves the connection bar. A canvas line does not call this.
A disconnect clears the model ask. The pane word stays.

### deck_key

<a id="method-deck_key"></a>

```rust
fn deck_key(&self) -> String
```

The user whose canvas is on the glass. Empty until a vault is open.

### glass_pane

<a id="method-glass_pane"></a>

```rust
fn glass_pane(&self) -> Option<u64>
```

Pane id for the glass user's execute. None when that user has no pane.

### live_glass_pane

<a id="method-live_glass_pane"></a>

```rust
fn live_glass_pane(&self) -> Option<u64>
```

The glass user's pane when its mux has answered. Another user's socket stays idle.

### land_focused_canvas

<a id="method-land_focused_canvas"></a>

```rust
fn land_focused_canvas(&mut self, glass: &str, pieces: &[ScopePiece]) -> bool
```

Seat focused canvas pieces on the user who owns this pane.
Showing returns false so the caller keeps the glass path.
Parked updates that seat and does not paint. Hold waits on this pane.
// @aa-sdd PK-1074 docs/specs/common/formal-plan.spec.md FINESSE:f-218

### note_replaced_run

<a id="method-note_replaced_run"></a>

```rust
fn note_replaced_run(&mut self)
```

This pane's socket is about to be replaced. A run on that socket cannot continue.
A run on another pane is left alone.

### wire_owned

<a id="method-wire_owned"></a>

```rust
fn wire_owned(&self, frame: &[u8]) -> bool
```

Wire one frame on the pane that owns the showing run.
The focused pane is the owner when the run has not named one.

### wire_pane

<a id="method-wire_pane"></a>

```rust
fn wire_pane(&self, pane: u64, frames: &[Vec<u8>])
```

The method `wire_pane` writes frames on one pane's session.
`pane` is the pane id. `frames` is the list. The focused id uses `session`. Any other id uses the matching parked session. A missing session skips that frame and continues with the next one.

### lift_showing

<a id="method-lift_showing"></a>

```rust
fn lift_showing(&mut self, user: String) -> ParkedDeck
```

Move the glass into a seat. The caller supplies the user key.

### plant_showing

<a id="method-plant_showing"></a>

```rust
fn plant_showing(&mut self, deck: ParkedDeck)
```

The method `plant_showing` copies a parked canvas onto the showing fields.
`deck` is that [`ParkedDeck`](struct.ParkedDeck.md). The `user` text on `deck` is not copied.

`cards` becomes `deck.cards`. `focus` becomes `deck.focus`. `facts` becomes `deck.facts`. `scope_text` becomes `deck.scope_text`.
`think` becomes `deck.think`. `vault` becomes `deck.vault`. `vault_canvas` becomes `deck.vault_canvas`. `vault_hold` becomes `deck.vault_hold`.
`edits` becomes `deck.edits`. `prompt_focus` becomes `deck.prompt_focus`. `group_live` becomes `deck.group_live`. `group_rest` becomes `deck.group_rest`.
`pipe_live` becomes `deck.pipe_live`. `pipe_rest` becomes `deck.pipe_rest`. `join_from` becomes `deck.join_from`.
`focus_prompt` becomes `deck.focus_prompt`. `land_newest` becomes `deck.land_newest`. `concealed` becomes `deck.concealed`. `conceal_armed` becomes `deck.conceal_armed`.
`view` becomes `deck.view`. `run_pane` becomes `deck.run_pane`.

`scroll_seat` is the text pane that receives the wheel. The method sets `scroll_seat` to `None`.
`canvas_sig_set` is true after a canvas heat value has been stored. The method sets `canvas_sig_set` to false.
`seat_mood_canvas` stores a clone of `vault_canvas` on `mood.canvas`.

### remember_deck

<a id="method-remember_deck"></a>

```rust
fn remember_deck(&mut self, deck: ParkedDeck)
```

The method `remember_deck` stores one user's canvas in `decks`.
`deck` is that [`ParkedDeck`](struct.ParkedDeck.md). If `deck.user` is empty, the method returns without a change.

If a row in `decks` has the same `user`, that row becomes `deck`. The method then returns.
If no row has that `user`, `deck` is appended to `decks`.

### plant_user

<a id="method-plant_user"></a>

```rust
fn plant_user(&mut self, user: &str) -> bool
```

Put a parked user back on the glass. False when that seat is absent.

### load_user_canvas

<a id="method-load_user_canvas"></a>

```rust
fn load_user_canvas(&self, user: &str) -> Option<LoadedCanvas>
```

The method `load_user_canvas` reads one user's first canvas from the vault file.
`user` is that user text. The return is a [`LoadedCanvas`](struct.LoadedCanvas.md), or `None` when the read does not yield cards.

If `home_dir` returns nothing, the method returns `None`. If `vault_rt` is absent, the method returns `None`.
The path is `crate::vault::vault_file` of that home directory and `user`. The path joins `.aetheris`, the sanitized user text, and `aetheris.db`.

`crate::vault::Vault::open` runs on that path and `user`. If the open fails, the method returns `None`.
`load_or_create` then runs on the open vault. If that load fails, the method returns `None`.
The first canvas is kept. If the canvas list is empty, the method returns `None`.

`cards_from_canvas` splits that canvas into cards and held ids. If the card list is empty, the method returns `None`.
The held ids alone do not produce a [`LoadedCanvas`](struct.LoadedCanvas.md). If the card list is not empty, the method returns a [`LoadedCanvas`](struct.LoadedCanvas.md).
`vault` is the open vault. `cards` and `hold` are that split. `canvas_id` is the canvas id.

### adopt_loaded

<a id="method-adopt_loaded"></a>

```rust
fn adopt_loaded(&mut self, loaded: LoadedCanvas)
```

The method `adopt_loaded` replaces the showing canvas with a loaded vault canvas.
`loaded` is that [`LoadedCanvas`](struct.LoadedCanvas.md). The canvas that was showing is not stored in `decks`.

The method reads `card_stamp`. If that result is negative, the method treats it as 0. The value is then a `u64`.
If the value is greater than `cell_seq`, the value replaces `cell_seq`.

`cards` becomes `loaded.cards`. `concealed` is cleared. `conceal_armed` becomes false.
`seat_cell_think` copies saved think fields from `think_yaml` onto the cards. `seat_expanded` applies `expanded` to the cards.

`focus` becomes 0. `facts` becomes `None`. `scope_text` is cleared. `edits` becomes a new `crate::edit::EditDesk`. `prompt_focus` becomes `None`.

`group_live` becomes 0. `group_rest` is cleared. `pipe_live` becomes false. `pipe_rest` is cleared. `join_from` becomes `None`.
`focus_prompt` becomes false. `land_newest` becomes false.

`view` becomes `None`. `run_pane` becomes `None`. `vault_canvas` becomes `loaded.canvas_id`. `vault_hold` becomes `loaded.hold`.
`vault` becomes the open `crate::vault::Vault` from `loaded`.

`scroll_seat` becomes `None`. `canvas_sig_set` becomes false. `seat_mood_canvas` stores a clone of `vault_canvas` on `mood.canvas`.

### seat_canvas_user

<a id="method-seat_canvas_user"></a>

```rust
fn seat_canvas_user(&mut self, picked: &str)
```

The named user takes the glass. The user who was showing keeps the seat and the run.
A failed load leaves the open user on the glass.
The same user does not reload, so a live run is not marked interrupted.

### card_snap

<a id="method-card_snap"></a>

```rust
fn card_snap(&self, cell: &[u8; 16]) -> Option<crate::model::ThinkSnap>
```

The model snap for a cell on the glass or on a parked seat.

### deliver_scope

<a id="method-deliver_scope"></a>

```rust
fn deliver_scope(
    &mut self,
    pane: u64,
    user: &str,
    pieces: &[ScopePiece],
    mux: bool,
    paint: bool,
) -> Vec<Vec<u8>>
```

Apply canvas pieces to the user who owns them.
`pane` is the session that carried the pieces. `paint` writes the status line and the thinking file. A parked seat does not.

### close_vault

<a id="method-close_vault"></a>

```rust
fn close_vault(&mut self)
```

The method `close_vault` drops the open vault from the showing fields.
`vault` becomes `None`. `vault_canvas` is cleared. `vault_hold` is cleared.

### store_vault

<a id="method-store_vault"></a>

```rust
fn store_vault(&mut self)
```

Write the open canvas from this thread. A missing file writes nothing.

### search_vault

<a id="method-search_vault"></a>

```rust
fn search_vault(&mut self) -> Result<Option<SearchSeat>, ()>
```

The method `search_vault` searches the open vault and returns a [`SearchSeat`](struct.SearchSeat.md).
The method calls `store_vault` before the search. If `vault_rt` is absent, the method returns `Err(())`.
If `vault` is absent, the method returns `Err(())`.

The needle is a clone of `tool_needle`. The canvas id is a clone of `vault_canvas`.
`crate::vault::Vault::search` runs with that canvas id and that needle. If the search returns an error, the method returns `Err(())`.

If the search succeeds, `seat_from_hits` receives the open cards and the hit list. If the hit list is empty, that call returns `None`.
If the hit list is not empty, the call returns a [`SearchSeat`](struct.SearchSeat.md). `count` is the hit count. `index` is the first open card whose id is in the hits.
If no open card matches, `index` is the card count. The method wraps that value in `Ok`.

### store_think_yaml

<a id="method-store_think_yaml"></a>

```rust
fn store_think_yaml(&self)
```

The method `store_think_yaml` writes the shared think record.
If `home_dir` returns nothing, the method returns without a write. The path is `think_yaml_path` of that directory.
The file is `.casserole` joined to `aetheris-think.yaml`.

`write_think_yaml` receives that path and `think_yaml`. The returned bool is ignored.
If the record is blocked, that write returns false and leaves the file alone.

### keep_card_think

<a id="method-keep_card_think"></a>

```rust
fn keep_card_think(&mut self, cell: [u8; 16])
```

The five fields for this cell. A match leaves the file alone.

### remember_live_expanded

<a id="method-remember_live_expanded"></a>

```rust
fn remember_live_expanded(&mut self)
```

Checked cards join the file. An unchecked card is left as it was.
Other ids stay. A nil id stays out. A blocked file stays.
A memory door updates the seat and writes nothing.
// @aa-sdd PK-1077 docs/specs/common/formal-plan.spec.md FINESSE:f-206

### keep_card_expanded

<a id="method-keep_card_expanded"></a>

```rust
fn keep_card_expanded(&mut self, cell: [u8; 16])
```

The check for this cell. A match leaves the file alone. A memory door writes nothing.

### store_expanded

<a id="method-store_expanded"></a>

```rust
fn store_expanded(&self)
```

The method `store_expanded` writes the expanded-view record.
`door` is the SSH client settings store. If [`Door::has_file`](../door/struct.Door.md#method-has_file) is false, the method returns.
If `expanded` is blocked, the method returns. If `home_dir` returns nothing, the method returns.

The path is `expanded_yaml_path` of that directory. The file is `.casserole` joined to `aetheris-expanded.yaml`.
`write_expanded_yaml` receives that path and `expanded`. The returned bool is ignored.

### arm_conceal

<a id="method-arm_conceal"></a>

```rust
fn arm_conceal(&mut self)
```

Seed the conceal set once. A fold does not call this.

### release_deck

<a id="method-release_deck"></a>

```rust
fn release_deck(&mut self)
```

A fold clears the omission. It never adds an id.

### store_hide

<a id="method-store_hide"></a>

```rust
fn store_hide(&mut self)
```

Hide or Show for the next open. A memory door writes nothing. A blocked file stays.

### store_settings

<a id="method-store_settings"></a>

```rust
fn store_settings(&mut self)
```

Painted borders for the next open. A memory door writes nothing. A blocked file stays.

### aetheris_status_facts

<a id="method-aetheris_status_facts"></a>

```rust
fn aetheris_status_facts(&self) -> String
```

Vault, wire, air-gap, and engine. The canvas extra stays on the Aetheris strip.

### shell_status_facts

<a id="method-shell_status_facts"></a>

```rust
fn shell_status_facts(&self) -> String
```

Vault, mode, air-gap, and engine. AMWP stays on the Aetheris strip.
The engine is this pane's model. A canvas cell is not this word.
// @aa-sdd PK-994 docs/specs/common/stages.spec.md FINESSE:f-230

### aetheris_status_face

<a id="method-aetheris_status_face"></a>

```rust
fn aetheris_status_face(&self) -> (String, String)
```

West notebook line and the east user name. The link chip stays on the connection bar.

### store_vt_yaml

<a id="method-store_vt_yaml"></a>

```rust
fn store_vt_yaml(&self)
```

The method `store_vt_yaml` writes the VT100 think record.
If `home_dir` returns nothing, the method returns without a write. The path is `vt_think_yaml_path` of that directory.
The file is `.casserole` joined to `aetheris-vt100-think.yaml`.

`write_vt_think_yaml` receives that path and `vt_yaml`. The returned bool is ignored.
If the record is blocked, that write returns false and leaves the file alone.

### store_mood_yaml

<a id="method-store_mood_yaml"></a>

```rust
fn store_mood_yaml(&self)
```

The method `store_mood_yaml` writes the mood record for the current owner.
If `mood` is blocked, the method returns without a write. If `mood_owner` is empty, the method returns without a write.
If `home_dir` returns nothing, the method returns without a write.

The path is `mood_user_path` of that directory and `mood_owner`. The file is `.casserole` joined to `aetheris-mood-{name}.yaml`.
The name in that file is the sanitized owner text. `write_mood_yaml` receives that path and `mood`. The returned bool is ignored.

### remember_mood

<a id="method-remember_mood"></a>

```rust
fn remember_mood(&mut self, user: &str, yaml: MoodYaml)
```

The method `remember_mood` stores one user's mood record in `mood_bank`.
`user` is that user text. `yaml` is the [`MoodYaml`](struct.MoodYaml.md). If `user` is empty, the method returns without a change.

If a bank row's name equals `user`, that row's record becomes `yaml`. The method then returns.
The comparison uses the stored name and `user` as written. If no row matches, the method appends `user` and `yaml` as a new row.

### adopt_mood_user

<a id="method-adopt_mood_user"></a>

```rust
fn adopt_mood_user(&mut self, picked: &str)
```

Seat `picked`. Stash the user who was showing. Claim the shared file only
for the first user, and only when no per-user file exists yet.

### ensure_mood_user

<a id="method-ensure_mood_user"></a>

```rust
fn ensure_mood_user(&mut self, user: &str)
```

Read a pane user's file into the bank. A missing file is not cached and is not claimed.

### try_open_mood

<a id="method-try_open_mood"></a>

```rust
fn try_open_mood(&mut self, focus: usize)
```

The method `try_open_mood` opens the mood dialog for one card when the gate allows it.
`focus` is the requested card index. If `mood_desk` is already open, the method returns.

The index becomes `mood_focus_card` of the cards and `focus`, or the requested index when that call returns `None`.
If the deck is empty, the requested index stays. If the index sits in the deck, it stays. If the index is past the deck, it becomes the last card.

`mux` is true when `session` is present and `mux_answered` is true. `begin_mood_doc` receives the cards, that index, `mux`, and `grok`.

If the result is [`MoodBegin::NoCell`](enum.MoodBegin.md#member-nocell), `status` becomes [`MOOD_NO_CELL`](../chrome.md#constant-mood_no_cell).
If the result is [`MoodBegin::Burner`](enum.MoodBegin.md#member-burner), the group is the focused card's `group`, or 0 when that card is absent.
`burner_fault` of that group sets `status` to [`BURNER_DENIED`](../chrome.md#constant-burner_denied). If the group is 0, the notice is absent and `slide` stays as it was.
If the group is not 0, `slide` becomes an [`ErrorNotice`](struct.ErrorNotice.md) whose body is [`BURNER_DENIED`](../chrome.md#constant-burner_denied) and whose `hard` flag is false.

If the result is [`MoodBegin::NoMux`](enum.MoodBegin.md#member-nomux), `slide` becomes an [`ErrorNotice`](struct.ErrorNotice.md). The body is [`MOOD_KEYWORD`](../chrome.md#constant-mood_keyword). The `hard` flag is false.
`status` becomes [`MOOD_KEYWORD`](../chrome.md#constant-mood_keyword).

If the result is [`MoodBegin::Open`](enum.MoodBegin.md#member-open) and the index has no card, `status` becomes [`MOOD_NO_CELL`](../chrome.md#constant-mood_no_cell). The method then returns.
If the card is present, `open_on` opens the dialog on the index and that card's `cell`.
`seed_mood_field` copies `mood.line` into the draft. If the trimmed line is empty, the draft stays empty.

### fire_mood_send

<a id="method-fire_mood_send"></a>

```rust
fn fire_mood_send(&mut self)
```

One shaping frame. The card's running flag stays clear. This path does not call `plan_chain`.

### mood_miss

<a id="method-mood_miss"></a>

```rust
fn mood_miss(&mut self, detail: &str, hard: bool)
```

The method `mood_miss` closes the mood dialog and shows a notice.
`detail` is the notice text. `hard` is the notice flag.

The text is trimmed. If the trim is empty, the body is [`MOOD_KEYWORD`](../chrome.md#constant-mood_keyword). If the trim is not empty, the body is that text.
`close_glass` drops the chat and closes the dialog. The wire list stays. The generation stays.

`slide` becomes an [`ErrorNotice`](struct.ErrorNotice.md) with that body and `hard`. `status` becomes [`MOOD_KEYWORD`](../chrome.md#constant-mood_keyword).

### seal_mood

<a id="method-seal_mood"></a>

```rust
fn seal_mood(&mut self, keep_line: bool)
```

The method `seal_mood` saves the mood record and closes the dialog.
`keep_line` chooses whether the draft is stored.

If `keep_line` is true, `approve_mood_line` receives a clone of `mood_desk.draft`. The bool from that call is ignored.
If the trimmed draft is empty, the stored line and the switch stay as they were. If the trimmed draft is not empty, the stored line becomes that text.
If that text differs from the previous line, `pending` becomes true. The switch is turned on.
If `mood_has_line` is then false, the switch is turned off. A line stored from a non-empty draft still has text, so that check leaves the switch on.

If `mood_desk.last_size` holds a width and a height, `note_mood_size` receives that pair. The bool from that call is ignored.
If `mood` is blocked, the stored size stays as it was. If the width is outside [`MOOD_WIDTH_MIN`](../chrome.md#constant-mood_width_min) through [`MOOD_WIDTH_MAX`](../chrome.md#constant-mood_width_max), the stored size stays as it was.
If the height is outside [`MOOD_HEIGHT_MIN`](../chrome.md#constant-mood_height_min) through [`MOOD_HEIGHT_MAX`](../chrome.md#constant-mood_height_max), the stored size stays as it was.
If both sides sit inside those ranges, the pair is stored.

The method calls `store_mood_yaml`. If `mood` is blocked, if `mood_owner` is empty, or if the home directory is missing, that call writes nothing.
`close_glass` then drops the chat and closes the dialog. The wire list stays. The generation stays. The saved mood line stays. The mood switch stays.

### paint_token

<a id="method-paint_token"></a>

```rust
fn paint_token(&mut self, ctx: &egui::Context, used: i32, chars: i32, blocks: i32)
```

The token window. OK and the close icon dismiss it. Tests do not call
this, so the OS window stays closed. The open does not ask the host.

### paint_mood

<a id="method-paint_mood"></a>

```rust
fn paint_mood(&mut self, ctx: &egui::Context)
```

The mood glass. The window stays inside the app. The center is the
text window. The button row stays at the bottom. Tests do not call
this, so the OS window stays closed.

### send_model_wires

<a id="method-send_model_wires"></a>

```rust
fn send_model_wires(&mut self, wires: &[crate::model::ModelWire])
```

The method `send_model_wires` writes each model request as a text frame on the live session. The parameter `wires` is the slice of `ModelWire` values to send. A session that has not answered writes no frame.

A quiet session still changes the status when one wire is `ModelWire::ShellModel`. The status becomes `raw console has no modal`. The method then returns.

A live session builds one frame for each wire. `ModelWire::Tags` uses `tags_body` on that wire's cell. `ModelWire::GrokModels` uses `grok_models_body` on that wire's cell. `ModelWire::Show` uses `show_body` for the named id on that wire's cell. `ModelWire::ShellModel` uses `shell_model_body` on sixteen zero bytes. `ModelWire::ShellSet` sends its body on sixteen zero bytes. An empty settings body sends no frame.

A present frame is written with `wire` on the session. A missing session drops that frame.

### land_model

<a id="method-land_model"></a>

```rust
fn land_model(&mut self, console: bool, cell: [u8; 16], effect: &crate::model::ModelEffect)
```

The method `land_model` applies one `ModelEffect` and then sends that effect's wires. The parameter `console` is true when the change belongs to the console dialog rather than a card. The parameter `cell` is the card's sixteen-byte id. The parameter `effect` is the change to apply.

A status word on the effect replaces `status`. A missing status leaves `status` as it was.

A false `console` looks up the card with that cell. A target on the effect replaces the card target. A think snap on the effect is written with `apply_snap`. The method keeps that card's think record unless `think_open_echo` says the new snap only repeats the open state already stored. A true `console` skips the card update.

The method always calls `send_model_wires` with the effect's wires.

### model_card

<a id="method-model_card"></a>

```rust
fn model_card(&self, console: bool, cell: [u8; 16]) -> (String, crate::model::ThinkSnap, usize)
```

The method `model_card` returns the stored target, the think snap, and the card index for one cell. The parameter `console` is true for the console dialog. The parameter `cell` is the card's sixteen-byte id.

A console call returns an empty target, [`ThinkSnap::off`](../model/struct.ThinkSnap.md#method-off), and index 0. A cell that matches no card returns that same triple.

A matching card returns a clone of its target, the snap from `snap_of`, and that card's index.

### open_model

<a id="method-open_model"></a>

```rust
fn open_model(&mut self, index: usize)
```

The method `open_model` opens the model dialog on the card at `index`. An index past the deck returns without a change.

The method reads that card's cell, the text key from `cell_text`, the stored target, and the think snap. `open_cell` builds the effect from those values and the Grok record. `land_model` applies that effect with the console flag false.

### ring_model

<a id="method-ring_model"></a>

```rust
fn ring_model(&mut self)
```

The method `ring_model` asks the live session for the console model list. A false `doorbell` returns without a change.

A session that has not answered sets `status` to `raw console has no modal` and returns. A live session stores the current pane id in `console_model_pane`. `shell_open` becomes true. `engine_asked` becomes true. The method sends one `ModelWire::ShellModel` wire.

### ask_shell_once

<a id="method-ask_shell_once"></a>

```rust
fn ask_shell_once(&mut self)
```

The mux asks once. The answer seats the family and the model. The modal stays shut.
// @aa-sdd PK-994 docs/specs/common/stages.spec.md FINESSE:f-230

### seat_pane_engine

<a id="method-seat_pane_engine"></a>

```rust
fn seat_pane_engine(&mut self, pane: u64, word: &str)
```

The method `seat_pane_engine` stores one pane's model word.
`pane` is that pane. `word` is the engine word. The focused pane stores it on `engine_label`.
Any other pane stores it on that parked pane. A canvas cell is not this word.
// @aa-sdd PK-994 docs/specs/common/stages.spec.md FINESSE:f-230

### sync_list_slide

<a id="method-sync_list_slide"></a>

```rust
fn sync_list_slide(&mut self)
```

The method `sync_list_slide` copies a new model-list notice onto the error slide.
A generation that was already copied returns. A notice opens a red slide.
A cleared notice closes the slide only when the slide is still one of the two list sentences.
// @aa-sdd PK-994 docs/specs/common/stages.spec.md FINESSE:f-230

### take_model_note

<a id="method-take_model_note"></a>

```rust
fn take_model_note(&mut self, pane: u64, cell: &[u8; 16], body: &[u8])
```

The method `take_model_note` reads one model reply. `pane` is the session that carried it. `cell` is the sixteen-byte id the frame named. `body` is the raw reply.

A body that starts with `shell_model_result:` seats that pane's engine word. A key other than `CONSOLE_CELL`, or a pane other than the focused pane, returns after that seat. The focused console result still opens the dialog only when `shell_open` is true. A dismissed dialog still ignores the late fields.

A tags reply or a Grok catalog reply from another pane returns. The focused pane applies the reply and copies a new list notice onto the slide.

A body that starts with `show_result:` uses the card snap, or [`ThinkSnap::off`](../model/struct.ThinkSnap.md#method-off) when the card has none, and `apply_show` consumes it. A body that starts with `effort_off:` calls `effort_off` for that key. Any other body is ignored.

### drop_plate_docs

<a id="method-drop_plate_docs"></a>

```rust
fn drop_plate_docs(&mut self)
```

The method `drop_plate_docs` forgets the Grok record for every card and closes a card model dialog. Each card cell becomes a text id. `forget_all` drops those ids.

An open dialog that is not the console dialog is closed. A console dialog stays open. A closed dialog stays closed.

### copy_new_group_docs

<a id="method-copy_new_group_docs"></a>

```rust
fn copy_new_group_docs(&mut self, before: &[[u8; 16]], source_group: u64)
```

The method `copy_new_group_docs` copies Grok records from the cards already in a group onto the cards that group just gained. The parameter `before` lists the sixteen-byte cells that belonged to the group before the copy. The parameter `source_group` is that group's number. A `source_group` of 0 returns without a copy.

The method collects text keys for cards in that group whose cells are in `before`. A card in column 0 is kept until the end of that list. Every other matching card is pushed first. The new cards are the ones whose cells are not in `before`.

Each source key is paired in order with one new key. A Grok entry on the source is copied onto the new key with `copy_onto`. A missing entry copies nothing. Extra sources, and extra new cards, stay unpaired.

### note_target_change

<a id="method-note_target_change"></a>

```rust
fn note_target_change(&mut self, index: usize, text: &str) -> String
```

The method `note_target_change` stores a target edit while the field is still changing and returns the stored text. The parameter `index` is the card's place in the deck. The parameter `text` is the field's current text. An index past the deck returns `text` unchanged.

The method reads the previous target and the card's think snap. `target_keystroke` folds the keystroke into a stored target and may change the snap. When the previous provider is not `grok` and the stored provider is `grok`, the method adopts a Grok record. The earlier list is the text id of every card before this index. The source flag is the `grok_on` value of the nearest earlier card that already has a non-empty Grok model. `adopt_grok` replaces the stored target. A found source flag is written onto the snap.

The card target becomes the stored text. `apply_snap` writes the snap. The method returns the stored text.

### note_target_leave

<a id="method-note_target_leave"></a>

```rust
fn note_target_leave(&mut self, index: usize, text: &str) -> String
```

The method `note_target_leave` stores a target when the field loses focus and returns the stored text. The parameter `index` is the card's place in the deck. The parameter `text` is the field text at that moment. An index past the deck returns `text` unchanged.

`target_focus_loss` folds the text into a stored target and may change the think snap. `sync_grok_model` updates the Grok record for that card's text id. The card target becomes the stored text. `apply_snap` writes the snap. The method returns the stored text.

### paint_model

<a id="method-paint_model"></a>

```rust
fn paint_model(&mut self, ctx: &egui::Context)
```

Model Config. Close leaves the cell as last written.

### paint_model_body

<a id="method-paint_model_body"></a>

```rust
fn paint_model_body(&mut self, ui: &mut egui::Ui, session: &mut crate::model::ModelSession)
```

The method `paint_model_body` paints the family picker and the pane for the open model dialog. The parameter `ui` is the egui surface. The parameter `session` is the `ModelSession` being edited.

The family combo shows `family_label`, or a single space when that label is empty. The choices are [`Family::Ollama`](../model/enum.Family.md#member-ollama), [`Family::Grok`](../model/enum.Family.md#member-grok), and [`Family::Pool`](../model/enum.Family.md#member-pool). A click stores that family.

A picked family reads the stored target, the think snap, and the index from `model_card`. A console session passes an empty earlier-id list. A card session passes the text id of every card before that index. The flag list pairs every card's text id with that card's `grok_on`. `choose_family` builds the effect. `land_model` applies it.

The method then paints the matching pane. [`Family::Ollama`](../model/enum.Family.md#member-ollama) calls `paint_ollama_pane`. [`Family::Grok`](../model/enum.Family.md#member-grok) calls `paint_grok_pane`. [`Family::Pool`](../model/enum.Family.md#member-pool) calls `paint_pool_pane`. A session with no family paints nothing more. A list miss paints its sentence in red.

### paint_ollama_pane

<a id="method-paint_ollama_pane"></a>

```rust
fn paint_ollama_pane(&mut self, ui: &mut egui::Ui, session: &mut crate::model::ModelSession)
```

The method `paint_ollama_pane` paints the Ollama model fields and lands each edit. The parameter `ui` is the egui surface. The parameter `session` is the `ModelSession`. The console flag and the cell come from that session.

The row labeled `Model` is a single-line editor of width 220. The hover text is `OLLAMA_MODEL_TIP`. A change calls `type_ollama` with the current think snap and lands that effect. A lost focus trims the id, calls `commit_ollama` with true, and lands that effect.

The combo `ollama-models` lists `ollama_tags`. Its selected text is the current id, or a single space when the id is empty. The hover text is `OLLAMA_MODEL_TIP`. A clicked name types that name, lands the effect, commits it, and lands the commit.

The checkbox labeled `Thinking` uses `OLLAMA_THINK_TIP`. A change calls `set_ollama_think` and lands the effect. When `effort_visible` is true, a row labeled `Effort` lists `ollama_levels`. The selected text is the current level, or a single space when it is empty. The hover text is `OLLAMA_EFFORT_TIP`. A clicked level calls `set_ollama_level` and lands the effect. A hidden effort row paints nothing.

### paint_grok_pane

<a id="method-paint_grok_pane"></a>

```rust
fn paint_grok_pane(&mut self, ui: &mut egui::Ui, session: &mut crate::model::ModelSession)
```

The method `paint_grok_pane` paints the Grok model fields and lands each edit. The parameter `ui` is the egui surface. The parameter `session` is the `ModelSession`. The console flag and the cell come from that session. The labels `API` and `chat` are fixed text.

The model combo lists `grok_choices`. An empty combo, an empty stored id, or a stored id that is not a row shows a single space. The stored id stays. The hover text is `GROK_MODEL_TIP`. A click calls `pick_grok_model` and lands the effect. The effort combo is enabled only when `effort_enabled` is true. An empty effort shows the word `high`. The hover text is `GROK_EFFORT_TIP`. A click calls `set_grok_effort` and lands the effect.

The checkbox `Send effort` uses `SEND_EFFORT_TIP`. A change calls `set_send_effort` and lands the effect. The stream combo shows `true` or `false`. A click calls `set_stream` and lands the effect. The checkbox `burner cloud` uses `BURNER_CLOUD_TIP`. A change calls `set_burner` and lands the effect.

The temperature field is a single line of width 80 and uses `TEMPERATURE_TIP`. A lost focus calls `set_temperature` and lands the effect. Typing alone does not land. The max-tokens field is a single line of width 100 and uses `MAX_TOKENS_TIP`. A lost focus calls `set_max_tokens` and lands the effect. The checkbox `Thinking` uses `GROK_THINK_TIP`. A change reads the think snap from `model_card`, calls `set_grok_think`, and lands the effect.

### paint_pool_pane

<a id="method-paint_pool_pane"></a>

```rust
fn paint_pool_pane(&mut self, ui: &mut egui::Ui, session: &mut crate::model::ModelSession)
```

The method `paint_pool_pane` paints the pool sentence and the thinking checkbox. The parameter `ui` is the egui surface. The parameter `session` is the `ModelSession`. The label is `POOL_SENTENCE`.

The checkbox is `Thinking` and uses `POOL_THINK_TIP`. A change reads the think snap from `model_card`, calls `set_pool_think`, and lands the effect. A checkbox that did not change leaves the session as it was.

### disconnect

<a id="method-disconnect"></a>

```rust
fn disconnect(&mut self)
```

The method `disconnect` stores the account file, closes it, and drops the live session. `store_vault` runs first. `close_vault` runs next. `clear_canvas` clears the facts and the scope text. `drop_plate_docs` forgets the card model records. `clear_deck` clears the cards and the focus.

The edit history is cleared. Prompt focus is cleared. The group remainder is cleared and `group_live` becomes 0. The pipe remainder is cleared and `pipe_live` becomes false. `join_from` becomes absent. `run_pane` becomes absent. The mood glass is closed. `focus_prompt` becomes false. `land_newest` becomes false.

When a session is present, the held count fields are cleared before shutdown. `roux_used` becomes absent. `roux_chars` and `roux_blocks` become 0. `tokens_asked` becomes false. `shutdown` is called with `JOIN_BOUND`, which is two seconds. A shutdown error sets the status to `Disconnected: read thread did not stop` and returns. A clean shutdown, and a missing session, set the status to `Disconnected`.

### note_live_chars

<a id="method-note_live_chars"></a>

```rust
fn note_live_chars(&mut self, text: &str)
```

The unsent line on the focused pane. Enter submits it. Backspace drops one character.

### note_live_key

<a id="method-note_live_key"></a>

```rust
fn note_live_key(&mut self, key: Key)
```

Enter submits the line. Backspace drops one character. Other keys stay on the wire only.

### note_grid

<a id="method-note_grid"></a>

```rust
fn note_grid(&mut self)
```

The grid changed. The cached console text is stale.
// @aa-sdd PK-1171 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-209

### bump_heat

<a id="method-bump_heat"></a>

```rust
fn bump_heat(&mut self, kind: u8) -> u64
```

The method `bump_heat` advances the heat sequence and returns the new value. The parameter `kind` is the beat kind the caller is posting. Canvas heat calls this with 1. Terminal heat calls this with 2.

When `kind` differs from `heat_kind`, a kind of 1 clears `vt_sig_set`. Any other new kind clears `canvas_sig_set`. The same kind leaves both signature flags as they were. `heat_kind` becomes `kind`. `heat_seq` increases by 1, with wrap. The method returns that sequence.

### apply_heat

<a id="method-apply_heat"></a>

```rust
fn apply_heat(&mut self, paint: HeatPaint)
```

The method `apply_heat` stores one finished [`HeatPaint`](struct.HeatPaint.md). The parameter `paint` is that result.

When `paint.canvas` is true, `ring_used` becomes the estimate. `heat_chars`, `heat_blocks`, and `heat_body` take the character count, the block count, and the body. The method then returns.

When `paint.canvas` is false, `vt_used`, `vt_chars`, and `vt_blocks` take the estimate, the character count, and the block count. The body is not stored on that path.

### take_heat

<a id="method-take_heat"></a>

```rust
fn take_heat(&mut self, ctx: &egui::Context, posted: bool)
```

The method `take_heat` applies one finished heat result or asks for another paint. The parameter `ctx` is the egui context. The parameter `posted` is true when this frame offered a new beat.

Under test, the method calls `finish_pending`. Outside tests, it calls `poll`. A present [`HeatPaint`](struct.HeatPaint.md) is applied with `apply_heat`, and the method returns. When no paint is ready and `posted` is true, the method requests a repaint. A quiet poll with `posted` false does nothing more.

### post_canvas_heat

<a id="method-post_canvas_heat"></a>

```rust
fn post_canvas_heat(
    &mut self,
    ctx: &egui::Context,
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
    line_chars: u32,
    debug: &Option<(usize, Vec<String>)>,
    pin_active: bool,
    shown: &[bool],
    locks: &[bool],
    managed: i32,
)
```

Post the canvas beat when the seat changed. The frame paints the last integer.
// @aa-sdd PK-1171 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-209

### post_vt_heat

<a id="method-post_vt_heat"></a>

```rust
fn post_vt_heat(&mut self, ctx: &egui::Context)
```

Post the VT100 beat when the line or the grid changed. The key does not walk the grid.
// @aa-sdd PK-1171 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-209

### focused_pane_user

<a id="method-focused_pane_user"></a>

```rust
fn focused_pane_user(&self) -> String
```

The focused pane's user. Empty stays empty so a quiet field is not `anon`.

### canvas_user

<a id="method-canvas_user"></a>

```rust
fn canvas_user(&self) -> String
```

The vault user on the canvas. An empty vault uses the focused pane's user.

### mood_line_for_user

<a id="method-mood_line_for_user"></a>

```rust
fn mood_line_for_user(&self, user: &str) -> Option<String>
```

The line for one pane's user. None leaves a pane that was never told alone.

### store_ssh_mood

<a id="method-store_ssh_mood"></a>

```rust
fn store_ssh_mood(&self)
```

The method `store_ssh_mood` writes the per-user mood switches. A true `ssh_mood_blocked` returns without a write. A missing home directory returns without a write.

Otherwise `write_ssh_mood` writes `ssh_users` to the path from `ssh_mood_yaml_path`. A write error is ignored.

### apply_strokes

<a id="method-apply_strokes"></a>

```rust
fn apply_strokes(
    &mut self,
    events: &[egui::Event],
    focused: bool,
    live_shift: bool,
    live_command: bool,
    live_alt: bool,
)
```

The method `apply_strokes` turns console input into session bytes and into local console actions. The parameter `events` is the egui event list for this frame. The parameter `focused` is the console focus flag from before this paint. The parameters `live_shift`, `live_command`, and `live_alt` are the modifier flags for this frame. A false `focused` returns, and the method does not re-read focus.

Each event is read with `stroke_at`. An event that is not a stroke is skipped. [`TermStroke::Paste`](enum.TermStroke.md#member-paste) stores the text in `paste_in`. [`TermStroke::Copy`](enum.TermStroke.md#member-copy) copies the highlighted text when both `anchor` and `current` are present. Empty text is not copied. That stroke writes no interrupt byte.

[`TermStroke::Text`](enum.TermStroke.md#member-text) updates the unsent line with `note_live_chars` and appends `encode` of each character. [`TermStroke::Key`](enum.TermStroke.md#member-key) of End, when the view is not on the tail, sets `jump_tail` and sends nothing. [`TermStroke::Motion`](enum.TermStroke.md#member-motion) applies the menu label through `apply_console_label`. Any other [`TermStroke::Key`](enum.TermStroke.md#member-key) updates the unsent line with `note_live_key` and appends `encode` of that key. Encoding uses the grid's cursor-key mode.

When the outgoing bytes are not empty and a session is present, `write` sends them. A missing session drops the bytes.

### ui

<a id="method-ui"></a>

```rust
fn ui(&mut self, ui: &mut egui::Ui)
```

The method `ui` paints one client frame and then runs the click that frame recorded. The parameter `ui` is the egui surface for that frame.

The method copies the context and passes `tooltips_on` to `seat_button_hover`. The visuals are the dark theme. Panel fill, window fill, and the extreme background are [`PLATE`](../chrome.md#constant-plate). The method clones the input events. Shift is true when the shift modifier is down or either Shift key is down. Command is true for the command modifier or either Control key. Alt is true for the alt modifier or either Alt key. `focused` is `grid_focus` from before the paint.

Inside the central panel, `take_shell_keys` runs when the grid is focused and the plate is [`Plate::Ssh`](enum.Plate.md#member-ssh). `note_control_span` stores the content size and may fold the control bar. `footer_clock` is the thinking elapsed time, or absent when the deck is quiet. An open bar paints the menu first. A folded bar paints the menu later, after the product tabs. `mux` is true when the session has answered.

An open bar paints two rows and then the product tabs. The first row shows the fold mark, the label `Ollama`, the host field, the port field, the user field, the account list, the password field, the remember checkbox, and the link status. The host field is 160 points wide. Its hint is `localhost`. Its hover text is `HOST_TIP`. The port field is 64 points wide. Its hint is `2222`. Its hover text is `PORT_TIP`. The user field is 120 points wide and uses `USER_FIELD_TIP`. The password field hides its text. It is 120 points wide and uses `PASSWORD_TIP`.

The account list comes from [`Door`](../door/struct.Door.md), which stores saved accounts and window settings. Choosing a name sets `user`. A stored password for that name sets `password`. That choice also sets `remember` and marks a save. Changing the remember checkbox marks a save. Leaving the host field, the port field, the user field, or the password field marks a save. `paint_link_status` shows `status`.

The second row shows Connect, Disconnect, Reconnect, Clear Terminal, Load File, New Term, and Close Pane. Connect is enabled by `connect_allowed`. Disconnect is enabled when a session is present. The other buttons follow `shell_enables` for the current link phase and pane count. A click sets the matching flag. New Term sets `new_term_ask` and does not open a pane in this method. The minus and plus buttons step `term_font` by one. A changed size is stored with `store_font`. A present footer clock paints the thinking cluster. A present `door_note` is drawn under the row. A separator sits above the product tabs from `seat_plate_tabs`.

A folded bar uses one row. That row shows the fold mark, the link status, and the product tabs. A docked menu follows a separator. The thinking cluster is on the right when a clock is present. A present `door_note` is drawn under the row. A clicked fold mark steps `link_up` with [`ControlBeat::Mark`](enum.ControlBeat.md#member-mark) and then steps `control_open`. `step_product_tab` may arm console focus, replace `plate`, set `restore_aetheris`, set `land_newest`, and store a [`TabWord`](enum.TabWord.md).

On [`Plate::Aetheris`](enum.Plate.md#member-aetheris), the body is `paint_empty` and the footer is a 9-point heat strip plus the status text. The strip budget is `managed_tokens` when that value is above zero. Otherwise the budget comes from `selected_heat_budget` using the session window, the card targets, the focused target, the completion cap, and the application budgets. The two status texts come from `aetheris_status_face`.

On any other plate, the body is `paint_shell` and the method posts the terminal heat beat. The strip budget is `managed_tokens` when that value is above zero. Otherwise a known session window is the budget. An unknown window uses zero. The west text adds the mood flag and the counted tokens to the shell status. The east text is `status_user_name`. The link is treated as up only when `link_up` is true and the mux has answered.

`paint_model` runs for both plates. Off the Aetheris plate, `paint_token` uses `vt_used`. The character count and the block count are the held counts when `roux_used` is present. Otherwise they are the terminal counts. The method then flushes the divider, the wrap field, the line field, and the view field.

After the panel, strokes apply when the grid was focused and the plate is [`Plate::Ssh`](enum.Plate.md#member-ssh). A stored paste opens the paste dialog. A pane-focus request focuses that pane. A console-return id focuses that pane when it is not the current pane, arms console focus, and asks for another paint.

The click flags run as one chain, so only the first set flag acts. A model click calls `ring_model`. Connect, disconnect, reconnect, clear terminal, send file, and close pane call the matching methods. Import starts a console import. Stop calls `try_stop`. Delete calls `try_delete`. Clear calls `try_clear`. New appends a card. It copies the previous card's Grok entry when one exists. It keeps the think record and the expanded flag. It focuses the new card and asks the deck to land on it. Pipe calls `try_pipe` and clears the run flag first. Run calls `try_run`. A pending save calls `write_door` only when no earlier flag in the chain was set. The method then paints the shell dialogs and calls `relay_ssh_moods`.

### relay_ssh_moods

<a id="method-relay_ssh_moods"></a>

```rust
fn relay_ssh_moods(&mut self)
```

Hand the mood to every muxed pane of an armed user. A raw session is left alone.
Channel A does not paint. Another user's panes keep their own flag.

### seat_mood_canvas

<a id="method-seat_mood_canvas"></a>

```rust
fn seat_mood_canvas(&mut self)
```

The showing canvas. The pin map keys on this id. A missing id reads the head.

### land_mood_pin

<a id="method-land_mood_pin"></a>

```rust
fn land_mood_pin(&mut self, index: usize)
```

A live frame consumes a pending define. A refused frame leaves pending.

### try_run

<a id="method-try_run"></a>

```rust
fn try_run(&mut self)
```

The method `try_run` starts a run for the focused card, or for the whole group when the ask says so. `seat_mood_canvas` runs first. `ask_run_doc` returns the [`RunAsk`](enum.RunAsk.md) and any dropped mood notice. `seat_mood_slide` stores that notice.

[`RunAsk::Busy`](enum.RunAsk.md#member-busy) changes nothing. [`RunAsk::Excluded`](enum.RunAsk.md#member-excluded) sets the status to `Excluded.`. [`RunAsk::TooLarge`](enum.RunAsk.md#member-toolarge) sets the status to `too-large`. [`RunAsk::NoModel`](enum.RunAsk.md#member-nomodel) sets the status to `no-model`. [`RunAsk::BurnerDenied`](enum.RunAsk.md#member-burnerdenied) sets the status from `burner_fault` for the focused group. A slide on that fault replaces `slide`.

[`RunAsk::Send`](enum.RunAsk.md#member-send) returns when `live_glass_pane` is absent. Otherwise it clears the group remainder, sets `group_live` to 0, and clears the pipe remainder. `pipe_live` becomes false. `run_pane` becomes that pane. The frame is written with `wire_pane`. The mood pin is landed on the focus. `begin_run` marks the focused card.

[`RunAsk::Group`](enum.RunAsk.md#member-group) with an empty frame list sets the status to `Excluded.` and returns. A missing live pane returns. Fault text is cleared on members whose column is above 0 and whose mark is not excluded, and on the merge card in column 0. The group remainder is cleared. `group_live` becomes the group. The pipe remainder is cleared. Each member frame is written now, the mood pin is landed, and `begin_run` marks that card.

### try_stop

<a id="method-try_stop"></a>

```rust
fn try_stop(&mut self)
```

The method `try_stop` cancels the focused run and may send the next group frame. `halt_run` returns the cell to cancel. A missing halt sets the status to `Nothing is running.` and returns.

The method writes a cancel frame for that cell with `wire_owned`. `close_thought` clears the thinking strip. The status becomes `Stopped.`. The pipe remainder is cleared and `pipe_live` becomes false. A halt that does not follow returns here.

A follow halt sends the first frame still in `group_rest` when `wire_owned` accepts it. That frame is then removed. `begin_run` marks the card for that cell. An empty remainder, or a refused write, leaves the rest of the group as it was.

### try_pipe

<a id="method-try_pipe"></a>

```rust
fn try_pipe(&mut self)
```

The method `try_pipe` starts a chained run from the focused card. `seat_mood_canvas` runs first. `plan_chain_doc` returns the [`ChainAsk`](enum.ChainAsk.md) and any dropped mood notice. `seat_mood_slide` stores that notice.

[`ChainAsk::Busy`](enum.ChainAsk.md#member-busy) changes nothing. [`ChainAsk::Excluded`](enum.ChainAsk.md#member-excluded) sets the status to `Excluded.`. [`ChainAsk::TooLarge`](enum.ChainAsk.md#member-toolarge) sets the status to `too-large`. [`ChainAsk::NoModel`](enum.ChainAsk.md#member-nomodel) clears the pipe remainder, sets `pipe_live` false, and sets the status to `no-model`. [`ChainAsk::Pipe`](enum.ChainAsk.md#member-pipe) clears the pipe the same way and sets the status to `pipe`. [`ChainAsk::Quiet`](enum.ChainAsk.md#member-quiet) clears the pipe and leaves the status as it was.

[`ChainAsk::Send`](enum.ChainAsk.md#member-send) returns when the session has not answered. Otherwise it clears the group remainder, sets `group_live` to 0, and stores the rest of the chain. `pipe_live` becomes true. `run_pane` becomes the current pane id. The frame is written on the session. The mood pin is landed on the matching card. `begin_run` marks that card.

### try_clear

<a id="method-try_clear"></a>

```rust
fn try_clear(&mut self)
```

The method `try_clear` wipes the deck back to one card and cancels cards that are still running. `seat_mood_canvas` runs first. When the mood is not blocked and the canvas id is not empty, the mood pin for that canvas becomes `head`. `pending` becomes false. The mood file is stored.

The focused card's target and Ollama id are kept before the wipe. `drop_plate_docs` forgets the model records. The edit history is cleared. Prompt focus is cleared. Every running cell is collected. `clear_plate` replaces the deck. The kept target and Ollama id are written onto the focused card. The think record for that new cell is kept. The thinking strip is closed. The next paint focuses the prompt. `land_newest` becomes false. The group remainder, the pipe remainder, and `join_from` are cleared. `group_live` becomes 0. `pipe_live` becomes false.

When no card was running, `run_pane` becomes absent and the method returns. Otherwise each running cell gets a cancel frame through `wire_owned`. `run_pane` becomes absent. The status becomes `Stopped.`.

### try_delete

<a id="method-try_delete"></a>

```rust
fn try_delete(&mut self)
```

The method `try_delete` removes the planned cards and cancels any of them that are still running. `plan_delete` returns the cut. A missing plan returns without a change.

`pipe_hits_delete` says whether the live pipe must be dropped. `group_hits_delete` says the same for the live group. Running cells in the cut are collected. Each of those cells gets a cancel frame. The matching card stops running and loses its start time. The thinking strip is closed when any cancel was sent.

A dropped pipe clears `pipe_rest` and sets `pipe_live` false. A live pipe that was not dropped rewrites its indexes with `shift_index`. A dropped group clears `group_rest` and sets `group_live` to 0. `join_from` is shifted by the removed indexes when it is present.

When the mood is not blocked and the canvas id is not empty, `walked_pin` may move the pin. A changed pin is stored in the mood file. `apply_delete` removes the cards. Each removed cell is dropped from the edit history. Prompt focus is cleared. `forget_all` drops the Grok records. A changed expanded file is stored. An open card model dialog whose key was removed is closed. A group cut sets the status to `deleted group on Canvas`. Any other cut sets the status to `deleted turn on Canvas`.

### flush_divider

<a id="method-flush_divider"></a>

```rust
fn flush_divider(&mut self, ui: &egui::Ui)
```

The method `flush_divider` writes the inspector split after the save delay. The parameter `ui` supplies the context used to ask for another paint. A missing `divider_armed` instant returns without a write.

When `divider_span` is not ready, the method asks for another paint after `DIVIDER_SAVE_MS` and returns. That delay is 400 milliseconds. The same delayed paint is requested when the elapsed time is not yet due. A clock that cannot be read as milliseconds is treated as already due.

When the delay has passed, the method clears the armed instant. It saves the per-mille weight of `canvas_weight` through [`Door`](../door/struct.Door.md), which stores the window settings. A failed save, when `door_note` is empty, stores the text `door file could not be written`.

### store_wrap_columns

<a id="method-store_wrap_columns"></a>

```rust
fn store_wrap_columns(&mut self, columns: u32)
```

The method `store_wrap_columns` saves the inspector wrap width. The parameter `columns` is that width. [`Door`](../door/struct.Door.md) stores the window settings. A failed save, when `door_note` is empty, stores the text `door file could not be written`. A present note is left as it was.

### store_line_chars

<a id="method-store_line_chars"></a>

```rust
fn store_line_chars(&mut self, count: u32)
```

The method `store_line_chars` saves the line-character count. The parameter `count` is that count. [`Door`](../door/struct.Door.md) stores the window settings. A failed save, when `door_note` is empty, stores the text `door file could not be written`. A present note is left as it was.

### store_view_rows

<a id="method-store_view_rows"></a>

```rust
fn store_view_rows(&mut self, rows: u32)
```

The method `store_view_rows` saves the view row count. The parameter `rows` is that count. [`Door`](../door/struct.Door.md) stores the window settings. A failed save, when `door_note` is empty, stores the text `door file could not be written`. A present note is left as it was.

### store_product_tab

<a id="method-store_product_tab"></a>

```rust
fn store_product_tab(&mut self, word: TabWord)
```

The method `store_product_tab` saves which product tab is showing. The parameter `word` is a [`TabWord`](enum.TabWord.md). [`TabWord::Aetheris`](enum.TabWord.md#member-aetheris) is stored as true. Any other word is stored as false. [`Door`](../door/struct.Door.md) stores the window settings. A failed save, when `door_note` is empty, stores the text `door file could not be written`.

### store_pin_bar

<a id="method-store_pin_bar"></a>

```rust
fn store_pin_bar(&mut self, open: bool)
```

The method `store_pin_bar` saves whether the pin bar is open. The parameter `open` is that flag. [`Door`](../door/struct.Door.md) stores the window settings. A failed save, when `door_note` is empty, stores the text `door file could not be written`. A present note is left as it was.

### flush_wrap

<a id="method-flush_wrap"></a>

```rust
fn flush_wrap(&mut self, ui: &egui::Ui)
```

The method `flush_wrap` applies a typed inspector wrap width after the typing delay. The parameter `ui` supplies the context used to ask for another paint. A missing `wrap_armed` instant returns without a change.

The elapsed time uses `WRAP_TYPE_MS` when the clock cannot be read. That delay is 800 milliseconds. When `wrap_due` is false, the method asks for another paint after that delay and returns.

When the delay has passed, the armed instant is cleared. [`TypedWrap::Keep`](enum.TypedWrap.md#member-keep) puts `wrap_columns` back into the field. [`TypedWrap::Apply`](enum.TypedWrap.md#member-apply) puts the parsed value into the field. A value that differs from `wrap_columns` is stored with `store_wrap_columns`.

### flush_line

<a id="method-flush_line"></a>

```rust
fn flush_line(&mut self, ui: &egui::Ui)
```

The method `flush_line` applies a typed line-character count after the typing delay. The parameter `ui` supplies the context used to ask for another paint. A missing `line_armed` instant returns without a change.

The elapsed time uses `WRAP_TYPE_MS` when the clock cannot be read. That delay is 800 milliseconds. When `wrap_due` is false, the method asks for another paint after that delay and returns.

When the delay has passed, the armed instant is cleared. [`TypedWrap::Keep`](enum.TypedWrap.md#member-keep) puts `line_chars` back into the field. [`TypedWrap::Apply`](enum.TypedWrap.md#member-apply) puts the parsed value into the field. A value that differs from `line_chars` is stored with `store_line_chars`.

### flush_view

<a id="method-flush_view"></a>

```rust
fn flush_view(&mut self, ui: &egui::Ui)
```

The method `flush_view` applies a typed view-row count after the typing delay. The parameter `ui` supplies the context used to ask for another paint. A missing `view_armed` instant returns without a change.

The elapsed time uses `WRAP_TYPE_MS` when the clock cannot be read. That delay is 800 milliseconds. When `wrap_due` is false, the method asks for another paint after that delay and returns.

When the delay has passed, the armed instant is cleared. [`TypedWrap::Keep`](enum.TypedWrap.md#member-keep) puts `view_rows` back into the field. [`TypedWrap::Apply`](enum.TypedWrap.md#member-apply) puts the parsed value into the field. A value that differs from `view_rows` is stored with `store_view_rows`.

### paint_slide

<a id="method-paint_slide"></a>

```rust
fn paint_slide(&mut self, ui: &mut egui::Ui)
```

Body plus Close at the top of the Aetheris pane. It stays until Close.

### take_prompt_undo

<a id="method-take_prompt_undo"></a>

```rust
fn take_prompt_undo(&mut self, ui: &mut egui::Ui)
```

Ctrl-Z and Shift-Ctrl-Z on the prompt that held focus. The terminal does not see the chord.

### paint_empty

<a id="method-paint_empty"></a>

```rust
fn paint_empty(&mut self, ui: &mut egui::Ui)
```

The method `paint_empty` paints the Aetheris canvas, the split bar, and the inspector, then applies the clicks from that paint. The parameter `ui` is the egui surface. Under test, the method clears the pin, grip, tab, and north-layer slots before it paints.

Control, Shift, and G is the duplicate chord. Control, Shift, and F5 is the pipe chord. A hit consumes that key. `take_prompt_undo` runs, and `prompt_focus` is cleared for this paint. The method copies prompts, replies, faults, marks, locks, groups, columns, folds, concealed cells, burner flags, bar flags, expanded flags, and targets. `focus_prompt`, `seat_focus`, and `land_newest` are taken and then cleared. Connected user names come from `live_seats`.

The column width comes from `canvas_weight`, the gap, and `split_reserved`. The canvas child uses the monospace face at `term_font`. The north layer holds the `Canvas` tab, the slide from `paint_slide`, and the pin row when `pin_bar_open` is true. A fold on that row asks to close the bar and keeps the pin flag and the needle. A closed bar clears `pin_enter_seen`. `PinView::frame` builds the pin filter from the needle, the burner flags, the prompts, and the replies.

The footer is `paint_bb_bars`. Its hit can request run, stop, new, clear, delete, collapse, expand, search, clear-search, export, canvas import, pull, terminal, mood flip, mood open, hide, or show. A row minus or plus steps `view_rows`, cancels a pending typed save, and stores the new count when it changed. A typed row marks the field dirty. A user pick is kept for later.

The deck is the hole between the header and the button bar. An open thinking strip shortens only the south edge. A non-empty `scope_text` is drawn first. `gather_slots` keeps deck order and treats a group as one block. `shown_slots` drops concealed cards, and also folded cards when `hide_collapsed` is true. A clear-search empties both needles. A search copies the tool needle into the find needle. `find_slots` filters by that needle, and `pin_slots` applies the pin view.

Each card paints a header. A merge card shows `Merge` as a selectable label. Any other card shows `turn_label`. The letters `I`, `E`, `P`, and `O` set [`Mark::Included`](enum.Mark.md#member-included), [`Mark::Excluded`](enum.Mark.md#member-excluded), [`Mark::Input`](enum.Mark.md#member-input), and [`Mark::Output`](enum.Mark.md#member-output). A shown lock uses `L` or `l` and calls `press_lock`. The fold button records that index. The `burner` and `Expanded View` checkboxes edit those flags. `View` is enabled when `view_body` finds text. `Copy` copies `copy_reply` when that text is present. A card that is not a merge shows the model button. A merge shows `Add Cell`. The bar toggle flips `bar_open`. A folded card may show a truncated brief.

An open bar shows `Group` and `Join` when the group is 0, or `Ungroup` otherwise, plus the target field. A change calls `note_target_change`. A lost focus calls `note_target_leave`. A click keeps the caret. An unfolded card that is not a merge paints the prompt window. Focus, wheel ownership, and a click are recorded. An unfolded reply uses `paint_bounded_reply` when `group_pane_height` returns a height, and `paint_open_reply` otherwise. A non-empty fault is drawn under an unfolded card. A secondary click on the reply stores the status `import refused on output`. The context menu can import, transfer, or duplicate. A hover caption comes from `frame_caption`. The edge color comes from `pinned_edge`.

A group block paints members in a horizontal scroller and then the merge card. `land_newest` scrolls to the bottom with no animation. A prompt or reply that owns the wheel clears the smooth scroll delta. The header plate covers the north band. An open thinking strip is `paint_think_strip`, and a clear, a close, or a kept height is recorded.

The split bar can be dragged. The canvas arrow and the inspector arrow call `arrow_share`. A drag calls `drag_weight`. Hover text says which side will be hidden. When the pin bar is folded, `P` is painted on the bar. A click on that mark asks to open the bar. When no view is open, hovering that mark shows `PIN_SHOW_TIP`.

The method posts canvas heat after `seat_mood_canvas`. A pin commit that survives `pin_commit_after_mark` calls `commit_pin`. A mark press does not by itself commit the pin. `token_used`, `token_chars`, and `token_blocks` are copied from the heat fields after that post. An inspector wider than zero shows the fact line, the `Inspector` label, the soft-wrap checkbox, the wrap stepper, and the line stepper. The body is `inspector_shown`. A mood line is drawn when `mood_sends` is true. The `T` button asks to open the token dialog.

After the paint, think-strip close and height are stored when they changed. A clear wipes the think log and stores the think file. While a view is open, the method skips the field stores and the toolbar actions below. While it is closed, soft wrap, wrap columns, line characters, and view rows are written back. A stepped value is stored at once. A typed field arms the matching delay. A changed pin-bar flag is stored. A changed split arms `divider_armed`. Changed prompts are noted in the edit history unless an apply is already running. Marks, locks, burner flags, bar flags, and targets are written back onto the cards.

`Group` may insert a merge and move `join_from`. `Join`, `Ungroup`, and `Add Cell` then use the shifted index. When none of those four ran, focus becomes `pane_focus`. A mood-open hit calls `try_open_mood`. `join_from` follows a member focus and clears when the armed card is no longer a member. Run sets `run_clicked`. Enter with Control or Command also sets it when no card is running and the view is closed. Duplicate, from the chord or the menu, calls `seat_duplicate`, copies Grok records, and keeps think and expanded state on the new cells. Transfer calls `ask_transfer_from_canvas`. Import calls `begin_import` and may set `door_note` or a hard slide. Pipe, new, clear, and delete set those click flags and clear the flags that would otherwise win the later chain. Collapse folds every card, releases the concealed set, and stores hide as false. Expand unfolds every card and releases the concealed set. Hide stores the concealed collapsed cells. Show clears hide and releases the deck. A single fold toggles that card and releases only that cell from the concealed set.

Search uses `search_vault` when a vault is open, and `search_turn` otherwise. A hit moves focus. A vault error sets the status to `search failed`. Export, canvas import, pull, and terminal run their begin helpers. Pull of `PullAsk::Empty` sets `no terminal line`. `PullAsk::Seated` focuses the new card, asks for prompt focus, asks to land on it, and sets `pulled excluded`. Terminal quiet does nothing. A word becomes the status. A write sends `terminal_bytes` when a session is present and sets `sent to terminal`, or sets `terminal write failed: ` plus the error. Stop sets `stop_clicked` and clears `run_clicked`. A view click calls `open_view` with the content rectangle.

Expanded checkboxes are written even while a view is open. A changed flag updates the expanded file. A user pick focuses that user's pane and may call `seat_canvas_user`. A model click calls `open_model`. The method then paints the view and the mood glass. An open-token hit sets `token_open` and clears `token_pick`. The heat counts are restored and `paint_token` runs. A primary press stores `scroll_seat` for the text pane under the pointer, unless the press is on the scrollbar. `grid_focus` becomes false.

### saved_view_rect

<a id="method-saved_view_rect"></a>

```rust
fn saved_view_rect(&self, screen: ViewRect) -> Option<ViewRect>
```

The method `saved_view_rect` reads a stored reply-window rectangle for the current user. The parameter `screen` is the rectangle the stored bounds must fit. A missing session returns `None`.

A missing home directory returns `None`. A missing path from `view_bounds_path` returns `None`. A file that cannot be read returns `None`. Otherwise `seated_bounds` parses the file against that one screen rectangle.

### open_view

<a id="method-open_view"></a>

```rust
fn open_view(&mut self, index: usize, screen: ViewRect)
```

The method `open_view` opens the reply window for one card. The parameter `index` is that card's place. The parameter `screen` is the content rectangle used to fit a saved window. An open view returns without a change.

A missing card, or a reply that `view_body` rejects, returns without a change. `view_generation` increases by 1 and stops at the maximum instead of wrapping. The body is the accepted reply text. Find starts closed. `saved_view_rect` may supply a saved rectangle. `place` is true only when that rectangle is present.

### store_closed_view

<a id="method-store_closed_view"></a>

```rust
fn store_closed_view(&self, last: Option<ViewRect>)
```

The method `store_closed_view` writes the reply window's last rectangle. The parameter `last` is that rectangle. A missing rectangle returns. A missing session returns. A missing home directory returns. A missing path from `view_bounds_path` returns.

Otherwise `store_view_bounds` writes the rectangle to that path. The method does not report a write error.

### paint_view

<a id="method-paint_view"></a>

```rust
fn paint_view(&mut self, ctx: &egui::Context)
```

The reply window. It is the modal above the canvas.
The Canvas tab and P paint under it. A click on the canvas stops here.
Tests do not call this, so the OS window stays closed.

### paint_vt_strip

<a id="method-paint_vt_strip"></a>

```rust
fn paint_vt_strip(&mut self, ui: &mut egui::Ui)
```

The method `paint_vt_strip` paints the terminal thinking strip and stores a close or a height change. The parameter `ui` is the egui surface. The strip uses the current pane id as its salt. `seat_vt_chrome` builds the chrome from `vt_open`, `vt_slide`, `vt_folded`, and `vt_height`.

`paint_think_strip` draws `vt_think` blocks with the terminal flag true. A returned strip can clear the log, mark the strip closed, or keep a height. `keep_vt_session` writes the open, slide, folded, and height fields back.

A close is noted on `vt_yaml`. A kept height is noted there too, and a real change also sets `vt_chrome.height`. A dirty yaml file is stored with `store_vt_yaml`. A clear wipes `vt_think` and `vt_chrome` through `clear_think`.

### paint_shell

<a id="method-paint_shell"></a>

```rust
fn paint_shell(&mut self, ui: &mut egui::Ui)
```

The method `paint_shell` paints the terminal plate. The parameter `ui` is the egui surface. One pane, or none, paints `paint_vt_strip` and then `paint_grid`, and returns.

More than one pane lays the tree out in the available rectangle with `PANE_GAP`. Each split bar is filled and can be dragged. A horizontal bar uses the horizontal resize cursor. A vertical bar uses the vertical resize cursor. A drag stores a ratio from the pointer along that bar's span. The ratio is applied with `set_split_ratio` after the bars are painted.

The focused pane is skipped in the parked loop and painted later in its own child. Every other leaf is `paint_parked`. The focused child paints the thinking strip and the grid, then strokes `CURSOR` inside the focus rectangle. The first parked hit that asks for focus, model, or import stores `focus_pane_request`. A model hit also sets `pane_model_clicked`. An import hit also sets `import_clicked`.

### paint_parked

<a id="method-paint_parked"></a>

```rust
fn paint_parked(&mut self, ui: &mut egui::Ui, id: u64, rect: egui::Rect) -> Option<ParkedHit>
```

The method `paint_parked` paints one unfocused terminal pane and returns the click it saw. The parameter `ui` is the egui surface. The parameter `id` is the pane id. The parameter `rect` is that pane's rectangle. An unknown id returns `None`.

The face is monospace at `term_font`. A glyph width at or below one half becomes 8. A row height at or below one half becomes 16. The thinking strip uses that pane's own chrome and blocks. A close or a kept height is written to the shared `vt_yaml`, and a dirty file is stored. A clear wipes that pane's think log. The grid is resized to the leftover columns and rows, and a live session is resized with it.

The scroll area sticks to the bottom and paints each visible history cell. A zero-width cell is skipped. `paint_colors` supplies the ink with selection false. A non-default background, or a reverse cell, fills the cell. Non-empty text is drawn. The cursor cell is stroked with `CURSOR`. `at_tail` becomes the result of `view_on_tail`.

A click sets focus. The context menu uses `console_menu_gates` and `console_menu_pick`. A picked label forces focus and runs `apply_console_label`. [`MenuFollow::Copy`](enum.MenuFollow.md#member-copy) copies text. [`MenuFollow::Url`](enum.MenuFollow.md#member-url) opens the address. [`MenuFollow::Paste`](enum.MenuFollow.md#member-paste) writes a normalized paste when that pane has a session, and shows `NOT_CONNECTED` under the title `Paste` when it does not. [`MenuFollow::Import`](enum.MenuFollow.md#member-import) and [`MenuFollow::Model`](enum.MenuFollow.md#member-model) are returned on the [`ParkedHit`](struct.ParkedHit.md). [`MenuFollow::Export`](enum.MenuFollow.md#member-export) calls `ask_session_export`. [`MenuFollow::Transfer`](enum.MenuFollow.md#member-transfer) calls `ask_transfer_from_console`. [`MenuFollow::Quiet`](enum.MenuFollow.md#member-quiet) changes nothing more. The hit's focus flag is true when the pane was clicked or when model or import was chosen.

### paint_grid

<a id="method-paint_grid"></a>

```rust
fn paint_grid(&mut self, ui: &mut egui::Ui)
```

The method `paint_grid` paints the focused terminal grid, the selection, and the console menu. The parameter `ui` is the egui surface. A primary press clears `scroll_seat`.

The face is monospace at `term_font`. A glyph width at or below one half becomes 8. A row height at or below one half becomes 16. Columns and rows come from the available size minus 16 points across. A size change resizes the grid, calls `note_grid`, and resizes a live session.

A non-zero `console_scroll_lines` turns stick-to-bottom off and sets a forced offset from `console_scroll_target`. The scroll source allows the scrollbar and the wheel, and never drag-scrolls. `jump_tail` scrolls the last row to the bottom.

The grid area accepts click and drag. The context menu uses `console_menu_gates` and `console_menu_pick`. `console_focus.take` may request focus on a click or a drag start. A focused response installs `console_event_filter` as the focus lock. A drag start sets both `anchor` and `current` to `cell_at`. A later drag updates `current` only. A stopped drag copies `selected_text` when that text is not empty.

Each visible history cell is painted. A zero-width cell is skipped. `in_selection` marks the highlight. `paint_colors` supplies the ink. A selected cell, a non-default background, or a reverse cell is filled. Non-empty text is drawn. The cursor cell is stroked with `CURSOR`. `at_tail` becomes `view_on_tail`. A jump clears `jump_tail`.

A menu label runs `apply_console_label`. [`MenuFollow::Copy`](enum.MenuFollow.md#member-copy) copies text. [`MenuFollow::Url`](enum.MenuFollow.md#member-url) opens the address. [`MenuFollow::Paste`](enum.MenuFollow.md#member-paste) normalizes the clipboard and calls `insert_paste` with the title `Paste` when the body is not empty. [`MenuFollow::Import`](enum.MenuFollow.md#member-import) sets `import_clicked`. [`MenuFollow::Export`](enum.MenuFollow.md#member-export) calls `ask_session_export` for this pane. [`MenuFollow::Transfer`](enum.MenuFollow.md#member-transfer) calls `ask_transfer_from_console` for this pane. [`MenuFollow::Model`](enum.MenuFollow.md#member-model) calls `ring_model`. [`MenuFollow::Quiet`](enum.MenuFollow.md#member-quiet) changes nothing more.

### paint_frame

<a id="method-paint_frame"></a>

```rust
fn paint_frame(&mut self)
```

The method `paint_frame` runs one egui frame and paints it into the window. A missing window returns. A missing `state` returns.

`take_egui_input` supplies the raw input. `run_ui` calls `ui`. `handle_platform_output` consumes the platform output when `state` is still present. The pixels-per-point value is the frame value when it is above zero, and otherwise the window scale factor. `tessellate` clips the shapes. The clear color is [`PLATE`](../chrome.md#constant-plate) with alpha 1.

A present painter calls `paint_and_update_textures` on the root viewport. The vertical-sync result is ignored. A requested repaint calls `request_redraw`. `store_vault` runs at the end of every painted frame.

### close

<a id="method-close"></a>

```rust
fn close(&mut self, event_loop: &ActiveEventLoop)
```

The method `close` stores the window and shuts the sessions down. The parameter `event_loop` is the loop that can exit. A present window is measured with `note_frame`. `store_frame` and `store_vault` run. `close_vault` runs. `close_product_tab` may store a [`TabWord`](enum.TabWord.md). `restore_aetheris` becomes false. `write_door` runs.

Each parked pane session is shut down with `JOIN_BOUND`, which is two seconds. The focused session is shut down the same way. Any shutdown error sets the status to `Disconnected: read thread did not stop`, sets `exit_after_paint`, asks for a redraw, and returns. A clean shutdown calls `exit` on the event loop.

## impl ApplicationHandler for App

```rust
impl ApplicationHandler for App
```

### resumed

<a id="method-resumed"></a>

```rust
fn resumed(&mut self, event_loop: &ActiveEventLoop)
```

The method `resumed` opens the client window after the event loop resumes. The parameter `event_loop` is passed to `open_window`. This method does nothing else.

### window_event

<a id="method-window_event"></a>

```rust
fn window_event(&mut self, event_loop: &ActiveEventLoop, id: WindowId, event: WindowEvent)
```

The method `window_event` handles one window event for this client. The parameter `event_loop` can exit. The parameter `id` is the window the event names. The parameter `event` is that event. A missing window returns. An id that is not this window's id returns.

A resize is clamped with `clamp_resize` and then noted with `note_frame`. A move is noted the same way. `on_window_event` may consume the event. A consumed event that asks for a repaint calls `request_redraw`.

`WindowEvent::CloseRequested` sets `exit_ask` and asks for a redraw. `WindowEvent::RedrawRequested` calls `paint_frame`. A set `exit_confirmed` is cleared and `close` runs, and the method returns. A set `exit_after_paint` exits the loop and returns. `drain_link` then runs. A second `exit_after_paint` exits the loop. `wake.show` or `wake.more` asks for another redraw. Every other event is ignored.

### about_to_wait

<a id="method-about_to_wait"></a>

```rust
fn about_to_wait(&mut self, event_loop: &ActiveEventLoop)
```

The method `about_to_wait` chooses how long the event loop sleeps and may ask for another paint. The parameter `event_loop` receives the control flow. `drain_link` returns the wake.

`commit_wait` uses `any_session` and `wake.more`. A returned millisecond count sets `ControlFlow::wait_duration`. `None` sets `ControlFlow::Wait`. `wake.show` or `wake.more` calls `request_redraw` when a window is present.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
