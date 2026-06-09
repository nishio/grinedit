---
type: concept
summary: JVM 上で動く Python 実装。GRINEdit が GUI 挙動のスクリプティングに採用
sources:
  - alpha0.10-src.zip
  - GRINEdit-alpha0.10.zip
  - grinedit-bin-rev194-20070115173728.zip
---

# Jython

JVM 上で動作する Python 実装。[GRINEdit](../entities/GRINEdit.md) は GUI の挙動（メニュー構築・マウス操作のメディエーション等）を Jython スクリプト（`pythonScripts/` の `initMenu.py` / `initMouseMediator.py` 等）で組み立てる設計を採る。

- [ソース版](../sources/alpha0.10-src.md)では `pythonScripts/` に 35 本の Jython/Python スクリプトが同梱される。
- [バイナリ版](../sources/GRINEdit-alpha0.10-bin.md)の fat jar は Jython 本体を内包する。
- この「Java コア + Jython スクリプトで拡張」という構成は、WASM 移植など将来の再実装を検討する際の論点になる（Jython 依存をどう扱うか）。

## Open Questions
- WASM 移植時、Jython スクリプト層を Python（Pyodide 等）で再現するか、JS/別言語に書き換えるか

## Updates

- 2026-06-09: [rev194 バイナリ](../sources/grinedit-bin-rev194.md)では `jython-2.1.jar` に加えて `JyConsole-1.3.jar` が依存として分離され、`menu-jyconsole` plugin からコンソールを開く構成が確認できた。
