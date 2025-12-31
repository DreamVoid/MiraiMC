package me.dreamvoid.miraimc.internal.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.loader.LibraryLoader;
import org.sqlite.SQLiteDataSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLite implements Database {
    private static boolean triedLibrary = false;

    private static HikariDataSource ds; // SQLite

    @Override
    public void initialize() throws ClassNotFoundException {
        LibraryLoader loader = MiraiMC.getPlatform().getLibraryLoader();
        if(!Utils.findClass("com.zaxxer.hikari.HikariDataSource")){
            try {
                loader.loadLibraryMaven("com.zaxxer", "HikariCP", Utils.getJavaVersion() >= 11 ? "5.1.0" : "4.0.3", MiraiMC.getConfig().General_MavenRepoUrl, MiraiMC.getConfig().PluginDir.toPath().resolve("libraries"));
            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new ClassNotFoundException("Couldn't find HikariCP library both local and remote.");
            }
        }

        String driver;
        if(Utils.findClass("org.sqlite.JDBC")){
            driver = "org.sqlite.JDBC";
        } else if (!triedLibrary){
            try {
                triedLibrary = true;
                loader.loadLibraryMaven("org.xerial", "sqlite-jdbc", "3.50.3.0", MiraiMC.getConfig().General_MavenRepoUrl, MiraiMC.getConfig().PluginDir.toPath().resolve("libraries"));
                initialize();
                return;
            } catch (Exception e) {
                throw new ClassNotFoundException("无法从Maven仓库下载SQLite依赖库", e);
            }
        } else {
            throw new ClassNotFoundException("无法加载SQLite依赖库，不再尝试");
        }

        try {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(driver);
            config.setPoolName("MiraiMC-SQLite");

            SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
            sqLiteDataSource.setUrl("jdbc:sqlite:" + Paths.get(MiraiMC.getConfig().Database_Drivers_SQLite_Path.replace("%plugin_folder%", MiraiMC.getConfig().PluginDir.toPath().toString())));
            config.setDataSource(sqLiteDataSource);

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
            config.addDataSourceProperty("journal_mode", "WAL");
            config.addDataSourceProperty("busy_timeout", "5000"); // 5秒

            ds = new HikariDataSource(config);
        } catch (AbstractMethodError error){
            Utils.resolveException(error, Utils.getLogger(), "发生了一个意料之中的问题，请查阅文档了解更多信息：https://docs.miraimc.dreamvoid.me/troubleshoot/faq");
        }
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
