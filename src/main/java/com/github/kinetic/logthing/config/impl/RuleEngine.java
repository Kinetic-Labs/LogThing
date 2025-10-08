package com.github.kinetic.logthing.config.impl;

import com.github.kinetic.logthing.config.AbstractRuleEngine;
import com.github.kinetic.logthing.config.type.RuleCondition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of rule engine for parsing condition strings
 */
public final class RuleEngine extends AbstractRuleEngine {

    private static final Pattern CONDITION_PATTERN = Pattern.compile(
            "when\\s+\\$(\\w+)\\s*(>|<|>=|<=|==|!=)\\s*(\\d+(?:\\.\\d+)?)\\s*(?:per\\s+(\\w+))?"
    );

    private RuleCondition parsedCondition;

    /**
     * Create a new {@link RuleEngine}
     */
    public RuleEngine(final String rawCondition) {
        super(rawCondition);
    }

    /**
     * Parse the condition string into structured data
     *
     * <p>
     * Parses conditions in the format:
     * "when $variable operator threshold [per timeunit]"
     * </p>s
     *
     * @throws IllegalArgumentException if the condition format is invalid
     */
    @Override
    public void parse() {
        if(rawCondition == null || rawCondition.trim().isEmpty())
            throw new IllegalArgumentException("Condition cannot be null or empty");

        final Matcher matcher = CONDITION_PATTERN.matcher(rawCondition.trim());

        if(!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid condition format. Expected: 'when $variable operator threshold [per timeunit]'"
            );
        }

        final String variable = matcher.group(1);
        final String operator = matcher.group(2);
        final double threshold = Double.parseDouble(matcher.group(3));
        final String timeUnit = matcher.group(4); // may be null

        this.parsedCondition = new RuleCondition(variable, operator, threshold, timeUnit);
    }

    /**
     * Get the parsed condition
     *
     * @return the parsed RuleCondition object, or null if not yet parsed
     */
    public RuleCondition getParsedCondition() {
        return parsedCondition;
    }
}
