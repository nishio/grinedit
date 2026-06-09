# GRINEdit rev194 recovered source

This directory is a derivative source reconstruction from
`grinedit-bin-rev194-20070115173728.zip`.

Provenance:

- Most Java files under `src/` were copied from
  `grinedit-app-alpha0.20.jar` entries named
  `.svn/text-base/*.java.svn-base`.
- `BasicStrokeEdge.java`, `NewMouseHandler.java`, and
  `NewMouseMediator.java` were decompiled from class files with CFR because
  no `.java.svn-base` entries were present for them.
- Python/Jython scripts, plugin init scripts, samples, sample data, and
  `config.py` were copied from the rev194 distribution zip.
- Bundled third-party libraries, JVM dependencies, Windows DLLs, Python
  binary artifacts, generated `$py.class` files, and plugin jars are not
  duplicated here. They remain available in the primary artifact zip.

This is not an original source release. Treat it as recovered code useful for
inspection, comparison, and future porting work.
