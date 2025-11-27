#!/usr/bin/env python3
from pathlib import Path

files = ('node.txt', 'deno.txt', 'bun.txt')

for name in files:
    path = Path(name)
    if not path.exists():
        print(f"{name}: file not found")
        continue

    total = 0
    for raw in path.read_text().splitlines():
        try:
            total += float(raw)
        except ValueError:
            continue

    print(f"{name}: {total}")
