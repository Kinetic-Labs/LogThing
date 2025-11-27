import { sealed } from "../extensions/class";
import * as fs from 'node:fs';

type LogLevel = 'INFO' | 'WARN' | 'ERROR' | 'FATAL' | 'DEBUG';
const colors = {
    purple: "\x1b[35m",
    yellow: "\x1b[33m",
    red: "\x1b[31m",
    bold_red: "\x1b[1;35m",
    cyan: "\x1b[36m",
    gray: "\x1b[37m",
    reset: "\x1b[0m"
} as const;

export @sealed class Logger {
    private readonly is_debug: boolean;
    private readonly log_name: string;
    private static readonly log_dir: string = "logs";

    constructor(debug: boolean) {
        this.is_debug = debug;
        this.log_name = `${Logger.log_dir}/${this.get_date().replaceAll("/", "_")}.log`;
    }

    private write_log(log: string): void {
        if(!fs.existsSync(Logger.log_dir)) {
            fs.mkdirSync(Logger.log_dir);
        }

        if(!fs.existsSync(this.log_name)) {
            fs.writeFileSync(this.log_name, log + "\n");
        } else {
            fs.appendFileSync(this.log_name, log + "\n");
        }
    }

    private get_date(): string {
        const now = new Date();
        const date: string = now.toLocaleDateString();
        const time: string = now.toLocaleTimeString([], { hour12: false });
        return `${date}-${time}`;
    }

    private format_tag(log_level: LogLevel): string {
        switch(log_level) {
            case "INFO": return `${colors.gray}[${colors.purple}INFO ${colors.gray}]${colors.reset}`;
            case "WARN": return `${colors.gray}[${colors.yellow}WARN ${colors.gray}]${colors.reset}`;
            case "ERROR": return `${colors.gray}[${colors.red}ERROR${colors.gray}]${colors.reset}`;
            case "FATAL": return `${colors.gray}[${colors.bold_red}FATAL${colors.gray}]${colors.reset}`;
            case "DEBUG": return `${colors.gray}[${colors.cyan}DEBUG${colors.gray}]${colors.reset}`;
        }
    }

    private format_log(message: string, log_level: LogLevel): string {
        const tag = this.format_tag(log_level);
        const date = this.get_date();

        return `${colors.gray}[${date}] ${tag} ${message}`;
    }

    private log(message: string, log_level: LogLevel): void {
        const formatted = this.format_log(message, log_level);

        this.write_log(formatted);
        console.log(formatted);
    }

    public info(message: string): void {
        this.log(message, "INFO")
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
        if(!this.is_debug) {
            return;
        }

        this.log(message, "DEBUG");
    }
}
