#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SRC_DIR="$ROOT_DIR/rev194-recovered-src"
ZIP="$ROOT_DIR/grinedit-bin-rev194-20070115173728.zip"
PREFIX="grinedit-bin-rev194-20070115173728"
BUILD_DIR="$ROOT_DIR/build/rev194-recovered"
DEPS_DIR="$BUILD_DIR/dependency"
CLASSES_DIR="$BUILD_DIR/classes"

if [[ ! -d "$SRC_DIR/src" ]]; then
  echo "Missing recovered source tree: $SRC_DIR" >&2
  echo "Run scripts/extract-rev194-source.sh first." >&2
  exit 1
fi

if [[ ! -f "$ZIP" ]]; then
  echo "Missing primary artifact: $ZIP" >&2
  exit 1
fi

rm -rf "$BUILD_DIR"
mkdir -p "$DEPS_DIR" "$CLASSES_DIR"

zipinfo -1 "$ZIP" |
  grep "^$PREFIX/dependency/.*\\.jar$" |
  while IFS= read -r p; do
    unzip -p "$ZIP" "$p" > "$DEPS_DIR/${p##*/}"
  done

find "$SRC_DIR/src" -type f -name '*.java' | sort > "$BUILD_DIR/sources.list"
classpath="$(find "$DEPS_DIR" -type f -name '*.jar' | sort | paste -sd: -)"

javac \
  --add-exports java.base/sun.reflect.generics.reflectiveObjects=ALL-UNNAMED \
  -encoding ISO-8859-1 \
  -Xlint:none \
  -nowarn \
  -classpath "$classpath" \
  -d "$CLASSES_DIR" \
  @"$BUILD_DIR/sources.list"
