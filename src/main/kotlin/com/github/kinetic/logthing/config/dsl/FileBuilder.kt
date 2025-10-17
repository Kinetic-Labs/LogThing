package com.github.kinetic.logthing.config.dsl

/**
 * The builder for the file config
 */
class FileBuilder {
    /**
     * The path of the file to be read.
     */
    var path: String = ""

    /**
     * The list of log kinds to be read from the file.
     */
    private val logKindsList = mutableListOf<String>()

    /**
     * Add log kinds to the list.
     */
    fun logKinds(vararg kinds: String) {
        logKindsList.addAll(kinds)
    }

    /**
     * Build the file config.
     */
    fun build() = FileConfig(path, logKindsList)
}
