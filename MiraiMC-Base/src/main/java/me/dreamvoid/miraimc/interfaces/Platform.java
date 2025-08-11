package me.dreamvoid.miraimc.interfaces;

import me.dreamvoid.miraimc.loader.LibraryLoader;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * MiraiMC 平台接口
 */
public interface Platform {
    String getPlayerName(UUID uuid);

    UUID getPlayerUUID(String name);

    void runTaskAsync(Runnable task);

    void runTaskLaterAsync(Runnable task, long delay);

    void runTaskTimerAsync(Runnable task, long delay, long period);

    String getPluginName();

    String getPluginVersion();

    List<String> getAuthors();

    Logger getPluginLogger();

    ClassLoader getPluginClassLoader();

    IMiraiAutoLogin getAutoLogin();

    IMiraiEvent getMiraiEvent();

    LibraryLoader getLibraryLoader();

    String getType();

    PluginConfig getPluginConfig();

}
