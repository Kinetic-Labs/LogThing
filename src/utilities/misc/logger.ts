import { final } from "../extensions/class.ts";
import * as fs from "node:fs";

type LogLevel = "INFO" | "WARN" | "ERROR" | "FATAL" | "DEBUG";

const colors = {
  purple: "\x1b[35m",
  yellow: "\x1b[33m",
  red: "\x1b[31m",
  bold_red: "\x1b[1;35m",
  cyan: "\x1b[36m",
  gray: "\x1b[37m",
  reset: "\x1b[0m",
} as const;

const TAGS = {
  INFO: `${colors.gray}[${colors.purple}INFO ${colors.gray}]${colors.reset}`,
  WARN: `${colors.gray}[${colors.yellow}WARN ${colors.gray}]${colors.reset}`,
  ERROR: `${colors.gray}[${colors.red}ERROR${colors.gray}]${colors.reset}`,
  FATAL: `${colors.gray}[${colors.bold_red}FATAL${colors.gray}]${colors.reset}`,
  DEBUG: `${colors.gray}[${colors.cyan}DEBUG${colors.gray}]${colors.reset}`,
} as const;

const getTimestamp = (() => {
  const pad = (n: number) => n.toString().padStart(2, "0");
  return () => {
    const d = new Date();
    return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}-${
      pad(d.getHours())
    }:${pad(d.getMinutes())}:${pad(d.getSeconds())}`;
  };
})();

if (!fs.existsSync("logs")) {
  fs.mkdirSync("logs");
}

@final
export class Logger {
  private readonly is_debug: boolean;
  private readonly log_stream: fs.WriteStream;

  constructor(debug: boolean) {
    this.is_debug = debug;
    const logPath = `logs/${
      new Date().toISOString().split("T")[0]!.replace(/-/g, "_")
    }.log`;
    this.log_stream = fs.createWriteStream(logPath, { flags: "a" });
  }

  private log(message: string, level: LogLevel): void {
    const tag = TAGS[level];
    const timestamp = getTimestamp();
    const formatted = `${colors.gray}[${timestamp}] ${tag} ${message}`;

    this.log_stream.write(formatted + "\n");
    console.log(formatted);
  }

  public info(message: string): void {
    this.log(message, "INFO");
  }

  public warn(message: string): void {
    this.log(message, "WARN");
  }

  public error(message: string): void {
    this.log(message, "ERROR");
  }

  public fatal(message: string): void {
    this.log(message, "FATAL");
  }

  public debug(message: string): void {
    if (this.is_debug) {
      this.log(message, "DEBUG");
    }
  }
}
