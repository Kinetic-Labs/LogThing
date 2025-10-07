package com.github.kinetic.logthing.module.impl.web;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.IEventListener;
import com.github.kinetic.logthing.event.impl.FinishedProcessingEvent;
import com.github.kinetic.logthing.event.impl.ProcessLogEvent;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.processor.impl.LogProcessor;
import com.github.kinetic.logthing.util.misc.HashUtil;
import com.github.kinetic.logthing.util.types.ParsedLog;
import com.github.kinetic.logthing.web.Server;

import static com.github.kinetic.logthing.util.web.WebConstants.PORT;

/**
 * Handles web dashboard and receiving logs
 */
public final class WebMonitorModule extends Module {
    private final Server server = new Server(PORT);
    private final HashUtil hashUtil = new HashUtil();

    public WebMonitorModule() {
        super("WebMonitorModule", "WMM", "Dashboard for alerts and data", Category.WEB);
    }

    @SuppressWarnings("unused")
    private final IEventListener<ProcessLogEvent> eventListener = event -> {
        Thread.currentThread().setName(getThreadName());

        final String theLog = event.getLog().rawLog();
        final LogProcessor logProcessor = new LogProcessor(theLog);
        final ParsedLog parsedLog = logProcessor.process();
        final String hashText = hashUtil.toMD5(theLog);

        LogThing.getInstance().getEventBus().dispatch(new FinishedProcessingEvent(hashText, parsedLog));
    };

    @Override
    public void onEnable() {
        super.onEnable();

        final Thread thread = new Thread(server::start);

        thread.setName(getThreadName());
        thread.start();
    }

    @Override
    public void onDisable() {
        server.stop();

        super.onDisable();
    }
}
