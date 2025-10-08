package com.github.kinetic.logthing.config

/**
 * Abstract class to create a rule parser
 */
abstract class AbstractRuleEngine(
    /**
     * Get path of config file to be parsed
     *
     * @return the raw condition
     */
    val rawCondition: String
) : Config {

    /**
     * Parse the config
     *
     *
     * When overridden, the class will implement logic to parse the rule
     *
     */
    abstract fun parse()
}
