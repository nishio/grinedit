#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SRC_DIR="$ROOT_DIR/alpha0.10-src"
BUILD_DIR="$ROOT_DIR/build"
CLASSES_DIR="$BUILD_DIR/classes"
SWT_VERSION="3.128.0"
SWT_JAR="$ROOT_DIR/lib/org.eclipse.swt.cocoa.macosx.aarch64-$SWT_VERSION.jar"
BIN_JAR="$ROOT_DIR/GRINEdit-alpha0.10-bin/GRINEdit-alpha0.10/GRINEdit-alpha0.10.jar"

mkdir -p "$ROOT_DIR/lib" "$CLASSES_DIR"

if [[ ! -f "$SWT_JAR" ]]; then
  curl -fL -o "$SWT_JAR" \
    "https://repo1.maven.org/maven2/org/eclipse/platform/org.eclipse.swt.cocoa.macosx.aarch64/$SWT_VERSION/org.eclipse.swt.cocoa.macosx.aarch64-$SWT_VERSION.jar"
fi

if [[ ! -f "$BIN_JAR" ]]; then
  echo "Missing dependency jar: $BIN_JAR" >&2
  echo "Extract GRINEdit-alpha0.10.zip first." >&2
  exit 1
fi

find "$SRC_DIR/src" -type f -name '*.java' | sort > "$BUILD_DIR/sources.list"

if ! javac -encoding ISO-8859-1 -Xlint:none -nowarn \
  -classpath "$SWT_JAR:$BIN_JAR" \
  -d "$CLASSES_DIR" \
  @"$BUILD_DIR/sources.list" \
  2> "$BUILD_DIR/javac.log"; then
  cat "$BUILD_DIR/javac.log" >&2
  exit 1
fi
rm -f "$BUILD_DIR/javac.log"
