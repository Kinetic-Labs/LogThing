import { globSync, watch } from "node:fs";
import { join } from "node:path";
import type { Logger } from "~/src/utilities/misc/logger.ts";
import type { EventEmitter } from "node:events";
import { options } from "~/src/main.ts";

export type LogEvent = {
  event: string;
  file: string;
  when: string;
};

export let t0 = 0;

export const watch_logs = (
  directory: string,
  logger: Logger,
  bus: EventEmitter,
): void => {
  if (options.bench) {
    t0 = performance.now();
  }

  logger.info(`Scanning ${directory} for existing files…`);

  const existing = globSync("**/*", { cwd: directory });

  for (const relative of existing) {
    bus.emit("log", {
      event: "change",
      file: join(directory, relative),
      when: Date.now().toString(),
    });
  }

  if (options.bench) {
    const elapsed = performance.now() - t0;
    logger.info(`Scanned ${existing.length} files in ${elapsed.toFixed(2)}ms`);
    return;
  }

  logger.info(`Watching ${directory} for changes…`);

  watch(directory, { recursive: true }, (event, file) => {
    if (file) {
      logger.debug(`Changed: ${event}: ${file}`);
      bus.emit("log", {
        event,
        file: join(directory, file),
        when: Date.now().toString(),
      });
    }
  });
};
