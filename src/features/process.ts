import type { LogEvent } from '../utilities/misc/watcher';
import { get_config } from '../config/parser';
import { join } from 'node:path';
import { readFileSync } from 'node:fs';
import { bus } from '../event/eventbus';

export type PostProcessLogEvent = {
    logs: Set<ParsedLog>;
};

export type ParsedLog = {
    timestamp: string;
    tag: string;
    level: string;
    message: string;
};

export const process_log_handler = async(event: LogEvent) => {
    const logs: Set<ParsedLog> = new Set();

    const config = await get_config();

    const logFile = join(config.log_dir, event.file);
    const text = readFileSync(logFile, 'utf8');
    const lines = text.trim().split('\n');

    for(const line of lines) {
        const dateMatch = config.date_pattern.exec(line);
        const levelMatch = config.level_pattern.exec(line);
        const messageMatch = config.message_pattern.exec(line);
        const tagMatch = config.tag_pattern.exec(line);

        const timestamp = dateMatch ? `${dateMatch[1]!} ${dateMatch[2]}` : null;
        const level = levelMatch ? levelMatch[1]!.trim() : null;
        const message = messageMatch ? messageMatch[1]!.trim() : null;
        const tag = tagMatch ? tagMatch[1]!.trim() : null;

        const Log = {
            timestamp,
            tag,
            level,
            message,
        } as ParsedLog;

        logs.add(Log);
    }

    const Event = {
        logs: logs,
    } satisfies PostProcessLogEvent;

    bus.emit('post-process-log', Event);
};
