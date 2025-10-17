package com.github.kinetic.logthing.features.processor.impl

import com.github.kinetic.logthing.LogThing
import com.github.kinetic.logthing.features.processor.AbstractProcessor
import com.github.kinetic.logthing.util.types.ParsedLog
import java.util.regex.Pattern

/**
 * This processor parses the raw log into a ParsedLog object.
 */
class DataExtractorProcessor(rawLog: String) : AbstractProcessor(rawLog) {

    /**
     * Parses the raw log into a ParsedLog object.
     */
    override fun process(): ParsedLog {
        val config = LogThing.getInstance().logThingConfig

        val timestampPattern = config.processor?.timestampPattern
        val tagPattern = config.processor?.tagPattern
        val levelPattern = config.processor?.levelPattern
        val fullPattern = String.format(
            "^%s\\s+%s\\s+%s\\s+(.*)$",
            timestampPattern, tagPattern, levelPattern
        )
        val pattern = Pattern.compile(fullPattern)
        val matcher = pattern.matcher(rawLog)

        val timestamp: String?
        val tag: String?
        val level: String
        val message: String

        if(matcher.find()) {
            timestamp = matcher.group(1)
            tag = matcher.group(2)
            level = matcher.group(3)
            message = matcher.group(4)
        } else {
            val simplePattern = Pattern.compile(
                String.format("^(%s)\\s+(.*)$", levelPattern)
            )
            val simpleMatcher = simplePattern.matcher(rawLog)

            if(simpleMatcher.find()) {
                timestamp = null
                tag = null
                level = simpleMatcher.group(1)
                message = simpleMatcher.group(2)
            } else {
                timestamp = null
                tag = null
                level = "UNKNOWN"
                message = rawLog
            }
        }

        return ParsedLog(timestamp, tag, level, message)
    }
}
