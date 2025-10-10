package com.github.kinetic.logthing.features.processor.impl

import com.github.kinetic.logthing.LogThing
import com.github.kinetic.logthing.features.processor.AbstractProcessor
import com.github.kinetic.logthing.util.types.ParsedLog
import java.util.regex.Pattern

class DataExtractorProcessor(rawLog: String) : AbstractProcessor(rawLog) {

    override fun process(): ParsedLog {
        val config = LogThing.getInstance().logThingConfig

        val timestampPattern = config.processorKey().timestampPattern()
        val tagPattern = config.processorKey().tagPattern()
        val levelPattern = config.processorKey().processorLevelPattern()
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

        if (matcher.find()) {
            timestamp = matcher.group(1)
            tag = matcher.group(2)
            level = matcher.group(3)
            message = matcher.group(4)
        } else {
            val simplePattern = Pattern.compile(
                String.format("^(%s)\\s+(.*)$", levelPattern)
            )
            val simpleMatcher = simplePattern.matcher(rawLog)

            if (simpleMatcher.find()) {
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
