package me.dreamvoid.miraimc.paper;

import cloud.commandframework.CommandTree;
import cloud.commandframework.arguments.standard.StringArrayArgument;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import me.dreamvoid.miraimc.commands.ICommandSender;
import me.dreamvoid.miraimc.commands.MiraiCommand;
import me.dreamvoid.miraimc.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.internal.loader.LibraryLoader;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.net.URLClassLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class PaperPlugin extends BukkitPlugin {
    private final ConcurrentHashMap<Integer, ScheduledTask> tasks = new ConcurrentHashMap<>();
    private final LibraryLoader loader;

    public PaperPlugin(){
        super();
        loader = new LibraryLoader((URLClassLoader) getClassLoader().getParent());
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getLogger().info("Registering commands for paper.");
        final Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> executionCoordinatorFunction =
                AsynchronousCommandExecutionCoordinator.<CommandSender>builder().build();
        Function<CommandSender, CommandSender> mapperFunction = Function.identity();
        PaperCommandManager<CommandSender> manager;
        try {
            manager = new PaperCommandManager<>(
                    /* Owning plugin */ this,
                    /* Coordinator function */ executionCoordinatorFunction,
                    /* Command Sender -> C */ mapperFunction,
                    /* C -> Command Sender */ mapperFunction
            );
            manager.command(manager.commandBuilder("mirai")
                    .argument(StringArrayArgument.of("args", (commandSenderCommandContext, s) -> null))
                    .handler(context -> new MiraiCommand().onCommand(new ICommandSender() {
                        @Override
                        public void sendMessage(String message) {
                            context.getSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }

                        @Override
                        public boolean hasPermission(String permission) {
                            return context.getSender().hasPermission(permission);
                        }
                    }, context.get("args")))
            );
            manager.command(manager.commandBuilder("miraimc")
                    .argument(StringArrayArgument.of("args", (commandSenderCommandContext, s) -> null))
                    .handler(context -> new MiraiMcCommand().onCommand(new ICommandSender() {
                        @Override
                        public void sendMessage(String message) {
                            context.getSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }

                        @Override
                        public boolean hasPermission(String permission) {
                            return context.getSender().hasPermission(permission);
                        }
                    }, context.get("args")))
            );
            manager.command(manager.commandBuilder("miraiverify")
                    .argument(StringArrayArgument.of("args", (commandSenderCommandContext, s) -> null))
                    .handler(context -> new MiraiVerifyCommand().onCommand(new ICommandSender() {
                        @Override
                        public void sendMessage(String message) {
                            context.getSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }

                        @Override
                        public boolean hasPermission(String permission) {
                            return context.getSender().hasPermission(permission);
                        }
                    }, context.get("args")))
            );
        } catch (final Exception e) {
            this.getLogger().severe("Failed to initialize the command this.manager");
        }
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
