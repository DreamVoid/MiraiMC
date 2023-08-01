package me.dreamvoid.miraimc.bungee.event.message.recall;

import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * (BungeeCord) 消息 - 消息撤回（抽象）
 */
@SuppressWarnings("unused")
abstract class AbstractMessageRecallEvent extends Event {
	public AbstractMessageRecallEvent(MessageRecallEvent event) {
		this.event = event;
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
}
