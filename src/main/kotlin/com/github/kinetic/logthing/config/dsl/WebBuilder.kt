package com.github.kinetic.logthing.config.dsl

class WebBuilder {
    var port: Int = 8080

    fun build() = WebConfig(port)
}
