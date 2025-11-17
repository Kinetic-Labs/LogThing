package com.github.kinetic.logthing.config.dsl;

import java.util.List;

public record FileConfig(String path, List<String> logKinds) {

}
