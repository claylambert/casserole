# Javadoc test tree

Open [index.html](index.html) in a browser. This tree is a standard Javadoc tree. The pages open from the file. No server process is required. That page is the formal Javadoc of the main Java sources. The packages are grouped as Casserole, Aetheris, and Roux. Roux is in this tree because the sources link across the three packages.

The tool was `javadoc` from the Zulu 26 JDK. The source path is `src/main/java`. The packages are `org.casserole`, `org.aetheris`, and `org.roux`. Private members are included. The classpath is `target/dependency`. The specification tag `@aa-sdd` is shown as Specification.

The Rust mirror is [rust/index.md](rust/index.md). Each page has a description, a summary table, and a detail section. The sentence in a summary row is the first sentence of the Rust doc comment.

Regenerate the Rust tree with:

```
python bin/rust_javadoc_md.py --workspace rust/aetheris --out javadoc/rust
```

The script is [../bin/rust_javadoc_md.py](../bin/rust_javadoc_md.py). It reads a Cargo workspace or one crate, and it writes this Markdown shape again.

This directory is a test run. A public copy waits until Hannibal says this tree belongs in the release.
