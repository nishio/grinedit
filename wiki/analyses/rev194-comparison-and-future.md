---
type: analysis
summary: TODO の未完了項目に対する rev194 比較、逆復元可能性、Java/WASM/GitHub 公開判断
sources:
  - alpha0.10-src.zip
  - GRINEdit-alpha0.10.zip
  - grinedit-bin-rev194-20070115173728.zip
  - rev194-recovered-src/
  - raw/init.md
---

# rev194 比較と将来判断

2026-06-09 に `TODO.md` の未完了項目を調査した結果。主な比較対象は [alpha0.10 ソース](../sources/alpha0.10-src.md)、[alpha0.10 バイナリ](../sources/GRINEdit-alpha0.10-bin.md)、[rev194 バイナリ](../sources/grinedit-bin-rev194.md)。

## 結論

公開ミラー上で確認した最新候補は `grinedit-bin-rev194-20070115173728.zip`。JAIST ミラーでは 2006-06-10 の alpha0.10、2006-11-28 の rev187、2007-01-15 の rev194 が確認でき、rev194 が最も新しい（[rev194 バイナリ](../sources/grinedit-bin-rev194.md)より）。

alpha0.10 と rev194 は、単なる小修正ではなく設計がかなり変わっている。rev194 では `Main-Class` が `GraphVisualizerTest` から `GRINEdit` へ変わり、fat jar から app jar + dependency jar へ移行し、plugin ディレクトリ、YAML/Pickle、JyConsole、CommonGateway、物理法則クラス群が追加されている（[alpha0.10 バイナリ](../sources/GRINEdit-alpha0.10-bin.md)、[rev194 バイナリ](../sources/grinedit-bin-rev194.md)より）。

バイナリからの逆復元は可能性が高い。rev194 の app jar には `.svn/text-base/*.java.svn-base` が 92 ファイル残っており、Java class 93 件の大半はソース断片から直接読める。追加で decompile が必要なのは、`BasicStrokeEdge`, `NewMouseHandler`, `NewMouseMediator` など少数に限られる（[rev194 バイナリ](../sources/grinedit-bin-rev194.md)より）。

現代で Java 版をそのまま発展させる価値は「考古学的復元・仕様抽出」にはあるが、「新規利用されるアプリ」としては低い。SWT/JFace/Jython 2.1/XML-RPC/JYAML/JyConsole など依存が古く、UI 配布も Windows SWT 前提であるため、長期的な価値は JS/Electron または Web/WASM 系への仕様移植で得る方が大きい（[macOS 復元手順](macos-restoration-alpha0.10.md)、[rev194 バイナリ](../sources/grinedit-bin-rev194.md)より）。

## alpha0.10 から rev194 への主な差分

- 配布構造: alpha0.10 は Jython/JFace/XML-RPC/SWT を内包した fat jar。rev194 は `grinedit-app-alpha0.20.jar` と `dependency/*.jar` に分離（[alpha0.10 バイナリ](../sources/GRINEdit-alpha0.10-bin.md)、[rev194 バイナリ](../sources/grinedit-bin-rev194.md)より）。
- 起動クラス: alpha0.10 は `org.nishiohirokazu.grinEdit.GraphVisualizerTest`。rev194 は `org.nishiohirokazu.grinEdit.GRINEdit`（[alpha0.10 ソース](../sources/alpha0.10-src.md)、[rev194 バイナリ](../sources/grinedit-bin-rev194.md)より）。
- 永続化: alpha0.10 には `MA_LoadJSON` / `MA_SaveAsJSON` がある。rev194 では JSON menu action が消え、`MA_SaveAsYaml`、`plugins/grinedit-app/menu-YAML/init.py`、`menu-pickle/init.py`、`sampleData/yaml/` が確認できる。ただし YAML 保存メニューは JYAML のバグに触れたコメントで無効化されており、確認済みの主張は「YAML 読込プラグインと YAML 保存実装が存在」に留める（[alpha0.10 ソース](../sources/alpha0.10-src.md)、[rev194 バイナリ](../sources/grinedit-bin-rev194.md)より）。
- Plugin: rev194 は `PluginClassLoader` により `plugins/` 以下の jar を再帰探索し、`Mediator.loadClass` が通常 classpath で見つからない class を plugin classloader から読む（[rev194 バイナリ](../sources/grinedit-bin-rev194.md)より）。
- GUI/console: rev194 は `GRINEditDefaultGUI` と `JyConsole-1.3.jar` を持ち、`menu-jyconsole` plugin から JyConsole を開く構成（[rev194 バイナリ](../sources/grinedit-bin-rev194.md)、[Jython](../concepts/jython.md)より）。
- レイアウト/物理法則: rev194 は `ILayout`, `SimpleLayout`, `SeparateComponentLayout`, `SparceLayout`, `PL_*` 物理法則群を追加し、`config.py` で `DEFAULT_PHYSICAL_LAWS` と `LAYOUT_ENGINE` を指定する（[rev194 バイナリ](../sources/grinedit-bin-rev194.md)より）。
- 外部操作: rev194 は `CommonGateway` に `addVertex`, `addEdge`, `mod*`, `get*`, `batch`, `autoLayout`, `pause`, `rendering` などの共通 API を持ち、XML-RPC/Jython/保存形式の共通入口になっている（[rev194 バイナリ](../sources/grinedit-bin-rev194.md)より）。

## GitHub 公開判断

alpha0.10 ソースを GitHub に出す前提は妥当。著者本人がオーナーで、[GRINEdit](../entities/GRINEdit.md)ページでも権利上の制約はないと整理済み。2026-06-10 に public repo <https://github.com/nishio/grinedit> として公開した。公開対象は、一次資料 zip、展開済み alpha0.10 ソース、復元スクリプト、wiki。`build/`, `lib/`, `raw/`, `alpha0.10-src/cachedir/` は生成物または未整理メモとして公開対象から外した。

rev194 は source zip ではなく binary zip なので、GitHub 公開時は「rev194 のソース」としてではなく、一次資料バイナリとして扱うのが安全。復元ソースを作る場合は別ディレクトリに derivation として明示する。

## Java 版の価値

Java 版は、オリジナル挙動を観察する参照実装として価値がある。特に、rev194 の `CommonGateway`、plugin loader、保存形式、物理法則、レイアウト API は移植時の仕様抽出に使える（[rev194 バイナリ](../sources/grinedit-bin-rev194.md)より）。

一方、現代アプリとして Java/SWT/Jython のまま発展させる価値は限定的。Jython 2.1、SWT/JFace、古い XML-RPC/JYAML/JyConsole への依存が強く、macOS 復元でも SWT の差し替えと `-XstartOnFirstThread` が必要だった（[macOS 復元手順](macos-restoration-alpha0.10.md)、[Jython](../concepts/jython.md)より）。

## WASM / Web 移植の方向

WASM 移植は「Java bytecode をそのまま WASM に載せる」より、「グラフモデル、物理法則、CommonGateway 相当 API、plugin/保存形式を JS/TypeScript に再実装する」方が現実的。理由は GUI が SWT/AWT 混在であり、Jython スクリプト層も JVM 前提だから（[rev194 バイナリ](../sources/grinedit-bin-rev194.md)、[Jython](../concepts/jython.md)より）。

Electron 化または Web アプリ化では、Canvas/WebGL 上に renderer と layout loop を置き、YAML/JSON/Pickle のうち現代で扱いやすい YAML/JSON を移植対象にするのがよい。Pickle は Python/Jython 依存の歴史的互換機能として扱うのが無難。

## 推奨次アクション

1. rev194 app jar の `.svn/text-base/*.java.svn-base` を derivation として抽出し、欠けている `BasicStrokeEdge`, `NewMouseHandler`, `NewMouseMediator` を decompile で補完する。
2. rev194 を macOS の現代 SWT で起動できるか試す。成功すれば alpha0.20 の動作観察が可能になる。
3. 移植設計では Java UI を延命するより、rev194 から仕様を抽出して TypeScript 実装に寄せる。

## Open Questions

- rev194 の source zip または SVN/CVS 履歴はどこかに残っているか
- rev194 は macOS で依存差し替えにより実行できるか
- YAML 保存が JYAML バグにより無効化されている件は、実際の現代 JVM でも再現するか
- WASM と Electron のどちらを最初の移植ターゲットにするか

## Updates

- 2026-06-10: `nishio/grinedit` を public GitHub repository として作成し、初回コミット `0e679e5` を `main` に push。
- 2026-06-10: standalone source zip は見つからなかったが、rev194 バイナリ内の `.svn/text-base` と class decompile から [rev194-recovered-src](../sources/rev194-recovered-src.md)を生成。`scripts/build-rev194-recovered.sh` で Java 19 コンパイル検証済み。
