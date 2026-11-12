# org.casserole.app.ui.ssh

Component file. It carries how this package is built.

**Depends:** [java/org/aetheris/config/config](../../../../aetheris/config/config.spec.md), [java/org/aetheris/swing/swing](../../../../aetheris/swing/swing.spec.md), [java/org/aetheris/wire/wire](../../../../aetheris/wire/wire.spec.md), [java/org/casserole/mood/mood](../../../mood/mood.spec.md), [java/org/roux/token/token](../../../../roux/token/token.spec.md)

## Product tab

The door keeps one word, `ui.product.tab`. The words are `ssh` and `aetheris`. A missing word opens on SSH. A saved Aetheris word waits for Connect, then takes the plate and lands on the newest card. A close stores the plate that is showing.

**semantic-key:** PK-4442 PK-4443 PK-4444 PK-4445 PK-4446

## Ctrl-Shift-C copies the highlight and writes nothing

Ctrl-Shift-C copies the console highlight to the clipboard and the console writes nothing on that chord. Ctrl-C stays the interrupt, byte 0x03.

**semantic-key:** PK-4437
