package com.github.kinetic.logthing.config.dsl

class ErrorSpikeBuilder {
    var condition: String = ""
    var window: String = ""

    fun build() = ErrorSpikeConfig(condition, window)
}
