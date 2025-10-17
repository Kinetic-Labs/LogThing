package com.github.kinetic.logthing.config.dsl

/**
 * The builder for the alerts config
 */
class AlertsBuilder {

    /**
     * The error spike config, or null if it's not enabled.
     */
    var errorSpikeConfig: ErrorSpikeConfig? = null

    /**
     * Create an error spike config.
     */
    fun errorSpike(block: ErrorSpikeBuilder.() -> Unit) {
        errorSpikeConfig = ErrorSpikeBuilder().apply(block).build()
    }

    /**
     * Build the alerts config.
     */
    fun build() = AlertsConfig(errorSpikeConfig)
}
