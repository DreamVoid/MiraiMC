package me.dreamvoid.miraimc.sponge;

import com.google.inject.Inject;
import me.dreamvoid.miraimc.*;
import me.dreamvoid.miraimc.commands.MiraiCommand;
import me.dreamvoid.miraimc.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.sponge.utils.Metrics;
import me.dreamvoid.miraimc.sponge.utils.SpecialUtils;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.metric.MetricsConfigManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Plugin(id = "miraimc",
        name = "MiraiMC",
        description = "MiraiBot for Minecraft server",
        version = "1.8-rc2",
        url = "https://github.com/DreamVoid/MiraiMC",
        authors = {"DreamVoid"}
)
public class SpongePlugin implements Platform {
    private MiraiMCPlugin lifeCycle;
    private MiraiMCConfig platformConfig;
    private java.util.logging.Logger SpongeLogger;

    public SpongePlugin(){
    }

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private File dataFolder;

    @Inject
    private PluginContainer pluginContainer;

    @Inject
    private MetricsConfigManager metricsConfigManager;

    private MiraiEvent MiraiEvent;
    private MiraiAutoLogin MiraiAutoLogin;

    /**
     * 触发 GamePreInitializationEvent 时，插件准备进行初始化，这时默认的 Logger 已经准备好被调用，同时你也可以开始引用配置文件中的内容。
     */
    @Listener
    public void onLoad(GamePreInitializationEvent e) {
        SpongeLogger = new SpongeLogger("MiraiMC", this);
        lifeCycle = new MiraiMCPlugin(this);
        lifeCycle.startUp();
        platformConfig = new SpongeConfig(this);

        try {
            lifeCycle.preLoad();

            MiraiAutoLogin = new MiraiAutoLogin(this);
            MiraiEvent = new MiraiEvent(this);
        } catch (Exception ex) {
            getLogger().warn("An error occurred while loading plugin.");
            ex.printStackTrace();
        }
    }

    /**
     * 触发 GameInitializationEvent 时，插件应该完成他所需功能的所有应该完成的准备工作，你应该在这个事件发生时注册监听事件。
     */
    @Listener
    public void onEnable(GameInitializationEvent e) {
        lifeCycle.postLoad();

        // 监听事件
        if(MiraiMCConfig.General.LogEvents){
            getLogger().info("Registering events.");
            Sponge.getEventManager().registerListeners(this, new Events());
        }

        // bStats统计
        if(MiraiMCConfig.General.AllowBStats) {
            if(this.metricsConfigManager.getCollectionState(this.pluginContainer).asBoolean()){
                getLogger().info("Initializing bStats metrics.");
                int pluginId = 12847;
                new Metrics(this.pluginContainer,getLogger(), getDataFolder().toPath(), pluginId);
            } else {
                getLogger().warn("你在配置文件中启用了bStats，但是MetricsConfigManager告知MiraiMC不允许收集信息，因此bStats已关闭");
                getLogger().warn("要启用bStats，请执行命令 /sponge metrics miraimc enable");
                getLogger().warn("或者在配置文件中禁用bStats隐藏此警告");
            }
        }

        // HTTP API
        if(MiraiMCConfig.General.EnableHttpApi){
            getLogger().info("Initializing HttpAPI async task.");
            Sponge.getScheduler().createTaskBuilder()
                    .async()
                    .execute(new MiraiHttpAPIResolver(this))
                    .intervalTicks(MiraiMCConfig.HttpApi.MessageFetch.Interval)
                    .name("MiraiMC-HttpApi")
                    .submit(this);
        }
    }

    /**
     * 触发 GameStartingServerEvent 时，服务器初始化和世界载入都已经完成，你应该在这时注册插件命令。
     */
    @Listener
    public void onServerLoaded(GameStartingServerEvent e) {
        getLogger().info("Registering commands.");

        CommandSpec mirai = CommandSpec.builder()
                .description(Text.of("MiraiMC Bot Command."))
                .permission("miraimc.command.mirai")
                .executor((src, arg) -> {
                    if(arg.<String>getOne("args").isPresent()){
                        String argo = arg.<String>getOne("args").get();
                        String[] args = argo.split("\\s+");
                        new MiraiCommand().onCommand(SpecialUtils.getSender(src), args);
                        return CommandResult.builder().successCount(1).build();
                    } else throw new ArgumentParseException(Text.of("isPresent() returned false!"),"MiraiMC",0);
                })
                .arguments(GenericArguments.remainingJoinedStrings((Text.of("args"))))
                .build();
        CommandSpec miraimc = CommandSpec.builder()
                .description(Text.of("MiraiMC Plugin Command."))
                .permission("miraimc.command.miraimc")
                .executor((src, arg) -> {
                    if(arg.<String>getOne("args").isPresent()){
                        String argo = arg.<String>getOne("args").get();
                        String[] args = argo.split("\\s+");
                        new MiraiMcCommand().onCommand(SpecialUtils.getSender(src), args);
                        return CommandResult.builder().successCount(1).build();
                    } else throw new ArgumentParseException(Text.of("isPresent() returned false!"),"MiraiMC",0);
                })
                .arguments(GenericArguments.remainingJoinedStrings((Text.of("args"))))
                .build();
        CommandSpec miraiverify = CommandSpec.builder()
                .description(Text.of("MiraiMC LoginVerify Command."))
                .permission("miraimc.command.miraiverify")
                .executor((src, arg) -> {
                    if(arg.<String>getOne("args").isPresent()){
                        String argo = arg.<String>getOne("args").get();
                        String[] args = argo.split("\\s+");
                        new MiraiVerifyCommand().onCommand(SpecialUtils.getSender(src), args);
                        return CommandResult.builder().successCount(1).build();
                    } else throw new ArgumentParseException(Text.of("isPresent() returned false!"),"MiraiMC",0);
                })
                .arguments(GenericArguments.remainingJoinedStrings((Text.of("args"))))
                .build();

        Sponge.getCommandManager().register(this, mirai, "mirai");
        Sponge.getCommandManager().register(this, miraimc, "miraimc");
        Sponge.getCommandManager().register(this, miraiverify, "miraiverify");
    }

    /**
     * 触发 GameStoppingServerEvent 时，服务器会进入最后一个 Tick，紧接着就会开始保存世界。
     */
    @Listener
    public void onServerStopping(GameStoppingServerEvent event){
        lifeCycle.unload();
    }

    public Logger getLogger() {
        return logger;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public PluginContainer getPluginContainer() {
        return pluginContainer;
    }

    @Override
    public String getPlayerName(UUID uuid) {
        return Sponge.getServer().getPlayer(uuid).get().getName();
    }

    @Override
    public UUID getPlayerUUID(String name) {
        return Sponge.getServer().getPlayer(name).get().getUniqueId();
    }

    @Override
    public void runTaskAsync(Runnable task) {
        Sponge.getScheduler().createAsyncExecutor(this).execute(task);
    }

    @Override
    public void runTaskLaterAsync(Runnable task, long delay) {
        Sponge.getScheduler().createAsyncExecutor(this).schedule(task, delay * 50, TimeUnit.MILLISECONDS);
    }

    private final HashMap<Integer, Task> tasks = new HashMap<>();

    @Override
    public int runTaskTimerAsync(Runnable task, long period) {
        Task task1 = Sponge.getScheduler().createTaskBuilder().async().execute(task).intervalTicks(period).submit(this);
        int taskId; // 谁让sponge的任务id是uuid呢
        do {
            taskId = new Random().nextInt();
        } while(tasks.containsKey(taskId));
        tasks.put(taskId, task1);
        return taskId;
    }

    @Override
    public void cancelTask(int taskId) {
        tasks.get(taskId).cancel();
    }

    @Override
    public String getPluginName() {
        return getPluginContainer().getName();
    }

    @Override
    public String getPluginVersion() {
        return getPluginContainer().getVersion().orElse("reserved");
    }

    @Override
    public List<String> getAuthors() {
        return getPluginContainer().getAuthors();
    }

    @Override
    public java.util.logging.Logger getPluginLogger() {
        return SpongeLogger;
    }

    @Override
    public ClassLoader getPluginClassLoader() {
        return getClass().getClassLoader();
    }

    @Override
    public IMiraiAutoLogin getAutoLogin() {
        return MiraiAutoLogin;
    }

    @Override
    public IMiraiEvent getMiraiEvent() {
        return MiraiEvent;
    }

    @Override
    public MiraiMCConfig getPluginConfig() {
        return platformConfig;
    }
}
