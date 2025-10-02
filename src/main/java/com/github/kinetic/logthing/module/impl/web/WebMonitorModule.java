package com.github.kinetic.logthing.module.impl.web;

import com.github.kinetic.logthing.Main;
import com.github.kinetic.logthing.event.IEventListener;
import com.github.kinetic.logthing.event.impl.FinishedProcessingEvent;
import com.github.kinetic.logthing.event.impl.PreProcessLog;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.web.Server;

import static com.github.kinetic.logthing.utils.web.Constants.PORT;

public class WebMonitorModule extends Module {
    private final Server server = new Server(PORT);

    public WebMonitorModule() {
        super("WebMonitorModule", "Dashboard for alerts and data", Category.WEB);
    }

    @SuppressWarnings("unused")
    private final IEventListener<PreProcessLog> eventListener = event -> {
        String theLog = event.getLog().getRawLog();
        log.debug(theLog);

        String hash = event.getLog().getRawLog();

        Main.getEventBus().dispatch(new FinishedProcessingEvent("raaah", event.getLog()));
    };

    @Override
    public void onEnable() {
        new Thread(server::start).start();

        super.onEnable();
    }

    @Override
    public void onDisable() {
        server.stop();

        super.onDisable();
    }
}
