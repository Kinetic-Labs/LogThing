package com.github.kinetic.logthing.config.dsl

class LogKindsBuilder {
    val kinds = mutableListOf<String>()

    operator fun String.unaryPlus() {
        kinds.add(this)
    }

    operator fun String.invoke(vararg others: String) {
        kinds.add(this)
        kinds.addAll(others)
    }
}
