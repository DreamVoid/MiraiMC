package me.dreamvoid.miraimc.internal;

import com.google.gson.JsonObject;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.dreamvoid.miraimc.MiraiMCConfig;
import me.dreamvoid.miraimc.MiraiMCPlugin;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;

public final class Utils {
    static {
        if (!Boolean.getBoolean("MiraiMC.StandWithNpp") && System.getProperty("os.name").toLowerCase().contains("windows") && findProcess("notepad++.exe")) {
            Arrays.asList("========================================",
                    "喜欢用Notepad++，拦不住的", "建议使用 Visual Studio Code 或 Sublime",
                    "VSCode: https://code.visualstudio.com/",
                    "Sublime: https://www.sublimetext.com/",
                    "不要向MiraiMC作者寻求任何帮助。",
                    "进程将在20秒后继续运行",
                    "========================================").forEach(s -> Logger.getLogger("MiraiMC Preload Checker").severe(s));
            try {
                Thread.sleep(20000);
            } catch (InterruptedException ignored) {}
        }
        if(Boolean.getBoolean("MiraiMC.StandWithNpp")){
            Logger.getLogger("MiraiMC Preload Checker").severe("不要向MiraiMC作者寻求任何帮助。");
        }

        if(findClass("cpw.mods.modlauncher.Launcher") || findClass("net.minecraftforge.server.console.TerminalHandler")) { // 抛弃Forge用户，别问为什么
            Logger.getLogger("MiraiMC Preload Checker").severe("任何Forge服务端均不受MiraiMC支持，请尽量更换其他服务端使用！");
            Logger.getLogger("MiraiMC Preload Checker").severe("作者不会处理任何使用了Forge服务端导致的问题。");
            Logger.getLogger("MiraiMC Preload Checker").severe("兼容性报告: https://docs.miraimc.dreamvoid.me/troubleshoot/compatibility-report");
        }
    }

    private static boolean findProcess(String processName) {
        BufferedReader bufferedReader = null;
        try {
            Process proc = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq " + processName + "\"");
            bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(processName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            return false;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ignored) {}
            }
        }
    }

    private static boolean findClass(String className){
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static MiraiMCPlugin PlatformPlugin;
    public static Logger logger;
    public static ClassLoader classLoader;

    public static Connection connection; // SQLite
    public static HikariDataSource ds; // MySQL

    public static void setPlatformPlugin(MiraiMCPlugin platformPlugin){
        Utils.PlatformPlugin = platformPlugin;
    }

    public static void setLogger(Logger logger){
        Utils.logger = logger;
    }

    public static void setClassLoader(ClassLoader classLoader) {
        Utils.classLoader = classLoader;
    }

    public static void initializeSQLite() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + new File(MiraiMCConfig.PluginDir,"database.db").getPath());
    }

    public static void closeSQLite() throws SQLException {
        connection.close();
    }

    public static void initializeMySQL(){
        String driver = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            driver = "com.mysql.cj.jdbc.Driver";
        } catch (ClassNotFoundException ignored) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                driver = "com.mysql.jdbc.Driver";
            } catch (ClassNotFoundException ignored1) {}
        }

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl("jdbc:mysql://" + MiraiMCConfig.Database.MySQL.Address + "/" + MiraiMCConfig.Database.MySQL.Database);
        config.setUsername(MiraiMCConfig.Database.MySQL.Username);
        config.setPassword(MiraiMCConfig.Database.MySQL.Password);
        config.setConnectionTimeout(MiraiMCConfig.Database.MySQL.Poll.ConnectionTimeout);
        config.setIdleTimeout(MiraiMCConfig.Database.MySQL.Poll.IdleTimeout);
        config.setMaxLifetime(MiraiMCConfig.Database.MySQL.Poll.MaxLifetime);
        config.setMaximumPoolSize(MiraiMCConfig.Database.MySQL.Poll.MaximumPoolSize);
        config.setKeepaliveTime(MiraiMCConfig.Database.MySQL.Poll.KeepaliveTime);
        config.setMinimumIdle(MiraiMCConfig.Database.MySQL.Poll.MinimumIdle);
        config.addDataSourceProperty("cachePrepStmts", "true" );
        config.addDataSourceProperty("prepStmtCacheSize", "250" );
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048" );

        ds = new HikariDataSource(config);
    }

    public static void closeMySQL(){
        ds.close();
    }

    /**
     * Http 相关实用类
     */
    public static final class Http {
        /**
         * 发送HTTP GET请求
         * @param url URL 链接
         * @return 远程服务器返回内容
         * @throws IOException 出现任何连接问题时抛出
         */
        public static String get(String url) throws IOException {
            URL obj = new URL(url);
            StringBuilder sb = new StringBuilder();
            HttpURLConnection httpUrlConn = (HttpURLConnection) obj.openConnection();

            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.setRequestProperty("User-Agent", "Mozilla/5.0 DreamVoid MiraiMC");
            httpUrlConn.setConnectTimeout(5000);
            httpUrlConn.setReadTimeout(10000);

            InputStream input = httpUrlConn.getInputStream();
            InputStreamReader read = new InputStreamReader(input, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(read);
            String data = br.readLine();
            while (data != null) {
                sb.append(data);
                data = br.readLine();
            }
            br.close();
            read.close();
            input.close();
            httpUrlConn.disconnect();

            return sb.toString();
        }

        /**
         * 发送HTTP POST请求
         * @param json Gson对象
         * @param URL 链接
         * @return 远程服务器返回内容
         */
        public static String post(JsonObject json, String URL) throws IOException {
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpPost post = new HttpPost(URL);
                post.setHeader("Content-Type", "application/json");
                post.addHeader("Authorization", "Basic YWRtaW46");
                StringEntity s = new StringEntity(json.toString(), StandardCharsets.UTF_8);
                s.setContentType(new BasicHeader(org.apache.http.protocol.HTTP.CONTENT_TYPE, "application/json"));
                post.setEntity(s);
                // 发送请求
                HttpResponse httpResponse = client.execute(post);
                // 获取响应输入流
                InputStream inStream = httpResponse.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        inStream, StandardCharsets.UTF_8));
                StringBuilder strber = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    strber.append(line).append("\n");
                inStream.close();
                if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    logger.warning("Http request returned bad status code: " + httpResponse.getStatusLine().getStatusCode()+", reason: "+ httpResponse.getStatusLine().getReasonPhrase());
                }
                return strber.toString();
            }
        }
    }

    @NotNull
    public static File getMiraiDir(){
        return MiraiMCConfig.General.MiraiWorkingDir.equals("default") ? new File(MiraiMCConfig.PluginDir,"MiraiBot") : new File(MiraiMCConfig.General.MiraiWorkingDir);
    }
}
