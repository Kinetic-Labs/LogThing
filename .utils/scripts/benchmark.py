#!/usr/bin/env python3
import subprocess, re, statistics, concurrent.futures, threading, sys, os, shutil

lock = threading.Lock()
cols = shutil.get_terminal_size().columns


def run(name, cmd):
    times = []
    for i in range(0, 100):
        with lock:
            print(f"{name}: {i}/99".ljust(cols), end="\r", flush=True)
        try:
            out = subprocess.check_output(cmd, shell=True, text=True, stderr=subprocess.DEVNULL)
            times += [float(m) for m in re.findall(r'Bench:\s+([\d.]+)ms', out)]
        except Exception:
            pass
    with lock:
        print(f"{name}: done".ljust(cols))
    return name, times if times else [0]


with concurrent.futures.ThreadPoolExecutor() as ex:
    fut = [ex.submit(run, n, c) for n, c in (
        ("Bun", "pnpm bench:bun"),
        ("Deno", "pnpm bench:deno"),
        ("Node", "pnpm bench:node"))]
    res = dict(f.result() for f in concurrent.futures.as_completed(fut))

print("\nRuntime (ms)")
print(f"Bun:  {round(statistics.mean(res['Bun']), 2)}")
print(f"Deno: {round(statistics.mean(res['Deno']), 2)}")
print(f"Node: {round(statistics.mean(res['Node']), 2)}")
