package me.dreamvoid.miraimc.sponge.event.group;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import net.mamoe.mirai.event.events.GroupEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

/**
 * (Bungee) Mirai 核心事件 - 群（抽象）
 */
abstract class AbstractGroupEvent extends AbstractEvent {
	private final GroupEvent event;
	private final Cause cause;

	public AbstractGroupEvent(GroupEvent event, Cause cause) {
		this.event = event;
		this.cause = cause;
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

	@Override
	public @NotNull Cause getCause() {
		return cause;
	}
}
