# Log

## [2026-06-09 21:36] setup | LLM Wiki を grinedit プロジェクトに instantiate

`llm-wiki.md` のパターンに従い、GRINEdit プロジェクト用の LLM Wiki を scaffold。
- `CLAUDE.md`（スキーマ）作成。GitHub公開予定のため GFM リンク方式を採用（monika-mentoring-wiki 準拠）
- `wiki/{index,log,concepts,entities,sources,analyses}` を作成
- `scripts/lint_wiki.py`（GFM対応版）を配置
- `llm-wiki.md` 末尾の「GRINEdit alpha0.10 restoration notes」を以下のページに migrate:
  - entities: [GRINEdit](entities/GRINEdit.md), [西尾泰和](entities/nishio-hirokazu.md)
  - sources: [alpha0.10-src](sources/alpha0.10-src.md), [GRINEdit-alpha0.10-bin](sources/GRINEdit-alpha0.10-bin.md)
  - concepts: [Jython](concepts/jython.md)
  - analyses: [macOS復元手順 alpha0.10](analyses/macos-restoration-alpha0.10.md)
- 親 wiki森 の `~/llm-wiki/wikis.yaml` に grinedit を登録

## [2026-06-09 23:59] ingest | rev194 最新候補バイナリを取得

JAIST の SourceForge.jp ミラーから `grinedit-bin-rev194-20070115173728.zip` を取得し、[grinedit-bin-rev194](sources/grinedit-bin-rev194.md)として source ページ化。
- ミラー上では `20479/` に alpha0.10、`22881/` に rev187、`23582/` に rev194 があり、手元で確認した公開ミラー上では rev194 が最新候補
- rev194 は fat jar ではなく app jar + dependency jar 形式
- app jar に `.svn/text-base/*.java.svn-base` が 92 ファイル残っていることを確認

## [2026-06-09 23:59] filing-back | TODO 未完了項目の判断を整理

`TODO.md` の「最新バイナリとの差分」「バイナリからリバースエンジニアリングできるか」「Java 版の価値」「WASM 移植検討」を[rev194 比較と将来判断](analyses/rev194-comparison-and-future.md)に filing back。
- alpha0.10 と rev194 の設計差分を整理
- rev194 の逆復元は `.svn/text-base` により大半が可能、欠落 class は少数と判断
- Java 版は参照実装・仕様抽出として価値があり、現代アプリとしては TypeScript/Web/Electron 系への移植が有望と判断

## [2026-06-09 23:59] lint | rev194 追加後の wiki lint

`python3 scripts/lint_wiki.py` を実行し、重複ページID 0、孤立ページ 0、壊れたリンク 0、index 未登録 0、frontmatter 不備 0 を確認。
