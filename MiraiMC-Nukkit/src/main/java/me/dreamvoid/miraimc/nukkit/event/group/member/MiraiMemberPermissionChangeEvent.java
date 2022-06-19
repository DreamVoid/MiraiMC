package me.dreamvoid.miraimc.nukkit.event.group.member;

import net.mamoe.mirai.event.events.MemberPermissionChangeEvent;
import me.dreamvoid.miraimc.nukkit.NukkitPlugin;

/**
 * (Nukkit) Mirai 核心事件 - 群 - 群成员 - 成员权限 - 成员权限改变
 */
public class MiraiMemberPermissionChangeEvent extends AbstractGroupMemberEvent {
    public MiraiMemberPermissionChangeEvent(MemberPermissionChangeEvent event) {
        super(event);
        this.event = event;

        NukkitPlugin.getInstance().getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.nukkit.event.MiraiGroupMemberPermissionChangeEvent(event));
    }

    private final MemberPermissionChangeEvent event;

    /**
     * 返回成员的原有权限。
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    public int getOriginPermission() {
        return event.getOrigin().getLevel();
    }

    /**
     * 返回成员的新权限。
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    public int getNewPermission() {
        return event.getNew().getLevel();
    }

    /**
     * 获取被操作成员QQ号
     * @return QQ号
     */
    public long getMemberID(){
        return event.getMember().getId();
    }
}
