package com.github.kinetic.logthing.config.type;


/**
 * Represents a parsed rule condition
 */
@SuppressWarnings("NullableProblems")
public record RuleCondition(String variable, String operator, double threshold, String timeUnit) {

    @Override
    public String toString() {
        return String.format("RuleCondition{variable='%s', operator='%s', threshold=%.2f, timeUnit='%s'}",
                variable, operator, threshold, timeUnit);
    }
}

