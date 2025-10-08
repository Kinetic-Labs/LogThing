package com.github.kinetic.logthing.util.io.fs;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.impl.LogCreatedEvent;
import com.github.kinetic.logthing.util.Util;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static java.nio.file.StandardWatchEventKinds.*;

public final class WatchUtil implements Util {

    private final Path directory;
    private final AtomicBoolean running;
    private WatchService watcher;
    private Thread watcherThread;

    public WatchUtil(final Path directory) {
        if(directory == null)
            throw new IllegalArgumentException("Directory cannot be null");

        if(!Files.isDirectory(directory))
            throw new IllegalArgumentException("Path must be a directory: " + directory);

        this.running = new AtomicBoolean(false);
        this.directory = directory;
    }

    /**
     * Starts the file watcher and dispatches events for existing files in the directory.
     * <p>
     * Will spawn a new daemon thread
     * </p>
     *
     * @throws IOException           if the watcher cannot be started or directory cannot be read
     * @throws IllegalStateException if the watcher is already running
     */
    public void startWatcher() throws IOException {
        Thread.currentThread().setName("WUW");

        if(running.getAndSet(true))
            throw new IllegalStateException("Watcher is already running");

        watcher = FileSystems.getDefault().newWatchService();
        // register events
        directory.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

        dispatchExistingFiles();

        watcherThread = new Thread(this::watchLoop, "FWD");
        watcherThread.setDaemon(true);
        watcherThread.start();

        log.info("File watcher daemon started for directory: " + directory);
    }

    /**
     * Dispatches LogCreatedEvent for each existing file in the directory.
     */
    private void dispatchExistingFiles() {
        try(final Stream<Path> files = Files.list(directory)) {
            files.filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            LogThing.getInstance().getEventBus()
                                    .dispatch(new LogCreatedEvent(file));
                        } catch(Exception exception) {
                            log.trace("Failed to dispatch event for file: " + file, exception);
                        }
                    });
        } catch(final IOException ioException) {
            log.trace("Failed to list existing files in directory: " + directory, ioException);
        }
    }

    /**
     * Main watch loop that processes file system events.
     */
    private void watchLoop() {
        try {
            while(running.get() && !Thread.currentThread().isInterrupted()) {
                WatchKey key;

                try {
                    key = watcher.take();
                } catch(final InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();

                    break;
                }

                for(final WatchEvent<?> event : key.pollEvents()) {
                    final WatchEvent.Kind<?> kind = event.kind();

                    if(kind == OVERFLOW) {
                        log.warn("Watch service overflow - some events may have been lost");
                        continue;
                    }

                    @SuppressWarnings("unchecked")
                    final WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                    final Path fileName = pathEvent.context();
                    final Path fullPath = directory.resolve(fileName);

                    if(kind == ENTRY_CREATE || kind == ENTRY_MODIFY) {
                        if(Files.isRegularFile(fullPath)) {
                            try {
                                LogThing.getInstance().getEventBus()
                                        .dispatch(new LogCreatedEvent(fullPath));
                            } catch(final Exception exception) {
                                log.trace("Failed to dispatch event for: " + fullPath, exception);
                            }
                        }
                    }
                }

                boolean valid = key.reset();

                if(!valid) {
                    log.warn("Watch key no longer valid - directory may have been deleted");
                    break;
                }
            }
        } finally {
            cleanup();
        }
    }

    /**
     * Stops the file watcher and cleans up resources.
     */
    public void stopWatcher() {
        if(running.compareAndSet(true, false)) {
            if(watcherThread == null)
                return;

            watcherThread.interrupt();

            try {
                // join threads
                watcherThread.join(5000);
            } catch(final InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                log.warn("Interrupted while waiting for watcher thread to stop");
            }

            cleanup();
            log.info("File watcher stopped for directory: " + directory);
        }
    }

    /**
     * Cleans up resources.
     */
    private void cleanup() {
        if(watcher == null)
            return;

        try {
            watcher.close();
        } catch(final IOException ioException) {
            log.trace("Error closing watch service", ioException);
        }
    }
}
