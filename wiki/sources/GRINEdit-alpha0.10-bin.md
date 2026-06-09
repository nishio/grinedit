---
type: source
summary: GRINEdit alpha0.10 のバイナリ配布。Jython 等を同梱した fat jar
sources:
  - GRINEdit-alpha0.10.zip
---

# GRINEdit-alpha0.10-bin（バイナリ配布）

[GRINEdit](../entities/GRINEdit.md) alpha0.10 のバイナリ配布物 `GRINEdit-alpha0.10.zip`。中身は `GRINEdit-alpha0.10.jar`、すなわち以下を内包する **fat jar**:

- [Jython](../concepts/jython.md)
- JFace / Eclipse runtime クラス
- XML-RPC
- **Windows 用 SWT クラス**（当時の配布ターゲットが Windows だったことを示す）

## 利用上の含意
- 同梱 SWT が Windows 用のため、macOS でそのまま GUI 起動はできない。
- macOS でソースから実行する際は、ソースをコンパイルしたクラスを classpath の先頭に置き、この fat jar は **SWT 以外の依存（Jython/JFace/XML-RPC/Eclipse runtime）**の供給元として使う。SWT は別途プラットフォーム用 jar を用意する。
- 詳細: [macOS復元手順](../analyses/macos-restoration-alpha0.10.md)

## Open Questions
- alpha0.10 バイナリを現代 Windows 環境でそのまま起動できるか

## Updates

- 2026-06-09: 最新候補として [rev194 バイナリ](grinedit-bin-rev194.md)を取得し、alpha0.10 との差分を[rev194 比較と将来判断](../analyses/rev194-comparison-and-future.md)に整理。
