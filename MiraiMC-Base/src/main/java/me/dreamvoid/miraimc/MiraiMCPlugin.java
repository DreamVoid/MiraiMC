package me.dreamvoid.miraimc;

import java.util.List;
import java.util.logging.Logger;

public abstract class MiraiMCPlugin {
    public abstract String getName();

    public abstract String getVersion();

    public abstract List<String> getAuthors();

    public abstract Logger getLogger();

    public abstract Platform getServer();

    public abstract IMiraiAutoLogin getAutoLogin();

    private static MiraiMCPlugin INSTANCE;

    public static MiraiMCPlugin getPlugin(){
        return INSTANCE;
    }

    public static void setPlugin(MiraiMCPlugin plugin){
        INSTANCE = plugin;
    }
}
