# The merge cell

The merge cell is a group of cards that share one joined reply. It depends on a session from [the desk](../the-desk/README.md). The marks and the inspector are [The canvas](../the-canvas/README.md). How Run sends one card is [The turn](../the-turn/README.md).

This book begins with an empty canvas. The one Group screen-shot in The canvas stays in that book.

One member is Ollama. Ollama is the local model host. The other member is the Grok API id grok-4.3. The id is not grok-4.2. The id is not grok-4.20. The pool is not the model these screen-shots use. The pool is a separate chat. A model is not selected through the pool. The Model button on the card bar opens Model Config. The text pane on the card does not open that dialog. Family is the group of models this card uses. Family Ollama uses the row the host list shows. In these screen-shots that row is deepseek-r1:1.5b. Family Grok uses the row grok-4.3. The execution-target field shows the target this card would send. That field is not the selection. It does not open Model Config.

Group starts a join on the focused card. That card becomes member 1. A merge card is added under it. A second ordinary card is the other input. Run sends each member on its own. The merge card is not sent. Join adds the card you click after a member has focus. The new member keeps its own model. When every member has completed, the merge card holds the joined replies, each under a JOIN line. A failed or cancelled member fails the merge and leaves that reply blank. The inspector lists the member prompts as one user line and the merge reply as the assistant line. Ungroup removes the card from the group. When the last member leaves, the merge card returns to an ordinary turn. Add Cell is on the merge card only. The new member is empty. The merge card still holds the joined replies.

| |
| --- |
