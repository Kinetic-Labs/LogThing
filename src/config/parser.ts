import { pathToFileURL } from 'node:url';

type Config = {
    version: number;
    web_port: number;
    log_dir: string;
    date_pattern: RegExp;
    level_pattern: RegExp;
    message_pattern: RegExp;
    tag_pattern: RegExp;
};

export async function parse_config(configPath: string): Promise<Config> {
  const fileUrl = pathToFileURL(configPath).href;
  const user = await import(fileUrl);
  const raw = user.default ?? user;

  return {
    version:          Number(raw.version),
    web_port:         Number(raw.web_port),
    log_dir:          String(raw.log_dir),
    date_pattern:     new RegExp(raw.date_pattern),
    level_pattern:    new RegExp(raw.level_pattern),
    message_pattern:  new RegExp(raw.message_pattern),
    tag_pattern:      new RegExp(raw.tag_pattern),
  };
}

// todo: add mjs or just ts
export const get_config = async(): Promise<Config> =>
    await parse_config("config.cjs");
