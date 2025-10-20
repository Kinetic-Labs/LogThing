package com.github.kinetic.logthing.config.dsl;

public final class ProcessorConfig {

    private final String levelPattern;
    private final String timestampPattern;
    private final String tagPattern;

    public ProcessorConfig(
        String levelPattern,
        String timestampPattern,
        String tagPattern
    ) {
        this.levelPattern = levelPattern;
        this.timestampPattern = timestampPattern;
        this.tagPattern = tagPattern;
    }

    public String getLevelPattern() {
        return levelPattern;
    }

    public String getTimestampPattern() {
        return timestampPattern;
    }

    public String getTagPattern() {
        return tagPattern;
    }
}
