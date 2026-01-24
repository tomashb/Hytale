package com.heneria.server.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public void register(Module module) {
        modules.add(module);
    }

    public void enableAll(ServerContext context) {
        for (Module module : modules) {
            module.onEnable(context);
        }
    }

    public void disableAll() {
        for (Module module : modules) {
            module.onDisable();
        }
    }

    public List<Module> listModules() {
        return Collections.unmodifiableList(modules);
    }
}
