package me.dreamvoid.miraimc;

import me.dreamvoid.miraimc.internal.loader.LibraryLoader;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public interface Platform {
    String getPlayerName(UUID uuid);

    UUID getPlayerUUID(String name);

    void runTaskAsync(Runnable task);

    void runTaskLaterAsync(Runnable task, long delay);

    int runTaskTimerAsync(Runnable task, long period);

    void cancelTask(int taskId);

    String getPluginName();

    String getPluginVersion();

    List<String> getAuthors();

    Logger getPluginLogger();

    ClassLoader getPluginClassLoader();

    IMiraiAutoLogin getAutoLogin();

    IMiraiEvent getMiraiEvent();

    MiraiMCConfig getPluginConfig();

    LibraryLoader getLibraryLoader();
}
