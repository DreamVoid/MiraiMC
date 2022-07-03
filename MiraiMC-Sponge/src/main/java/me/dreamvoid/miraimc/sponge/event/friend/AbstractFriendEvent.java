package me.dreamvoid.miraimc.sponge.event.friend;

import me.dreamvoid.miraimc.api.bot.MiraiFriend;
import net.mamoe.mirai.event.events.FriendEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

/**
 * (Sponge) 好友（抽象）
 */
abstract class AbstractFriendEvent extends AbstractEvent {
	private final FriendEvent event;

	private final Cause cause;

	public AbstractFriendEvent(FriendEvent event, Cause cause){
		this.event = event;
		this.cause = cause;
	}

	/**
	 * 获取机器人账号
	 * @return 机器人账号
	 */
	public long getBotID() {
		return event.getBot().getId();
	}

	/**
	 * 获取好友QQ号
	 * @return QQ号
	 */
	public long getFriendID(){
		return event.getFriend().getId();
	}
	/**
	 * 获取好友昵称
	 * @return 昵称
	 */
	public String getFriendNick() {
		return event.getFriend().getNick();
	}

	/**
	 * (?) 获取好友昵称
	 * @return 昵称
	 */
	public String getFriendRemark(){
		return event.getFriend().getRemark();
	}

	/**
	 * 获取哈希值
	 * @return 哈希值
	 */
	public int getHashCode() {
		return event.hashCode();
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
	 * 获取好友实例
	 * @return MiraiFriend 实例
	 */
	public MiraiFriend getFriend(){
		return new MiraiFriend(event.getBot(), event.getFriend().getId());
	}

	/**
	 * 获取好友头像链接
	 * @return Url链接
	 */
	public String getAvatarUrl() {
		return event.getFriend().getAvatarUrl();
	}

	@Override
	public @NotNull Cause getCause() {
		return cause;
	}
}
