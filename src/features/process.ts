import type { LogEvent } from "../utilities/misc/watcher.ts";
import { get_config } from "../config/parser.ts";
import { resolve } from "node:path";
import { readFileSync } from "node:fs";
import { bus } from "../event/eventbus.ts";

export type ParsedLog = {
  timestamp: string;
  tag: string;
  level: string;
  message: string;
};

export const process_log_handler = async (event: LogEvent): Promise<void> => {
  const config = await get_config();
  const { date_pattern, level_pattern, message_pattern, tag_pattern } = config;

  const logFile = resolve(event.file);
  const text = readFileSync(logFile, "utf8");
  const lines = text.trim().split("\n");
  const result: ParsedLog[] = new Array(lines.length);
  const len = lines.length;

  for (let i = 0; i < len; i++) {
    const line = lines[i];
    const dateMatch = date_pattern.exec(line!);
    const levelMatch = level_pattern.exec(line!);
    const messageMatch = message_pattern.exec(line!);
    const tagMatch = tag_pattern.exec(line!);

    result[i] = {
      timestamp: dateMatch ? `${dateMatch[1]} ${dateMatch[2]}` : "",
      level: levelMatch ? levelMatch[1]!.trim() : "",
      message: messageMatch ? messageMatch[1]!.trim() : "",
      tag: tagMatch ? tagMatch[1]!.trim() : "",
    };
  }

  bus.emit("post-process-log", { logs: result });
};
