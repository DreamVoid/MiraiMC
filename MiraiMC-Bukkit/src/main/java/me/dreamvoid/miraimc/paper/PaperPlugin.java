package me.dreamvoid.miraimc.paper;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import me.dreamvoid.miraimc.commands.MiraiCommand;
import me.dreamvoid.miraimc.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.internal.loader.LibraryLoader;
import me.dreamvoid.miraimc.paper.utils.SpecialUtils;
import org.bukkit.plugin.Plugin;

import java.net.URLClassLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class PaperPlugin extends BukkitPlugin {
    private final ConcurrentHashMap<Integer, ScheduledTask> tasks = new ConcurrentHashMap<>();
    private final LibraryLoader loader;

    public PaperPlugin(){
        super();
        loader = new LibraryLoader((URLClassLoader) getClassLoader().getParent());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onEnable() {
        super.onEnable();

        getLogger().info("Registering commands for paper.");
        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("mirai", "MiraiMC Bot Command.", (stack, args) -> new MiraiCommand().onCommand(SpecialUtils.getSender(stack), args));
            commands.register("miraimc", "MiraiMC Plugin Command.", (stack, args) -> new MiraiMcCommand().onCommand(SpecialUtils.getSender(stack), args));
            commands.register("miraiverify", "MiraiMC LoginVerify Command.", (stack, args) -> new MiraiVerifyCommand().onCommand(SpecialUtils.getSender(stack), args));
        });
    }

    @Override
    public void runTaskAsync(Runnable task) {
        getServer().getAsyncScheduler().runNow(this, scheduledTask -> task.run());
    }

    @Override
    public void runTaskLaterAsync(Runnable task, long delay) {
        getServer().getAsyncScheduler().runDelayed(this, scheduledTask -> task.run(), delay * 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public ClassLoader getPluginClassLoader() {
        return getClassLoader().getParent();
    }

    @Override
    public LibraryLoader getLibraryLoader() {
        return loader;
    }

    @Override
    public String getType() {
        return "Paper";
    }
}
