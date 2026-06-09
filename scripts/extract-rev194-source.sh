#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
ZIP="$ROOT_DIR/grinedit-bin-rev194-20070115173728.zip"
PREFIX="grinedit-bin-rev194-20070115173728"
OUT="$ROOT_DIR/rev194-recovered-src"
WORK="$ROOT_DIR/build/rev194-recovery"
APP_JAR="$WORK/grinedit-app-alpha0.20.jar"
CFR_VERSION="0.152"
CFR_JAR="$WORK/cfr-$CFR_VERSION.jar"

if [[ "${1:-}" == "--force" ]]; then
  rm -rf "$OUT"
elif [[ -e "$OUT" ]]; then
  echo "Refusing to overwrite $OUT. Re-run with --force to regenerate." >&2
  exit 1
fi

if [[ ! -f "$ZIP" ]]; then
  echo "Missing primary artifact: $ZIP" >&2
  exit 1
fi

mkdir -p "$WORK" "$OUT/src"

unzip -p "$ZIP" "$PREFIX/grinedit-app-alpha0.20.jar" > "$APP_JAR"

while IFS= read -r entry; do
  out_rel="$(printf '%s\n' "$entry" | sed 's#/.svn/text-base/#/#; s#\.svn-base$##')"
  mkdir -p "$OUT/src/$(dirname "$out_rel")"
  unzip -p "$APP_JAR" "$entry" > "$OUT/src/$out_rel"
done < <(jar tf "$APP_JAR" | grep '\.java\.svn-base$' | sort)

zipinfo -1 "$ZIP" | while IFS= read -r path; do
  case "$path" in
    "$PREFIX"/*) ;;
    *) continue ;;
  esac
  inner="${path#"$PREFIX"/}"
  case "$inner" in
    config.py|README.TXT|run.bat|pythonScripts/*|plugins/*|sample/*|sampleData/*) ;;
    *) continue ;;
  esac
  case "$inner" in
    *.class|*.jar|*.dll|*.pyd|*.exe|*.zip|*.png) continue ;;
  esac
  mkdir -p "$OUT/$(dirname "$inner")"
  unzip -p "$ZIP" "$path" > "$OUT/$inner"
done

if [[ ! -f "$CFR_JAR" ]]; then
  curl -fL -o "$CFR_JAR" \
    "https://repo1.maven.org/maven2/org/benf/cfr/$CFR_VERSION/cfr-$CFR_VERSION.jar"
fi

DECOMPILE_CLASSES=(
  "org/nishiohirokazu/graph/BasicStrokeEdge.class"
  "org/nishiohirokazu/grinEdit/mouseOperation/NewMouseHandler.class"
  "org/nishiohirokazu/grinEdit/mouseOperation/NewMouseMediator.class"
)

rm -rf "$WORK/classes" "$WORK/decompiled"
mkdir -p "$WORK/classes" "$WORK/decompiled"

for classfile in "${DECOMPILE_CLASSES[@]}"; do
  mkdir -p "$WORK/classes/$(dirname "$classfile")"
  unzip -p "$APP_JAR" "$classfile" > "$WORK/classes/$classfile"
  java -jar "$CFR_JAR" \
    --silent true \
    --outputdir "$WORK/decompiled" \
    "$WORK/classes/$classfile" >/dev/null
done

for src in $(find "$WORK/decompiled" -type f -name '*.java' | sort); do
  rel="${src#"$WORK/decompiled"/}"
  mkdir -p "$OUT/src/$(dirname "$rel")"
  cp "$src" "$OUT/src/$rel"
done

cat > "$OUT/README.md" <<'EOF'
# GRINEdit rev194 recovered source

This directory is a derivative source reconstruction from
`grinedit-bin-rev194-20070115173728.zip`.

Provenance:

- Most Java files under `src/` were copied from
  `grinedit-app-alpha0.20.jar` entries named
  `.svn/text-base/*.java.svn-base`.
- `BasicStrokeEdge.java`, `NewMouseHandler.java`, and
  `NewMouseMediator.java` were decompiled from class files with CFR because
  no `.java.svn-base` entries were present for them.
- Python/Jython scripts, plugin init scripts, samples, sample data, and
  `config.py` were copied from the rev194 distribution zip.
- Bundled third-party libraries, JVM dependencies, Windows DLLs, Python
  binary artifacts, generated `$py.class` files, and plugin jars are not
  duplicated here. They remain available in the primary artifact zip.

This is not an original source release. Treat it as recovered code useful for
inspection, comparison, and future porting work.
EOF

cat > "$OUT/MISSING-SOURCES.md" <<'EOF'
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
EOF

java_count=$(find "$OUT/src" -type f -name '*.java' | wc -l | tr -d ' ')
py_count=$(find "$OUT" -type f -name '*.py' | wc -l | tr -d ' ')
echo "Recovered $java_count Java files and $py_count Python/Jython files into $OUT"
