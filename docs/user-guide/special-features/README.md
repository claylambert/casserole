# Special Features

This book describes four controls that sit beside an ordinary turn. Mood is a saved line the conversation can carry in front of the prompt. The pin filter marks cards from the words you type. Search keeps the cards whose text holds those words. The thinking pane is the log of the model's reasoning. The model on the mood card and on the thinking card is the Grok API id `grok-4.3`. The pool is not the model these screen-shots use.

The second bar starts closed. The `v` at the right of the first bar opens it. That bar holds the search field and the Mood controls. The pin bar is the `P` above the canvas. That `P` is the pin, and it is not the prompt-only mark on a card. The thinking pane opens from Tools. Tools is the menu on the desk.

The screen-shots are in the [images](images/README.md) folder. Each screen-shot is the window when the window fills the screen. The Font size spinner is set to 20. The picture on this page is 30 percent larger than the doubled copy. The doubled copy is the original screen-shot with the width doubled and the height doubled. The doubled copy and the original screen-shot are in the [archaeology](images/archaeology/README.md) folder.

This book has thirty-nine screen-shots. If a control is not visible, this book does not show a picture of it. This book does not invent a name for a control it does not show.

## Mood

Mood is a saved line the conversation can carry in front of the prompt. The model that defines the mood is the model of the card you have selected. In these screen-shots that card is `grok-4.3`. Approve keeps the line. The switch turns that line on and off. Clear mood removes the saved line. Debug Mood shows the shape of that line in the inspector. The shape is the sentence the inspector uses to describe that line. The prompt on the card stays the words that were typed.

| |
| --- |
| <h2>Second bar</h2><br><br>![The v opens the second bar.](images/mood-01-open_130.png)<br><br>The v opens the second bar. It sits at the right of the first bar. The second bar holds Search and Mood. |
| <h2>Mood</h2><br><br>![Mood is on the second bar.](images/mood-02-button_130.png)<br><br>Mood is on the second bar. The round button beside it is grey. Grey means no mood string is saved yet. |
| <h2>grok-4.3</h2><br><br>![The model row is grok-4.3.](images/mood-03-model_130.png)<br><br>The model row is grok-4.3. Model on the card opened Model Config. Family is the group of models this card uses. The group in this screen-shot is Grok. Effort is high. Send effort is checked. Stream is false. Temperature is 1. Max tokens is 128000. Burner cloud is unchecked. Thinking is unchecked. |
| <h2>Mood dialog</h2><br><br>![The dialog title is Mood · grok-4.3.](images/mood-04-dialog_130.png)<br><br>The dialog title is Mood · grok-4.3. The footer is SSH Mood Active, Clear mood, Approve, and Cancel. The model that defines the mood is the model of the card you have selected. |
| <h2>The mood line</h2><br><br>![The draft reads short, spare, dry.](images/mood-05-line_130.png)<br><br>The draft reads short, spare, dry. The prompt was Set a short spare dry mood. The yellow line in the transcript is mood: short, spare, dry. The model that defines the mood is the model of the card you have selected. |
| <h2>SSH Mood Active</h2><br><br>![SSH Mood Active is checked.](images/mood-06-ssh-box_130.png)<br><br>SSH Mood Active is checked. Approve with this box checked lets the SSH status carry the mood while the switch is on. The model that defines the mood is the model of the card you have selected. |
| <h2>Switch on</h2><br><br>![The round button is green.](images/mood-07-switch_130.png)<br><br>The round button is green. Approve saved the line and turned the switch on. The inspector shows the yellow line short, spare, dry. |
| <h2>SSH mood active</h2><br><br>![The SSH status reads mood active.](images/mood-08-ssh_130.png)<br><br>The SSH status reads mood active. The engine on that line is grok/grok-4.3. The terminal screen is not written. |
| <h2>Inspector</h2><br><br>![The inspector shows the yellow mood line.](images/mood-09-inspector_130.png)<br><br>The inspector shows the yellow mood line. The line is short, spare, dry. It sits above the working set. The switch is still green. |
| <h2>Switch off</h2><br><br>![The round button is red.](images/mood-10-off_130.png)<br><br>The round button is red. The saved line stays. The inspector reads mood: off short, spare, dry. A send does not carry the mood while the switch is off. |
| <h2>SSH status off</h2><br><br>![The SSH status no longer reads mood active.](images/mood-11-ssh-quiet_130.png)<br><br>The SSH status no longer reads mood active. The engine stays grok/grok-4.3. The terminal screen is unchanged. The SSH box can stay checked. The switch is what turns the status phrase off. |
| <h2>Debug Mood</h2><br><br>![Debug Mood is checked.](images/mood-12-debug_130.png)<br><br>Debug Mood is checked. File, then Settings, opens this dialog. The label says the switches are saved for the next open. |
| <h2>The shape</h2><br><br>![The inspector lists the mood, the shape, and the user line.](images/mood-13-triplet_130.png)<br><br>The inspector lists the mood, the shape, and the user line. The mood is short, spare, dry. The shape is I'm opening a window with this atmosphere… would you like to step into it with me? Shape your replies around this mood — let it breathe through the words. The user line is Say ridge once. |
| <h2>The card</h2><br><br>![The card still reads Say ridge once.](images/mood-14-cell_130.png)<br><br>The card still reads Say ridge once. The mood is not typed into the prompt. The inspector keeps the yellow line and the shape. |
| <h2>Clear mood</h2><br><br>![Clear mood asks Clear the Mood Setting?](images/mood-15-clear_130.png)<br><br>Clear mood asks Clear the Mood Setting? Yes removes the saved line. No leaves it. The switch is still green in this screen-shot. The model that defines the mood is the model of the card you have selected. |
| <h2>Cleared</h2><br><br>![The round button is grey again.](images/mood-16-cleared_130.png)<br><br>The round button is grey again. Yes cleared the line, and Cancel closed the dialog. The inspector reads mood: (none). The card still reads Say ridge once. |

## The pin filter

`P` above the canvas opens the pin bar. That `P` is the pin. It is not the Input only mark on a card. The bar is a checkbox named pin, a word field, a red x, OK, and a v that folds the bar. OK writes Included or Excluded on each card whose text can be read, using the words in the field. A card that matches stays Included. A card that does not match becomes Excluded. An Excluded card is not sent.

| |
| --- |
| <h2>Pin</h2><br><br>![P opens the pin bar.](images/pin-01-mark_130.png)<br><br>P opens the pin bar. The bar is closed in this screen-shot. Three cards are on the canvas. |
| <h2>The bar</h2><br><br>![The pin checkbox is unchecked.](images/pin-02-bar_130.png)<br><br>The pin checkbox is unchecked. The word field, the red x, and OK stay disabled until the box is checked. |
| <h2>Pin words</h2><br><br>![The field holds lantern.](images/pin-03-words_130.png)<br><br>The field holds lantern. The box is checked. OK has not been pressed, so the marks have not moved. The words a, the, and, of, and to are dropped. A match needs every remaining word in the prompt or the reply. |
| <h2>Match</h2><br><br>![The lantern card is the match.](images/pin-04-match_130.png)<br><br>The lantern card is the match. OK applied lantern. The card reads lantern watch on the ridge. |
| <h2>Miss</h2><br><br>![The quartz card misses lantern.](images/pin-05-miss_130.png)<br><br>The quartz card misses lantern. It reads quartz shelf in the dark. A card that does not match becomes Excluded. |
| <h2>Clear the words</h2><br><br>![The red x emptied the field.](images/pin-06-empty_130.png)<br><br>The red x emptied the field. The checkbox stays on. OK has not been pressed on the empty field. An OK on an empty field excludes every card whose text can be read. |
| <h2>Excluded</h2><br><br>![Harbor is Excluded.](images/pin-07-excluded_130.png)<br><br>Harbor is Excluded. The pin word is still lantern. Harbor bell at dawn does not hold that word. E is the mark. An Excluded card is not sent. |
| <h2>Included</h2><br><br>![Harbor is Included.](images/pin-08-promote_130.png)<br><br>Harbor is Included. OK applied harbor. The lantern card and the quartz card are Excluded. I is the mark on the harbor card. |
| <h2>Pin off</h2><br><br>![The pin checkbox is unchecked and the marks stay.](images/pin-09-off_130.png)<br><br>The pin checkbox is unchecked and the marks stay. The field still shows harbor, and the field is disabled. Clearing the box does not restore the letters OK wrote. |
| <h2>Fold</h2><br><br>![The v folds the pin bar.](images/pin-10-fold_130.png)<br><br>The v folds the pin bar. The checkbox is on again. The field holds harbor. |
| <h2>P on</h2><br><br>![P is green while the pin stays on.](images/pin-11-green_130.png)<br><br>P is green while the pin stays on. The bar is folded. Harbor stays Included. The lantern card and the quartz card stay Excluded. |

## Search

Search is on the second bar, at the left. The field is the text to find on this canvas. Search keeps the cards whose text contains those words. The red x clears the field and brings the hidden cards back. When the words match, the canvas keeps the matching cards. The status line counts them. When nothing matches, the status line reads no hits. The pin marks are a different control and stay as they were.

| |
| --- |
| <h2>Search</h2><br><br>![Search is the button beside the field.](images/search-01-field_130.png)<br><br>Search is the button beside the field. The field holds lantern. The red x clears that field. |
| <h2>One hit</h2><br><br>![The status reads search 1.](images/search-02-hit_130.png)<br><br>The status reads search 1. The lantern card is the card on the canvas. It reads lantern watch on the ridge. |
| <h2>No hits</h2><br><br>![The status reads no hits.](images/search-03-miss_130.png)<br><br>The status reads no hits. The field holds zzzz-no-such. |
| <h2>Search cleared</h2><br><br>![The status reads search cleared.](images/search-04-clear_130.png)<br><br>The status reads search cleared. The three cards are back. The pin marks are unchanged. Harbor is Included. The lantern card and the quartz card are Excluded. |

## The thinking pane

Tools, then Show Thinking, opens the thinking pane for the selected tab. This tab is Aetheris, which is the canvas. The pane has a fold mark. It has a clear mark. It has Close. The top edge sets the height. A thought arrives when three things are true. The card is Included. Thinking is checked. Stream is true. The banner is the model name. It is drawn between characters that form a box.

| |
| --- |
| <h2>Show Thinking</h2><br><br>![Show Thinking is the Tools item.](images/think-01-menu_130.png)<br><br>Show Thinking is the Tools item. It opens the thinking pane for this tab. |
| <h2>The pane</h2><br><br>![The thinking pane is open.](images/think-02-pane_130.png)<br><br>The thinking pane is open. It sits above the first bar. v folds the text. C clears the log. Close hides the pane. |
| <h2>Folded</h2><br><br>![The fold mark reads ^.](images/think-03-fold_130.png)<br><br>The fold mark reads ^. The text row is hidden. C and Close stay on the folded row. A click on ^ opens the text again. |
| <h2>The height</h2><br><br>![The top edge sets the height.](images/think-04-drag_130.png)<br><br>The top edge sets the height. The pane in this screen-shot is the taller one. Drag that edge up to grow the text row. |
| <h2>Thinking</h2><br><br>![Thinking is checked and Stream is true.](images/think-05-thinking_130.png)<br><br>Thinking is checked and Stream is true. Model Config is still on grok-4.3. Family is Grok. Effort stays high. Send effort stays checked. Temperature is 1. Max tokens is 128000. Stream is what sends the reasoning into this pane. |
| <h2>The banner</h2><br><br>![The pane reads grok-4.3 and the thought.](images/think-06-banner_130.png)<br><br>The pane reads grok-4.3 and the thought. The banner is the model word. The thought is I need to name one ridge. The included card holds Name one ridge. The status says running. |
| <h2>Clear</h2><br><br>![C cleared the thinking log.](images/think-07-clear_130.png)<br><br>C cleared the thinking log. The pane is empty. The status reads Stopped. The card still holds Name one ridge. |
| <h2>Close</h2><br><br>![Close is the mark on the thinking pane.](images/think-08-close_130.png)<br><br>Close is the mark on the thinking pane. It hides the pane. The picture shows Close before you click it. |
