package com.github.kinetic.logthing.config.dsl

class FileBuilder {
    var path: String = ""
    private val logKindsList = mutableListOf<String>()

    fun logKinds(vararg kinds: String) {
        logKindsList.addAll(kinds)
    }

    fun build() = FileConfig(path, logKindsList)
}
