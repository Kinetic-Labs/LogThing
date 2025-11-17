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
import java.util.Objects;

/**
 * Module for watching a given directory and dispatching events when logs are created.
 */
public final class LogServiceModule extends Module {

    /**
     * The path to the directory to watch
     */
    private final String logPath = Objects.requireNonNull(configUtil.getInputsConfig().file()).path();

    /**
     * The watch utility
     */
    private final WatchUtil watchUtil = new WatchUtil(Path.of(logPath));

    /**
     * The thread running the watcher
     */
    private Thread thread;

    /**
     * Create a new {@link LogServiceModule}
     */
    public LogServiceModule() {
        super("LogServiceModule", "LSM", "Watches a given directory and dispatches events when logs are created.", Category.IO);
    }

    /**
     * The event listener for {@link LogCreatedEvent}
     */
    @SuppressWarnings("unused")
    private final IEventListener<LogCreatedEvent> eventListener = event -> {
        final Path path = event.getPath();
        final FileUtil fileUtil = new FileUtil(path);
        final String contents = fileUtil.read();
        final String[] lines = contents.split("\\r?\\n");

        for(final String line : lines) {
            if(line.trim().isEmpty())
                continue;

            final Log theLog = new Log(line);

            LogThing.getInstance().getEventBus().dispatch(new ProcessLogEvent(theLog));
        }
    };

    /**
     * Rescan the directory for new logs.
     */
    public void rescan() {
        watchUtil.rescan();
    }

    /**
     * Start the watcher thread.
     */
    public void startWatcher() {
        try {
            watchUtil.startWatcher();
        } catch(final IOException ioException) {
            log.trace("Failed to start watcher", ioException);
        }
    }

    /**
     * Enable the module.
     */
    @Override
    protected void onEnable() {
        thread = new Thread(this::startWatcher);
        thread.setName(getThreadName());
        thread.start();

        super.onEnable();
    }

    /**
     * Disable the module.
     */
    @Override
    protected void onDisable() {
        watchUtil.stopWatcher();
        thread.interrupt();

        super.onDisable();
    }
}
