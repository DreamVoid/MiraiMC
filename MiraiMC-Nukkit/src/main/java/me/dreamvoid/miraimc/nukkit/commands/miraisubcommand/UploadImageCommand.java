package me.dreamvoid.miraimc.nukkit.commands.miraisubcommand;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.config.PluginConfig;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseSubCommand;

import java.io.File;
import java.util.NoSuchElementException;

public class UploadImageCommand extends BaseSubCommand {
	protected UploadImageCommand(String name) {
		super(name);
	}

	@Override
	public boolean canUser(CommandSender sender) {
		return sender.hasPermission("miraimc.command.mirai.uploadimage");
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (args.length >= 3) {
			File ImageDir = new File(PluginConfig.PluginDir, "images");
			if(!ImageDir.exists() && !ImageDir.mkdir()) sender.sendMessage("&c图片文件夹创建失败，是否有目录的读写权限？");
			File image = new File(ImageDir, args[2]);

			if(!image.exists() || image.isDirectory()) {
				sender.sendMessage(TextFormat.colorize('&',"&c指定的图片文件不存在，请检查是否存在文件" + image.getPath()+"！"));
				return true;
			}

			try {
				sender.sendMessage(TextFormat.colorize('&', "&a图片上传成功，可使用Mirai Code发送图片：[mirai:image:" + MiraiBot.getBot(Long.parseLong(args[1])).uploadImage(image)) + "]");
			} catch (NoSuchElementException e){
				sender.sendMessage(TextFormat.colorize('&', "&c指定的机器人不存在！"));
				return true;
			}

		} else sender.sendMessage(TextFormat.colorize('&',"&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));

		return false;
	}

	@Override
	public CommandParameter[] getParameters() {
		return new CommandParameter[]{
				CommandParameter.newType("账号", CommandParamType.INT),
				CommandParameter.newType("图片文件", CommandParamType.TEXT),
		};
	}
}
