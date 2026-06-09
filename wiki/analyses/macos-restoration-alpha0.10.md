---
type: analysis
summary: GRINEdit alpha0.10 を Apple Silicon macOS でソースから起動させた復元手順とハマりどころ
sources:
  - alpha0.10-src.zip
  - GRINEdit-alpha0.10.zip
---

# macOS (Apple Silicon) での alpha0.10 ソース復元

2026年に [GRINEdit](../entities/GRINEdit.md) alpha0.10（2006年頃のソース）を Apple Silicon macOS 上でソースからビルド・起動させた際の手順とハマりどころ。

## classpath 戦略
[バイナリ fat jar](../sources/GRINEdit-alpha0.10-bin.md) には Windows 用 SWT が同梱されているため、macOS ではそのままでは GUI が動かない。そこで:

1. ソースを自前でコンパイルしたクラス（`build/classes`）を classpath の**先頭**に置く
2. fat jar は **SWT 以外の依存**（Jython / JFace / XML-RPC / Eclipse runtime）の供給元として使う
3. SWT は macOS Apple Silicon 用の jar を別途用意する

## SWT のバージョン選定（JDK 制約）
- 採用: `org.eclipse.swt.cocoa.macosx.aarch64:3.128.0`（`lib/` に配置）
- Maven Central 最新の `3.134.0` は **Java 21 バイトコード**を要求するが、このマシンの JDK は **19**。そのため最新は使えず 3.128.0 を選定した。

## コンパイル時のエンコーディング
- `javac -encoding ISO-8859-1` でコンパイルする。
- 2006年のソースツリーは日本語コメント等でエンコーディングが混在しており、UTF-8 指定だと失敗する。ISO-8859-1 にすると（バイト列をそのまま通すため）コンパイルが通る。

## 欠落ファイル
- ソース zip に `config.py` が含まれていなかったため、このマシン用に `alpha0.10-src/config.py` を手で追加した。

## 再現スクリプト
- `scripts/build-alpha010.sh` — ソースを `build/classes` にコンパイル
- `scripts/run-alpha010.sh` — `-XstartOnFirstThread` 付きでアプリを起動（macOS の SWT/Cocoa は main スレッド要求のため必須）

## 動作確認（2026-06-09 JST）
`scripts/run-alpha010.sh` を実行し:
- 5秒間プロセスが生存
- `initMouseMediator.py` と `initMenu.py` のロードをログ出力
- macOS の input method 初期化を例外なく完了

確認後、テストプロセスは意図的に kill した。→ **ソースからの起動は成功**。

## Open Questions
- JDK を 21 以上に上げれば SWT 3.134.0 を使えるか、その必要があるか
- GUI を実際に操作した時の機能完全性（起動確認のみで操作確認は未実施）
- 同梱バイナリ（最新リリース）との機能差分
