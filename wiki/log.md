# Log

## [2026-06-09 21:36] setup | LLM Wiki を grinedit プロジェクトに instantiate

`llm-wiki.md` のパターンに従い、GRINEdit プロジェクト用の LLM Wiki を scaffold。
- `CLAUDE.md`（スキーマ）作成。GitHub公開予定のため GFM リンク方式を採用（monika-mentoring-wiki 準拠）
- `wiki/{index,log,concepts,entities,sources,analyses}` を作成
- `scripts/lint_wiki.py`（GFM対応版）を配置
- `llm-wiki.md` 末尾の「GRINEdit alpha0.10 restoration notes」を以下のページに migrate:
  - entities: [GRINEdit](entities/GRINEdit.md), [西尾泰和](entities/nishio-hirokazu.md)
  - sources: [alpha0.10-src](sources/alpha0.10-src.md), [GRINEdit-alpha0.10-bin](sources/GRINEdit-alpha0.10-bin.md)
  - concepts: [Jython](concepts/jython.md)
  - analyses: [macOS復元手順 alpha0.10](analyses/macos-restoration-alpha0.10.md)
- 親 wiki森 の `~/llm-wiki/wikis.yaml` に grinedit を登録

## [2026-06-09 23:59] ingest | rev194 最新候補バイナリを取得

JAIST の SourceForge.jp ミラーから `grinedit-bin-rev194-20070115173728.zip` を取得し、[grinedit-bin-rev194](sources/grinedit-bin-rev194.md)として source ページ化。
- ミラー上では `20479/` に alpha0.10、`22881/` に rev187、`23582/` に rev194 があり、手元で確認した公開ミラー上では rev194 が最新候補
- rev194 は fat jar ではなく app jar + dependency jar 形式
- app jar に `.svn/text-base/*.java.svn-base` が 92 ファイル残っていることを確認

## [2026-06-09 23:59] filing-back | TODO 未完了項目の判断を整理

`TODO.md` の「最新バイナリとの差分」「バイナリからリバースエンジニアリングできるか」「Java 版の価値」「WASM 移植検討」を[rev194 比較と将来判断](analyses/rev194-comparison-and-future.md)に filing back。
- alpha0.10 と rev194 の設計差分を整理
- rev194 の逆復元は `.svn/text-base` により大半が可能、欠落 class は少数と判断
- Java 版は参照実装・仕様抽出として価値があり、現代アプリとしては TypeScript/Web/Electron 系への移植が有望と判断

## [2026-06-09 23:59] lint | rev194 追加後の wiki lint

`python3 scripts/lint_wiki.py` を実行し、重複ページID 0、孤立ページ 0、壊れたリンク 0、index 未登録 0、frontmatter 不備 0 を確認。

## [2026-06-10 00:03] publish | GitHub public repo 作成

`nishio/grinedit` を public GitHub repository として作成し、初回コミット `0e679e5` (`Restore GRINEdit alpha0.10 sources and wiki`) を `main` に push。

- URL: <https://github.com/nishio/grinedit>
- 公開対象: 一次資料 zip、展開済み alpha0.10 ソース、復元スクリプト、wiki、README
- 除外対象: `build/`, `lib/`, `raw/`, `alpha0.10-src/cachedir/`

## [2026-06-10 00:04] lint | publish 反映後の wiki lint

`python3 scripts/lint_wiki.py` を実行し、重複ページID 0、孤立ページ 0、壊れたリンク 0、index 未登録 0、frontmatter 不備 0 を確認。

## [2026-06-10 00:16] ingest | rev194 バイナリから復元ソースを生成

standalone source zip は見つからなかったが、`grinedit-bin-rev194-20070115173728.zip` 内の `grinedit-app-alpha0.20.jar` に残っていた `.svn/text-base/*.java.svn-base` から [rev194-recovered-src](sources/rev194-recovered-src.md)を生成。

- `scripts/extract-rev194-source.sh` を追加
- Java 95 files、Python/Jython 78 files、total 201 files
- `.java.svn-base` が無かった `BasicStrokeEdge`, `NewMouseHandler`, `NewMouseMediator` は CFR 0.152 で decompile
- `scripts/build-rev194-recovered.sh` を追加し、Java 19 で 93 class files までコンパイルできることを確認

## [2026-06-10 00:18] lint | rev194-recovered-src 追加後の wiki lint

`python3 scripts/lint_wiki.py` を実行し、重複ページID 0、孤立ページ 0、壊れたリンク 0、index 未登録 0、frontmatter 不備 0 を確認。

## [2026-06-10 00:39] decision | Java/SWT 版の延命中止

rev194 を現代 macOS の SWT で起動して挙動観察するタスクは中止。挙動観察が十分できておらず、SWT/Jython 依存を延命する費用対効果が悪いため、Java 版は「読むための歴史的実装」として扱う。以後は [rev194-recovered-src](sources/rev194-recovered-src.md)から TypeScript/Web/Electron 移植の最小仕様を抽出する。

## [2026-06-10 00:59] filing-back | TypeScript + Web MVP 仕様を作成

Java/SWT 版延命を中止した判断に従い、TypeScript + Web を一次移植先にする [TypeScript + Web MVP](analyses/typescript-web-mvp.md) を作成。

- Electron は後段の配布形態として扱う
- MVP は graph model、YAML fixture、command API、layout step、Canvas UI に限定
- Jython/XML-RPC/plugin jar loading/Pickle は MVP 外

## [2026-06-10 01:11] implementation | TypeScript + Web MVP 初期実装

[TypeScript + Web MVP](analyses/typescript-web-mvp.md) に従い、`web/` にブラウザ版の初期実装を追加。

- Vite + TypeScript + Vitest の構成を追加
- `GraphModel`, `CommandGateway`, YAML import/export, deterministic layout step を実装
- rev194 YAML sample 3 件を fixture 化し、import/export と command/layout のテストを追加
- Canvas UI で sample load、layout step/run、vertex/edge 追加、selection 表示、YAML preview を実装

## [2026-06-10 01:12] lint | TypeScript + Web MVP 実装後の wiki lint

`python3 scripts/lint_wiki.py` を実行し、重複ページID 0、孤立ページ 0、壊れたリンク 0、index 未登録 0、frontmatter 不備 0 を確認。
