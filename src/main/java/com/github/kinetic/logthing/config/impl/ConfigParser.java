package com.github.kinetic.logthing.config.impl;

import com.github.kinetic.logthing.config.AbstractConfigParser;
import com.github.kinetic.logthing.config.dsl.Config;
import com.github.kinetic.nixthing.ast.NixExpression;
import com.github.kinetic.nixthing.ast.NixInteger;
import com.github.kinetic.nixthing.ast.NixList;
import com.github.kinetic.nixthing.ast.NixSet;
import com.github.kinetic.nixthing.ast.NixString;
import com.github.kinetic.nixthing.core.enviornment.Environment;
import com.github.kinetic.nixthing.core.eval.Evaluator;
import com.github.kinetic.nixthing.core.lexer.Lexer;
import com.github.kinetic.nixthing.core.parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of {@link AbstractConfigParser} for parsing Nix config files
 */
@SuppressWarnings("SameParameterValue")
public final class ConfigParser extends AbstractConfigParser {

    private static final String CONFIG_FILE = "logthing.nix";

    /**
     * Create a new {@link ConfigParser}
     */
    public ConfigParser() {
    }

    /**
     * Parse the config file
     *
     * @return the parsed config
     */
    @Override
    public Config parse() {
        try {
            final String input = loadConfigFile(CONFIG_FILE);

            final Lexer lexer = new Lexer(input);
            final Parser parser = new Parser(lexer.tokenize());
            final NixExpression parsedAst = parser.parse();

            final Evaluator evaluator = new Evaluator();
            final Environment globalEnv = new Environment(null);
            final NixExpression evaluatedResult = evaluator.eval(
                    parsedAst,
                    globalEnv
            );

            if(!(evaluatedResult instanceof NixSet))
                throw new RuntimeException("Expected NixSet as root config");

            return buildConfig((NixSet) evaluatedResult);
        } catch(Exception exception) {
            throw new RuntimeException(
                    "Failed to parse Nix config: " + exception.getMessage(),
                    exception
            );
        }
    }

    private Config buildConfig(final NixSet root) {
        final Optional<NixSet> configSet = getSet(root, "config");

        if(configSet.isEmpty())
            throw new RuntimeException(
                    "Missing 'config' attribute in Nix file"
            );

        final NixSet config = configSet.get();

        final Config.Builder builder = Config.builder();

        getSet(config, "inputs").flatMap(inputs -> getSet(inputs, "file")).ifPresent(file -> {
            getString(file, "path").ifPresent(builder::inputPath);
            getList(file, "logKinds").ifPresent(builder::logKinds);
        });

        getSet(config, "processor").ifPresent(processor -> {
            getString(processor, "levelPattern").ifPresent(
                    builder::levelPattern
            );
            getString(processor, "timestampPattern").ifPresent(
                    builder::timestampPattern
            );
            getString(processor, "tagPattern").ifPresent(builder::tagPattern);
        });

        getSet(config, "web").flatMap(web -> getInteger(web, "port")).ifPresent(builder::webPort);

        getSet(config, "alerts")
                .flatMap(alerts -> getSet(alerts, "errorSpike")).ifPresent(errorSpike -> {
                    getString(errorSpike, "condition").ifPresent(
                            builder::alertCondition
                    );
                    getString(errorSpike, "window").ifPresent(builder::alertWindow);
                });

        return builder.build();
    }

    private String loadConfigFile(final String filename) throws IOException {
        final Path configPath = Path.of(filename);

        if(!Files.exists(configPath))
            throw new IOException("Config file not found: " + filename);

        return Files.readString(configPath);
    }

    private Optional<NixExpression> getAttr(final NixSet set, final String attrName) {
        if(set.getEnv() == null)
            return Optional.empty();

        return set.getEnv().lookup(attrName);
    }

    private Optional<String> getString(final NixSet root, final String path) {
        return getAttr(root, path)
                .filter(val -> val instanceof NixString)
                .map(val -> ((NixString) val).getValue());
    }

    private Optional<Integer> getInteger(final NixSet root, final String path) {
        return getAttr(root, path)
                .filter(val -> val instanceof NixInteger)
                .map(val -> ((NixInteger) val).getValue());
    }

    private Optional<NixSet> getSet(final NixSet root, final String path) {
        return getAttr(root, path)
                .filter(val -> val instanceof NixSet)
                .map(val -> (NixSet) val);
    }

    private Optional<List<String>> getList(final NixSet root, final String path) {
        return getAttr(root, path)
                .filter(val -> val instanceof NixList)
                .map(val ->
                        ((NixList) val).getElements()
                                .stream()
                                .filter(e -> e instanceof NixString)
                                .map(e -> ((NixString) e).getValue())
                                .collect(Collectors.toList())
                );
    }
}
