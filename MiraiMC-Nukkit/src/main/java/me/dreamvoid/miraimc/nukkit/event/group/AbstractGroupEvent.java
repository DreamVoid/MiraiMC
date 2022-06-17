package me.dreamvoid.miraimc.nukkit.event.group;

import cn.nukkit.event.Event;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import net.mamoe.mirai.event.events.GroupEvent;

/**
 * (Bungee) Mirai 核心事件 - 群（抽象）
 */
abstract class AbstractGroupEvent extends Event {
	private final GroupEvent event;

	public AbstractGroupEvent(GroupEvent event) {
		this.event = event;
	}

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
