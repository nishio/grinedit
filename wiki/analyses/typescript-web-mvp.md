---
type: analysis
summary: GRINEdit を TypeScript + Web に移植する最小仕様。core とテストを先行し、Electron は後段に回す
sources:
  - rev194-recovered-src/
  - grinedit-bin-rev194-20070115173728.zip
  - alpha0.10-src.zip
---

# TypeScript + Web MVP

[GRINEdit](../entities/GRINEdit.md) の現代移植は TypeScript + Web を一次ターゲットにする。Java/SWT/Jython 版の延命は中止済みであり、[rev194-recovered-src](../sources/rev194-recovered-src.md)は実行対象ではなく仕様抽出元として扱う。

## 方針

- Electron は最初の実装対象にしない。まずブラウザで動く純粋な TypeScript/Web 実装を作り、Electron は後から配布形態として被せる。
- UI 互換よりも、graph model、保存形式、command API、layout step のテスト可能性を優先する。
- Jython/XML-RPC/plugin loader は直接移植しない。`CommonGateway` 相当の command layer に置き換える。
- Java の描画クラス名は互換データを読むために保持するが、Web 側の実装は Canvas 2D と TypeScript model に寄せる。

## MVP 範囲

### Core

- `Graph`
  - `All`, `Vertex`, `Edge`, `Law` の named dictionaries
  - object add/remove/modify/get
  - vertex/edge dictionaries
- `Vertex`
  - `BoxVertex`, `CircleVertex`
  - `id`, `label`, `position`, `velocity`, `selected`, basic style params
- `Edge`
  - `LinearEdge`, `TriangleEdge`, `BasicStrokeEdge`
  - `v1`, `v2`, `color`, `width`, `selected`
- Layout / physical law
  - MVP は deterministic な force step のみ
  - `PL_SpringEdge`, `PL_Repulsion`, `PL_Anchor` 相当を最小実装
- Command API
  - `initGraph`
  - `addVertex`, `addEdge`, `addObject`
  - `delVertex`, `delEdge`, `delObject`
  - `modVertex`, `modEdge`, `modObject`
  - `getVertex`, `getEdge`, `getObjects`
  - `autoLayout`, `pause`, `rendering`

### File Format

- rev194 YAML sample を fixture として読む。
- `Vertex` の短縮形 `{label: Hello}` と `{classname, params}` の両方を読む。
- `Edge` の短縮形 `[v1, v2]` と `{classname, params}` の両方を読む。
- Export は YAML/JSON の round-trip 可能な normalized form を優先する。
- Pickle は移植対象外。歴史的互換情報としてだけ扱う。

### Web UI

- 1 screen の作業画面を作る。landing page は作らない。
- Canvas で graph を表示する。
- fixture 選択、sample load、layout step/run/pause、selection 表示、YAML text preview を持つ。
- 最初の編集操作は drag vertex と add vertex/edge まででよい。
- `MO_MoveVertex` はドラッグ中に `PL_Anchor` の target を更新し、Shift なしで mouse up した場合はその場に anchor を残すため、Web UI でもドラッグした頂点はデフォルトで pin 留めする。Shift を押して離した場合は pin を解除する（[rev194-recovered-src](../sources/rev194-recovered-src.md)より）。
- Jython console、plugin menu、SWT/JFace menu 再現は MVP 外。

## Tests

最初から Vitest を入れる。

- YAML fixtures
  - `small.txt`: 4 vertices / 3 edges
  - `small2.txt`: `CircleVertex`, `TriangleEdge` の `{classname, params}` を読む
  - `testBasicStrokeEdge.txt`: `BasicStrokeEdge.width` を読む
  - `sqr10.txt`: legacy 形式の 10x10 square grid を YAML fixture に変換して読む
- Graph/command
  - `addVertex`, `addEdge`, `modVertex`, `delEdge`
  - named dictionaries と `All` の同期
- Export
  - YAML import -> normalized export -> import で vertex/edge count と params が保たれる
- Layout
  - fixed seed/initial positions で 1 step 後の位置が有限値かつ deterministic

## Directory Plan

`web/` を追加する。

- `web/src/core/`: graph, command, layout, serialization
- `web/src/fixtures/`: rev194 YAML samples copied from `rev194-recovered-src/sampleData/yaml/`
- `web/src/ui/`: Canvas renderer and controls
- `web/src/test/`: Vitest tests

## Non-goals

- SWT/JFace UI の再現
- Jython 実行環境
- Java plugin jar の runtime loading
- XML-RPC server
- Pickle compatibility
- Electron packaging

## Open Questions

- YAML export の canonical form を rev194 形式にどこまで寄せるか
- layout constants は rev194 に寄せるか、Web 操作用に調整するか
- Electron 化をいつ入れるか

## Updates

### 2026-06-10

`web/` に Vite + TypeScript + Vitest の初期実装を追加した。MVP 範囲として graph model、command layer、rev194 YAML fixture import/export、deterministic layout step、Canvas UI を実装し、Electron packaging は後段判断のまま残した。

`rev194-recovered-src/sampleData/legacy/sqr10.txt` は 10x10 の square grid サンプルとして Web fixture 化する。rev194 の `MO_MoveVertex` はドラッグした頂点を `PL_Anchor` target に入れ、Shift なしで release すると anchor を残すため、Web 版でもドラッグ後の pin 留めをデフォルト UX とする。

`web/src/fixtures/sqr10.yaml` として 100 vertices / 180 edges の 10x10 grid を追加した。Web 版は `Vertex.pinned` を持ち、layout step は pinned vertex を動かさない。Canvas ではドラッグが始まった頂点を pin 留めし、Shift を押したまま release した場合は pin を解除する。

original 挙動に寄せるため、rev194 の source から次の移植ルールを確認した。`ViewportTransformer` は graph 座標を `scale = 40.0`、`screenCenter = [300, 300]`、`center = [0, 0]` で screen 座標へ変換する。Web 版では canvas の中心を `screenCenter` とし、graph 座標と screen pixel 座標を分ける（[rev194-recovered-src](../sources/rev194-recovered-src.md)より）。

renderer は `DefaultRenderer` が全 vertex の `screenPos` を更新してから edge、vertex の順に描画する。`BoxVertex` は margin 3px の矩形、`CircleVertex` は diameter 15px、色は AWT `ColorHolder` の `GRINGREEN = [100, 200, 100]`, `SELECTED_VERTEX = [100, 100, 200]`, `SELECTED_EDGE = [200, 100, 100]` を基準にする（[rev194-recovered-src](../sources/rev194-recovered-src.md)より）。

layout は `SimpleLayout` が最大10回の反復で各 vertex の `dVelList` を初期化し、`PL_SpringEdge`, `PL_Repulsion`, `PL_Anchor` を適用して position に足し込む。`PL_SpringEdge` の default normal length は 1.0、spring strength は 0.1。`PL_Repulsion` は radius 3.0, k 0.02 で、rev194 では距離の二乗を radius の二乗で正規化している。`MassPoint.getParams()` は `position` と `velocity` を返すため、Web export も `x/y` ではなく `position/velocity` を canonical にする（[rev194-recovered-src](../sources/rev194-recovered-src.md)より）。

実装では、TS 内部の `x/y` を graph 座標として扱い、Canvas で `scale = 40` の viewport transform を適用する形に改めた。`sqr10` fixture も pixel 座標ではなく `position: [-4.5, -4.5]` から `position: [4.5, 4.5]` の graph 座標へ直した。layout は rev194 の `SimpleLayout` に合わせて spring/repulsion を iter 0 に適用し、anchor は後続 iter で target に戻す方式にした。`PL_SpringEdge` と `PL_Repulsion` の Law params も layout に反映し、legacy loader が `sqr10` 読込後に行う `defaultSpringStrength = 1.0 / max(degree)` 相当を `sqr10` fixture の `defaultSpringStrength: 0.25` として持たせた。
