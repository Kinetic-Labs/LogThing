package com.github.kinetic.logthing.config.dsl

class InputsBuilder {
    var fileConfig: FileConfig? = null

    fun file(block: FileBuilder.() -> Unit) {
        fileConfig = FileBuilder().apply(block).build()
    }

    fun build() = InputsConfig(fileConfig)
}
