package me.dreamvoid.miraimc.sponge.commands;

import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.sponge.SpongePlugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class MiraiVerifyCommand implements CommandExecutor {
    final PluginContainer plugin;

    public MiraiVerifyCommand(SpongePlugin plugin){
        this.plugin = plugin.getPluginContainer();
    }

    @Override
    public @NotNull CommandResult execute(@NotNull CommandSource sender, CommandContext arg) throws CommandException {
        if(arg.<String>getOne("args").isPresent()){
            String argo = arg.<String>getOne("args").get();
            String[] args = argo.split("\\s+");
            switch (args[0].toLowerCase()){
                case "unsafedevice":{
                    if(args.length >= 2){
                        MiraiLoginSolver.solve(Long.parseLong(args[1]));
                        sender.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a已将验证请求提交到服务器"));
                    } else sender.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法：/miraiverify unsafedevice <账号>"));
                    break;
                }
                case "captcha":{
                    if(args.length >= 3){
                        sender.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a已将验证码提交到服务器"));
                        MiraiLoginSolver.solve(Long.parseLong(args[1]),args[2]);
                    } else sender.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法：/miraiverify captcha <账号> <验证码>"));
                    break;
                }
                case "cancel":{
                    if(args.length >= 2){
                        MiraiLoginSolver.cancel(Long.parseLong(args[1]));
                        sender.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a已取消登录验证流程"));
                    } else sender.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法：/miraiverify cancel <账号>"));
                    break;
                }
            }
            return CommandResult.builder().successCount(1).build();
        } else throw new ArgumentParseException(Text.of("isPresent() returned false!"),"MiraiMC",0);
    }
}
