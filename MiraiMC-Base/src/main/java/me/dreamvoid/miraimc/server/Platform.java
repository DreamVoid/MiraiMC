package me.dreamvoid.miraimc.server;

import java.util.UUID;

public interface Platform {
    String getPlayerName(UUID uuid);

    UUID getPlayerUUID(String name);

    void runTaskAsync(Runnable task);

}
