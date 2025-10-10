package com.github.kinetic.logthing.config.dsl

class AlertsBuilder {
    var errorSpikeConfig: ErrorSpikeConfig? = null

    fun errorSpike(block: ErrorSpikeBuilder.() -> Unit) {
        errorSpikeConfig = ErrorSpikeBuilder().apply(block).build()
    }

    fun build() = AlertsConfig(errorSpikeConfig)
}
