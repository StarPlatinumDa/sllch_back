package com.vueadmin.system.service.impl;

import com.vueadmin.common.core.domain.entity.SysUser;
import com.vueadmin.system.domain.Chat;
import com.vueadmin.system.domain.GroupChat;
import com.vueadmin.system.domain.GroupMember;
import com.vueadmin.system.domain.GroupVerification;
import com.vueadmin.system.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/8 13:57
 */
@Service
public class GroupService {

    private final String baseURL = "http://192.168.2.6:8080/";

    @Resource
    private GroupChatMapper groupChatMapper;
    @Resource
    private GroupMemberMapper groupMemberMapper;
    @Resource
    private NewsMapper newsMapper;
    @Resource
    private GroupVerificationMapper groupVerificationMapper;
    @Resource
    private ChatMapper chatMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    // 创建群聊
    public HashMap<String, Object> addGroup(int ownerId, String groupName) {
        HashMap<String, Object> res = new HashMap<>();

        SysUser sysUser = sysUserMapper.selectUserById((long) ownerId);
        if (sysUser == null) {
            res.put("status", -1);
            res.put("msg", "创建失败！");
            return res;
        }
        GroupChat groupChat = new GroupChat();
        groupChat.setGroupName(groupName);
        groupChat.setOwnerId(ownerId);
        groupChat.setGroupPhoto(baseURL + "images/defaultGroupPhoto.png");
        int r = groupChatMapper.addGroup(groupChat);
        if (r == -1) {
            res.put("status", -1);
            res.put("msg", "创建失败！");
        } else {
            res.put("status", 1);
            res.put("msg", "创建成功！");
            res.put("groupId", groupChat.getGroupId());
            addMember(groupChat.getGroupId(), ownerId, sysUser.getNickName());
        }

        return res;
    }

    // 解散群聊
    public HashMap<String, Object> delGroup(int groupId) {
        HashMap<String, Object> res = new HashMap<>();

        int r = groupChatMapper.delGroup(groupId);
        if (r == -1) {
            res.put("status", -1);
            res.put("msg", "解散失败！");
        } else {
            chatMapper.delGroupChatFrames(groupId);
            res.put("status", 1);
            res.put("msg", "解散成功！");
        }

        return res;
    }

    // 修改群名
    public HashMap<String, Object> updateGroupName(int groupId, String groupName) {
        HashMap<String, Object> res = new HashMap<>();

        HashMap<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        map.put("groupName", groupName);
        int r = groupChatMapper.updateGroupName(map);
        if (r == -1) {
            res.put("status", -1);
            res.put("msg", "修改失败！");
        } else {
            res.put("status", 1);
            res.put("msg", "修改成功！");
        }

        return res;
    }

    // 添加成员
    public HashMap<String, Object> addMember(int groupId, int memberId, String memberName) {
        HashMap<String, Object> res = new HashMap<>();

        GroupMember groupMember = new GroupMember(groupId, memberId, memberName);
        int r = groupMemberMapper.addMember(groupMember);
        if (r == -1) {
            res.put("status", -1);
            res.put("msg", "添加失败！");
        } else {
            res.put("status", 1);
            res.put("msg", "添加成功！");
        }

        return res;
    }

    // 删除成员
    public HashMap<String, Object> delMember(int groupId, int memberId) {
        HashMap<String, Object> res = new HashMap<>();

        GroupMember groupMember = new GroupMember(groupId, memberId, "");
        int r = groupMemberMapper.delMember(groupMember);
        if (r == -1) {
            res.put("status", -1);
            res.put("msg", "删除失败！");
        } else {
            Chat chat = new Chat();
            chat.setUserId(memberId);
            chat.setChatId(groupId);
            chat.setChatType(1);
            chatMapper.delChatFrame(chat);
            res.put("status", 1);
            res.put("msg", "删除成功！");
        }

        return res;
    }

    // 查找所有群成员
    public HashMap<String, Object> selectAllMember(int groupId) {
        HashMap<String, Object> res = new HashMap<>();

        List<SysUser> users = groupMemberMapper.selectAllMember(groupId);
        users.forEach(user -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("memberId", user.getUserId());
            map.put("groupId", groupId);
            GroupMember groupMember = groupMemberMapper.selectMember(map);
            user.setNickName(groupMember.getMemberName());
        });
        if (users.size() == 0) {
            res.put("status", -1);
            res.put("msg", "查找失败！");
        } else {
            res.put("status", 1);
            res.put("msg", "查找成功！");
            res.put("users", users);
        }

        return res;
    }

    // 修改成员昵称
    public HashMap<String, Object> updateMemberName(int groupId, int memberId, String memberName) {
        HashMap<String, Object> res = new HashMap<>();

        GroupMember groupMember = new GroupMember(groupId, memberId, memberName);
        int r = groupMemberMapper.updateMemberName(groupMember);
        if (r == -1) {
            res.put("status", -1);
            res.put("msg", "修改失败！");
        } else {
            res.put("status", 1);
            res.put("msg", "修改成功！");
        }

        return res;
    }

    // 查找用户加入的群聊
    public HashMap<String, Object> selectAllGroup(int userId) {
        HashMap<String, Object> res = new HashMap<>();

        List<GroupChat> groupChats = groupMemberMapper.selectAllGroup(userId);
        if (groupChats == null) {
            res.put("status", -1);
            res.put("msg", "查找失败！");
        } else {
            ArrayList<HashMap<String, Object>> groupList = new ArrayList<>();

            groupChats.forEach(group -> {
                HashMap<String, Object> map = new HashMap<>();
                map.put("groupId", group.getGroupId());
                map.put("groupName", group.getGroupName());
                map.put("ownerId", group.getOwnerId());
                map.put("groupPhoto", group.getGroupPhoto());
                HashMap<String, Object> m = new HashMap<>();
                m.put("userId", userId);
                m.put("chatId", group.getGroupId());
                map.put("lastNews", newsMapper.getLastNews(m));

                groupList.add(map);
            });

            res.put("status", 1);
            res.put("msg", "查找成功！");
            res.put("groupList", groupList);
        }

        return res;
    }

    // 修改群头像
    public HashMap<String, Object> updateGroupPhoto(int groupId, String groupPhoto) {
        HashMap<String, Object> res = new HashMap<>();

        HashMap<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        map.put("groupPhoto", groupPhoto);
        int r = groupChatMapper.updateGroupPhoto(map);
        if (r == -1) {
            res.put("status", -1);
            res.put("msg", "修改失败！");
        } else {
            res.put("status", 1);
            res.put("msg", "修改成功！");
        }

        return res;
    }

    // 通过群号或群名称查找群
    public HashMap<String, Object> selectGroupByIdOrName(String content) {
        HashMap<String, Object> res = new HashMap<>();

        List<GroupChat> groups = groupChatMapper.selectGroupByIdOrName(content);
        res.put("status", 1);
        res.put("groups", groups);
        res.put("msg", "查询成功！");

        return res;
    }

    // 查询验证消息
    public HashMap<String, Object> selectGroupVerifications(int userId, int groupId, int fromId) {
        HashMap<String, Object> res = new HashMap<>();

        GroupVerification groupVerification = new GroupVerification();
        groupVerification.setFromId(fromId);
        groupVerification.setUserId(userId);
        groupVerification.setGroupId(groupId);
        List<GroupVerification> groupVerifications = groupVerificationMapper.selectGroupVerifications(groupVerification);
        if (groupVerifications == null) {
            res.put("status", -1);
            res.put("msg", "查询失败！");
        } else {
            res.put("groupVerifications", groupVerifications);
            res.put("status", 1);
            res.put("msg", "查询成功！");
        }

        return res;
    }

    // 更新验证消息（申请进群）
    public HashMap<String, Object> updateVerificationOfApply(int fromId, int groupId, int verificationStatus) {
        HashMap<String, Object> res = new HashMap<>();

        HashMap<String, Object> map = new HashMap<>();
        map.put("fromId", fromId);
        map.put("groupId", groupId);
        map.put("verificationStatus", verificationStatus);
        int r = groupVerificationMapper.updateVerificationOfApply(map);

        if (r == -1) {
            res.put("status", -1);
            res.put("msg", "更新失败！");
        } else {
            res.put("status", 1);
            res.put("msg", "更新成功！");
        }

        return res;
    }

    // 更新验证消息（管理员邀请进群）
    public HashMap<String, Object> updateVerificationOfInvite(int fromId, int toId, int groupId, int verificationStatus) {
        HashMap<String, Object> res = new HashMap<>();

        HashMap<String, Object> map = new HashMap<>();
        map.put("fromId", fromId);
        map.put("groupId", groupId);
        map.put("toId", toId);
        map.put("verificationStatus", verificationStatus);
        int r = groupVerificationMapper.updateVerificationOfApply(map);

        if (r == -1) {
            res.put("status", -1);
            res.put("msg", "更新失败！");
        } else {
            res.put("status", 1);
            res.put("msg", "更新成功！");
        }

        return res;
    }

}
