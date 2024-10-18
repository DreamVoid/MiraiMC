package me.dreamvoid.miraimc.nukkit;

import me.dreamvoid.miraimc.interfaces.PluginConfig;

public class NukkitConfig extends PluginConfig {
    private final NukkitPlugin plugin;

    protected NukkitConfig(NukkitPlugin plugin){
        this.plugin = plugin;
        PluginDir = plugin.getDataFolder();
    }

    @Override
    protected void saveDefaultConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }

    @Override
    protected String getString(String path, String defaults) {
        return plugin.getConfig().getString(path, defaults);
    }

    @Override
    protected int getInt(String path, int defaults) {
        return plugin.getConfig().getInt(path, defaults);
    }

    @Override
    protected long getLong(String path, long defaults) {
        return plugin.getConfig().getLong(path, defaults);
    }

    @Override
    protected boolean getBoolean(String path, boolean defaults) {
        return plugin.getConfig().getBoolean(path, defaults);
    }
}
