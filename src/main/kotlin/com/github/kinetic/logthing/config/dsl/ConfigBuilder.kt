package com.github.kinetic.logthing.config.dsl

class ConfigBuilder {
    var inputs: InputsConfig? = null
    var processor: ProcessorConfig? = null
    var web: WebConfig? = null
    var alerts: AlertsConfig? = null

    fun inputs(block: InputsBuilder.() -> Unit) {
        inputs = InputsBuilder().apply(block).build()
    }

    fun processor(block: ProcessorBuilder.() -> Unit) {
        processor = ProcessorBuilder().apply(block).build()
    }

    fun web(block: WebBuilder.() -> Unit) {
        web = WebBuilder().apply(block).build()
    }

    fun alerts(block: AlertsBuilder.() -> Unit) {
        alerts = AlertsBuilder().apply(block).build()
    }

    fun build(): Config = Config(inputs, processor, web, alerts)
}
