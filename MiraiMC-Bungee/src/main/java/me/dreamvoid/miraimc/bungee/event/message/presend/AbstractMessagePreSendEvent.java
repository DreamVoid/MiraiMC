package me.dreamvoid.miraimc.bungee.event.message.presend;

import net.mamoe.mirai.event.events.MessagePreSendEvent;
import net.mamoe.mirai.message.code.MiraiCode;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

/**
 * (Bungee) Mirai 核心事件 - 消息 - 主动发送消息前（抽象）
 */
abstract class AbstractMessagePreSendEvent extends Event implements Cancellable {
	public AbstractMessagePreSendEvent(MessagePreSendEvent event) {
		this.event = event;
	}

	private final MessagePreSendEvent event;

	/**
	 * 返回发送这条信息的机器人ID
	 * @return 机器人ID
	 */
	public long getBotID(){
		return event.getBot().getId();
	}

	/**
	 * 返回接收者ID
	 * @return 可以是QQ号或群号
	 */
	public long getTargetID(){
		return event.getTarget().getId();
	}

	/**
	 * 返回接收到的消息内容转换到字符串的结果<br>
	 * 此方法使用 contentToString()<br>
	 * QQ 对话框中以纯文本方式会显示的消息内容，这适用于MC与QQ的消息互通等不方便展示原始内容的场景。<br>
	 * 无法用纯文字表示的消息会丢失信息，如任何图片都是 [图片]
	 * @return 转换字符串后的消息内容
	 */
	public String getMessage(){
		return event.getMessage().contentToString();
	}

	/**
	 * 返回接收到的消息内容转换到字符串的结果<br>
	 * 此方法使用 contentToString()<br>
	 * QQ 对话框中以纯文本方式会显示的消息内容，这适用于MC与QQ的消息互通等不方便展示原始内容的场景。<br>
	 * 无法用纯文字表示的消息会丢失信息，如任何图片都是 [图片]
	 * @return 转换字符串后的消息内容
	 * @see #getMessage()
	 * @deprecated
	 */
	@Deprecated
	public String getMessageContent(){
		return event.getMessage().contentToString();
	}

	/**
	 * 返回接收到的消息内容<br>
	 * 此方法使用 toString()<br>
	 * Java 对象的 toString()，会尽可能包含多的信息用于调试作用，行为可能不确定<br>
	 * 如需处理常规消息内容，请使用 {@link #getMessageContent()}
	 * @return 原始消息内容
	 */
	public String getMessageToString(){
		return event.getMessage().toString();
	}

	/**
	 * 设置将要发送的消息内容
	 * 支持 Mirai Code
	 * @param message 带 Mirai Code 的消息
	 */
	public void setMessage(String message) {
		event.setMessage(MiraiCode.deserializeMiraiCode(message));
	}

	/**
	 * 消息是否已被取消发送
	 * @return 如果消息被取消发送，返回true
	 */
	@Override
	public boolean isCancelled() {
		return event.isCancelled();
	}

	/**
	 * 取消消息的发送<br>
	 * 请注意，无论 cancel 参数设为什么，只要被取消过，就不能撤销。<br>
	 * 也就是说，已取消的事件永远不会继续发送消息。
	 * @param cancel 调用此方法将忽略本参数，无论设为什么
	 */
	@Override
	public void setCancelled(boolean cancel) {
		event.cancel();
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
}
