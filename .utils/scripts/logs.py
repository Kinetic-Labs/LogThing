#!/usr/bin/env python3

import os
import random
import string
from datetime import datetime

NUM_LOGS = 10
LOGS_PER_FILE = 1000
WORDS_PER_LOG = (10, 30)
OUTPUT_DIR = "env/demo_logs"
MODULE_IDS = range(1000, 9999)
SEVERITIES = ["[INFO]", "[WARN]", "[DEBUG]", "[ERROR]"]
MODULE_TAGS = ["[A]", "[B]", "[C]", "[D]"]
WORDS = [
    "system", "module", "config", "error", "network", "update", "thread", "signal",
    "cache", "process", "input", "output", "driver", "kernel", "session", "buffer",
    "request", "response", "timeout", "event", "packet", "stream", "queue", "log",
    "async", "handler", "state", "debug", "memory", "cpu", "disk", "auth", "sync",
    "load", "service", "cluster", "data", "node", "manager", "core", "runtime",
    "threadpool", "connect", "database", "client", "server", "listener", "user",
    "task", "exception", "warning", "startup", "shutdown", "loop", "interface"
]

os.makedirs(OUTPUT_DIR, exist_ok=True)


def random_word():
    base = random.choice(WORDS)
    if random.random() < 0.3:
        base += "_" + ''.join(random.choices(string.ascii_lowercase, k=random.randint(2, 5)))
    return base


def generate_log_content():
    timestamp = datetime.now().strftime("%Y-%m-%d_%H:%M:%S")
    module_tag = random.choice(MODULE_TAGS)
    severity = random.choice(SEVERITIES)
    module_id = random.choice(MODULE_IDS)

    word_count = random.randint(*WORDS_PER_LOG)
    shuffled_words = [random_word() for _ in range(word_count)]
    random.shuffle(shuffled_words)
    word_blob = " ".join(shuffled_words)

    return f"{timestamp} {module_tag:<5} {severity:<8} Module loaded (id={module_id}) {word_blob}"


def main():
    for i in range(NUM_LOGS):
        name = f"log_{i + 1}"
        filepath = os.path.join(OUTPUT_DIR, f"{name}.log")

        with open(filepath, "w", encoding="utf-8") as f:
            for _ in range(LOGS_PER_FILE):
                log_content = generate_log_content()
                f.write(log_content + "\n")

        print(f"done: {filepath}")


if __name__ == "__main__":
    main()
