package com.github.kinetic.logthing;

import com.github.kinetic.logthing.event.EventBus;
import com.github.kinetic.logthing.module.ModuleBuilder;
import com.github.kinetic.logthing.module.ModuleRepository;
import com.github.kinetic.logthing.module.impl.data.LogConsumerModule;
import com.github.kinetic.logthing.module.impl.misc.RequestLoggerModule;
import com.github.kinetic.logthing.module.impl.web.WebMonitorModule;
import com.github.kinetic.logthing.utils.io.log.LogUtils;
import com.github.kinetic.logthing.utils.misc.ModuleUtils;
import com.github.kinetic.logthing.utils.misc.SignalUtils;
import com.github.kinetic.logthing.utils.misc.TerminalUtils;

@SuppressWarnings("unused")
public class LogThing {
    private static final LogUtils log = new LogUtils();
    private static final TerminalUtils terminal = new TerminalUtils();
    private static final SignalUtils signals = new SignalUtils();
    private static final ModuleRepository moduleRepository = new ModuleRepository();
    private static final ModuleUtils moduleUtils = new ModuleUtils();
    private static EventBus eventBus;

    private static void initializeModules() {
        ModuleBuilder.create().putAll(
                new RequestLoggerModule(),
                new WebMonitorModule(),
                new LogConsumerModule()
        );

        moduleUtils.enableModule(RequestLoggerModule.class);
        moduleUtils.enableModule(LogConsumerModule.class);
        moduleUtils.enableModule(WebMonitorModule.class);
    }

    private static EventBus initializeEventBus() {
        return new EventBus();
    }

    private static void destroyModules() {
        ModuleRepository.getInstance().getEnabledModules().forEach(module -> {
            log.info("Removing module: " + module.getName());

            if(module.isEnabled())
                module.toggle();
        });
    }

    private static void initialize() {
        log.info("Initializing LogThing...");

        log.info("Disabling control echo");
        terminal.disableControlEcho();

        log.info("Setting up signals...");
        signals.setupHandlers();

        log.info("Initializing EventBus...");
        eventBus = initializeEventBus();

        log.info("Initializing Modules...");
        initializeModules();

        log.info("Initialized LogThing.");
    }

    private static void destroy() {
        Thread.currentThread().setName("MSH");
        log.info("Shutting down LogThing...");

        log.info("Unloading Modules...");
        destroyModules();

        log.info("Re-enabling control echo...");
        terminal.enableControlEcho();

        log.info("Shut down LogThing.");
    }

    static void main(String[] args) {
        Thread.currentThread().setName("LM");

        initialize();

        Runtime.getRuntime().addShutdownHook(new Thread(LogThing::destroy));

        try {
            Thread.currentThread().join();
        } catch(final InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            log.info("Interrupted thread: " + Thread.currentThread().getName());
        }
    }

    public static ModuleRepository getModuleRepository() {
        return ModuleRepository.getInstance();
    }

    public static EventBus getEventBus() {
        return eventBus;
    }
}
