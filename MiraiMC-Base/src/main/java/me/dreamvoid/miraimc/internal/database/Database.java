package me.dreamvoid.miraimc.internal.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {
    void initialize() throws ClassNotFoundException;

    void close();

    Connection getConnection() throws SQLException;
}
