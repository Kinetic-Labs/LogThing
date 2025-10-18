package com.github.kinetic.logthing.config.dsl

/**
 * The builder for the inputs config
 */
class InputsBuilder {

    /**
     * The file input config, or null if it's not enabled.
     */
    var fileConfig: FileConfig? = null

    /**
     * Create a file input config.
     */
    fun file(block: FileBuilder.() -> Unit) {
        fileConfig = FileBuilder().apply(block).build()
    }

    /**
     * Build the inputs config.
     */
    fun build() = InputsConfig(fileConfig)
}
