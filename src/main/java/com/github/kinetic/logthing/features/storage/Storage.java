package com.github.kinetic.logthing.features.storage;

import com.github.kinetic.logthing.util.io.log.LogUtil;
import com.github.kinetic.logthing.util.types.ParsedLog;

import java.util.Set;

public interface Storage {
    LogUtil log = new LogUtil();

    Set<ParsedLog> getLogs();

    void insert(final ParsedLog log);

    void init();

    void destroy();
}
