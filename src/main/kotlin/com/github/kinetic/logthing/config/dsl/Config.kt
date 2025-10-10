package com.github.kinetic.logthing.config.dsl

data class Config(
    val inputs: InputsConfig?,
    val processor: ProcessorConfig?,
    val web: WebConfig?,
    val alerts: AlertsConfig?
)
data class InputsConfig(val file: FileConfig?)
data class FileConfig(val path: String, val logKinds: List<String>)
data class ProcessorConfig(val levelPattern: String, val timestampPattern: String, val tagPattern: String)
data class WebConfig(val port: Int)
data class AlertsConfig(val errorSpike: ErrorSpikeConfig?)
data class ErrorSpikeConfig(val condition: String, val window: String)

fun config(block: ConfigBuilder.() -> Unit): Config {
    return ConfigBuilder().apply(block).build()
}
