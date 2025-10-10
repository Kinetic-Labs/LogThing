package com.github.kinetic.logthing.module.impl.web;

import com.github.kinetic.logthing.config.impl.RuleEngine;
import com.github.kinetic.logthing.config.type.RuleCondition;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;

import java.util.Objects;

public final class AlertModule extends Module {

    private final RuleEngine ruleEngine;
    private Thread thread;

    public AlertModule() {
        super("AlertModule", "AM", "Alert daemon", Category.WEB);

        this.ruleEngine = new RuleEngine(
                Objects.requireNonNull(configUtil.getAlertsConfig().getErrorSpike()).getCondition()
        );
    }

    private void alertDaemon() {
        ruleEngine.parse();

        final RuleCondition ruleCondition = ruleEngine.getParsedCondition();

        if(ruleCondition == null) {
            log.warn("ruleCondition is null");
            return;
        }

        log.debug(ruleCondition.toString());
    }

    @Override
    protected void onEnable() {
        super.onEnable();

        thread = new Thread(this::alertDaemon);
        thread.setName(getThreadName());
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    protected void onDisable() {
        thread.interrupt();

        super.onDisable();
    }
}
