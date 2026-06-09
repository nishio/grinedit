# Wiki Index

GRINEdit（西尾泰和が2006年頃に開発したグラフ/関係エディタ）を現代環境で復元・再評価し、将来の展開を判断するためのナレッジベース。

## Concepts

- [Jython](concepts/jython.md) — JVM 上の Python 実装。GRINEdit が GUI 挙動のスクリプティングに採用

## Entities

- [GRINEdit](entities/GRINEdit.md) — グラフ/関係エディタ本体。Jython でスクリプト可能な SWT デスクトップアプリ
- [西尾泰和 (nishio-hirokazu)](entities/nishio-hirokazu.md) — GRINEdit の原作者。本KBのオーナー

## Sources

- [alpha0.10-src](sources/alpha0.10-src.md) — alpha0.10 ソース配布（Java 63 + Jython 35 ファイル）
- [GRINEdit-alpha0.10-bin](sources/GRINEdit-alpha0.10-bin.md) — alpha0.10 バイナリ配布（Jython 等同梱の fat jar）
- [grinedit-bin-rev194](sources/grinedit-bin-rev194.md) — 最新候補バイナリ配布（alpha0.20 相当、plugin/YAML/JyConsole 等を含む）
- [rev194-recovered-src](sources/rev194-recovered-src.md) — rev194 バイナリから復元した派生ソース（Java 95 + Python/Jython 78 ファイル）

## Analyses

- [macOS復元手順 alpha0.10](analyses/macos-restoration-alpha0.10.md) — Apple Silicon macOS でソースから起動させた手順とハマりどころ
- [rev194 比較と将来判断](analyses/rev194-comparison-and-future.md) — TODO の未完了項目に対する差分・逆復元・Java/WASM/GitHub 公開判断
- [TypeScript + Web MVP](analyses/typescript-web-mvp.md) — TypeScript/Web 移植の最小仕様。core とテスト先行、Electron は後段
