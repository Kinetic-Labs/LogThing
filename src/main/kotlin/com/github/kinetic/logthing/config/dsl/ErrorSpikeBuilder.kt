package com.github.kinetic.logthing.config.dsl

/**
 * The builder for the error spike config
 */
class ErrorSpikeBuilder {
    /**
     * The condition for the error spike.
     */
    var condition: String = ""

    /**
     * The time window for the error spike.
     */
    var window: String = ""

    /**
     * Build the error spike config.
     */
    fun build() = ErrorSpikeConfig(condition, window)
}
