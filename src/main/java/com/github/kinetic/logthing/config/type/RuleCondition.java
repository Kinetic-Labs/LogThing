package com.github.kinetic.logthing.config.type;

/**
 * A rule condition
 *
 * @param variable  the variable to check
 * @param operator  the operator to use
 * @param threshold the threshold to compare to
 * @param timeUnit  the time unit of the threshold
 */
@SuppressWarnings("NullableProblems")
public record RuleCondition(String variable, String operator, double threshold, String timeUnit) {

    /**
     * Returns a string representation of the rule condition
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return String.format("RuleCondition{variable='%s', operator='%s', threshold=%.2f, timeUnit='%s'}",
                variable, operator, threshold, timeUnit);
    }
}
