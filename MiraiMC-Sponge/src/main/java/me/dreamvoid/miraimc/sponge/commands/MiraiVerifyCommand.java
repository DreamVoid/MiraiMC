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
    PluginContainer plugin;

    public MiraiVerifyCommand(SpongePlugin plugin){
        this.plugin = plugin.getPluginContainer();
    }

    @Override
    public @NotNull CommandResult execute(@NotNull CommandSource src, CommandContext arg) throws CommandException {
        if(arg.<String>getOne("args").isPresent()){
            String argo = arg.<String>getOne("args").get();
            String[] args = argo.split("\\s+");
            switch (args[0].toLowerCase()){
                case "unsafedevice":{
                    if(args.length >= 2){
                        MiraiLoginSolver.solveUnsafeDeviceLoginVerify(Long.parseLong(args[1]),false);
                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a已将验证请求提交到服务器"));
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法：/miraiverify unsafedevice <账号>"));
                    break;
                }
                case "unsafedevicecancel":{
                    if(args.length >= 2){
                        MiraiLoginSolver.solveUnsafeDeviceLoginVerify(Long.parseLong(args[1]),true);
                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a已取消登录验证流程"));
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法：/miraiverify unsafedevicecancel <账号>"));
                    break;
                }
                case "slidercaptcha":{
                    if(args.length >= 3){
                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a已将ticket提交到服务器"));
                        MiraiLoginSolver.solveSliderCaptcha(Long.parseLong(args[1]),args[2]);
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法：/miraiverify slidercaptcha <账号> <ticket>"));
                    break;
                }
                case "slidercaptchacancel":{
                    if(args.length >= 2){
                        MiraiLoginSolver.solveSliderCaptcha(Long.parseLong(args[1]),true);
                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a已取消登录验证流程"));
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法：/miraiverify slidercaptchacancel <账号>"));
                    break;
                }
                case "piccaptcha":{
                    if(args.length >= 3){
                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a已将验证码提交到服务器"));
                        MiraiLoginSolver.solvePicCaptcha(Long.parseLong(args[1]),args[2]);
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法：/miraiverify piccaptcha <账号> <验证码>"));
                    break;
                }
                case "piccaptchacancel":{
                    if(args.length >= 2){
                        MiraiLoginSolver.solvePicCaptcha(Long.parseLong(args[1]),true);
                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a已取消登录验证流程"));
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法：/miraiverify piccaptchacancel <账号>"));
                    break;
                }
            }
            return CommandResult.builder().successCount(1).build();
        } else throw new ArgumentParseException(Text.of("isPresent() returned false!"),"MiraiMC",0);
    }
}
