package me.dreamvoid.miraimc.paper;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import me.dreamvoid.miraimc.commands.ICommandSender;
import me.dreamvoid.miraimc.commands.MiraiCommand;
import me.dreamvoid.miraimc.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.internal.loader.LibraryLoader;
import org.bukkit.ChatColor;
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
            // 一步到位
            class Sender{
                private final CommandSourceStack stack;
                private Sender(CommandSourceStack stack){
                    this.stack = stack;
                }

                private ICommandSender get(){
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

            final Commands commands = event.registrar();
            commands.register("mirai", "MiraiMC Bot Command.", (stack, args) -> new MiraiCommand().onCommand(new Sender(stack).get(), args));
            commands.register("miraimc", "MiraiMC Plugin Command.", (stack, args) -> new MiraiMcCommand().onCommand(new Sender(stack).get(), args));
            commands.register("miraiverify", "MiraiMC LoginVerify Command.", (stack, args) -> new MiraiVerifyCommand().onCommand(new Sender(stack).get(), args));
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
