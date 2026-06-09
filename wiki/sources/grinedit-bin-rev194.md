---
type: source
summary: GRINEdit の最新候補バイナリ配布。rev194 / alpha0.20 相当の Windows 向け配布 zip
sources:
  - grinedit-bin-rev194-20070115173728.zip
  - https://ftp.jaist.ac.jp/pub/sourceforge.jp/rel-visualizer/
  - https://ftp.jaist.ac.jp/pub/sourceforge.jp/rel-visualizer/23582/
---

# grinedit-bin-rev194-20070115173728

[GRINEdit](../entities/GRINEdit.md) の最新候補バイナリ配布物。JAIST の SourceForge.jp ミラー `rel-visualizer/23582/` にある `grinedit-bin-rev194-20070115173728.zip`。

## 入手と同定

2026-06-09 に JAIST ミラーから取得した。

- URL: <https://ftp.jaist.ac.jp/pub/sourceforge.jp/rel-visualizer/23582/grinedit-bin-rev194-20070115173728.zip>
- ミラー上の更新時刻: 2007-01-15 17:55
- 表示サイズ: 7.5M
- 手元ファイルサイズ: 7,912,625 bytes
- SHA-256: `7409334ad74556715ca33f755e0ea431d3af7faaa4d57547d8358fafdb84b95b`

JAIST ミラーの `rel-visualizer/` には `20479/`, `22881/`, `23582/` があり、それぞれ `GRINEdit-alpha0.10.zip`（2006-06-10）, `grinedit-bin-rev187-20061128162223.zip`（2006-11-28）, `grinedit-bin-rev194-20070115173728.zip`（2007-01-15）を含む。したがって手元で確認した公開ミラー上では rev194 が最新候補。

## 内容

rev194 は [alpha0.10 バイナリ](GRINEdit-alpha0.10-bin.md)の fat jar 形式とは異なり、アプリ jar と依存 jar を分けた配布になっている。

- `grinedit-app-alpha0.20.jar`: アプリ本体。manifest の `Main-Class` は `org.nishiohirokazu.grinEdit.GRINEdit`
- `StartGRINEdit.jar`: `org.nishiohirokazu.jarRunner.JarRunner` を `Main-Class` に持つスタータ jar
- `dependency/`: `jython-2.1.jar`, `jface-3.0.1.jar`, `swt-win32-3.2.jar`, `xmlrpc-2.0.1.jar`, `jyaml-lib-1.0-beta-3.jar`, `JyConsole-1.3.jar` など
- `plugins/`: `BasicStrokeEdge`, `grinedit-app/menu-*`, `plugins/test`
- `sample/`: Jython, plugin, XML-RPC, demo, website-as-graph などのサンプル
- `sampleData/yaml/`: YAML 形式のサンプルデータ
- `swt-win32-3232.dll`, `swt-awt-win32-3232.dll`: Windows SWT DLL

## 実装上の手掛かり

`grinedit-app-alpha0.20.jar` の manifest は Maven でのビルド、`Build-Jdk: 1.5.0_07`、`Main-Class: org.nishiohirokazu.grinEdit.GRINEdit` を示す。`Mediator.VERSION_STR` は `GRINEdit alpha 0.20`。

アプリ jar には `.svn/text-base/*.java.svn-base` が 92 ファイル残っている。これは [alpha0.10 ソース](alpha0.10-src.md)が CVS metadata を持つのに対し、rev194 時点の開発作業コピーが SVN 系の痕跡を持つことを示す。

## 利用上の含意

- 配布物は Windows SWT を同梱しているため、macOS ではそのまま GUI 起動できない。
- アプリ本体の Java ソース断片と Python/Jython スクリプトは配布物内に多く残っているため、rev194 のソース復元は現実的。
- `BasicStrokeEdge.jar` と `MO_RedBackgroundWhenDoubleClicked.jar` は class のみで、別途 `javap` または decompiler が必要。

## Updates

- 2026-06-10: この一次資料から [rev194-recovered-src](rev194-recovered-src.md)を生成。Java 95 ファイル、Python/Jython 78 ファイルを復元し、Java 19 でコンパイル検証済み。

## Open Questions

- rev194 を macOS の現代 SWT で起動できるか
- rev187 と rev194 の差分は何か
- SourceForge/SourceForge.jp 以外に rev194 以降のバイナリまたはソースが残っているか
