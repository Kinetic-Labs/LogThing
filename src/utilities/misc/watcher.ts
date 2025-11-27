import { globSync, watch } from 'node:fs';
import type { Logger } from './logger';
import type { EventEmitter } from 'node:events';
import { options } from '../../main';

export type LogEvent = {
  event: string;
  file: string;
  when: string;
};

export let t0 = 0;
const handle_bench = () => {
  if(options.bench) {
    t0 = performance.now();
  }
};

export const watch_logs = (
  directory: string,
  logger: Logger,
  bus: EventEmitter
): void => {
  handle_bench();

  logger.info(`Scanning ${directory} for existing files…`);

  const existing = globSync('**/*', {
    cwd: directory
  });

  for(const relative of existing) {
    const abs = new URL(relative, `file://${directory}/`).pathname;

    bus.emit('log', {
      event: 'change',
      file: abs,
      when: Date.now().toString(),
    });
  }

  logger.info(`Watching ${directory} for changes…`);

  watch(directory, { recursive: true }, (event, file) => {
    logger.debug(`Changed: ${event}: ${file}`);

    bus.emit('log', {
      event,
      file: file ?? '',
      when: Date.now().toString(),
    });
  });
};
