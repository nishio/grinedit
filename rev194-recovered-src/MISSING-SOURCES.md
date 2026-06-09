# Missing Or Derived Sources

The rev194 app jar contained 92 `.java.svn-base` source files. The following
source files were not present as `.java.svn-base` and were recovered by CFR:

- `src/org/nishiohirokazu/graph/BasicStrokeEdge.java`
- `src/org/nishiohirokazu/grinEdit/mouseOperation/NewMouseHandler.java`
- `src/org/nishiohirokazu/grinEdit/mouseOperation/NewMouseMediator.java`

Anonymous inner classes are represented by their enclosing source files.
`sample/plugins/MO_RedBackgroundWhenDoubleClicked/MO_RedBackgroundWhenDoubleClicked.jar`
is not duplicated; the corresponding source was present in the app jar as
`MO_RedBackgroundWhenDoubleClicked.java.svn-base`.
