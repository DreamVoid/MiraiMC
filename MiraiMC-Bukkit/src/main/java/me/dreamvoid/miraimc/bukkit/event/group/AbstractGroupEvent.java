package me.dreamvoid.miraimc.bukkit.event.group;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import net.mamoe.mirai.event.events.GroupEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * (Bukkit) 群（抽象）
 */
abstract class AbstractGroupEvent extends Event {
	public AbstractGroupEvent(GroupEvent event) {
		super(true);
		this.event = event;
	}

	private static final HandlerList handlers = new HandlerList();
	private final GroupEvent event;

	public @NotNull HandlerList getHandlers() { return handlers; }
	public static HandlerList getHandlerList() { return handlers; }

	/**
	 * 获取机器人账号
	 * @return 机器人账号
	 */
	public long getBotID() { return event.getBot().getId(); }

	/**
	 * 返回加入群的群号
	 * @return 群号
	 */
	public long getGroupID() { return event.getGroup().getId(); }

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
	 * 获取群实例
	 * @return MiraiGroup 实例
	 */
	public MiraiGroup getGroup(){
		return new MiraiGroup(event.getBot(), event.getGroup().getId());
	}
}
