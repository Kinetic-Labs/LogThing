package com.github.kinetic.logthing.config

import com.github.kinetic.logthing.config.dsl.Config
import java.io.File
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

/**
 * This is the class that actually loads the config script.
 */
object ConfigLoader {
    /**
     * Loads the config script from the given file path.
     */
    fun loadConfig(filePath: String): Config {
        val scriptFile = File(filePath)

        if(!scriptFile.exists())
            throw IllegalArgumentException("Config file not found: $filePath")

        val scriptSource = scriptFile.toScriptSource()
        val compilationConfig = createJvmCompilationConfigurationFromTemplate<ConfigScript>()

        return when(val evalResult = BasicJvmScriptingHost().eval(scriptSource, compilationConfig, null)) {
            is ResultWithDiagnostics.Success -> {
                when(val returnValue = evalResult.value.returnValue) {
                    // try to get config object
                    is ResultValue.Value -> {
                        returnValue.value as? Config
                            ?: throw IllegalStateException("Script must evaluate to a Config object, got: ${returnValue.value?.javaClass}")
                    }

                    else -> {
                        (returnValue.scriptInstance as? ConfigScript)?.config
                            ?: throw IllegalStateException("Script did not return a Config object")
                    }
                }
            }

            is ResultWithDiagnostics.Failure -> {
                val errors = evalResult.reports.joinToString("\n") { report ->
                    "${report.severity}: ${report.message}" +
                            (report.location?.let { " at ${it.start.line}:${it.start.col}" } ?: "")
                }
                throw IllegalStateException("Failed to evaluate config script:\n$errors")
            }
        }
    }
}
