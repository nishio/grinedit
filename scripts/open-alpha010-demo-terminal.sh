#!/usr/bin/env bash
set -euo pipefail

osascript <<'APPLESCRIPT'
tell application "Terminal"
  activate
  do script "cd /Users/nishio/grinedit && ./scripts/run-alpha010-demo.sh"
end tell
APPLESCRIPT

