package me.dreamvoid.miraimc.internal;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.dreamvoid.miraimc.LifeCycle;
import me.dreamvoid.miraimc.internal.config.PluginConfig;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
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
            Process proc = Runtime.getRuntime().exec(new String[]{"tasklist", "/FI", "\"IMAGENAME", "eq", processName, "\""});
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

    public static boolean isDebugMode(){
        return Boolean.getBoolean("MiraiMC.debug");
    }

    public static void setLogger(Logger logger){
        Utils.logger = logger;
    }
    
    public static Logger getLogger(){
        return logger;
    }

    public static void setClassLoader(ClassLoader classLoader) {
        Utils.classLoader = classLoader;
        if(isDebugMode()){
            logger.info("ClassLoader type: " + classLoader.getParent().getClass().getPackage().getName());
            logger.info("Is an instance of URLClassLoader: " + (classLoader instanceof URLClassLoader));
            if (classLoader instanceof URLClassLoader) {
                logger.info("Paths: ");
                for(URL u : ((URLClassLoader) classLoader).getURLs()){
                    logger.info("- " + u.getPath());
                }
            }
        }
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
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", String.format("MiraiMC/%s (%s; %s)", LifeCycle.getPlatform().getPluginVersion(), LifeCycle.getPlatform().getType(), System.getProperty("os.name")));
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);

            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String data = br.readLine();
                while (data != null) {
                    sb.append(data).append(System.lineSeparator());
                    data = br.readLine();
                }
            }

            connection.disconnect();
            return sb.toString();
        }

        /**
         * 发送HTTP POST请求
         * @param json Gson对象
         * @param url 链接
         * @return 远程服务器返回内容
         */
        public static String post(JsonObject json, String url) throws IOException {
            URL obj = new URL(url.replace(" ", "%20"));
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", String.format("MiraiMC/%s (%s; %s)", LifeCycle.getPlatform().getPluginVersion(), LifeCycle.getPlatform().getType(), System.getProperty("os.name")));
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic YWRtaW46");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);

            try(DataOutputStream os = new DataOutputStream(connection.getOutputStream())){
                os.write(new Gson().toJson(json).getBytes(StandardCharsets.UTF_8));
            }

            StringBuilder sb = new StringBuilder();

            try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                String data = br.readLine();
                while (data != null) {
                    sb.append(data).append(System.lineSeparator());
                    data = br.readLine();
                }
            }

            return sb.toString();
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

    public static void resolveException(Throwable throwable, Logger logger, String reason) {
        if(!reason.isEmpty()) logger.severe(reason);
        logger.severe("如果你确信这是 MiraiMC 的错误，前往 GitHub 报告 issue 并附上完整服务器日志。");

        Throwable t = throwable;
        while(t != null){
            if (t == throwable) {
                logger.severe(throwable.toString());
            } else {
                logger.severe("Caused by: " + t);
            }

            for(StackTraceElement element : t.getStackTrace()){
                getLogger().severe(String.format("\tat %s.%s(%s:%d)", element.getClassName(), element.getMethodName(), element.getFileName(), element.getLineNumber()));
            }

            t = t.getCause();
        }
    }

    public static int getJavaVersion() {
        String[] versionElements = System.getProperty("java.version").split("\\.");
        int discard = Integer.parseInt(versionElements[0]);
        int version;
        if (discard == 1) {
            version = Integer.parseInt(versionElements[1]);
        } else {
            version = discard;
        }
        return version;
    }
}
