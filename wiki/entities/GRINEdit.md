---
type: entity
summary: 西尾泰和が2006年頃に開発したグラフ/関係エディタ。Jython でスクリプト可能な SWT デスクトップアプリ
sources:
  - alpha0.10-src.zip
  - GRINEdit-alpha0.10.zip
  - grinedit-bin-rev194-20070115173728.zip
  - rev194-recovered-src/
---

# GRINEdit

GRINEdit は[西尾泰和](nishio-hirokazu.md)が2006年頃に開発したグラフ/関係（relation）エディタ・ビジュアライザ。SourceForge.jp の CVS プロジェクト `rel-visualizer` がリポジトリ。

## 基本情報
- メインクラス: `org.nishiohirokazu.grinEdit.GraphVisualizerTest`（[alpha0.10-src](../sources/alpha0.10-src.md)より）
- 実装: Java（SWT/JFace ベースの GUI）+ [Jython](../concepts/jython.md) スクリプトで挙動を拡張する構成
- CVS metadata: `:ext:hiroka-n@cvs.sourceforge.jp:/cvsroot/rel-visualizer`
- 構成規模（alpha0.10 ソース）: Java 63ファイル + Python/Jython 35スクリプト
- 起動時に `initMouseMediator.py` / `initMenu.py` などの Jython スクリプトを読み込んで GUI を組み立てる

著者本人（西尾）がオーナーのため、リバースエンジニアリング・再配布・移植に権利上の制約はない。

## バージョン
- **alpha0.10** — 現在手元にある最古の版。[ソース zip](../sources/alpha0.10-src.md) と[バイナリ zip](../sources/GRINEdit-alpha0.10-bin.md) の両方が残っている。2026-06-09 に macOS (Apple Silicon) でソースからの起動を確認（[macOS復元手順](../analyses/macos-restoration-alpha0.10.md)）。
- **rev194 / alpha0.20 相当** — 手元で確認した公開ミラー上の最新候補バイナリ。[rev194 バイナリ](../sources/grinedit-bin-rev194.md)として取得済み。バイナリ内の残存ソースから [rev194-recovered-src](../sources/rev194-recovered-src.md)を復元済み。alpha0.10 との差分は[rev194 比較と将来判断](../analyses/rev194-comparison-and-future.md)に整理。

## Open Questions
- rev194 を現代 macOS で起動できるか
- rev194 以降のバイナリまたはソースが残っているか

## Updates

- 2026-06-09: JAIST ミラーから `grinedit-bin-rev194-20070115173728.zip` を取得し、公開ミラー上の最新候補として記録（[rev194 バイナリ](../sources/grinedit-bin-rev194.md)より）。
- 2026-06-10: rev194 バイナリから [rev194-recovered-src](../sources/rev194-recovered-src.md)を復元し、コンパイル検証まで完了。
