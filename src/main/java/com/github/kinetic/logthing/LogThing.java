package com.github.kinetic.logthing;

import com.github.kinetic.logthing.config.flags.ArgParse;
import com.github.kinetic.logthing.config.flags.LogThingFlags;
import com.github.kinetic.logthing.config.impl.ConfigParser;
import com.github.kinetic.logthing.config.type.LogThingConfig;
import com.github.kinetic.logthing.event.EventBus;
import com.github.kinetic.logthing.features.storage.impl.memory.LogStorage;
import com.github.kinetic.logthing.module.ModuleBuilder;
import com.github.kinetic.logthing.module.ModuleRepository;
import com.github.kinetic.logthing.module.impl.data.LogConsumerModule;
import com.github.kinetic.logthing.module.impl.io.LogServiceModule;
import com.github.kinetic.logthing.module.impl.misc.RequestLoggerModule;
import com.github.kinetic.logthing.module.impl.web.AlertModule;
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
    private final LogThingFlags flag = new LogThingFlags();
    private static final LogThing instance = new LogThing();
    private EventBus eventBus;
    private LogThingConfig logThingConfig;

    private void initializeModules() {
        ModuleBuilder.create().putAll(
                new RequestLoggerModule(),
                new WebMonitorModule(),
                new LogConsumerModule(),
                new LogServiceModule(),
                new AlertModule()
        );

        // by default, we enable all modules, in future
        // implement settings panel for choosing what modules to use
        moduleUtil.enableModule(RequestLoggerModule.class);
        moduleUtil.enableModule(LogConsumerModule.class);
        moduleUtil.enableModule(WebMonitorModule.class);
        moduleUtil.enableModule(LogServiceModule.class);
        moduleUtil.enableModule(AlertModule.class);
    }

    private EventBus initializeEventBus() {
        return new EventBus();
    }

    private LogThingConfig loadConfig() {
        ConfigParser configParser = new ConfigParser("config.toml");

        return configParser.parse();
    }

    private void destroyModules() {
        ModuleRepository.getInstance().getEnabledModules().forEach(module -> {
            log.info("Removing module: " + module.getName());

            if(module.isEnabled())
                module.setEnabled(false);
        });
    }

    /**
     * Initialize LogThing
     */
    private void initialize() {
        log.info("Initializing LogThing...");

        log.info("Loading config...");
        this.logThingConfig = loadConfig();

        log.info("Initializing EventBus...");
        this.eventBus = initializeEventBus();

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

        log.info("Closing log storage...");
        LogStorage.getInstance().destroy();

        log.info("Unloading Modules...");
        destroyModules();

        log.info("LogThing shut down.");
    }

    /**
     * LogThing entrypoint
     *
     * @param args LogThing program flags
     */
    void launch(String[] args) {
        Thread.currentThread().setName("LM");

        ArgParse argParse = new ArgParse();
        argParse.parseArgs(args);

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

    public LogThingConfig getLogThingConfig() {
        return logThingConfig;
    }

    public static LogThing getInstance() {
        return instance;
    }

    public boolean isDebugMode() {
        return flag.isDebug();
    }
}
