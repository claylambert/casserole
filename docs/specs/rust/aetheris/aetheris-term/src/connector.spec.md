# rust.aetheris.aetheris-term.src.connector

## 18. Terminal component

| Piece | Owner |
|---|---|
| SSH handshake, login, channel, PTY request | `russh` |
| Byte parser | Open pick. `vte` or `alacritty_terminal` |
| Widget, grid paint, keys, settings | In-house library, JediTerm-shaped |
| Connector trait | In-house library. Read, write, resize |
| Init-or-raw, Scope T, Scope A listener | This client, the connector it supplies |
| Canvas, Turso, command verbs | Later sentences. Section 16 and section 17 still hold |
**semantic-key:** PK-621

## 5. Package 1. Terminal library

**Connector trait.** `read`, `write`, `resize`. The core does not call it and does not start a runtime.
**semantic-key:** PK-622

## 1. The seam

| Piece | Owner |
|---|---|
| SSH handshake, login, channel, PTY request | `russh` |
| Byte parser | `vte`. The grid stays in this library. `alacritty_terminal` stays the contingency |
| Widget, grid paint, keys, settings | This library, JediTerm-shaped |
| Connector trait | This library. Read, write, resize |
| Init-or-raw, Scope T, Scope A listener | The Aetheris client, in the connector it supplies |
| Canvas, Turso, host command verbs | The Aetheris client serial |
**semantic-key:** PK-623

## 3. Connector

This library's connector trait is read, write, and resize. One implementation uses `russh` and requests a VT100 PTY. Another project can supply its own connector.
**semantic-key:** PK-624

## 10. Connector and the host loop

The trait stays read, write, and resize. The core does not call the connector. The host loops.
**semantic-key:** PK-625
