package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import me.dreamvoid.miraimc.bungee.BungeePlugin;

import java.util.logging.Logger;

public class Utils {
    public static Logger Logger;

    public Utils(BukkitPlugin plugin){
        Logger = plugin.getLogger();
    }
    public Utils(BungeePlugin bungee){
        Logger = bungee.getLogger();
    }
}
