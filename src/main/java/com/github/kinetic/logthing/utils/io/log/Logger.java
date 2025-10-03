package com.github.kinetic.logthing.utils.io.log;

public class Logger {
    private static final class LoggerHolder {
        private static final LogUtils INSTANCE = new LogUtils();
    }

    public static LogUtils getInstance() {
        return LoggerHolder.INSTANCE;
    }
}
