import { final } from "~/src/utilities/extensions/class.ts";
import { Logger } from "~/src/utilities/misc/logger.ts";
import { DashboardServer } from "~/src/web/impl/dashboard.ts";
import { get_config } from "~/src/config/parser.ts";
import { watch_logs } from "~/src/utilities/misc/watcher.ts";
import { bus } from "~/src/event/eventbus.ts";
import { process_log_handler } from "~/src/features/process.ts";
import { insert_log_handler } from "~/src/features/storage.ts";
import process from "node:process";
import { Effect } from "effect";

const Flags = {
  debug: "-X:debug",
  bench: "-X:bench",
} as const;
type TFlag = Record<keyof typeof Flags, boolean>;

@final
class Main {
  public logger: Logger;

  constructor(logger: Logger) {
    this.logger = logger;
  }

  public async main(): Promise<void> {
    const config = await Effect.runPromise(get_config());
    const dashboard = new DashboardServer(config.web_port, this.logger);

    dashboard.serve();

    bus.addListener("log", process_log_handler);
    bus.addListener("post-process-log", insert_log_handler);

    watch_logs(config.log_dir, this.logger, bus);
  }
}

export const options: TFlag = Object.keys(Flags).reduce((accumulator, key) => {
  accumulator[key as keyof typeof Flags] = false;
  return accumulator;
}, {} as TFlag);

if (import.meta.main) {
  const [_runtime, _script, ...args] = process.argv;

  const flagByValue = Object.fromEntries(
    Object.entries(Flags).map(([key, value]) => [value, key]),
  ) as Record<string, keyof typeof Flags>;

  for (const arg of args) {
    const key = flagByValue[arg];
    if (key) {
      options[key] = true;
    }
  }

  const logger: Logger = new Logger(options.debug);

  const main = new Main(logger);

  await main.main();
}
