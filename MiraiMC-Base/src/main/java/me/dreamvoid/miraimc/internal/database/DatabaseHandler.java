package me.dreamvoid.miraimc.internal.database;

public class DatabaseHandler {
    private static Database database;

    public static Database getDatabase() {
        return database;
    }

    public static void setDatabase(Database database) {
        DatabaseHandler.database = database;
    }
}
