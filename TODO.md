- [x] 0.10 srcが動くことを確認する
- [x] 知見をLLM Wikiにまとめる (see wiki/, schema: CLAUDE.md)
- [x] 0.10 のsrcをGitHubにpublishする: https://github.com/nishio/grinedit
- [x] 最新のバイナリリリースとの機能の違いは？ → wiki/analyses/rev194-comparison-and-future.md
- [x] バイナリからリバースエンジニアリングできる？(私が著者です) → 可能性高。rev194 app jar に `.svn/text-base/*.java.svn-base` が 92 files
- [x] 現代においてもJava版に価値があるか検討 → 参照実装・仕様抽出には価値あり。現代アプリとしては JS/Electron/Web 移植が有望
- [x] WASMに移植することを検討 → Java bytecode 直載せより TypeScript/Web 再実装が現実的

## Next

- [ ] rev194 app jar の `.svn/text-base/*.java.svn-base` を derivation として抽出する
- [ ] `BasicStrokeEdge`, `NewMouseHandler`, `NewMouseMediator` を decompile で補完する
- [ ] rev194 を macOS の現代 SWT で起動できるか試す
- [ ] TypeScript/Web/Electron 移植の最小仕様を切る
