val config = config {
    inputs {
        file {
            path = "logs/"
            logKinds {
                "INFO",
                "WARNING",
                "ERROR",
                "DEBUG",
                "TRACE",
                "FATAL"
            }
        }
    }

    processor {
        level_pattern = "\\[(INFO|WARNING|ERROR|DEBUG|TRACE|FATAL)\\]"
        timestamp_pattern = "(\\d{4}-\\d{2}-\\d{2}\\_\\d{2}:\\d{2}:\\d{2})"
        tag_pattern = "\\[([A-Z0-9_]+)\\]"
    }

    web {
        port = 9595
    }

    alerts {
        errorSpike {
            condition = "when $error_rate > 100 per min"
            window = "5m"
        }
    }
}
