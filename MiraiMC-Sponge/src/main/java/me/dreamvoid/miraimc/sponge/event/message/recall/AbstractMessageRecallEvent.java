package me.dreamvoid.miraimc.sponge.event.message.recall;

import net.mamoe.mirai.event.events.MessageRecallEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

/**
 * (Sponge) Mirai 核心事件 - 消息 - 消息撤回（抽象）
 */
abstract class AbstractMessageRecallEvent extends AbstractEvent {
	private final Cause cause;

	public AbstractMessageRecallEvent(MessageRecallEvent event, Cause cause) {
		this.event = event;
		this.cause = cause;
	}

	private final MessageRecallEvent event;

	/**
	 * 获取机器人账号
	 * @return 机器人账号
	 */
	public long getBotID() { return event.getBot().getId(); }

	/**
	 * 获取被撤回信息的发送者昵称
	 * @return 发送者昵称
	 */
	public String getAuthorNick() { return event.getAuthor().getNick(); }

	/**
	 * 获取被撤回信息的发送者ID
	 * @return 发送者ID
	 */
	public long getAuthorID() { return event.getAuthorId(); }

	/**
	 * 获取信息发送时间
	 * @return 发送时间
	 */
	public long getMessageTime() { return event.getMessageTime(); }

	/**
	 * (?)获取信息编号
	 * @return 信息编号
	 */
	public int[] getMessageIds() { return event.getMessageIds(); }

	/**
	 * 获取原始事件内容<br>
	 * [!] 不推荐使用
	 * @return 原始事件内容
	 */
	@Override
	public String toString() {
		return event.toString();
	}

	@Override
	public @NotNull Cause getCause() {
		return cause;
	}
}
