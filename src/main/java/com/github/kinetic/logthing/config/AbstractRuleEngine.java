package com.github.kinetic.logthing.config;

/**
 * Abstract class to create a rule parser
 */
public abstract class AbstractRuleEngine implements Config {

    protected final String rawCondition;

    /**
     * Create a new {@link AbstractRuleEngine}
     *
     * @param rawCondition the condition to be parsed
     */
    public AbstractRuleEngine(final String rawCondition) {
        this.rawCondition = rawCondition;
    }

    /**
     * Parse the config
     *
     * <p>
     * When overridden, the class will implement logic to parse the rule
     * </p>
     */
    protected abstract void parse();

    /**
     * Get path of config file to be parsed
     *
     * @return the raw condition
     */
    @SuppressWarnings("unused")
    public String getRawCondition() {
        return rawCondition;
    }
}
