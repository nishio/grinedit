# grinedit

## テーマ
GRINEdit（西尾泰和が2006年頃に開発したグラフ/関係エディタ、SourceForge `rel-visualizer`）を現代環境で復元・再評価し、将来の展開（GitHub 公開、Java版の価値検討、WASM 移植）を判断するためのナレッジベース。

リポジトリは公開予定なので、ソースコード（`alpha0.10-src/` 等）と wiki を同一 repo に同居させる。著者本人（西尾）がオーナーであり、リバースエンジニアリングや再配布の権利上の制約はない。

## ディレクトリ構造

```
grinedit/
├── CLAUDE.md              # このファイル（スキーマ）
├── llm-wiki.md            # 元のidea file（参考、末尾に初期の復元メモ）
├── TODO.md                # 残タスク
├── alpha0.10-src/         # 復元したソース（一次資料、不変）
├── GRINEdit-alpha0.10-bin/ , *.zip   # バイナリ配布物（一次資料、不変）
├── scripts/               # 運用スクリプト
│   ├── build-alpha010.sh  # ソースを build/classes へコンパイル
│   ├── run-alpha010.sh    # macOS で起動（-XstartOnFirstThread）
│   └── lint_wiki.py       # wikiの健全性チェック
├── raw/                   # 取り込み待ちの生ソース（記事・調査メモ等、gitignore候補）
└── wiki/                  # LLMが生成・維持するwiki
    ├── index.md           # 全ページのカタログ
    ├── log.md             # 時系列の作業記録
    ├── concepts/          # 概念・技術ページ（SWT, Jython 等）
    ├── entities/          # ソフトウェア・人物・ツール・バージョン
    ├── sources/           # 一次資料（ソースzip・バイナリ）の要約
    └── analyses/          # 問い・調査から生まれた考察（復元手順・比較・移植検討）
```

## ページルール

### 全ページ共通
- 冒頭にYAMLフロントマター：type, summary, sources
- 主張には出典を明記：`[source名](相対パス.md)より` の形式で
- 矛盾・未解決の論点は「## Open Questions」セクションで明示
- 更新は上書きせず「## Updates」で追記

### ページ間リンク
- GitHubで閲覧してもクリック可能なように **GitHub-flavored Markdown link** を使う: `[text](相対パス.md)`
- 例: 同ディレクトリ `[alpha0.10-src](alpha0.10-src.md)`、別ディレクトリ `[GRINEdit](../entities/GRINEdit.md)`
- Obsidian的な `[[name]]` 形式は使わない（GitHub上でクリックできない）
- lint scriptが basename で resolve するので一意なファイル名が前提

### フロントマター例
```yaml
---
type: source
summary: 1文で説明
sources:
  - alpha0.10-src.zip
---
```

## 操作

### Ingest（ソース取り込み）
1. raw/ の新ファイル、または alpha0.10-src/ 内のコード断片を読む（`a.txt` 等は適切にリネーム）
2. 既存wikiページと照合
3. 関連ページを更新 or 新規作成
4. index.md を更新
5. log.md に `## [YYYY-MM-DD HH:MM] ingest | <description>` を記録

### Query（質問）
1. wiki/ を検索して回答を作成
2. 有用な回答は analyses/ に filing back
3. log.md に `## [YYYY-MM-DD HH:MM] filing-back | <description>` を記録

### Lint（健全性チェック）
1. 機械的: `python3 scripts/lint_wiki.py`（孤立・壊れたリンク・未登録など）
2. 意味的: 矛盾・stale claim・概念ページ不足・新質問の提案
3. 完了後 `## [YYYY-MM-DD HH:MM] lint | <summary>` を log.md に記録

> 時刻を含めるのは、深夜lintと同日ingestの順序を区別するため。実際の時刻を確認してから記録する。

## 運用方針
- 一次資料（ソース・バイナリ）は不変。wiki だけを LLM が編集する
- ソースの主張も無批判に採用せず、実際に動かして確かめた事実を重視する
- スキーマ（このファイル）も実験を通じて改善していく
