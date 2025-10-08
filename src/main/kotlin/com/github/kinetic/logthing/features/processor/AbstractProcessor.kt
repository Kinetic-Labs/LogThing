package com.github.kinetic.logthing.features.processor

import com.github.kinetic.logthing.util.io.log.LogUtil
import com.github.kinetic.logthing.util.types.ParsedLog

abstract class AbstractProcessor(protected val rawLog: String) {
    protected val log = LogUtil()

    /**
     * Process a log
     *
     * When overridden, the method will parse the provided raw log
     *
     * @return [ParsedLog]
     */
    abstract fun process(): ParsedLog
}
