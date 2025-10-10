@file:Suppress("unused")

package com.github.kinetic.logthing.config

import com.github.kinetic.logthing.config.dsl.Config
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

@KotlinScript(
    fileExtension = "logthing.kts",
    compilationConfiguration = ConfigScriptCompilationConfiguration::class
)
abstract class ConfigScript {
    var config: Config? = null
}

object ConfigScriptCompilationConfiguration : ScriptCompilationConfiguration({
    defaultImports(
        "com.github.kinetic.logthing.config.dsl.*"
    )

    jvm {
        dependenciesFromCurrentContext(wholeClasspath = true)
    }

    ide {
        acceptedLocations(ScriptAcceptedLocation.Everywhere)
    }
}) {
    @Suppress("unused")
    private fun readResolve(): Any = ConfigScriptCompilationConfiguration
}
