package me.dreamvoid.miraimc.nukkit;

import cn.nukkit.plugin.PluginBase;
import me.dreamvoid.miraimc.internal.MiraiLoader;
import me.dreamvoid.miraimc.internal.Utils;

import java.io.IOException;

public class NukkitPlugin extends PluginBase {

    private MiraiEvent MiraiEvent;
    private MiraiAutoLogin MiraiAutoLogin;

    @Override
    public void onLoad() {
        try {
            Utils.setLogger(new NukkitLogger("MiraiMC-Nukkit","", this));
            Utils.setClassLoader(this.getClass().getClassLoader());
            new NukkitConfig(this).loadConfig();

            MiraiLoader.loadMiraiCore();
            this.MiraiEvent = new MiraiEvent(this);
            this.MiraiAutoLogin = new MiraiAutoLogin(this);
        } catch (IOException e) {
            getLogger().error("An error occurred while loading plugin, reason: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
