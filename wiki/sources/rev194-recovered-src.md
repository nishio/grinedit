---
type: source
summary: rev194 バイナリ配布から復元した派生ソースツリー。Java 95 ファイル、Python/Jython 78 ファイル
sources:
  - grinedit-bin-rev194-20070115173728.zip
  - rev194-recovered-src/
  - scripts/extract-rev194-source.sh
  - scripts/build-rev194-recovered.sh
---

# rev194-recovered-src

[rev194 バイナリ](grinedit-bin-rev194.md)から復元した [GRINEdit](../entities/GRINEdit.md) alpha0.20 相当の派生ソースツリー。`alpha0.10-src/` は一次資料として不変に保ち、新しいコードは `rev194-recovered-src/` に分離した。

## 生成方法

`scripts/extract-rev194-source.sh` が `grinedit-bin-rev194-20070115173728.zip` から以下を抽出する。

- `grinedit-app-alpha0.20.jar` 内の `.svn/text-base/*.java.svn-base` を `src/**/*.java` に復元
- `.java.svn-base` が無かった `BasicStrokeEdge.java`, `NewMouseHandler.java`, `NewMouseMediator.java` を CFR 0.152 で class から decompile
- `pythonScripts/`, `plugins/`, `sample/`, `sampleData/`, `config.py` から、Python/Jython、YAML、テキスト、設定ファイル等のコード・データを抽出
- 依存 jar、Windows DLL、Python binary artifacts、generated `$py.class`、plugin jar は重複保存せず、一次資料 zip に残す

## 内容

- Java source: **95 files**
- Python/Jython: **78 files**
- total files: **201 files**
- `scripts/build-rev194-recovered.sh` でコンパイル検証済み
- Java 19 では `UtilCast.java` が JDK 内部 API `sun.reflect.generics.reflectiveObjects.NotImplementedException` を参照するため、`--add-exports java.base/sun.reflect.generics.reflectiveObjects=ALL-UNNAMED` が必要

コンパイル検証では `build/rev194-recovered/classes` に **93 class files** が生成された。deprecation/removal/unchecked の警告は出るが、エラーはない。

## 注意

これはオリジナルの source release ではなく、バイナリ配布物からの復元コードである。仕様抽出、alpha0.10 との差分調査、将来の Web/Electron/WASM 移植検討には有用だが、一次資料としては [rev194 バイナリ](grinedit-bin-rev194.md)を参照する。

## Open Questions

- rev194 の本来の SVN/CVS repository または source zip が別の場所に残っているか
- decompile した 3 ファイルの意味論がオリジナルソースと完全一致するか
- 復元ソースから macOS 用に実行可能な rev194 を組み立てられるか

