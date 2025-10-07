package com.github.kinetic.logthing.processor;

import com.github.kinetic.logthing.util.io.log.LogUtil;
import com.github.kinetic.logthing.util.types.ParsedLog;

public abstract class AbstractProcessor {

    protected final String rawLog;
    protected final LogUtil log = new LogUtil();

    /**
     * Create a new instance of {@link AbstractProcessor}
     *
     * @param rawLog raw contents of log
     */
    protected AbstractProcessor(final String rawLog) {
        this.rawLog = rawLog;
    }

    /**
     * Process a log
     * <p>
     * When overridden, the method will parse the provided raw log
     * </p>
     *
     * @return {@link ParsedLog}
     */
    protected abstract ParsedLog process();
}
