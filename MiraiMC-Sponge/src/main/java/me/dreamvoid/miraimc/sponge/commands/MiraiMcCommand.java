package me.dreamvoid.miraimc.sponge.commands;

import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.sponge.SpongeConfig;
import me.dreamvoid.miraimc.sponge.SpongePlugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.io.IOException;

public class MiraiMcCommand implements CommandExecutor {
    PluginContainer plugin;

    public MiraiMcCommand(SpongePlugin plugin){
        this.plugin = plugin.getPluginContainer();
    }
    
    @Override
    public @NotNull CommandResult execute(@NotNull CommandSource src, CommandContext arg) throws CommandException {
        if(arg.<String>getOne("args").isPresent()){
            String argo = arg.<String>getOne("args").get();
            String[] args = argo.split("\\s+");
            switch (args[0].toLowerCase()) {
                case "reload": {
                    if(src.hasPermission("miraimc.command.miraimc.reload")){
                        try {
                            SpongeConfig.reloadConfig();
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a配置文件已经重新加载，部分配置可能需要重新启动服务器才能生效！"));
                        } catch (IOException e) {
                            e.printStackTrace();
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c加载配置文件时出错，请检查控制台了解更多信息！"));
                        }
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "bind": {
                    if(src.hasPermission("miraimc.command.miraimc.bind")){
                        if(args.length >= 2){
                            switch (args[1].toLowerCase()){
                                case "add": {
                                    if(args.length>=4){
                                        Task.builder().async().name("MiraiMC Bot Login Task").execute(() -> {

                                            String uuid = Sponge.getServer().getPlayer(args[2]).get().getUniqueId().toString();
                                            long qqid = Long.parseLong(args[3]);
                                            MiraiMC.addBinding(uuid,qqid);
                                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a已添加绑定！"));
                                        }).submit(plugin);
                                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法: /miraimc bind add <玩家名> <QQ号>"));
                                    break;
                                }
                                case "removeplayer":{
                                    if(args.length>=3){
                                        Task.builder().async().name("MiraiMC Bot Login Task").execute(() -> {
                                            String uuid = Sponge.getServer().getPlayer(args[2]).get().getUniqueId().toString();
                                            MiraiMC.removeBinding(uuid);
                                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a已移除相应绑定！"));
                                        }).submit(plugin);
                                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法: /miraimc bind removeplayer <玩家名>"));
                                    break;
                                }
                                case "removeqq":{
                                    if(args.length>=3){
                                        Task.builder().async().name("MiraiMC Bot Login Task").execute(() -> {
                                            long qqid = Long.parseLong(args[2]);
                                            MiraiMC.removeBinding(qqid);
                                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a已移除相应绑定！"));
                                        }).submit(plugin);
                                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法: /miraimc bind removeqq <QQ号>"));
                                    break;
                                }
                                case "getplayer":{
                                    if(args.length>=3){
                                        Task.builder().async().name("MiraiMC Bot Login Task").execute(() -> {
                                            String uuid = Sponge.getServer().getPlayer(args[2]).get().getUniqueId().toString();
                                            long qqId = MiraiMC.getBinding(uuid);
                                            if(qqId!=0){
                                                src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a绑定的QQ号："+qqId));
                                            } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c未找到符合条件的记录！"));
                                        }).submit(plugin);
                                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法: /miraimc bind getplayer <玩家名>"));
                                    break;
                                }
                                case "getqq":{
                                    if(args.length>=3){
                                        Task.builder().async().name("MiraiMC Bot Login Task").execute(() -> {
                                            long qqid = Long.parseLong(args[2]);
                                            String UUID = MiraiMC.getBinding(qqid);
                                            if(!UUID.equals("")){
                                                Player player = Sponge.getServer().getPlayer(UUID).get(); // 对于此方法来说，任何玩家都存在. 亲测是真的
                                                src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a绑定的玩家名："+player.getName()));
                                            } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c未找到符合条件的记录！"));
                                        }).submit(plugin);
                                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法: /miraimc bind getqq <QQ号>"));
                                    break;
                                }
                                default:{
                                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c未知或不完整的命令，请输入 /miraimc bind 查看帮助！"));
                                    break;
                                }
                            }
                        } else {
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6&lMiraiMC&r &b插件帮助菜单&r &a玩家绑定"));
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/miraimc bind add <玩家名> <QQ号>:&r 为玩家和QQ号添加绑定"));
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/miraimc bind getplayer <玩家名>:&r 获取指定玩家名绑定的QQ号"));
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/miraimc bind getqq <QQ号>:&r 获取指定QQ号绑定的玩家名"));
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/miraimc bind removeplayer <玩家名>:&r 删除一个玩家的绑定"));
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/miraimc bind removeqq <QQ号>:&r 删除一个QQ号的绑定"));
                        }
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "help": {
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6&lMiraiMC&r &b插件帮助菜单"));
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/miraimc bind:&r 玩家绑定菜单"));
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/miraimc reload:&r 重新加载插件"));
                    break;
                }
                default:{
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c未知或不完整的命令，请输入 /miraimc help 查看帮助！"));
                    break;
                }
            }
            return CommandResult.builder().successCount(1).build();
        } else throw new ArgumentParseException(Text.of("isPresent() returned false!"),"MiraiMC",0);
    }
}
