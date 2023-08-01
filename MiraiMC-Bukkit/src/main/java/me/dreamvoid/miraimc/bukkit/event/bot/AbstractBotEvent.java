package me.dreamvoid.miraimc.bukkit.event.bot;

import me.dreamvoid.miraimc.api.MiraiBot;
import net.mamoe.mirai.event.events.BotEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * (Bukkit) Bot（抽象）
 */
@SuppressWarnings("unused")
public abstract class AbstractBotEvent extends Event {
	public AbstractBotEvent(BotEvent event){
		super(true);
		this.event = event;
	}

	private static final HandlerList handlers = new HandlerList();
	private final BotEvent event;

	public @NotNull HandlerList getHandlers() { return handlers; }
	public static HandlerList getHandlerList() { return handlers; }

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
