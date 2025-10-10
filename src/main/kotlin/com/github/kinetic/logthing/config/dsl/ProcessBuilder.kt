package com.github.kinetic.logthing.config.dsl

class ProcessorBuilder {
    var levelPattern: String = ""
    var timestampPattern: String = ""
    var tagPattern: String = ""

    fun build() = ProcessorConfig(levelPattern, timestampPattern, tagPattern)
}
