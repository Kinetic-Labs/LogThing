package com.github.kinetic.logthing.config.impl

import com.github.kinetic.logthing.config.AbstractRuleEngine
import com.github.kinetic.logthing.config.type.RuleCondition
import java.util.regex.Pattern

/**
 * Implementation of the rule engine for parsing condition strings
 */
class RuleEngine(rawCondition: String) : AbstractRuleEngine(rawCondition) {

    var parsedCondition: RuleCondition? = null
        private set

    companion object {
        private val CONDITION_PATTERN = Pattern.compile(
            "when\\s+\\$(\\w+)\\s*(>|<|>=|<=|==|!=)\\s*(\\d+(?:\\.\\d+)?)\\s*(?:per\\s+(\\w+))?"
        )
    }

    /**
     * Parse the condition string into structured data
     *
     *
     * Parses conditions in the format:
     * "when $variable operator threshold [per timeunit]"
     *
     *
     * @throws IllegalArgumentException if the condition format is invalid
     */
    override fun parse() {
        if (rawCondition.trim().isEmpty())
            throw IllegalArgumentException("Condition cannot be null or empty")

        val matcher = CONDITION_PATTERN.matcher(rawCondition.trim())

        if (!matcher.matches()) {
            throw IllegalArgumentException(
                $$"Invalid condition format. Expected: 'when $variable operator threshold [per timeunit]'"
            )
        }

        val variable = matcher.group(1)
        val operator = matcher.group(2)
        val threshold = matcher.group(3).toDouble()
        val timeUnit = matcher.group(4) // may be null

        this.parsedCondition = RuleCondition(variable, operator, threshold, timeUnit)
    }
}
