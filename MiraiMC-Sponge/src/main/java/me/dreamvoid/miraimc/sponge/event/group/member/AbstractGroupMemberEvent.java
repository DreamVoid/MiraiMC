package me.dreamvoid.miraimc.sponge.event.group.member;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import me.dreamvoid.miraimc.api.bot.group.MiraiNormalMember;
import net.mamoe.mirai.event.events.GroupMemberEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

/**
 * (Sponge) Mirai 核心事件 - 群 - 群成员 - 群成员事件（抽象）
 */
abstract class AbstractGroupMemberEvent extends AbstractEvent {
	private final Cause cause;

	public AbstractGroupMemberEvent(GroupMemberEvent event, Cause cause){
		this.event = event;
		this.cause = cause;
	}

	private final GroupMemberEvent event;
	/**
	 * 获取机器人账号
	 * @return 机器人账号
	 */
	public long getBotID() { return event.getBot().getId(); }

	/**
	 * 返回目标群的群号
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

	/**
	 * 获取群员实例
	 * @return MiraiNormalMember 实例
	 */
	public MiraiNormalMember getMember(){
		return new MiraiNormalMember(event.getGroup(), event.getMember().getId());
	}

	/**
	 * 获取哈希值
	 * @return 哈希值
	 */
	public int getHashCode() {
		return event.hashCode();
	}

	@Override
	public @NotNull Cause getCause() {
		return cause;
	}
}
