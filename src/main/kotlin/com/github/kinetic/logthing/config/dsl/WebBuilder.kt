package com.github.kinetic.logthing.config.dsl

/**
 * The builder for the web config
 */
class WebBuilder {
    /**
     * The port for the web server to use.
     */
    var port: Int = 9595

    /**
     * Build the web config.
     */
    fun build() = WebConfig(port)
}
