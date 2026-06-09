---
type: source
summary: GRINEdit alpha0.10 のソース配布。SourceForge grinedit/alpha-0.10 の alpha0.10-src.zip
sources:
  - alpha0.10-src.zip
---

# alpha0.10-src（GRINEdit alpha0.10 ソース）

[GRINEdit](../entities/GRINEdit.md) alpha0.10 のソース配布物。SourceForge `grinedit/alpha-0.10` の `alpha0.10-src.zip` を `alpha0.10-src/` に展開したもの。

## 内容
- Java ソース **63 ファイル**
- Python/[Jython](../concepts/jython.md) スクリプト **35 ファイル**（`pythonScripts/`。`initMenu.py`, `initMouseMediator.py`, `legacyLoader.py` 等）
- CVS metadata（`:ext:hiroka-n@cvs.sourceforge.jp:/cvsroot/rel-visualizer` を指す）
- `pythonScripts/sample` 以下にバンドルされた生成済み/ランタイム成果物
- メインクラス: `org.nishiohirokazu.grinEdit.GraphVisualizerTest`

## 注意点 / 欠落
- ソース zip には **`config.py` が含まれていなかった**。このマシン用にローカルの `alpha0.10-src/config.py` を別途追加した。
- 2006年当時のソースツリーは日本語コメント等で**エンコーディングが混在**している。コンパイルは `javac -encoding ISO-8859-1` で通る（[macOS復元手順](../analyses/macos-restoration-alpha0.10.md)参照）。

## 関連
- バイナリ配布: [GRINEdit-alpha0.10-bin](GRINEdit-alpha0.10-bin.md)
- 復元手順とハマりどころ: [macOS復元手順](../analyses/macos-restoration-alpha0.10.md)
