package com.github.kinetic.logthing.config.dsl

/**
 * The builder for the processor config
 */
class ProcessorBuilder {
    /**
     * The log level pattern for the processor to use.
     */
    var levelPattern: String = ""

    /**
     * The timestamp pattern for the processor to use.
     */
    var timestampPattern: String = ""

    /**
     * The tag pattern for the processor to use.
     */
    var tagPattern: String = ""

    /**
     * Build the processor config.
     */
    fun build() = ProcessorConfig(levelPattern, timestampPattern, tagPattern)
}
