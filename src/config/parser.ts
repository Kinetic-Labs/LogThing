import { pathToFileURL } from 'node:url';
import type { Config } from './types';

let configCache: Promise<Config> | null = null;

export async function parse_config(configPath: string): Promise<Config> {
  const fileUrl = pathToFileURL(configPath).href;
  const module = await import(fileUrl);
  const raw = module.default ?? module;

  return {
    version: Number(raw.version),
    web_port: Number(raw.web_port),
    log_dir: String(raw.log_dir),
    date_pattern: new RegExp(raw.date_pattern),
    level_pattern: new RegExp(raw.level_pattern),
    message_pattern: new RegExp(raw.message_pattern),
    tag_pattern: new RegExp(raw.tag_pattern),
  };
}

export const get_config = (): Promise<Config> => {
  configCache ??= parse_config("config.mjs");
  return configCache;
};

