package me.dreamvoid.miraimc.internal;

import org.bukkit.Bukkit;

import java.util.logging.Logger;

public class Utils {
    public static Logger getLogger(){
        return Bukkit.getPluginManager().getPlugin("MiraiMC").getLogger();
    }
}
