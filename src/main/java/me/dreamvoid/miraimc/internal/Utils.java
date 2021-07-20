package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.bukkit.BukkitPlugin;

import java.util.logging.Logger;

public class Utils {
    public static BukkitPlugin plugin;

    public Utils(BukkitPlugin plugin){
        Utils.plugin = plugin;
    }

    public static Logger getLogger() {
        return plugin.getLogger();
    }
}
