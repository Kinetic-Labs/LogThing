@file:Suppress("unused")

package com.github.kinetic.logthing.config

import com.github.kinetic.logthing.config.dsl.Config
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

/**
 * The script definition for the config script.
 */
@KotlinScript(
    fileExtension = "logthing.kts",
    compilationConfiguration = ConfigScriptCompilationConfiguration::class
)
abstract class ConfigScript {
    /**
     * The config object.
     */
    var config: Config? = null
}

/**
 * The compilation configuration for the config script.
 */
object ConfigScriptCompilationConfiguration : ScriptCompilationConfiguration({
    /**
     * The default imports for the config script.
     */
    defaultImports(
        "com.github.kinetic.logthing.config.dsl.*"
    )

    /**
     * The dependencies for the config script.
     */
    jvm {
        dependenciesFromCurrentContext(wholeClasspath = true)
    }

    /**
     * The ide options for the config script.
     */
    ide {
        acceptedLocations(ScriptAcceptedLocation.Everywhere)
    }
}) {
    /**
     * The deserializer for the config script.
     */
    @Suppress("unused")
    private fun readResolve(): Any = ConfigScriptCompilationConfiguration
}
