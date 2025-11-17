package com.github.kinetic.logthing.config.dsl;

import com.github.kinetic.logthing.util.template.TemplateUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * The config class, created by the DSL.
 */
public final class Config {

    private final InputsConfig inputs;
    private final ProcessorConfig processor;
    private final WebConfig web;
    private final AlertsConfig alerts;
    private final DatabaseConfig database;

    private Config(
            final InputsConfig inputs,
            final ProcessorConfig processor,
            final WebConfig web,
            final AlertsConfig alerts,
            final DatabaseConfig database
    ) {
        this.inputs = inputs;
        this.processor = processor;
        this.web = web;
        this.alerts = alerts;
        this.database = database;
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

    public DatabaseConfig getDatabase() {
        return database;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private InputsConfig inputs;
        private ProcessorConfig processor;
        private WebConfig web;
        private AlertsConfig alerts;
        private DatabaseConfig database;
        private String inputPath;
        private List<String> logKinds;
        private String levelPattern;
        private String timestampPattern;
        private String tagPattern;
        private Integer webPort;
        private String alertCondition;
        private String alertWindow;
        private String databaseName;
        private String databaseUsername;
        private String databasePassword;
        private Integer databasePort;
        private String databaseHost;
        private String databaseUri;

        public void inputPath(final String path) {
            this.inputPath = path;
        }

        public void logKinds(final List<String> kinds) {
            this.logKinds = kinds;
        }

        public void levelPattern(final String pattern) {
            this.levelPattern = pattern;
        }

        public void databaseName(final String databaseName) {
            this.databaseName = databaseName;
        }

        public void databaseUsername(final String username) {
            this.databaseUsername = username;
        }

        public void databasePassword(final String password) {
            this.databasePassword = password;
        }

        public void databasePort(final int port) {
            this.databasePort = port;
        }

        public void databaseHost(final String host) {
            this.databaseHost = host;
        }

        public void databaseUri(final String uri) {
            this.databaseUri = uri;
        }

        public void timestampPattern(final String pattern) {
            this.timestampPattern = pattern;
        }

        public void tagPattern(final String pattern) {
            this.tagPattern = pattern;
        }

        public void webPort(final int port) {
            this.webPort = port;
        }

        public void alertCondition(final String condition) {
            this.alertCondition = condition;
        }

        public void alertWindow(final String window) {
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

            if(databaseName != null &&
                    databaseUsername != null &&
                    databasePassword != null &&
                    databaseHost != null &&
                    databaseUri != null &&
                    databasePort != null
            ) {
                this.databaseUri = getDatabaseUri();
                this.database = new DatabaseConfig(
                        databaseName,
                        databaseUsername,
                        databasePassword,
                        databaseHost,
                        databaseUri,
                        databasePort
                );
            }

            return new Config(inputs, processor, web, alerts, database);
        }

        @NotNull
        private String getDatabaseUri() {
            final TemplateUtil templateUtil = new TemplateUtil(databaseUri);
            final HashMap<String, String> variables = new HashMap<>();

            variables.put("USERNAME", databaseUsername);
            variables.put("PASSWORD", databasePassword);
            variables.put("HOST", databaseHost);
            variables.put("PORT", databasePort.toString());
            variables.put("DATABASE", databaseName);

            return templateUtil.process(variables);
        }
    }
}
