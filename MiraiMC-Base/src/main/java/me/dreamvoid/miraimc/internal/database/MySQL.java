package me.dreamvoid.miraimc.internal.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.internal.Utils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQL implements Database {
    private static boolean triedLibrary = false;

    private static HikariDataSource ds; // MySQL

    @Override
    public void initialize() throws ClassNotFoundException {
        if(!Utils.findClass("com.zaxxer.hikari.HikariDataSource")){
            try {
                MiraiMC.getPlatform().getLibraryLoader().loadLibraryMaven("com.zaxxer", "HikariCP", Utils.getJavaVersion() >= 11 ? "5.1.0" : "4.0.3", MiraiMC.getConfig().General_MavenRepoUrl, MiraiMC.getConfig().PluginDir.toPath().resolve("libraries"));
            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new ClassNotFoundException("Couldn't find HikariCP library both local and remote.");
            }
        }

        String driver;
        if (Utils.findClass("com.mysql.cj.jdbc.Driver")){
            driver = "com.mysql.cj.jdbc.Driver";
        } else if (Utils.findClass("com.mysql.jdbc.Driver")){
            driver = "com.mysql.jdbc.Driver";
        } else if(!triedLibrary){
            try {
                triedLibrary = true;
                MiraiMC.getPlatform().getLibraryLoader().loadLibraryMaven("com.mysql", "mysql-connector-j", "9.1.0", MiraiMC.getConfig().General_MavenRepoUrl, MiraiMC.getConfig().PluginDir.toPath().resolve("libraries"));
                initialize();
                return;
            } catch (ParserConfigurationException | IOException | SAXException e) {
                throw new ClassNotFoundException("无法从Maven仓库下载MySQL依赖库", e);
            }
        } else {
            throw new ClassNotFoundException("无法加载MySQL依赖库，不再尝试");
        }

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setPoolName("MiraiMC-MySQL");

        config.setJdbcUrl("jdbc:mysql://" + MiraiMC.getConfig().Database_Drivers_MySQL_Address + "/" + MiraiMC.getConfig().Database_Drivers_MySQL_Database + MiraiMC.getConfig().Database_Drivers_MySQL_Parameters);
        config.setUsername(MiraiMC.getConfig().Database_Drivers_MySQL_Username);
        config.setPassword(MiraiMC.getConfig().Database_Drivers_MySQL_Password);

        config.setConnectionTimeout(MiraiMC.getConfig().Database_Settings_Pool_ConnectionTimeout);
        config.setIdleTimeout(MiraiMC.getConfig().Database_Settings_Pool_IdleTimeout);
        config.setMaxLifetime(MiraiMC.getConfig().Database_Settings_Pool_MaxLifetime);
        config.setMaximumPoolSize(MiraiMC.getConfig().Database_Settings_Pool_MaximumPoolSize);
        config.setKeepaliveTime(MiraiMC.getConfig().Database_Settings_Pool_KeepaliveTime);
        config.setMinimumIdle(MiraiMC.getConfig().Database_Settings_Pool_MinimumIdle);
        config.setLeakDetectionThreshold(60000); // 60秒内未关闭的连接将被记录
        config.setConnectionTestQuery("SELECT 1");
        config.setAutoCommit(true);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);
    }

    @Override
    public void close() {
        ds.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
