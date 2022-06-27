package me.dreamvoid.miraimc.internal;

import com.google.gson.JsonObject;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public final class Utils {
    public static Logger logger;
    public static ClassLoader classLoader;

    public static Connection connection; // SQLite
    public static HikariDataSource ds; // MySQL

    public static void setLogger(Logger logger){
        Utils.logger = logger;
    }

    public static void setClassLoader(ClassLoader classLoader) {
        Utils.classLoader = classLoader;
    }

    public static void initializeSQLite() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + new File(Config.PluginDir,"database.db").getPath());
    }

    public static void closeSQLite() throws SQLException {
        connection.close();
    }

    public static void initializeMySQL(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver"); // 1.17才有cj.jdbc，所以不换
        config.setJdbcUrl("jdbc:mysql://" + Config.DB_MySQL_Address + "/" + Config.DB_MySQL_Database);
        config.setUsername(Config.DB_MySQL_Username);
        config.setPassword(Config.DB_MySQL_Password);
        config.setConnectionTimeout(Config.DB_MySQL_Poll_ConnectionTimeout);
        config.setIdleTimeout(Config.DB_MySQL_Poll_IdleTimeout);
        config.setMaxLifetime(Config.DB_MySQL_Poll_MaxLifetime);
        config.setMaximumPoolSize(Config.DB_MySQL_Poll_MaximumPoolSize);
        config.setKeepaliveTime(Config.DB_MySQL_Poll_KeepaliveTime);
        config.setMinimumIdle(Config.DB_MySQL_Poll_MinimumIdle);
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
            httpUrlConn.setConnectTimeout(30000);
            httpUrlConn.setReadTimeout(30000);

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
}
