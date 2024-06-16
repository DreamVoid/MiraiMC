package me.dreamvoid.miraimc.internal;

import com.google.gson.JsonObject;
import me.dreamvoid.miraimc.internal.config.PluginConfig;
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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.logging.Logger;

public final class Utils {
    static {
        Logger logger = Logger.getLogger("MiraiMC Preload Checker");
        // 此处放置插件自检代码
        if (!Boolean.getBoolean("MiraiMC.StandWithNpp") && System.getProperty("os.name").toLowerCase().contains("windows") && findProcess("notepad++.exe")) {
            Arrays.asList("========================================",
                    "喜欢用Notepad++，拦不住的", "建议使用 Visual Studio Code 或 Sublime",
                    "VSCode: https://code.visualstudio.com/",
                    "Sublime: https://www.sublimetext.com/",
                    "不要向MiraiMC作者寻求任何帮助。",
                    "进程将在20秒后继续运行",
                    "========================================").forEach(logger::severe);
            try {
                Thread.sleep(20000);
            } catch (InterruptedException ignored) {}
        }
        if(Boolean.getBoolean("MiraiMC.StandWithNpp")){
            logger.severe("不要向MiraiMC作者寻求任何帮助。");
        }

        if(findClass("cpw.mods.modlauncher.Launcher") || findClass("net.minecraftforge.server.console.TerminalHandler")) { // 抛弃Forge用户，别问为什么
            logger.severe("任何Forge服务端均不受MiraiMC支持，请尽量更换其他服务端使用！");
            logger.severe("作者不会处理任何使用了Forge服务端导致的问题。");
            logger.severe("兼容性报告: https://docs.miraimc.dreamvoid.me/troubleshoot/compatibility-report");
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

    public static boolean findClass(String className){
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static Logger logger;
    private static ClassLoader classLoader;

    public static void setLogger(Logger logger){
        Utils.logger = logger;
    }
    
    public static Logger getLogger(){
        return logger;
    }

    public static void setClassLoader(ClassLoader classLoader) {
        Utils.classLoader = classLoader;
    }
    
    public static ClassLoader getClassLoader(){
        return classLoader;
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
            URL obj = new URL(url.replace(" ", "%20"));
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
                sb.append(data).append(System.lineSeparator());
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

        /**
         * 下载一个文件，覆盖已存在的文件
         * @param url 链接
         * @param saveFile 将要保存到的文件
         */
        public static void download(String url, File saveFile){
            try (InputStream inputStream = new URL(url).openStream()){
                logger.info("Downloading " + url);
                Files.copy(inputStream, saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                logger.severe(String.format("Failed to download %s, reason: %s", url, e));
            }
        }
    }

    @NotNull
    public static File getMiraiDir(){
        return PluginConfig.General.MiraiWorkingDir.equals("default") ? new File(PluginConfig.PluginDir,"MiraiBot") : new File(PluginConfig.General.MiraiWorkingDir);
    }

    public static void resolveException(Exception exception, Logger logger, String reason) {
        if(!reason.isEmpty()) logger.severe(reason);
        logger.severe("如果你确信这是 MiraiMC 的错误，前往 GitHub 报告 issue 并附上完整服务器日志。");

        Throwable throwable = exception;
        while(throwable != null){
            if (throwable == exception) {
                logger.severe(exception.toString());
            } else {
                logger.severe("Caused by: " + throwable);
            }

            for(StackTraceElement element : throwable.getStackTrace()){
                getLogger().severe(String.format("\tat %s.%s(%s:%d)", element.getClassName(), element.getMethodName(), element.getFileName(), element.getLineNumber()));
            }

            throwable = throwable.getCause();
        }
    }
}
