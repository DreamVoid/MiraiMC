package me.dreamvoid.miraimc.bungee;

import me.dreamvoid.miraimc.interfaces.PluginConfig;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BungeeConfig extends PluginConfig {
    private final BungeePlugin plugin;
    private Configuration config;

    public BungeeConfig(BungeePlugin plugin) {
        this.plugin = plugin;
        PluginDir = plugin.getDataFolder();
    }

    @Override
    protected void saveDefaultConfig() throws IOException {
        if (!plugin.getDataFolder().exists() && !plugin.getDataFolder().mkdirs()) throw new RuntimeException("Failed to create folder " + plugin.getDataFolder().getPath());
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = plugin.getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            }
        }
        config = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
    }

    @Override
    protected String getString(String path, String defaults) {
        return config.getString(path, defaults);
    }

    @Override
    protected int getInt(String path, int defaults) {
        return config.getInt(path, defaults);
    }

    @Override
    protected long getLong(String path, long defaults) {
        return config.getLong(path, defaults);
    }

    @Override
    protected boolean getBoolean(String path, boolean defaults) {
        return config.getBoolean(path, defaults);
    }
}
