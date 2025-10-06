package com.github.kinetic.logthing.module.impl.web;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.IEventListener;
import com.github.kinetic.logthing.event.impl.FinishedProcessingEvent;
import com.github.kinetic.logthing.event.impl.ProcessLogEvent;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.processor.impl.LogProcessor;
import com.github.kinetic.logthing.utils.misc.HashUtils;
import com.github.kinetic.logthing.utils.types.ParsedLog;
import com.github.kinetic.logthing.web.Server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.github.kinetic.logthing.utils.web.WebConstants.PORT;

public final class WebMonitorModule extends Module {
    private final Server server = new Server(PORT);
    private final HashUtils hashUtils = new HashUtils();

    public WebMonitorModule() {
        super("WebMonitorModule", "WMM", "Dashboard for alerts and data", Category.WEB);
    }

    @SuppressWarnings("unused")
    private final IEventListener<ProcessLogEvent> eventListener = event -> {
        Thread.currentThread().setName(getThreadName());

        final String theLog = event.getLog().rawLog();
        final LogProcessor logProcessor = new LogProcessor(theLog);
        final ParsedLog parsedLog = logProcessor.process();

        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] messageDigest = md.digest(theLog.getBytes());
            final String hashText = hashUtils.convertToHex(messageDigest);

            LogThing.getInstance().getEventBus().dispatch(new FinishedProcessingEvent(hashText, parsedLog));
        } catch(final NoSuchAlgorithmException noSuchAlgorithmException) {
            LogThing.getInstance().getEventBus().dispatch(new FinishedProcessingEvent(null, parsedLog));

            log.trace("Failed to hash MD5", noSuchAlgorithmException);
        }
    };

    @Override
    public void onEnable() {
        final Thread thread = new Thread(server::start);

        thread.setName(getThreadName());
        thread.start();

        super.onEnable();
    }

    @Override
    public void onDisable() {
        server.stop();

        super.onDisable();
    }
}
