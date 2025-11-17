package com.github.kinetic.logthing.module.impl.web;

import com.github.kinetic.logthing.config.impl.RuleEngine;
import com.github.kinetic.logthing.config.type.RuleCondition;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;

import java.util.Objects;

/**
 * Module for alerting when a spike in error logs is detected
 */
public final class AlertModule extends Module {

    /**
     * The rule engine for parsing the alert condition
     */
    private final RuleEngine ruleEngine;

    /**
     * The thread for running the alert daemon
     */
    private Thread thread;

    /**
     * Create a new {@link AlertModule}
     */
    public AlertModule() {
        super("AlertModule", "AM", "Alert daemon", Category.WEB);

        this.ruleEngine = new RuleEngine(
                Objects.requireNonNull(configUtil.getAlertsConfig().errorSpike()).condition()
        );
    }

    /**
     * The daemon thread for alerting
     */
    private void alertDaemon() {
        ruleEngine.parse();

        final RuleCondition ruleCondition = ruleEngine.getParsedCondition();

        if(ruleCondition == null) {
            log.warn("ruleCondition is null");
            return;
        }

        log.debug(ruleCondition.toString());
    }

    /**
     * Enable the module
     */
    @Override
    protected void onEnable() {
        super.onEnable();

        thread = new Thread(this::alertDaemon);
        thread.setName(getThreadName());
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Disable the module
     */
    @Override
    protected void onDisable() {
        thread.interrupt();

        super.onDisable();
    }
}
