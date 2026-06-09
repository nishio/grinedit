- [x] 0.10 srcが動くことを確認する
- [x] 知見をLLM Wikiにまとめる (see wiki/, schema: CLAUDE.md)
- [x] 0.10 のsrcをGitHubにpublishする: https://github.com/nishio/grinedit
- [x] 最新のバイナリリリースとの機能の違いは？ → wiki/analyses/rev194-comparison-and-future.md
- [x] バイナリからリバースエンジニアリングできる？(私が著者です) → 可能性高。rev194 app jar に `.svn/text-base/*.java.svn-base` が 92 files
- [x] 現代においてもJava版に価値があるか検討 → 参照実装・仕様抽出には価値あり。現代アプリとしては JS/Electron/Web 移植が有望
- [x] WASMに移植することを検討 → Java bytecode 直載せより TypeScript/Web 再実装が現実的

## Next

- [x] rev194 app jar の `.svn/text-base/*.java.svn-base` を derivation として抽出する → rev194-recovered-src/
- [x] `BasicStrokeEdge`, `NewMouseHandler`, `NewMouseMediator` を decompile で補完する
- [x] rev194 を macOS の現代 SWT で起動できるか試す → 中止。挙動観察が十分できておらず、Java/SWT 版の延命は無理と判断
- [x] TypeScript/Web/Electron 移植の最小仕様を切る → wiki/analyses/typescript-web-mvp.md

## Web MVP

- [x] TypeScript + Web core を実装する
- [x] rev194 YAML fixture tests を追加する
- [x] 最小 Canvas UI を実装する

## Next Web

- [ ] YAML textarea からの import/edit を実装する
- [ ] Canvas 操作の Playwright/Vitest browser test を追加する
- [ ] Electron packaging の要否を再判断する
