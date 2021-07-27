package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import me.dreamvoid.miraimc.bungee.BungeePlugin;

import java.util.logging.Logger;

public class Utils {
    public static Logger logger;

    public Utils(BukkitPlugin plugin){
        logger = plugin.getLogger();
    }
    public Utils(BungeePlugin bungee){
        logger = bungee.getLogger();
    }
}
