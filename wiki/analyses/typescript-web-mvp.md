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
- Jython console、plugin menu、SWT/JFace menu 再現は MVP 外。

## Tests

最初から Vitest を入れる。

- YAML fixtures
  - `small.txt`: 4 vertices / 3 edges
  - `small2.txt`: `CircleVertex`, `TriangleEdge` の `{classname, params}` を読む
  - `testBasicStrokeEdge.txt`: `BasicStrokeEdge.width` を読む
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
