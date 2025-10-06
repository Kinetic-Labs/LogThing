package com.github.kinetic.logthing.processor;

import com.github.kinetic.logthing.utils.io.log.LogUtils;
import com.github.kinetic.logthing.utils.types.ParsedLog;

public abstract class AbstractProcessor {

    protected final String rawLog;
    protected final LogUtils log = new LogUtils();

    public AbstractProcessor(final String rawLog) {
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
