package me.dreamvoid.miraimc.bungee.event.bot;

import me.dreamvoid.miraimc.api.MiraiBot;
import net.mamoe.mirai.event.events.BotEvent;
import net.md_5.bungee.api.plugin.Event;
import org.jetbrains.annotations.NotNull;

/**
 * (Bungee) Mirai 核心事件 - Bot（抽象）
 */
public abstract class AbstractBotEvent extends Event {
	public AbstractBotEvent(BotEvent event){
		this.event = event;
	}

	private final BotEvent event;
	/**
	 * 获取机器人账号
	 * @return 机器人账号
	 */
	public long getBotID() {
		return event.getBot().getId();
	}

	/**
	 * 获取机器人昵称
	 * @return 机器人昵称
	 */
	public String getNick() { return event.getBot().getNick(); }

	/**
	 * 获取机器人实例
	 * @return 机器人实例
	 */
	public MiraiBot getBot() {
		return MiraiBot.asBot(event.getBot());
	}

	/**
	 * 获取原始事件内容<br>
	 * [!] 不推荐使用
	 * @return 原始事件内容
	 */
	@Override
	public String toString() {
		return event.toString();
	}

	/**
	 * 获取哈希值
	 * @return 哈希值
	 */
	public int getHashCode() {
		return event.hashCode();
	}
}
