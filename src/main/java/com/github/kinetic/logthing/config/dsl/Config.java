package com.github.kinetic.logthing.config.dsl;

import java.util.List;

/**
 * The config class, created by the DSL.
 */
public final class Config {

    private final InputsConfig inputs;
    private final ProcessorConfig processor;
    private final WebConfig web;
    private final AlertsConfig alerts;

    private Config(InputsConfig inputs, ProcessorConfig processor, WebConfig web, AlertsConfig alerts) {
        this.inputs = inputs;
        this.processor = processor;
        this.web = web;
        this.alerts = alerts;
    }

    public InputsConfig getInputs() {
        return inputs;
    }

    public ProcessorConfig getProcessor() {
        return processor;
    }

    public WebConfig getWeb() {
        return web;
    }

    public AlertsConfig getAlerts() {
        return alerts;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private InputsConfig inputs;
        private ProcessorConfig processor;
        private WebConfig web;
        private AlertsConfig alerts;
        private String inputPath;
        private List<String> logKinds;
        private String levelPattern;
        private String timestampPattern;
        private String tagPattern;
        private Integer webPort;
        private String alertCondition;
        private String alertWindow;

        public void inputPath(String path) {
            this.inputPath = path;
        }

        public void logKinds(List<String> kinds) {
            this.logKinds = kinds;
        }

        public void levelPattern(String pattern) {
            this.levelPattern = pattern;
        }

        public void timestampPattern(String pattern) {
            this.timestampPattern = pattern;
        }

        public void tagPattern(String pattern) {
            this.tagPattern = pattern;
        }

        public void webPort(int port) {
            this.webPort = port;
        }

        public void alertCondition(String condition) {
            this.alertCondition = condition;
        }

        public void alertWindow(String window) {
            this.alertWindow = window;
        }

        public Config build() {
            FileConfig fileConfig = null;

            if(inputPath != null || logKinds != null)
                fileConfig = new FileConfig(inputPath, logKinds);

            if(fileConfig != null)
                inputs = new InputsConfig(fileConfig);

            if(levelPattern != null || timestampPattern != null || tagPattern != null)
                processor = new ProcessorConfig(levelPattern, timestampPattern, tagPattern);

            if(webPort != null)
                web = new WebConfig(webPort);


            if(alertCondition != null || alertWindow != null) {
                final ErrorSpikeConfig errorSpikeConfig = new ErrorSpikeConfig(alertCondition, alertWindow);

                alerts = new AlertsConfig(errorSpikeConfig);
            }

            return new Config(inputs, processor, web, alerts);
        }
    }
}
