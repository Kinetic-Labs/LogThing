import { options } from "../main";
import { generate_hash } from "../utilities/misc/hash";
import { Logger } from "../utilities/misc/logger";
import { t0 } from "../utilities/misc/watcher";
import type { ParsedLog } from "./process";

export let LogStorage: Set<ParsedLog> = new Set();

const handle_bench = () => {
    const ms = performance.now() - t0;
    console.log(ms);
};

export const insert_log_handler = async(event: any) => {
    const logger: Logger = new Logger(options.debug);

    for(const log of event.logs) {
        logger.debug(`Adding log: ${generate_hash(log.message)}...`);
        LogStorage.add(log);
    }

    handle_bench();
};
