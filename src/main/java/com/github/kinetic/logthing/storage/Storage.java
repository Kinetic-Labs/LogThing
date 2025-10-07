package com.github.kinetic.logthing.storage;

import com.github.kinetic.logthing.util.io.log.LogUtil;
import com.github.kinetic.logthing.util.types.ParsedLog;

import java.util.List;

public interface Storage {
    LogUtil log = new LogUtil();
    List<ParsedLog> getLogs();

    void insert(final ParsedLog log);
    void init();
    void destroy();
}
