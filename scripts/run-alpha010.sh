#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

"$ROOT_DIR/scripts/build-alpha010.sh"

cd "$ROOT_DIR/alpha0.10-src"

exec java -XstartOnFirstThread \
  -cp "../build/classes:../lib/org.eclipse.swt.cocoa.macosx.aarch64-3.128.0.jar:../GRINEdit-alpha0.10-bin/GRINEdit-alpha0.10/GRINEdit-alpha0.10.jar" \
  org.nishiohirokazu.grinEdit.GraphVisualizerTest

