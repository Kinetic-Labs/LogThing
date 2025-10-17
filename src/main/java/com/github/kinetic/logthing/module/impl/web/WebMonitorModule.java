package com.github.kinetic.logthing.module.impl.web;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.IEventListener;
import com.github.kinetic.logthing.event.impl.FinishedProcessingEvent;
import com.github.kinetic.logthing.event.impl.ProcessLogEvent;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.features.processor.impl.DataExtractorProcessor;
import com.github.kinetic.logthing.util.misc.HashUtil;
import com.github.kinetic.logthing.util.types.ParsedLog;
import com.github.kinetic.logthing.features.web.Server;

import static com.github.kinetic.logthing.util.web.WebConstants.PORT;

/**
 * Handles web dashboard and receiving logs
 */
public final class WebMonitorModule extends Module {

    /**
     * The web server
     */
    private final Server server = new Server(PORT);

    /**
     * The hash util
     */
    private final HashUtil hashUtil = new HashUtil();

    /**
     * Create a new WebMonitorModule instance
     */
    public WebMonitorModule() {
        super("WebMonitorModule", "WMM", "Dashboard for alerts and data", Category.WEB);
    }

    /**
     * The event listener for {@link ProcessLogEvent}
     */
    @SuppressWarnings("unused")
    private final IEventListener<ProcessLogEvent> eventListener = event -> {
        Thread.currentThread().setName(getThreadName());

        final String theLog = event.getLog().rawLog();
        final DataExtractorProcessor dataExtractorProcessor = new DataExtractorProcessor(theLog);
        final ParsedLog parsedLog = dataExtractorProcessor.process();
        final String hashText = hashUtil.toMD5(theLog);

        LogThing.getInstance().getEventBus().dispatch(new FinishedProcessingEvent(hashText, parsedLog));
    };

    /**
     * Enable the module
     */
    @Override
    protected void onEnable() {
        final Thread thread = new Thread(server::start);

        thread.setName(getThreadName());
        thread.start();

        super.onEnable();
    }

    /**
     * Disable the module
     */
    @Override
    protected void onDisable() {
        server.stop();

        super.onDisable();
    }
}
