import { sealed, mono } from "./utilities/extensions/class";
import { Logger } from "./utilities/misc/logger";
import { DashboardServer } from "./web/impl/dashboard";
import { get_config } from "./config/parser";
import { watch_logs } from "./utilities/misc/watcher";
import { bus } from "./event/eventbus";
import { process_log_handler } from "./features/process";
import { insert_log_handler } from "./features/storage";

const Flags = {
    debug: "-X:debug",
    bench: "-X:bench",
} as const;
type TFlag = Record<keyof typeof Flags, boolean>;

@sealed
@mono
class Main {
    public logger: Logger;

    constructor(logger: Logger) {
        this.logger = logger;
    }

    public async main(): Promise<void> {
        const config = await get_config();
        const dashboard = new DashboardServer(config.web_port, this.logger);

        dashboard.serve();

        bus.addListener('log', process_log_handler);
        bus.addListener('post-process-log', insert_log_handler);

        watch_logs(config.log_dir, this.logger, bus);
    }
}

export const options: TFlag = Object.keys(Flags).reduce((accumulator, key) => {
    accumulator[key as keyof typeof Flags] = false;
    return accumulator;
}, {} as TFlag);

if(import.meta.main) {
    const [_runtime, _script, ...args] = process.argv;

    const flagByValue = Object.fromEntries(
        Object.entries(Flags).map(([k, v]) => [v, k])
    ) as Record<string, keyof typeof Flags>;

    for(const arg of args) {
        const key = flagByValue[arg];
        if(key) {
            options[key] = true;
        }
    }

    const logger: Logger = new Logger(options.debug);

    const main = new Main(logger);

    await main.main();
}
