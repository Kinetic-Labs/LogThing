package com.github.kinetic.logthing.config.dsl

/**
 * The builder for the log kinds config
 */
class LogKindsBuilder {
    /**
     * The list of log kinds.
     */
    val kinds = mutableListOf<String>()

    /**
     * Add a log kind to the list.
     */
    operator fun String.unaryPlus() {
        kinds.add(this)
    }

    /**
     * Add a log kind to the list and add any others to the list.
     */
    operator fun String.invoke(vararg others: String) {
        kinds.add(this)
        kinds.addAll(others)
    }
}
