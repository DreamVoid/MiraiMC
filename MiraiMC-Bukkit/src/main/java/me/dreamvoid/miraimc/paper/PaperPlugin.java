package me.dreamvoid.miraimc.paper;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import me.dreamvoid.miraimc.commands.ICommandSender;
import me.dreamvoid.miraimc.commands.MiraiCommand;
import me.dreamvoid.miraimc.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.loader.LibraryLoader;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;

public class PaperPlugin extends BukkitPlugin {
    private final LibraryLoader loader;

    public PaperPlugin(){
        super();
        loader = new LibraryLoader((URLClassLoader) getClassLoader().getParent());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onEnable() {
        super.onEnable();

        getLogger().info("正在向 Paper 注册命令.");
        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("mirai", "MiraiMC Bot Command.", (stack, args) -> new MiraiCommand().onCommand(convertSender(stack), args));
            commands.register("miraimc", "MiraiMC Plugin Command.", (stack, args) -> new MiraiMcCommand().onCommand(convertSender(stack), args));
            commands.register("miraiverify", "MiraiMC LoginVerify Command.", (stack, args) -> new MiraiVerifyCommand().onCommand(convertSender(stack), args));
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
    public void runTaskTimerAsync(Runnable task, long delay, long period) {
        getServer().getAsyncScheduler().runAtFixedRate(this, scheduledTask -> task.run(), delay * 50, period * 50, TimeUnit.MILLISECONDS);
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

    @SuppressWarnings({"UnstableApiUsage", "deprecation"})
    private static ICommandSender convertSender(CommandSourceStack stack){
        return new ICommandSender() {
            @Override
            public void sendMessage(String message) {
                stack.getSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }

            @Override
            public boolean hasPermission(String permission) {
                return stack.getSender().hasPermission(permission);
            }
        };
    }
}
