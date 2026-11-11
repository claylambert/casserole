# Specifications

The files in this directory say what the server and the two clients do. One file is one Java package or one Rust module. A Java package is the namespace a set of Java classes share. A Rust module is the namespace a set of Rust items share. The file sits under `java/` or `rust/`, in the same relative place as the source it describes.

Open the file and find a heading. The first sentence under that heading is the requirement, the behavior the code is supposed to have. The pages were written in English before the source was accepted. A later change to the program is checked against these same pages.

A comment in the source cites a page by its public id. A public id is the identifier the page carries for that requirement. The comment also cites the path under `docs/specs/`, so you can open the file directly from the comment. Start at the class, take the id from the comment, and you arrive at the sentence. Start at the sentence, and the index names every class that cites it.

> **How a page is read**
>
> One file is one Java package or one Rust module. The file sits beside the source it describes, under `java/` or `rust/`. You seek one heading and follow the first sentence under it. That sentence is the requirement. A public id at the end of the section is the label a comment in the source uses when it points at that requirement. The same identifier is the public key on that section.

The index of every page is [semantic-key-index.md](semantic-key-index.md). Each row of that index names a public id, the specification file that holds that requirement, and the source lines that cite it. If you have a class open, take the id from the comment and find that id in the index. If you have a requirement open, the index names the class that cites it.

The tree holds the specifications for three silos. A silo is one project directory with its own build script, its own install script, and its own start script. The server silo is the Java server both desktop clients share. Its root spec is [org.casserole](java/org/casserole/casserole.spec.md). The Java client silo is the Java desktop client. Its root spec is [org.aetheris](java/org/aetheris/aetheris.spec.md). The Rust client silo is the Rust desktop client. Its root spec is [aetheris-client](rust/aetheris/aetheris-client/aetheris-client.spec.md). The files for those silos sit under `java/` or `rust/`. The specification files are the `.spec.md` files in that tree. A `.spec.md` file is one specification, written in Markdown, for the package or module beside it. Build and start commands are on the README at the root of the repository, [README.md](../../README.md).
