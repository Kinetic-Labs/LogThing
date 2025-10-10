import com.github.kinetic.logthing.config.dsl.*

config {
    inputs {
        file {
            path = "logs/"
            logKinds("INFO", "WARNING", "ERROR", "DEBUG", "TRACE", "FATAL")
        }
    }

    processor {
        levelPattern = "\\[(INFO|WARNING|ERROR|DEBUG|TRACE|FATAL)\\]"
        timestampPattern = "(\\d{4}-\\d{2}-\\d{2}\\_\\d{2}:\\d{2}:\\d{2})"
        tagPattern = "\\[([A-Z0-9_]+)\\]"
    }

    web {
        port = 9595
    }

    alerts {
        errorSpike {
            condition = $$"when $error_rate > 100 per min"
            window = "5m"
        }
    }
}
