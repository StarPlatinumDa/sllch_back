package com.vueadmin.system.mapper;

import com.vueadmin.system.domain.GroupChat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/8 13:56
 */
@Mapper
@Repository
public interface GroupChatMapper {

    // 创建群聊
    public int addGroup(GroupChat groupChat);

    // 解散群聊
    public int delGroup(int groupId);

    // 修改群名(map->{groupId, groupName})
    public int updateGroupName(Map map);

    // 修改群头像(map->{groupId, groupPhoto})
    public int updateGroupPhoto(Map map);

    // 查找某一个群
    public GroupChat selectOne(int groupId);

    // 通过群号或群名称查找群
    public List<GroupChat> selectGroupByIdOrName(String content);

}
