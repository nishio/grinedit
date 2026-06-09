#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SAMPLE_FILE="${1:-$ROOT_DIR/GRINEdit-alpha0.10-bin/GRINEdit-alpha0.10/sampleData/legacy/petersen_colored.txt}"

if [[ ! -f "$SAMPLE_FILE" ]]; then
  echo "Missing sample file: $SAMPLE_FILE" >&2
  exit 1
fi

"$ROOT_DIR/scripts/build-alpha010.sh"

cd "$ROOT_DIR/alpha0.10-src"

exec java -XstartOnFirstThread \
  -Dgrinedit.demo.legacy="$SAMPLE_FILE" \
  -cp "../build/classes:../lib/org.eclipse.swt.cocoa.macosx.aarch64-3.128.0.jar:../GRINEdit-alpha0.10-bin/GRINEdit-alpha0.10/GRINEdit-alpha0.10.jar" \
  org.nishiohirokazu.grinEdit.GraphVisualizerTest
