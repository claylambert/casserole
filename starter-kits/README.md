# Starter kits

This directory holds one starter kit. The kit is packed as one zip archive, a single compressed file that contains the example documents. The archive is shipped with the bytes it had when it was packed, so the documents inside are the documents that shipped with the kit. You can build and run Casserole while this archive stays packed. The product in this repository is the Casserole Server and the two Aetheris Clients, and the way to build and run them is the [root page](../README.md). The kit is a separate convenience when you want the example documents that the archive contains.

> **What the archive is for**
>
> Those documents show Architecture-Augmented Spec-Driven Development (AA-SDD). AA-SDD is the practice of writing an engineering specification in English before the code. They also show English-Native Intermediate Representation (EN-IR). EN-IR means the English itself is the form a later build follows. The page is specific enough to carry out. A person can read it aloud. The documents inside the archive explain their own files. This page tells you how to unpack the archive and which file to open first.

Unpacking the archive leaves the Casserole Server as it is, leaves both Aetheris Clients as they are, and leaves the scripts as they are. The file you unpack is [aa-sdd-seed-kit-prod-1.0-2026-10-16-1925.zip](aa-sdd-seed-kit-prod-1.0-2026-10-16-1925.zip).

## Unpack

Run the unpack command from the repository root, the folder that contains [pom.xml](../pom.xml). Maven, which is the Java build tool, reads that file when it builds the Java programs. The unpack command uses the same folder so the relative path to the zip resolves. Windows PowerShell is the PowerShell window that Windows ships. Paste this command into that window:

```powershell
Expand-Archive -Path starter-kits\aa-sdd-seed-kit-prod-1.0-2026-10-16-1925.zip -DestinationPath starter-kits\kit
```

`Expand-Archive` is the PowerShell command that opens a zip and writes the files inside it into the destination folder. On Linux, open a shell in the same repository root and run:

```bash
unzip starter-kits/aa-sdd-seed-kit-prod-1.0-2026-10-16-1925.zip -d starter-kits/kit
```

`unzip` is the command that opens the zip and writes the files into the directory named after `-d`. `starter-kits/kit` is the folder that holds the unpacked kit after either command finishes. The bytes inside the zip stay the bytes that were packed. You can remove the `starter-kits/kit` folder when you are done reading. The archive in this directory stays, so you can unpack it again later.

## Where the docs live

The documents sit at the root of that unpacked folder. They live inside the archive until you unpack it, and they are not written as loose files beside this page.

| Path inside the kit | What it is |
|---|---|
| `README.md` | The operator manual. Read this for the rest of the kit. |
| `VisionStatement.README.md` | The vision statement. |
| `plans/README.md` | The plans. |
| `aa-sdd/README.md` | The method notes that shipped with the kit. |

The Java build leaves these files unread. The Rust Aetheris Client build leaves these files unread. The way to build and run Casserole is the [root page](../README.md).
