export type Config = {
  version: number;
  web_port: number;
  log_dir: string;
  date_pattern: RegExp;
  level_pattern: RegExp;
  message_pattern: RegExp;
  tag_pattern: RegExp;
};
