package com.github.kinetic.logthing.module.impl.web;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.IEventListener;
import com.github.kinetic.logthing.event.impl.FinishedProcessingEvent;
import com.github.kinetic.logthing.event.impl.PreProcessLog;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.utils.misc.HashUtils;
import com.github.kinetic.logthing.web.Server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.github.kinetic.logthing.utils.web.Constants.PORT;

public class WebMonitorModule extends Module {
    private final Server server = new Server(PORT);
    private final HashUtils hashUtils = new HashUtils();

    public WebMonitorModule() {
        super("WebMonitorModule", "Dashboard for alerts and data", Category.WEB);
    }

    @SuppressWarnings("unused")
    private final IEventListener<PreProcessLog> eventListener = event -> {
        String theLog = event.getLog().getRawLog();
        log.debug(theLog);

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(theLog.getBytes());
            String hashText = hashUtils.convertToHex(messageDigest);

            LogThing.getEventBus().dispatch(new FinishedProcessingEvent(hashText, event.getLog()));
        } catch(NoSuchAlgorithmException noSuchAlgorithmException) {
            LogThing.getEventBus().dispatch(new FinishedProcessingEvent(null, event.getLog()));

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
