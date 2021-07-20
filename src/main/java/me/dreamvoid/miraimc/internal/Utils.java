package me.dreamvoid.miraimc.internal;

//import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import org.bukkit.Bukkit;

import java.util.logging.Logger;

public class Utils {
    /*public static BukkitPlugin plugin;

    public Utils(BukkitPlugin plugin){
        Utils.plugin = plugin;
    }*/
    // TO DO: 这里有插件加载和重载的问题，找个机会修了，先用临时方案
    public static Logger getLogger() {
        if(Bukkit.getPluginManager().getPlugin("MiraiMC") != null){
            return Bukkit.getPluginManager().getPlugin("MiraiMC").getLogger();
        } else return Bukkit.getLogger();
        //return plugin.getLogger();
    }
}
