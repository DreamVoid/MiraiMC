package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.bukkit.BukkitPlugin;

import java.util.logging.Logger;

public class Utils {
    public static Utils Instance;
    private final Logger Logger;

    public Utils(BukkitPlugin plugin){
        Instance = this;
        this.Logger = plugin.getLogger();
    }

    public Logger getLogger(){ return Logger; }
}
