# LLM Wiki

A pattern for building personal knowledge bases using LLMs.

This is an idea file, it is designed to be copy pasted to your own LLM Agent (e.g. OpenAI Codex, Claude Code, OpenCode / Pi, or etc.). Its goal is to communicate the high level idea, but your agent will build out the specifics in collaboration with you.

## The core idea

Most people's experience with LLMs and documents looks like RAG: you upload a collection of files, the LLM retrieves relevant chunks at query time, and generates an answer. This works, but the LLM is rediscovering knowledge from scratch on every question. There's no accumulation. Ask a subtle question that requires synthesizing five documents, and the LLM has to find and piece together the relevant fragments every time. Nothing is built up. NotebookLM, ChatGPT file uploads, and most RAG systems work this way.

The idea here is different. Instead of just retrieving from raw documents at query time, the LLM **incrementally builds and maintains a persistent wiki** — a structured, interlinked collection of markdown files that sits between you and the raw sources. When you add a new source, the LLM doesn't just index it for later retrieval. It reads it, extracts the key information, and integrates it into the existing wiki — updating entity pages, revising topic summaries, noting where new data contradicts old claims, strengthening or challenging the evolving synthesis. The knowledge is compiled once and then *kept current*, not re-derived on every query.

This is the key difference: **the wiki is a persistent, compounding artifact.** The cross-references are already there. The contradictions have already been flagged. The synthesis already reflects everything you've read. The wiki keeps getting richer with every source you add and every question you ask.

You never (or rarely) write the wiki yourself — the LLM writes and maintains all of it. You're in charge of sourcing, exploration, and asking the right questions. The LLM does all the grunt work — the summarizing, cross-referencing, filing, and bookkeeping that makes a knowledge base actually useful over time. In practice, I have the LLM agent open on one side and Obsidian open on the other. The LLM makes edits based on our conversation, and I browse the results in real time — following links, checking the graph view, reading the updated pages. Obsidian is the IDE; the LLM is the programmer; the wiki is the codebase.

This can apply to a lot of different contexts. A few examples:

- **Personal**: tracking your own goals, health, psychology, self-improvement — filing journal entries, articles, podcast notes, and building up a structured picture of yourself over time.
- **Research**: going deep on a topic over weeks or months — reading papers, articles, reports, and incrementally building a comprehensive wiki with an evolving thesis.
- **Reading a book**: filing each chapter as you go, building out pages for characters, themes, plot threads, and how they connect. By the end you have a rich companion wiki. Think of fan wikis like [Tolkien Gateway](https://tolkiengateway.net/wiki/Main_Page) — thousands of interlinked pages covering characters, places, events, languages, built by a community of volunteers over years. You could build something like that personally as you read, with the LLM doing all the cross-referencing and maintenance.
- **Business/team**: an internal wiki maintained by LLMs, fed by Slack threads, meeting transcripts, project documents, customer calls. Possibly with humans in the loop reviewing updates. The wiki stays current because the LLM does the maintenance that no one on the team wants to do.
- **Competitive analysis, due diligence, trip planning, course notes, hobby deep-dives** — anything where you're accumulating knowledge over time and want it organized rather than scattered.

## Architecture

There are three layers:

**Raw sources** — your curated collection of source documents. Articles, papers, images, data files. These are immutable — the LLM reads from them but never modifies them. This is your source of truth.

**The wiki** — a directory of LLM-generated markdown files. Summaries, entity pages, concept pages, comparisons, an overview, a synthesis. The LLM owns this layer entirely. It creates pages, updates them when new sources arrive, maintains cross-references, and keeps everything consistent. You read it; the LLM writes it.

**The schema** — a document (e.g. CLAUDE.md for Claude Code or AGENTS.md for Codex) that tells the LLM how the wiki is structured, what the conventions are, and what workflows to follow when ingesting sources, answering questions, or maintaining the wiki. This is the key configuration file — it's what makes the LLM a disciplined wiki maintainer rather than a generic chatbot. You and the LLM co-evolve this over time as you figure out what works for your domain.

## Operations

**Ingest.** You drop a new source into the raw collection and tell the LLM to process it. An example flow: the LLM reads the source, discusses key takeaways with you, writes a summary page in the wiki, updates the index, updates relevant entity and concept pages across the wiki, and appends an entry to the log. A single source might touch 10-15 wiki pages. Personally I prefer to ingest sources one at a time and stay involved — I read the summaries, check the updates, and guide the LLM on what to emphasize. But you could also batch-ingest many sources at once with less supervision. It's up to you to develop the workflow that fits your style and document it in the schema for future sessions. If given file is like `a.txt` rename properly.

**Query.** You ask questions against the wiki. The LLM searches for relevant pages, reads them, and synthesizes an answer with citations. Answers can take different forms depending on the question — a markdown page, a comparison table, a slide deck (Marp), a chart (matplotlib), a canvas. The important insight: **good answers can be filed back into the wiki as new pages.** A comparison you asked for, an analysis, a connection you discovered — these are valuable and shouldn't disappear into chat history. This way your explorations compound in the knowledge base just like ingested sources do.

**Lint.** Periodically, ask the LLM to health-check the wiki. Look for: contradictions between pages, stale claims that newer sources have superseded, orphan pages with no inbound links, important concepts mentioned but lacking their own page, missing cross-references, data gaps that could be filled with a web search. The LLM is good at suggesting new questions to investigate and new sources to look for. This keeps the wiki healthy as it grows.

## Indexing and logging

Two special files help the LLM (and you) navigate the wiki as it grows. They serve different purposes:

**index.md** is content-oriented. It's a catalog of everything in the wiki — each page listed with a link, a one-line summary, and optionally metadata like date or source count. Organized by category (entities, concepts, sources, etc.). The LLM updates it on every ingest. When answering a query, the LLM reads the index first to find relevant pages, then drills into them. This works surprisingly well at moderate scale (~100 sources, ~hundreds of pages) and avoids the need for embedding-based RAG infrastructure.

**log.md** is chronological. It's an append-only record of what happened and when — ingests, queries, lint passes. A useful tip: if each entry starts with a consistent prefix (e.g. `## [2026-04-02] ingest | Article Title`), the log becomes parseable with simple unix tools — `grep "^## \[" log.md | tail -5` gives you the last 5 entries. The log gives you a timeline of the wiki's evolution and helps the LLM understand what's been done recently.

## Optional: CLI tools

At some point you may want to build small tools that help the LLM operate on the wiki more efficiently. A search engine over the wiki pages is the most obvious one — at small scale the index file is enough, but as the wiki grows you want proper search. [qmd](https://github.com/tobi/qmd) is a good option: it's a local search engine for markdown files with hybrid BM25/vector search and LLM re-ranking, all on-device. It has both a CLI (so the LLM can shell out to it) and an MCP server (so the LLM can use it as a native tool). You could also build something simpler yourself — the LLM can help you vibe-code a naive search script as the need arises.

## Tips and tricks

- **Obsidian Web Clipper** is a browser extension that converts web articles to markdown. Very useful for quickly getting sources into your raw collection.
- **Download images locally.** In Obsidian Settings → Files and links, set "Attachment folder path" to a fixed directory (e.g. `raw/assets/`). Then in Settings → Hotkeys, search for "Download" to find "Download attachments for current file" and bind it to a hotkey (e.g. Ctrl+Shift+D). After clipping an article, hit the hotkey and all images get downloaded to local disk. This is optional but useful — it lets the LLM view and reference images directly instead of relying on URLs that may break. Note that LLMs can't natively read markdown with inline images in one pass — the workaround is to have the LLM read the text first, then view some or all of the referenced images separately to gain additional context. It's a bit clunky but works well enough.
- **Obsidian's graph view** is the best way to see the shape of your wiki — what's connected to what, which pages are hubs, which are orphans.
- **Marp** is a markdown-based slide deck format. Obsidian has a plugin for it. Useful for generating presentations directly from wiki content.
- **Dataview** is an Obsidian plugin that runs queries over page frontmatter. If your LLM adds YAML frontmatter to wiki pages (tags, dates, source counts), Dataview can generate dynamic tables and lists.
- The wiki is just a git repo of markdown files. You get version history, branching, and collaboration for free.

## Why this works

The tedious part of maintaining a knowledge base is not the reading or the thinking — it's the bookkeeping. Updating cross-references, keeping summaries current, noting when new data contradicts old claims, maintaining consistency across dozens of pages. Humans abandon wikis because the maintenance burden grows faster than the value. LLMs don't get bored, don't forget to update a cross-reference, and can touch 15 files in one pass. The wiki stays maintained because the cost of maintenance is near zero.

The human's job is to curate sources, direct the analysis, ask good questions, and think about what it all means. The LLM's job is everything else.

The idea is related in spirit to Vannevar Bush's Memex (1945) — a personal, curated knowledge store with associative trails between documents. Bush's vision was closer to this than to what the web became: private, actively curated, with the connections between documents as valuable as the documents themselves. The part he couldn't solve was who does the maintenance. The LLM handles that.


## Note

This document is intentionally abstract. It describes the idea, not a specific implementation. The exact directory structure, the schema conventions, the page formats, the tooling — all of that will depend on your domain, your preferences, and your LLM of choice. Everything mentioned above is optional and modular — pick what's useful, ignore what isn't. For example: your sources might be text-only, so you don't need image handling at all. Your wiki might be small enough that the index file is all you need, no search engine required. You might not care about slide decks and just want markdown pages. You might want a completely different set of output formats. The right way to use this is to share it with your LLM agent and work together to instantiate a version that fits your needs. The document's only job is to communicate the pattern. Your LLM can figure out the rest.

## GRINEdit alpha0.10 restoration notes

- Source: `alpha0.10-src.zip` from SourceForge `grinedit/alpha-0.10`, expanded under `alpha0.10-src`.
- The source ZIP contains CVS metadata pointing at `:ext:hiroka-n@cvs.sourceforge.jp:/cvsroot/rel-visualizer`.
- Contents found: 63 Java source files, 35 Python/Jython scripts, CVS metadata, and bundled generated/runtime artifacts under `pythonScripts/sample`.
- Main class: `org.nishiohirokazu.grinEdit.GraphVisualizerTest`.
- The source ZIP did not contain `config.py`; a local `alpha0.10-src/config.py` was added for this machine.
- `GRINEdit-alpha0.10.zip` contains `GRINEdit-alpha0.10.jar`, a fat jar with Jython, JFace, XML-RPC, Eclipse runtime classes, and Windows SWT classes. For source execution on macOS, source-compiled classes are placed first in the classpath and the fat jar is used for non-SWT dependencies.
- Apple Silicon macOS runtime uses `org.eclipse.swt.cocoa.macosx.aarch64:3.128.0`. Maven Central latest `3.134.0` requires Java 21 bytecode, while this machine has JDK 19.
- Java source compilation works with `javac -encoding ISO-8859-1`; this avoids failures from mixed legacy Japanese/comment encodings in the 2006 source tree.
- Repro scripts: `scripts/build-alpha010.sh` compiles source into `build/classes`; `scripts/run-alpha010.sh` runs the app with `-XstartOnFirstThread`.
- Verification on 2026-06-09 JST: `scripts/run-alpha010.sh` stayed alive for 5 seconds, logged `initMouseMediator.py` and `initMenu.py`, and initialized the macOS input method without throwing an exception. The test process was then killed intentionally.
- For visual observation on macOS, launch through Terminal using `scripts/open-alpha010-terminal.sh`. A detached `nohup` launch can keep the process alive briefly but may not surface a visible `java` app/window reliably.
- User observation on 2026-06-09 JST: the window did open, but no useful content was visible. Treat the current restoration as "application window starts" rather than "meaningful sample/content rendering works." Next investigation should determine whether sample data must be loaded manually, whether `start.py` intentionally disables the demo (`SHOW_DEMO = False`), or whether rendering/layout is failing silently.

## GRINEdit alpha0.10 source reading notes

- Startup path: `GraphVisualizerTest.main()` constructs `GraphVisualizerTest`, which obtains the SWT `Display`, initializes Jython via `Infrastructure.getPyi()`, obtains the singleton `Mediator`, creates a `Canvas`, initializes mouse operations and menus from Python, opens a 600x600 shell, then starts a 10 ms timer that repeatedly calls `updateScreen()`.
- Jython initialization: `Infrastructure.getPyi()` executes `config.py`, then `pythonScripts/init.py`. `init.py` appends `SCRIPT_PATH` and `JYTHON_PATH`, creates the global/builtin `med = Mediator.getInstance()`, installs gettext helpers, and starts an XML-RPC server on port 8080 when `USE_XML_RPC = True`.
- Rendering loop: `updateScreen()` clears the double buffer, optionally draws a background image, runs `med.graph.layoutStep()`, renders edges and vertices, draws any mouse-operation overlay, and copies the buffer to the canvas. If the graph has no vertices or edges, this loop correctly paints only an empty white canvas.
- Initial graph state: `Mediator` constructs `graph = new RenderableGraph()`. `RenderableGraph` starts with empty `vertexList` and `edgeList`, plus default physical laws: `SpringEdge`, `Repulsion`, and `Anchor`. Therefore the application has no visible graph unless a script, menu action, drag-and-drop, or XML-RPC client adds data.
- Why the observed window was blank: `pythonScripts/start.py` is executed after the window opens, but both `DO_TEST` and `SHOW_DEMO` are `False`. So the normal alpha0.10 startup intentionally loads no sample data. This makes the user's blank-window observation consistent with the source code.
- The disabled demo in `start.py` is probably not runnable as-is. Inside `if SHOW_DEMO:`, it imports `Graph` instead of `RenderableGraph`, but `Graph` has no `addVertex()` or `addEdge()` methods. It also calls `mediator.setGraph(pgw)` even though `init.py` exposes the singleton as `med`, not `mediator`. The comment says the demo "doesn't work @ alpha1.0"; in this alpha0.10 source it also looks broken.
- Menus are created from `pythonScripts/initMenu.py`: File menu entries include `Load LegacyFormat Data`, `Load PairedFormat Data`, `Load from JSON`, `Save as JSON`, `Run Script`, `Open Jython Console`, `Load Background Image`, and `Capture`. On macOS the SWT menu may appear in the system menu bar rather than inside the window, so the window itself can look content-free until data is loaded.
- Most reliable manual content path appears to be LegacyFormat. `legacyLoader.py` constructs a `RenderableGraph`, parses `VERTEX` / `NODE` / `EDGE` style files, creates circle or labeled box vertices, creates linear edges, calls `med.setGraph(g)`, and adjusts spring strength from vertex degree. The binary ZIP includes samples under `GRINEdit-alpha0.10-bin/GRINEdit-alpha0.10/sampleData/legacy/`, including `petersen.txt`, `petersen_colored.txt`, `measure10.txt`, `sqr10.txt`, and `concentrate.txt`.
- Drag-and-drop is wired to LegacyFormat too. `DragDropListener.drop()` ignores file type and calls `legacyLoader.load(files[0])` on the first dropped file. This suggests a useful next observation is to launch the app, then drop `sampleData/legacy/petersen.txt` onto the window.
- JSON support is incomplete. `MA_SaveAsJSON` writes `vertexList`, `edgeList`, and `lawList`, but `MA_LoadJSON` only reads `vertexList`; edge and law loading are marked `TODO`. The bundled `sampleData/legacy/tmp.txt` is actually JSON and includes edges/laws, but loading it through `Load from JSON` would not reconstruct the full graph.
- PairedFormat likely has source-level problems. `pairedLoader.py` imports `Graph`, then calls `g.addVertex()` and `g.addEdge()`, which only exist on `RenderableGraph`. It also uses `Mediator.instance.setGraph(g)` rather than the `med` global used elsewhere. Treat this loader as suspect until tested or patched.
- Some edit-menu scripts look stale or partially disabled. For example, directed/undirected edge scripts call `setDirected()`, but `RenderableEdge` only has a public `directed` field and no setter. The dis-anchor script writes `v.anchored`, but anchoring is actually represented through `RenderableGraph.anchorTable`. Several of these menu items are commented out, which fits an alpha-stage codebase.
- XML-RPC is a first-class interaction path. `XMLRPCHandler` supports `initGraph`, `addVertex`, `addEdge`, `modVertex`, `modEdge`, `modLaw`, and query methods. `pythonScripts/sample/website_as_graph/website_as_graph.py` connects to `http://localhost:8080/RPC2`, creates DOM nodes as graph vertices, and adds edges externally. So GRINEdit was not only a file viewer; it was also meant to be scriptable/live-controllable.
- Layout model: positions are in graph coordinates near the origin; `ViewportTransformer` maps them to screen coordinates using `scale = 100.0` and `halfWidth = 300`, so the origin appears near the center of a 600x600 canvas. Unlabeled vertices are `CircleVertex` with diameter 15; labeled vertices are `BoxVertex`. The force-directed layout runs continuously via spring, repulsion, and anchor laws.
- Mouse defaults: left mouse is `Move Vertex`; right mouse is `Scaling`. Translation exists as a mouse operation and is available in the menu. `MO_MoveVertex` anchors dragged vertices by inserting them into `graph.anchorTable`.
- Current interpretation: alpha0.10 has a working SWT shell, timer, canvas, menu initialization, graph model, force layout, and likely LegacyFormat loading. The initial blank window should be recorded as expected default behavior, not yet as a rendering failure. Meaningful rendering should be verified by loading a legacy sample or by sending graph data through XML-RPC.
- Practical next step: add a small observation helper that either auto-loads `sampleData/legacy/petersen.txt` at startup or runs an XML-RPC script after launch. That would turn the current "window starts" verification into a "graph content renders" verification.
- Demo helper added: `GraphVisualizerTest` now reads Java system property `grinedit.demo.legacy` and injects it into Jython as `DEMO_LEGACY_FILE` before executing `start.py`. `start.py` loads that path through `legacyLoader.load(...)` when present. Normal `scripts/run-alpha010.sh` remains blank/default; `scripts/run-alpha010-demo.sh` passes `petersen_colored.txt` by default; `scripts/open-alpha010-demo-terminal.sh` opens that demo in Terminal for visual observation.
- Demo verification on 2026-06-09 JST: a 5-second run of `scripts/run-alpha010-demo.sh` stayed alive and logged `load demo legacy data .../petersen_colored.txt` followed by `load legacy data`, so the demo path reached the LegacyFormat loader successfully. Visual confirmation still needs a human observation or screenshot because the process was killed after log verification.
