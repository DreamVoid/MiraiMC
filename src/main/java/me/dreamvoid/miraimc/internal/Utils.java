package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.bukkit.BukkitPlugin;

import java.util.logging.Logger;

public class Utils {
    public static Utils Instance;
    public static Logger Logger;

    public Utils(BukkitPlugin plugin){
        Instance = this;
        Logger = plugin.getLogger();
    }
}
