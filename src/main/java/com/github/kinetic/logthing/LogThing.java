package com.github.kinetic.logthing;

import com.github.kinetic.logthing.config.dsl.Config;
import com.github.kinetic.logthing.config.flags.ArgParse;
import com.github.kinetic.logthing.config.flags.LogThingFlags;
import com.github.kinetic.logthing.config.impl.ConfigParser;
import com.github.kinetic.logthing.config.impl.MiscSettings;
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

/**
 * Main class for LogThing, called by {@link Start}
 */
@SuppressWarnings("unused")
public final class LogThing {

    /**
     * Utilities
     */
    private final LogUtil log = new LogUtil();
    private final TerminalUtil terminal = new TerminalUtil();
    private final ModuleRepository moduleRepository = new ModuleRepository();
    private final ModuleUtil moduleUtil = new ModuleUtil();
    private final LogThingFlags flag = new LogThingFlags();
    private final MiscSettings moduleSettings = new MiscSettings(
        "moduleSettings"
    );

    /**
     * The LogThing instance
     */
    private static final LogThing instance = new LogThing();

    /**
     * The EventBus
     */
    private EventBus eventBus;

    /**
     * The config
     */
    private Config logThingConfig;

    private void initializeModules() {
        ModuleBuilder.create().putAll(
            new RequestLoggerModule(),
            new WebMonitorModule(),
            new LogConsumerModule(),
            new LogServiceModule(),
            new AlertModule()
        );

        // by default, we enable all modules, in the future
        // implement a settings panel for choosing what modules to use
        // and save the state
        moduleUtil.enableModule(RequestLoggerModule.class);
        moduleUtil.enableModule(LogConsumerModule.class);
        moduleUtil.enableModule(WebMonitorModule.class);
        moduleUtil.enableModule(LogServiceModule.class);
        moduleUtil.enableModule(AlertModule.class);
    }

    /**
     * Initialize the EventBus
     *
     * @return the initialized EventBus
     */
    private EventBus initializeEventBus() {
        return new EventBus();
    }

    /**
     * Load the config file
     *
     * @return the loaded config
     */
    private Config loadConfig() {
        ConfigParser configParser = new ConfigParser();

        return configParser.parse();
    }

    /**
     * Destroy all modules
     */
    private void destroyModules() {
        ModuleRepository.getInstance()
            .getEnabledModules()
            .forEach(module -> {
                log.debug("Removing module: " + module.getName());

                if (module.isEnabled()) module.setEnabled(false);
            });
    }

    /**
     * Initialize LogThing
     */
    private void initialize() {
        final String step = "INITIALIZING";
        TerminalUtil.startProgress(4, step);

        TerminalUtil.nextStep(step);
        this.logThingConfig = loadConfig();

        TerminalUtil.nextStep(step);
        this.eventBus = initializeEventBus();

        TerminalUtil.nextStep(step);
        initializeModules();

        TerminalUtil.nextStep("RUNNING");
    }

    /**
     * Deinitialize LogThing
     */
    private void destroy() {
        Thread.currentThread().setName("MSH");

        final String step = "SHUTDOWN";
        TerminalUtil.startProgress(3, step);

        TerminalUtil.nextStep(step);
        LogStorage.getInstance().destroy();

        TerminalUtil.nextStep(step);
        destroyModules();

        TerminalUtil.nextStep(step);
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
        } catch (final InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            log.info("Interrupted thread: " + Thread.currentThread().getName());
        }
    }

    /**
     * Get the module repository
     *
     * @return the module repository
     */
    public ModuleRepository getModuleRepository() {
        return ModuleRepository.getInstance();
    }

    /**
     * Get the event bus
     *
     * @return the event bus
     */
    public EventBus getEventBus() {
        return eventBus;
    }

    /**
     * Get the config file
     *
     * @return the config file
     */
    public Config getLogThingConfig() {
        return logThingConfig;
    }

    /**
     * Get the LogThing instance
     *
     * @return the LogThing instance
     */
    public static LogThing getInstance() {
        return instance;
    }

    /**
     * Check if debug mode is enabled
     *
     * @return true if debug mode is enabled
     */
    public boolean isDebugMode() {
        return flag.isDebug();
    }

    /**
     * Get the module settings
     *
     * @return the module settings
     */
    public MiscSettings getModuleSettings() {
        return moduleSettings;
    }
}
