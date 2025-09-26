package com.github.kinetic.logthing.module.impl.web;

import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.web.Server;

import static com.github.kinetic.logthing.utils.web.Constants.PORT;

public class WebMonitorModule extends Module {
    private final Server server = new Server(PORT);

    public WebMonitorModule() {
        super("WebMonitorModule", "Dashboard for alerts and data", Category.WEB);

        setEnabled(true);
    }

    @Override
    public void onEnable() {
        server.start();
    }

    @Override
    public void onDisable() {
        server.stop();
    }
}
