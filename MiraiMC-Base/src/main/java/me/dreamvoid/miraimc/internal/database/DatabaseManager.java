package me.dreamvoid.miraimc.internal.database;

public abstract class DatabaseManager {
    private static Database database;

    public static Database getDatabase() {
        return database;
    }

    public static void setDatabase(Database database) {
        DatabaseManager.database = database;
    }
}
