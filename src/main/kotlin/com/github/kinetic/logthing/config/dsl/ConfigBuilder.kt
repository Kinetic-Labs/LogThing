package com.github.kinetic.logthing.config.dsl

/**
 * The builder for the config class, created by the DSL.
 */
class ConfigBuilder {

    /**
     * The inputs config, or null if it's not enabled.
     */
    var inputs: InputsConfig? = null

    /**
     * The processor config, or null if it's not enabled.
     */
    var processor: ProcessorConfig? = null

    /**
     * The web config, or null if it's not enabled.
     */
    var web: WebConfig? = null

    /**
     * The alerts config, or null if it's not enabled.
     */
    var alerts: AlertsConfig? = null

    /**
     * Create the 'inputs' config.
     */
    fun inputs(block: InputsBuilder.() -> Unit) {
        inputs = InputsBuilder().apply(block).build()
    }

    /**
     * Create the processor config.
     */
    fun processor(block: ProcessorBuilder.() -> Unit) {
        processor = ProcessorBuilder().apply(block).build()
    }

    /**
     * Create the web config.
     */
    fun web(block: WebBuilder.() -> Unit) {
        web = WebBuilder().apply(block).build()
    }

    /**
     * Create the 'alerts' config.
     */
    fun alerts(block: AlertsBuilder.() -> Unit) {
        alerts = AlertsBuilder().apply(block).build()
    }

    /**
     * Build the config.
     */
    fun build(): Config = Config(inputs, processor, web, alerts)
}
