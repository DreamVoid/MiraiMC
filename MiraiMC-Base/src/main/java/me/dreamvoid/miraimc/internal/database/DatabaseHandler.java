package me.dreamvoid.miraimc.internal.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DatabaseHandler {
    private static Database database;

    public static Database getDatabase() {
        return database;
    }

    public static void setDatabase(Database database) {
        DatabaseHandler.database = database;
    }

    public static int executeUpdate(String command, Object... args) throws SQLException {
        if(getDatabase() == null) throw new UnsupportedOperationException("Database is null");

        try(PreparedStatement pstmt = getDatabase().getConnection().prepareStatement(command)){
            for(int i = 0; i < args.length; i++){
                pstmt.setObject(i + 1, args[i]);
            }
            return pstmt.executeUpdate();
        }
    }

    public static ResultSet executeQuery(String command, Object... args) throws SQLException {
        if(getDatabase() == null) throw new UnsupportedOperationException("Database is null");

        try(PreparedStatement pstmt = getDatabase().getConnection().prepareStatement(command)){
            for(int i = 0; i < args.length; i++){
                pstmt.setObject(i + 1, args[i]);
            }
            return pstmt.executeQuery();
        }
    }
}
