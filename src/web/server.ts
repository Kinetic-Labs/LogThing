import type { Logger } from "../utilities/misc/logger";
import { open } from "../utilities/extensions/class";

import { join, extname } from 'node:path';
import * as node_fs from 'node:fs';

const toBool = [() => true, () => false];

export @open class Server {
    protected readonly port: number;
    protected readonly logger: Logger;
    protected readonly static_path: string;
    protected readonly mime_types: Record<string, string>;

    constructor(port: number, logger: Logger) {
        this.port = port;
        this.logger = logger;
        this.static_path = join(process.cwd(), "../public");
        this.mime_types = {
            default: "application/octet-stream",
            html: "text/html; charset=UTF-8",
            js: "text/javascript",
            css: "text/css",
            png: "image/png",
            jpg: "image/jpeg",
            gif: "image/gif",
            ico: "image/x-icon",
            svg: "image/svg+xml",
        };
    }

    protected async prepareFile(
        url: string
    ): Promise<{
        found: boolean;
        ext: string; stream:
        node_fs.ReadStream;
    }>{
        const paths = [this.static_path, url];
        if(url.endsWith("/")) {
            paths.push("html/dashboard.html");
        }

        const filePath = join(...paths);
        const pathTraversal = !filePath.startsWith(this.static_path);
        const exists = await node_fs.promises.access(filePath).then(...toBool);
        const found = !pathTraversal && exists;
        const streamPath = found ? filePath : join(this.static_path, "error", "404.html");
        const ext = extname(streamPath).substring(1).toLowerCase();
        const stream = node_fs.createReadStream(streamPath);

        return { found, ext, stream };
    };

    protected serve(): void {
        this.logger.info(`Starting server on http://127.0.0.1:${this.port}`);
    }
}
