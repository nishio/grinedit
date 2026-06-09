# GRINEdit restoration

GRINEdit is a graph/relation editor developed by Nishio Hirokazu around 2006.
This repository preserves the recovered alpha0.10 source and binary artifacts,
plus a wiki for restoration notes and future-porting decisions.

## Contents

- `alpha0.10-src/`: recovered alpha0.10 source tree.
- `rev194-recovered-src/`: derivative source reconstruction from the newer
  rev194 / alpha0.20 binary distribution.
- `alpha0.10-src.zip`: original alpha0.10 source distribution.
- `GRINEdit-alpha0.10.zip` and `GRINEdit-alpha0.10-bin/`: alpha0.10 binary distribution.
- `grinedit-bin-rev194-20070115173728.zip`: latest binary candidate found on the SourceForge.jp mirror.
- `scripts/`: local build, run, and wiki lint helpers.
- `wiki/`: maintained knowledge base.

## macOS alpha0.10 run

On Apple Silicon macOS:

```bash
scripts/run-alpha010.sh
```

The script downloads the platform SWT jar into `lib/`, compiles sources into
`build/classes`, and starts SWT with `-XstartOnFirstThread`.

## rev194 recovered source

The newer rev194 distribution did not include a standalone source zip, but its
app jar retained many SVN working-copy source files. Regenerate the recovered
source tree with:

```bash
scripts/extract-rev194-source.sh --force
```

Compile the recovered Java sources with:

```bash
scripts/build-rev194-recovered.sh
```

Compilation uses dependency jars from the rev194 primary artifact and writes
only to `build/`.

## Wiki

Start with [wiki/index.md](wiki/index.md). After wiki edits, run:

```bash
python3 scripts/lint_wiki.py
```

Primary artifacts are preserved as historical sources. The license and modern
redistribution policy are intentionally not generalized from bundled third-party
dependencies.
