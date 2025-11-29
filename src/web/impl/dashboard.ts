import type { Logger } from "../../utilities/misc/logger";
import type { IncomingMessage, ServerResponse } from 'node:http';
import { sealed, mono } from "../../utilities/extensions/class";
import { Server } from "../server";
import { createServer } from 'node:http';
import { LogStorage } from "../../features/storage";
import type { ParsedLog } from "../../features/process";
import { options } from "../../main";

type Handler = (req: IncomingMessage, res: ServerResponse) => void;
type Route = { path: string; handler: Handler };

const level_api_handler: Handler = (_req, res) => {
    const levels = new Set<string>();

    for(const log of LogStorage) {
        levels.add(log.level);
    }

    res.writeHead(200, {'content-type': 'application/json'});
    res.end(JSON.stringify([...levels]));
};

const log_api_handler: Handler = (_req, res) => {
    const logs = new Set<ParsedLog>();

    for(const log of LogStorage) {
        logs.add(log);
    }

    res.writeHead(200, {"content-type": "application/json"});
    res.end(JSON.stringify([...logs]));
};

@sealed
@mono
export class DashboardServer extends Server {
    private readonly routes: Route[] = [
        {path: "/api/logs/get", handler: log_api_handler},
        {path: "/api/logs/levels", handler: level_api_handler},
    ];

    constructor(port: number, logger: Logger) {
        super(port, logger);
    }

    private route(req: IncomingMessage, res: ServerResponse): boolean {
        for(const route of this.routes) {
            if(req.url === route.path) {
                route.handler(req, res);
                return true;
            }
        }
        return false;
    }

    override serve(): void {
        if(options.bench) {
            return;
        }

        super.serve();

        createServer(async(req, res) => {
            const file = await this.prepareFile(req.url!);

            if(!file.found && this.route(req, res)) {
                this.logger.info(`${req.method} ${req.url} 200`);
                return;
            }

            if(file.found) {
                const mimeType = this.mime_types[file.ext] ?? this.mime_types.default;
                res.writeHead(200, {"Content-Type": mimeType});
                file.stream.pipe(res);
                this.logger.info(`${req.method} ${req.url} 200`);
                return;
            }

            res.writeHead(404, {"Content-Type": "text/html"});
            file.stream.pipe(res);

            this.logger.info(`${req.method} ${req.url} 404`);
        }).listen(this.port);
    }
}
