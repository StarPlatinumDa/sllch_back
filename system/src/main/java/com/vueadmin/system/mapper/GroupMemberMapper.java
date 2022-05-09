package com.vueadmin.system.mapper;

import com.vueadmin.common.core.domain.entity.SysUser;
import com.vueadmin.system.domain.GroupChat;
import com.vueadmin.system.domain.GroupMember;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/8 13:55
 */
public interface GroupMemberMapper {

    // 添加成员
    public int addMember(GroupMember groupMember);

    // 删除成员
    public int delMember(GroupMember groupMember);

    // 查找所有群成员
    public List<SysUser> selectAllMember(int groupId);

    // 修改成员昵称
    public int updateMemberName(GroupMember groupMember);

    // 查找用户加入的群聊
    public List<GroupChat> selectAllGroup(int userId);

    // 查找某一个用户(map->{groupId, memberId})
    public GroupMember selectMember(Map map);

}
