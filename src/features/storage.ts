import { options } from "../main.ts";
import { generate_hash } from "../utilities/misc/hash.ts";
import { Logger } from "../utilities/misc/logger.ts";
import { t0 } from "../utilities/misc/watcher.ts";
import type { ParsedLog } from "./process.ts";

interface LogEvent {
  logs: ParsedLog[];
}

export const LogStorage: Set<ParsedLog> = new Set();

const handle_bench = (logger: Logger): void => {
  if (options.bench) {
    const ms = performance.now() - t0;
    logger.info(`Bench: ${ms.toFixed(2)}ms`);
  }
};

export const insert_log_handler = (event: LogEvent): void => {
  const is_debug = options.debug;
  const storage = LogStorage;
  const logger: Logger = new Logger(is_debug);
  const logs = event.logs as ParsedLog[];
  const len = logs.length;

  if (len === 0) {
    handle_bench(logger);
    return;
  }

  if (is_debug) {
    for (let i = 0; i < len; i++) {
      const log = logs[i];
      logger.debug(`Adding log: ${generate_hash(log!.message)}...`);
      storage.add(log!);
    }
  } else {
    for (let i = 0; i < len; i++) {
      storage.add(logs[i]!);
    }
  }

  handle_bench(logger);
};
