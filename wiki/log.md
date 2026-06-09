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

## [2026-06-10 01:25] filing-back | sqr10 sample と drag pin UX を追記

[TypeScript + Web MVP](analyses/typescript-web-mvp.md) に、`rev194-recovered-src/sampleData/legacy/sqr10.txt` を square grid fixture として Web へ移植する方針を追記。

- `MO_MoveVertex` はドラッグ中に `PL_Anchor` target を更新し、Shift なしで release すると anchor を残す
- Web 版でもドラッグした頂点はデフォルトで pin 留めし、Shift release で pin 解除する

## [2026-06-10 01:32] implementation | sqr10 sample と drag pin UX を実装

`web/` に `sqr10` fixture と pinned vertex の最小実装を追加。

- `rev194-recovered-src/sampleData/legacy/sqr10.txt` から 100 vertices / 180 edges の `web/src/fixtures/sqr10.yaml` を生成
- `Vertex.pinned` を追加し、`anchored` params を pinned vertex として import
- layout step は pinned vertex の位置を固定
- Canvas drag は移動開始時に vertex を pin 留めし、Shift release で pin 解除
- Browser で `sqr10` load と drag 後の Pinned count 反映を確認

## [2026-06-10 01:33] lint | sqr10 sample と drag pin UX 実装後の wiki lint

`python3 scripts/lint_wiki.py` を実行し、重複ページID 0、孤立ページ 0、壊れたリンク 0、index 未登録 0、frontmatter 不備 0 を確認。

## [2026-06-10 01:49] filing-back | original viewport/render/layout 挙動を確認

[TypeScript + Web MVP](analyses/typescript-web-mvp.md) に、rev194 source を読んで確認した original 挙動の移植ルールを追記。

- `ViewportTransformer` は graph 座標と screen 座標を分け、rev194 では scale 40.0 を使う
- `DefaultRenderer` は screenPos 更新後に edge、vertex の順で描画する
- `BoxVertex`, `CircleVertex`, `LinearEdge`, `TriangleEdge`, `BasicStrokeEdge` の寸法・色を source から確認
- `SimpleLayout`, `PL_SpringEdge`, `PL_Repulsion`, `PL_Anchor` の定数と反復方法を確認
- `MassPoint.getParams()` は `position` と `velocity` を返すため、Web export も `position/velocity` を canonical に寄せる

## [2026-06-10 01:56] implementation | viewport/render/layout を original 挙動へ寄せる

rev194 source に合わせて Web 実装の座標・描画・layout を調整。

- TS 内部の `x/y` を graph 座標として扱い、Canvas では viewport scale 40 で screen 座標へ変換
- `BoxVertex` は margin 3px の矩形、`CircleVertex` は diameter 15px、色は AWT `ColorHolder` ベースへ変更
- `TriangleEdge` は line+arrow ではなく original と同じ三角形 polygon として描画
- layout を rev194 `SimpleLayout`/`PL_SpringEdge`/`PL_Repulsion`/`PL_Anchor` の定数と反復方式へ変更
- layout が `PL_SpringEdge` / `PL_Repulsion` の Law params を読むように変更し、`sqr10` に legacy loader 相当の `defaultSpringStrength: 0.25` を追加
- YAML export の canonical params を `position` / `velocity` へ変更し、`x/y` は互換 import として残す
- `sqr10` fixture は pixel ではなく graph 座標 `-4.5..4.5` の `position` に更新

## [2026-06-10 01:57] lint | original viewport/render/layout 反映後の wiki lint

`python3 scripts/lint_wiki.py` を実行し、重複ページID 0、孤立ページ 0、壊れたリンク 0、index 未登録 0、frontmatter 不備 0 を確認。

## [2026-06-10 01:59] lint | Law params 補足後の wiki lint

`python3 scripts/lint_wiki.py` を実行し、重複ページID 0、孤立ページ 0、壊れたリンク 0、index 未登録 0、frontmatter 不備 0 を確認。

## [2026-06-10 02:10] filing-back | unpin/disanchor 操作を確認

alpha0.10 と rev194 の source から、unpin 相当の操作を確認し、[TypeScript + Web MVP](analyses/typescript-web-mvp.md) に追記。

- alpha0.10 には `pythonScripts/menu/s_v_disanchored.py` の `make selected vertex dis-anchored` があるが、menu 登録はコメントアウト
- rev194 では `RenderableVertex.setDisanchored()` と `MO_MoveVertex` の Shift release による anchor remove が残っている
- Web 版では発見性のため selected vertex 用の `Unpin` button を追加する

## [2026-06-10 02:12] implementation | selected vertex の Unpin button を追加

`web/` に selected vertex の pin を外す操作を追加。

- `CommandGateway.pinVertex()` / `unpinVertex()` を追加
- toolbar に `Unpin` button を追加し、選択中 vertex が pinned の場合だけ有効化
- `Unpin` 実行後に Pinned count と YAML preview を更新
- Vitest に gateway 経由の pin/unpin test を追加

## [2026-06-10 02:12] lint | Unpin button 追加後の wiki lint

`python3 scripts/lint_wiki.py` を実行し、重複ページID 0、孤立ページ 0、壊れたリンク 0、index 未登録 0、frontmatter 不備 0 を確認。
