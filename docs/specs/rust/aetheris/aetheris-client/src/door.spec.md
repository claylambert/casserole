# aetheris-client::door

Contract file. Two sides match this component.

**Depends:** [rust/aetheris/aetheris-client/aetheris-client.spec.md](../aetheris-client.spec.md)

## The default font

When the Rust client has no usable font size, it uses 14.

A missing key reads 14. An unreadable key reads 14. A memory door reads 14. A size that was stored is left as written. The allowed range stays 8 through 36. Plus and minus stay. This rule does not change the Swing default, which stays 8. The letters stay I, E, P, and O. The pin letter stays P. The divider stays 11 pixels. The arrows stay. The tooltip wait stays. The hover emerald stays #00FF66.

The Rust constant is `FONT_DEFAULT`. The Rust client changes. The Swing client stays.

**Truth.** The Rust missing, unreadable, or memory-door font size is 14. A stored size wins. The clamp remains 8 through 36. The Swing default remains 8 under this rule.

**Reason.** An absent key was filling a size the Rust window no longer uses as its start, so a first open came up smaller than the face the client is built around.

**Avoidances.** Do not overwrite a stored size with 14. Do not move the Swing default in this rule. Do not widen or shrink the 8 through 36 range. Do not change the mark letters, the pin, the 11 pixel divider, the arrows, the tooltip wait, or #00FF66. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-4475
