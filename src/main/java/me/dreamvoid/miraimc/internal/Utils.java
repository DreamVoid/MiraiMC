package me.dreamvoid.miraimc.internal;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class Utils {
    public static Logger getLogger(){
        if (Bukkit.getPluginManager().getPlugin("MiraiMC") != null) {
            Plugin plugin = Bukkit.getPluginManager().getPlugin("MiraiMC");
            assert plugin != null;
            return plugin.getLogger();
        } else return Bukkit.getLogger();
    }
}
