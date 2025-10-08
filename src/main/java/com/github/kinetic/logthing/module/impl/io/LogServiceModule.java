package com.github.kinetic.logthing.module.impl.io;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.IEventListener;
import com.github.kinetic.logthing.event.impl.LogCreatedEvent;
import com.github.kinetic.logthing.event.impl.ProcessLogEvent;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.util.io.fs.FileUtil;
import com.github.kinetic.logthing.util.io.fs.WatchUtil;
import com.github.kinetic.logthing.util.types.Log;

import java.io.IOException;
import java.nio.file.Path;

public final class LogServiceModule extends Module {

    private final String logPath = LogThing
            .getInstance()
            .getLogThingConfig()
            .inputKey()
            .inputFileKey()
            .inputsFileLogPath();
    private final WatchUtil watchUtil = new WatchUtil(Path.of(logPath));
    private Thread thread;

    public LogServiceModule() {
        super("LogServiceModule", "LSM", "Watches a given directory and dispatches events when logs are created.", Category.IO);
    }

    @SuppressWarnings("unused")
    private final IEventListener<LogCreatedEvent> eventListener = event -> {
        final Path path = event.getPath();
        final FileUtil fileUtil = new FileUtil(path);
        final String contents = fileUtil.readToString();
        final String[] lines = contents.split("\\r?\\n");

        for(final String line : lines) {
            if(line.trim().isEmpty())
                continue;

            final Log theLog = new Log(line);

            LogThing.getInstance().getEventBus().dispatch(new ProcessLogEvent(theLog));
        }
    };

    public void startWatcher() {
        try {
            watchUtil.startWatcher();
        } catch(IOException ioException) {
            log.trace("Failed to start watcher", ioException);
        }
    }

    @Override
    protected void onEnable() {
        thread = new Thread(this::startWatcher);
        thread.setName(getThreadName());
        thread.start();

        super.onEnable();
    }

    @Override
    protected void onDisable() {
        watchUtil.stopWatcher();
        thread.interrupt();

        super.onDisable();
    }
}
