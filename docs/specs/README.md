# Spine map

The spine is the requirements for the Casserole Server, the Java Aetheris Client, and the Rust Aetheris Client.

This page is the map of that tree.

A requirement sits on the package page of the code it binds.

A requirement that names no single package sits in the common corpus.

The code in that package is the other side of the requirement.

## Where to start

The common corpus is the entry when a requirement names no single package.

Open [Common](common.spec.md) to read those nine pages.

The Java Aetheris Client starts at [org.aetheris](java/org/aetheris/aetheris.spec.md).

The Casserole Server starts at [org.casserole](java/org/casserole/casserole.spec.md).

Roux is the memory package the Casserole Server uses.

Roux starts at [org.roux](java/org/roux/roux.spec.md).

The Rust Aetheris Client starts at [aetheris-client](rust/aetheris/aetheris-client/aetheris-client.spec.md).

[aetheris-term](rust/aetheris/aetheris-term/aetheris-term.spec.md) is the terminal crate beside aetheris-client.

## How to walk

Choose a page in the tree below.

Each name in the tree is a link to that page.

A page in the common corpus names the index and the neighboring page at the top.

Follow those names to keep reading the common corpus.

A package page holds the requirements for that package.

The child pages in the tree are the packages under it.

Some package pages hold only the package name.

A requirement that names that package sits on that page.

A folder that holds child pages and no requirement page of its own has an index.

Open that index to see the child pages.

The code in the package is the other side of the requirement.

A tree node is a spine page.

The root label is the page the drawing sits on, and that label stays plain.

Every branch opens a package spec or a folder index.

A type is a word in the sentence. A type is not a node.

A tree sits in a pre block.

A code fence that holds a tree is a miss.

The child-page drawings are the map.

A second tree of those same pages is a miss.

## The tree

<pre>
spine/
├── <a href="common.spec.md">Common</a>
│   ├── <a href="common/method.spec.md">Method</a>
│   ├── <a href="common/canvas.spec.md">Canvas and engine</a>
│   ├── <a href="common/grok.spec.md">Grok and the model stream</a>
│   ├── <a href="common/stages.spec.md">Stages</a>
│   ├── <a href="common/formal-plan.spec.md">Formal plan</a>
│   ├── <a href="common/pre-plan.spec.md">Pre-plan</a>
│   ├── <a href="common/server.spec.md">Server</a>
│   ├── <a href="common/roux.spec.md">Roux</a>
│   └── <a href="common/rust.spec.md">Rust client and the desk</a>
├── <a href="java/README.md">java</a>
│   └── <a href="java/org/README.md">org</a>
│       ├── <a href="java/org/aetheris/aetheris.spec.md">org.aetheris</a>
│       │   ├── <a href="java/org/aetheris/config/config.spec.md">org.aetheris.config</a>
│       │   ├── <a href="java/org/aetheris/ssh/ssh.spec.md">org.aetheris.ssh</a>
│       │   ├── <a href="java/org/aetheris/swing/swing.spec.md">org.aetheris.swing</a>
│       │   ├── <a href="java/org/aetheris/vault/vault.spec.md">org.aetheris.vault</a>
│       │   └── <a href="java/org/aetheris/wire/wire.spec.md">org.aetheris.wire</a>
│       ├── <a href="java/org/casserole/casserole.spec.md">org.casserole</a>
│       │   ├── <a href="java/org/casserole/app/app.spec.md">org.casserole.app</a>
│       │   │   └── <a href="java/org/casserole/app/ui/README.md">ui</a>
│       │   │       └── <a href="java/org/casserole/app/ui/ssh/ssh.spec.md">org.casserole.app.ui.ssh</a>
│       │   ├── <a href="java/org/casserole/chat/chat.spec.md">org.casserole.chat</a>
│       │   │   └── <a href="java/org/casserole/chat/cmd/cmd.spec.md">org.casserole.chat.cmd</a>
│       │   ├── <a href="java/org/casserole/config/config.spec.md">org.casserole.config</a>
│       │   ├── <a href="java/org/casserole/edit/edit.spec.md">org.casserole.edit</a>
│       │   ├── <a href="java/org/casserole/format/format.spec.md">org.casserole.format</a>
│       │   │   ├── <a href="java/org/casserole/format/fence/fence.spec.md">org.casserole.format.fence</a>
│       │   │   ├── <a href="java/org/casserole/format/json/json.spec.md">org.casserole.format.json</a>
│       │   │   ├── <a href="java/org/casserole/format/list/list.spec.md">org.casserole.format.list</a>
│       │   │   ├── <a href="java/org/casserole/format/markdown/markdown.spec.md">org.casserole.format.markdown</a>
│       │   │   └── <a href="java/org/casserole/format/table/table.spec.md">org.casserole.format.table</a>
│       │   ├── <a href="java/org/casserole/lamina/lamina.spec.md">org.casserole.lamina</a>
│       │   │   └── <a href="java/org/casserole/lamina/spring/spring.spec.md">org.casserole.lamina.spring</a>
│       │   ├── <a href="java/org/casserole/llm/llm.spec.md">org.casserole.llm</a>
│       │   ├── <a href="java/org/casserole/log/log.spec.md">org.casserole.log</a>
│       │   ├── <a href="java/org/casserole/mood/mood.spec.md">org.casserole.mood</a>
│       │   ├── <a href="java/org/casserole/ollama/ollama.spec.md">org.casserole.ollama</a>
│       │   ├── <a href="java/org/casserole/pool/pool.spec.md">org.casserole.pool</a>
│       │   └── <a href="java/org/casserole/ssh/ssh.spec.md">org.casserole.ssh</a>
│       │       └── <a href="java/org/casserole/ssh/shell/shell.spec.md">org.casserole.ssh.shell</a>
│       └── <a href="java/org/roux/roux.spec.md">org.roux</a>
│           ├── <a href="java/org/roux/api/api.spec.md">org.roux.api</a>
│           ├── <a href="java/org/roux/crypto/crypto.spec.md">org.roux.crypto</a>
│           ├── <a href="java/org/roux/frame/frame.spec.md">org.roux.frame</a>
│           ├── <a href="java/org/roux/id/id.spec.md">org.roux.id</a>
│           ├── <a href="java/org/roux/index/index.spec.md">org.roux.index</a>
│           ├── <a href="java/org/roux/layout/layout.spec.md">org.roux.layout</a>
│           ├── <a href="java/org/roux/lock/lock.spec.md">org.roux.lock</a>
│           ├── <a href="java/org/roux/model/model.spec.md">org.roux.model</a>
│           ├── <a href="java/org/roux/page/page.spec.md">org.roux.page</a>
│           ├── <a href="java/org/roux/session/session.spec.md">org.roux.session</a>
│           ├── <a href="java/org/roux/spill/spill.spec.md">org.roux.spill</a>
│           ├── <a href="java/org/roux/spring/spring.spec.md">org.roux.spring</a>
│           ├── <a href="java/org/roux/tel/tel.spec.md">org.roux.tel</a>
│           ├── <a href="java/org/roux/token/token.spec.md">org.roux.token</a>
│           └── <a href="java/org/roux/toon/toon.spec.md">org.roux.toon</a>
└── <a href="rust/README.md">rust</a>
    └── <a href="rust/aetheris/README.md">aetheris</a>
        ├── <a href="rust/aetheris/aetheris-client/aetheris-client.spec.md">rust.aetheris.aetheris-client.aetheris-client</a>
        │   ├── <a href="rust/aetheris/aetheris-client/src/amwp.spec.md">rust.aetheris.aetheris-client.src.amwp</a>
        │   ├── <a href="rust/aetheris/aetheris-client/src/chrome.spec.md">rust.aetheris.aetheris-client.src.chrome</a>
        │   ├── <a href="rust/aetheris/aetheris-client/src/door.spec.md">rust.aetheris.aetheris-client.src.door</a>
        │   ├── <a href="rust/aetheris/aetheris-client/src/edit.spec.md">rust.aetheris.aetheris-client.src.edit</a>
        │   ├── <a href="rust/aetheris/aetheris-client/src/heatbar.spec.md">rust.aetheris.aetheris-client.src.heatbar</a>
        │   ├── <a href="rust/aetheris/aetheris-client/src/main.spec.md">rust.aetheris.aetheris-client.src.main</a>
        │   ├── <a href="rust/aetheris/aetheris-client/src/model.spec.md">rust.aetheris.aetheris-client.src.model</a>
        │   ├── <a href="rust/aetheris/aetheris-client/src/mood.spec.md">rust.aetheris.aetheris-client.src.mood</a>
        │   ├── <a href="rust/aetheris/aetheris-client/src/session.spec.md">rust.aetheris.aetheris-client.src.session</a>
        │   └── <a href="rust/aetheris/aetheris-client/src/vault.spec.md">rust.aetheris.aetheris-client.src.vault</a>
        └── <a href="rust/aetheris/aetheris-term/aetheris-term.spec.md">rust.aetheris.aetheris-term.aetheris-term</a>
            ├── <a href="rust/aetheris/aetheris-term/src/connector.spec.md">rust.aetheris.aetheris-term.src.connector</a>
            ├── <a href="rust/aetheris/aetheris-term/src/grid.spec.md">rust.aetheris.aetheris-term.src.grid</a>
            ├── <a href="rust/aetheris/aetheris-term/src/keys.spec.md">rust.aetheris.aetheris-term.src.keys</a>
            └── <a href="rust/aetheris/aetheris-term/src/lib.spec.md">rust.aetheris.aetheris-term.src.lib</a>
</pre>

## Figures

Two drawings sit in the images folder.

A requirement page that uses a drawing already links it.

A drawing is not a requirement page.

[Aetheris conceptual layout](images/Aetheris-conceptual-layout.png) is the mockup the Stages page cites.

[Reichenbach sink message edit](images/reichenbach-sink-message-edit.png) is the drawing the Roux identity page links.

The door that lists every page is [Spine](README.md).
