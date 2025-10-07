package com.github.kinetic.logthing;

import com.github.kinetic.logthing.event.EventBus;
import com.github.kinetic.logthing.module.ModuleBuilder;
import com.github.kinetic.logthing.module.ModuleRepository;
import com.github.kinetic.logthing.module.impl.data.LogConsumerModule;
import com.github.kinetic.logthing.module.impl.misc.RequestLoggerModule;
import com.github.kinetic.logthing.module.impl.web.WebMonitorModule;
import com.github.kinetic.logthing.util.io.log.LogUtil;
import com.github.kinetic.logthing.util.misc.ModuleUtil;
import com.github.kinetic.logthing.util.misc.TerminalUtil;

@SuppressWarnings("unused")
public final class LogThing {
    private final LogUtil log = new LogUtil();
    private final TerminalUtil terminal = new TerminalUtil();
    private final ModuleRepository moduleRepository = new ModuleRepository();
    private final ModuleUtil moduleUtil = new ModuleUtil();
    private static final LogThing instance = new LogThing();
    private EventBus eventBus;

    private void initializeModules() {
        ModuleBuilder.create().putAll(
                new RequestLoggerModule(),
                new WebMonitorModule(),
                new LogConsumerModule()
        );

        moduleUtil.enableModule(RequestLoggerModule.class);
        moduleUtil.enableModule(LogConsumerModule.class);
        moduleUtil.enableModule(WebMonitorModule.class);
    }

    private EventBus initializeEventBus() {
        return new EventBus();
    }

    private void destroyModules() {
        ModuleRepository.getInstance().getEnabledModules().forEach(module -> {
            log.info("Removing module: " + module.getName());

            if(module.isEnabled())
                module.toggle();
        });
    }

    /**
     * Initialize LogThing
     */
    private void initialize() {
        log.info("Initializing LogThing...");

        log.info("Disabling control echo");
        terminal.disableControlEcho();

        log.info("Initializing EventBus...");
        eventBus = initializeEventBus();

        log.info("Initializing Modules...");
        initializeModules();

        log.info("Initialized LogThing.");
    }

    /**
     * Deinitialize LogThing
     */
    private void destroy() {
        Thread.currentThread().setName("MSH");
        log.info("Shutting down LogThing...");

        log.info("Unloading Modules...");
        destroyModules();

        log.info("Re-enabling control echo...");
        terminal.enableControlEcho();

        log.info("LogThing shut down.");
    }

    /**
     * LogThing entrypoint
     *
     * @param args LogThing program flags
     */
    void launch(String[] args) {
        Thread.currentThread().setName("LM");

        initialize();

        Runtime.getRuntime().addShutdownHook(new Thread(this::destroy));

        try {
            Thread.currentThread().join();
        } catch(final InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            log.info("Interrupted thread: " + Thread.currentThread().getName());
        }
    }

    public ModuleRepository getModuleRepository() {
        return ModuleRepository.getInstance();
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public static LogThing getInstance() {
        return instance;
    }
}
