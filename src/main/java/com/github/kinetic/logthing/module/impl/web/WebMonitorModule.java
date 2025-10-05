package com.github.kinetic.logthing.module.impl.web;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.IEventListener;
import com.github.kinetic.logthing.event.impl.FinishedProcessingEvent;
import com.github.kinetic.logthing.event.impl.ProcessLogEvent;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.utils.misc.HashUtils;
import com.github.kinetic.logthing.web.Server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.github.kinetic.logthing.utils.web.Constants.PORT;

public final class WebMonitorModule extends Module {
    private final Server server = new Server(PORT);
    private final HashUtils hashUtils = new HashUtils();

    public WebMonitorModule() {
        super("WebMonitorModule", "Dashboard for alerts and data", Category.WEB);
    }

    @SuppressWarnings("unused")
    private final IEventListener<ProcessLogEvent> eventListener = event -> {
        final String theLog = event.getLog().rawLog();
        log.debug(theLog);

        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");

            final byte[] messageDigest = md.digest(theLog.getBytes());
            final String hashText = hashUtils.convertToHex(messageDigest);

            LogThing.getInstance().getEventBus().dispatch(new FinishedProcessingEvent(hashText, event.getLog()));
        } catch(final NoSuchAlgorithmException noSuchAlgorithmException) {
            LogThing.getInstance().getEventBus().dispatch(new FinishedProcessingEvent(null, event.getLog()));

            log.trace("Failed to hash MD5", noSuchAlgorithmException);
        }
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
