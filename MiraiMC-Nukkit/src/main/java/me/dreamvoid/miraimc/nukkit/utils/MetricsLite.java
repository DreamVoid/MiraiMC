package me.dreamvoid.miraimc.nukkit.utils;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.service.NKServiceManager;
import cn.nukkit.plugin.service.RegisteredServiceProvider;
import cn.nukkit.plugin.service.ServicePriority;
import cn.nukkit.utils.Config;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;

/**
 * bStats collects some data for plugin authors.
 * <p>
 * Check out https://bStats.org/ to learn more about bStats!
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class MetricsLite {

    static {
        // You can use the property to disable the check in your test environment
        if (System.getProperty("bstats.relocatecheck") == null || !System.getProperty("bstats.relocatecheck").equals("false")) {
            // Maven's Relocate is clever and changes strings, too. So we have to use this little "trick" ... :D
            final String defaultPackage = new String(new byte[]{'o', 'r', 'g', '.', 'b', 's', 't', 'a', 't', 's', '.', 'n', 'u', 'k', 'k', 'i', 't'});
            final String examplePackage = new String(new byte[]{'y', 'o', 'u', 'r', '.', 'p', 'a', 'c', 'k', 'a', 'g', 'e'});
            // We want to make sure nobody just copy & pastes the example and use the wrong package names
            if (MetricsLite.class.getPackage().getName().equals(defaultPackage) || MetricsLite.class.getPackage().getName().equals(examplePackage)) {
                throw new IllegalStateException("bStats Metrics class has not been relocated correctly!");
            }
        }
    }

    // This ThreadFactory enforces the naming convention for our Threads
    private final ThreadFactory threadFactory = task -> new Thread(task, "bStats-Metrics");

    // Executor service for requests
    // We use an executor service because the Nukkit scheduler is affected by server lags
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1, threadFactory);

    // The version of this bStats class
    public static final int B_STATS_VERSION = 1;

    // The url to which the data is sent
    private static final String URL = "https://bStats.org/submitData/bukkit";

    // Is bStats enabled on this server?
    private boolean enabled;

    // Should failed requests be logged?
    private static boolean logFailedRequests;

    // Should the sent data be logged?
    private static boolean logSentData;

    // Should the response text be logged?
    private static boolean logResponseStatusText;

    // The uuid of the server
    private static String serverUUID;

    // The plugin
    private final Plugin plugin;

    // The plugin id
    private final int pluginId;

    /**
     * Class constructor.
     *
     * @param plugin The plugin which stats should be submitted.
     * @param pluginId The id of the plugin.
     *                 It can be found at <a href="https://bstats.org/what-is-my-plugin-id">What is my plugin id?</a>
     */
    public MetricsLite(Plugin plugin, int pluginId) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null!");
        }
        this.plugin = plugin;
        this.pluginId = pluginId;

        try {
            loadConfig();
        } catch (IOException e) {
            // Failed to load configuration
            plugin.getLogger().warning("Failed to load bStats config!", e);
        }

        if (enabled) {
            boolean found = false;
            // Search for all other bStats Metrics classes to see if we are the first one
            for (Class<?> service : Server.getInstance().getServiceManager().getKnownService()) {
                try {
                    service.getField("B_STATS_VERSION"); // Our identifier :)
                    found = true; // We aren't the first
                    break;
                } catch (NoSuchFieldException ignored) { }
            }
            // Register our service
            Server.getInstance().getServiceManager().register(MetricsLite.class, this, plugin, ServicePriority.NORMAL);
            if (!found) {
                // We are the first!
                startSubmitting();
            }
        }
    }

    /**
     * Checks if bStats is enabled.
     *
     * @return Whether bStats is enabled or not.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Starts the Scheduler which submits our data every 30 minutes.
     */
    private void startSubmitting() {
        final Runnable submitTask = () -> {
            if (!plugin.isEnabled()) { // Plugin was disabled
                scheduler.shutdown();
                return;
            }
            // Nevertheless we want our code to run in the Nukkit main thread, so we have to use the Nukkit scheduler
            // Don't be afraid! The connection to the bStats server is still async, only the stats collection is sync ;)
            Server.getInstance().getScheduler().scheduleTask(plugin, this::submitData);
        };

        // Many servers tend to restart at a fixed time at xx:00 which causes an uneven distribution of requests on the
        // bStats backend. To circumvent this problem, we introduce some randomness into the initial and second delay.
        // WARNING: You must not modify and part of this Metrics class, including the submit delay or frequency!
        // WARNING: Modifying this code will get your plugin banned on bStats. Just don't do it!
        long initialDelay = (long) (1000 * 60 * (3 + Math.random() * 3));
        long secondDelay = (long) (1000 * 60 * (Math.random() * 30));
        scheduler.schedule(submitTask, initialDelay, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(submitTask, initialDelay + secondDelay, 1000 * 60 * 30, TimeUnit.MILLISECONDS);
    }

    /**
     * Gets the plugin specific data.
     * This method is called using Reflection.
     *
     * @return The plugin specific data.
     */
    public JsonObject getPluginData() {
        JsonObject data = new JsonObject();

        String pluginName = plugin.getDescription().getName();
        String pluginVersion = plugin.getDescription().getVersion();

        data.addProperty("pluginName", pluginName); // Append the name of the plugin
        data.addProperty("id", pluginId); // Append the id of the plugin
        data.addProperty("pluginVersion", pluginVersion); // Append the version of the plugin

        data.add("customCharts", new JsonArray());

        return data;
    }

    /**
     * Gets the server specific data.
     *
     * @return The server specific data.
     */
    private JsonObject getServerData() {
        // Minecraft specific data
        int playerAmount = Server.getInstance().getOnlinePlayers().size();
        int onlineMode = Server.getInstance().getPropertyBoolean("xbox-auth", true) ? 1 : 0;
        String softwareVersion = Server.getInstance().getApiVersion() + " (MC: " + Server.getInstance().getVersion().substring(1) + ")";
        String softwareName = Server.getInstance().getName();

        // OS/Java specific data
        String javaVersion = System.getProperty("java.version");
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");
        String osVersion = System.getProperty("os.version");
        int coreCount = Runtime.getRuntime().availableProcessors();

        JsonObject data = new JsonObject();

        data.addProperty("serverUUID", serverUUID);

        data.addProperty("playerAmount", playerAmount);
        data.addProperty("onlineMode", onlineMode);
        data.addProperty("bukkitVersion", softwareVersion);
        data.addProperty("bukkitName", softwareName);

        data.addProperty("javaVersion", javaVersion);
        data.addProperty("osName", osName);
        data.addProperty("osArch", osArch);
        data.addProperty("osVersion", osVersion);
        data.addProperty("coreCount", coreCount);

        return data;
    }

    /**
     * Collects the data and sends it afterwards.
     */
    @SuppressWarnings("unchecked")
    private void submitData() {
        final JsonObject data = getServerData();

        JsonArray pluginData = new JsonArray();
        // Search for all other bStats Metrics classes to get their plugin data
        for (Class<?> service : Server.getInstance().getServiceManager().getKnownService()) {
            try {
                service.getField("B_STATS_VERSION"); // Our identifier :)

                List<? extends RegisteredServiceProvider<?>> providers = null;
                try {
                    providers = Server.getInstance().getServiceManager().getRegistrations(service);
                } catch (Throwable t) { // no such method: ServiceManager.getRegistrations()
                    try {
                        Field handle = NKServiceManager.class.getDeclaredField("handle");
                        handle.setAccessible(true);
                        providers = ((Map<Class<?>, List<RegisteredServiceProvider<?>>>) handle.get(Server.getInstance().getServiceManager())).get(service);
                    } catch (NullPointerException | NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
                        if (logFailedRequests) {
                            plugin.getLogger().error("Encountered unexpected exception ", e);
                        }
                    }
                }

                if (providers != null) {
                    for (RegisteredServiceProvider<?> provider : providers) {
                        try {
                            Object plugin = provider.getService().getMethod("getPluginData").invoke(provider.getProvider());
                            if (plugin instanceof JsonObject) {
                                pluginData.add((JsonElement) plugin);
                            }
                        } catch (NullPointerException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) { }
                    }
                }
            } catch (NoSuchFieldException ignored) { }
        }

        data.add("plugins", pluginData);

        // Create a new thread for the connection to the bStats server
        new Thread(() -> {
            try {
                // Send the data
                sendData(plugin, data);
            } catch (Exception e) {
                // Something went wrong! :(
                if (logFailedRequests) {
                    plugin.getLogger().warning("Could not submit plugin stats of " + plugin.getName(), e);
                }
            }
        }).start();
    }

    /**
     * Loads the bStats configuration.
     *
     * @throws IOException If something did not work :(
     */
    private void loadConfig() throws IOException {
        File bStatsFolder = new File(plugin.getDataFolder().getParentFile(), "bStats");
        File configFile = new File(bStatsFolder, "config.yml");
        Config config = new Config(configFile);

        // Check if the config file exists
        if (!config.exists("serverUuid")) {
            // Add default values
            config.set("enabled", true);
            // Every server gets it's unique random id.
            config.set("serverUuid", UUID.randomUUID().toString());
            // Should failed request be logged?
            config.set("logFailedRequests", false);
            // Should the sent data be logged?
            config.set("logSentData", false);
            // Should the response text be logged?
            config.set("logResponseStatusText", false);

            DumperOptions dumperOptions = new DumperOptions();
            dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            writeFile(configFile,
                    "# bStats collects some data for plugin authors like how many servers are using their plugins.",
                    "# To honor their work, you should not disable it.",
                    "# This has nearly no effect on the server performance!",
                    "# Check out https://bStats.org/ to learn more :)",
                    new Yaml(dumperOptions).dump(config.getRootSection()));
        }

        // Load the data
        enabled = config.getBoolean("enabled", true);
        serverUUID = config.getString("serverUuid");
        logFailedRequests = config.getBoolean("logFailedRequests", false);
        logSentData = config.getBoolean("logSentData", false);
        logResponseStatusText = config.getBoolean("logResponseStatusText", false);
    }

    /**
     * Writes a String to a file. It also adds a note for the user.
     *
     * @param file The file to write to. Cannot be null.
     * @param lines The lines to write.
     * @throws IOException If something did not work :(
     */
    private void writeFile(File file, String... lines) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        }
    }

    /**
     * Sends the data to the bStats server.
     *
     * @param plugin Any plugin. It's just used to get a logger instance.
     * @param data The data to send.
     * @throws Exception If the request failed.
     */
    private static void sendData(Plugin plugin, JsonObject data) throws Exception {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        if (Server.getInstance().isPrimaryThread()) {
            throw new IllegalAccessException("This method must not be called from the main thread!");
        }
        if (logSentData) {
            plugin.getLogger().info("Sending data to bStats: " + data);
        }
        HttpsURLConnection connection = (HttpsURLConnection) new URL(URL).openConnection();

        // Compress the data to save bandwidth
        byte[] compressedData = compress(data.toString());

        // Add headers
        connection.setRequestMethod("POST");
        connection.addRequestProperty("Accept", "application/json");
        connection.addRequestProperty("Connection", "close");
        connection.addRequestProperty("Content-Encoding", "gzip"); // We gzip our request
        connection.addRequestProperty("Content-Length", String.valueOf(compressedData.length));
        connection.setRequestProperty("Content-Type", "application/json"); // We send our data in JSON format
        connection.setRequestProperty("User-Agent", "MC-Server/" + B_STATS_VERSION);

        // Send data
        connection.setDoOutput(true);
        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            outputStream.write(compressedData);
        }

        StringBuilder builder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        }

        if (logResponseStatusText) {
            plugin.getLogger().info("Sent data to bStats and received response: " + builder);
        }
    }

    /**
     * Gzips the given String.
     *
     * @param str The string to gzip.
     * @return The gzipped String.
     * @throws IOException If the compression failed.
     */
    private static byte[] compress(final String str) throws IOException {
        if (str == null) {
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(outputStream)) {
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
        }
        return outputStream.toByteArray();
    }

}
