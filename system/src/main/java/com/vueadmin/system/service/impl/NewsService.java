package com.vueadmin.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.vueadmin.common.core.domain.entity.SysUser;
import com.vueadmin.system.domain.*;
import com.vueadmin.system.mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @Author caiks
 * @Date 2022/5/8 19:48
 */
@Service
public class NewsService {
    @Resource
    private ChatMapper chatMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private NewsMapper newsMapper;
    @Resource
    private FriendMapper friendMapper;
    @Resource
    private FriendVerificationMapper friendVerificationMapper;
    @Resource
    private GroupVerificationMapper groupVerificationMapper;
    @Resource
    private GroupChatMapper groupChatMapper;
    @Resource
    private GroupMemberMapper groupMemberMapper;
    @Resource
    private GroupService groupService;

    public Map<String, Object> getChatFrames(int userId) {
        HashMap<String, Object> result = new HashMap<>();

        // 聊天列表
        List<Chat> chats = chatMapper.selectChatFrames(userId);

        // 通过聊天列表获取聊天对象简略信息
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < chats.size(); i++) {
            // 获取信息
            Chat chat = chats.get(i);
            if (chat.getChatType() == 1) {
                HashMap<String, Object> message = new HashMap<>();
                message.put("type", 1);
                message.put("groupId", chat.getChatId());
                System.out.println(chat.getChatId());
                GroupChat groupChat = groupChatMapper.selectOne(chat.getChatId());
                message.put("groupPhoto", groupChat.getGroupPhoto());
                message.put("groupName", groupChat.getGroupName());
                message.put("groupMembers", groupService.selectAllMember(groupChat.getGroupId()).get("users"));
                message.put("ownerId", groupChat.getOwnerId());
                HashMap<String, Object> map = new HashMap<>();
                map.put("userId", userId);
                map.put("chatId", chat.getChatId());
                map.put("source", chat.getChatType());
                News lastNews = newsMapper.getGroupLastNews(map);
                message.put("lastNews", JSON.toJSONString(lastNews));
                HashMap<String, Object> m = new HashMap<>();
                m.put("groupId", chat.getChatId());
                m.put("memberId", chat.getUserId());
                message.put("fromName", groupMemberMapper.selectMember(m));
                message.put("chatType", chat.getChatType());
                list.add(message);
                continue;
            }

            int chatId = chat.getChatId();
            SysUser user = sysUserMapper.selectUserById((long) chatId);

            HashMap<String, Object> message = new HashMap<>();
            message.put("type", 0);
            message.put("chatId", chat.getChatId());
            message.put("chatObjectPhoto", user.getAvatar());
            message.put("chatType", chat.getChatType());

            Friend friend = new Friend();
            friend.setFriendId(chat.getChatId());
            friend.setUserId(userId);
            Friend friend1 = friendMapper.selectOneFriend(friend);
            if (friend1 == null) {
                message.put("nickname", user.getNickName());
                message.put("isFriend", 0);
            } else {
                Friend friend2 = friendMapper.selectOneFriend(friend);
                message.put("nickname", friend2.getNickname());
                message.put("blackList", friend2.getFriendBlacklist());
                message.put("isFriend", 1);
            }
            message.put("userNickname", user.getNickName());

            // 获取每个聊天框最新一条消息
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("chatId", chat.getChatId());
            map.put("source", chat.getChatType());
            News lastNews = newsMapper.getLastNews(map);

            message.put("userPhoto", sysUserMapper.selectUserById((long) userId).getAvatar());
            message.put("lastNews", JSON.toJSONString(lastNews));

            // 将自定义聊天对象简略信息message添加到list里
            list.add(message);
        }

        result.put("chatFrames", list);
        result.put("msg", "请求成功！");
        result.put("status", 1);

        return result;
    }

    public Map<String, Object> getAllNewsByUserId(int userId) {
        HashMap<String, Object> result = new HashMap<>();

        List<News> news = newsMapper.getNewsByUserId(userId);
        result.put("news", news);
        result.put("msg", "请求成功！");
        result.put("status", 1);

        return result;
    }

    public Map<String, Object> updateBlackList(int userId, int chatId, int black) {
        HashMap<String, Object> result = new HashMap<>();

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("chatId", chatId);
        map.put("friendBlacklist", black);
        friendMapper.updateFriend(map);

        result.put("msg", "修改成功！");
        result.put("status", 1);

        return result;
    }

    public Map<String, Object> getFriends(int userId) {
        HashMap<String, Object> result = new HashMap<>();

        List<Friend> friends = friendMapper.selectFriends(userId);
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < friends.size(); i++) {
            Friend friend = friends.get(i);
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", friend.getUserId());
            map.put("friendId", friend.getFriendId());
            map.put("nickname", friend.getNickname());
            map.put("blackList", friend.getFriendBlacklist());
            map.put("headPhoto", sysUserMapper.selectUserById((long) friend.getFriendId()).getAvatar());
            list.add(map);
        }
        result.put("friends", list);
        result.put("msg", "请求成功！");
        result.put("status", 1);
        return result;
    }

    public Map<String, Object> selectOneFriend(int userId, int friendId) {
        HashMap<String, Object> result = new HashMap<>();

        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        Friend friend1 = friendMapper.selectOneFriend(friend);

        result.put("friend", friend1);
        result.put("msg", "请求成功！");
        result.put("status", 1);
        return result;
    }

    public Map<String, Object> delRecords(int userId, int chatId, int source) {
        HashMap<String, Object> result = new HashMap<>();

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("chatId", chatId);
        map.put("source", source);
        int r = newsMapper.delNews(map);
        if (r != -1) {
            result.put("msg", "删除成功！");
            result.put("status", 1);
        } else {
            result.put("msg", "删除失败！");
            result.put("status", -1);
        }

        return result;
    }

    public Map<String, Object> delFriend(int userId, int friendId) {
        HashMap<String, Object> result = new HashMap<>();

        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        int r = friendMapper.delFriend(friend);
        if (r != -1) {
            result.put("msg", "删除成功！");
            result.put("status", 1);
        } else {
            result.put("msg", "删除失败！");
            result.put("status", -1);
        }

        return result;
    }

    public Map<String, Object> updateNickname(int userId, int friendId, String nickname) {
        HashMap<String, Object> result = new HashMap<>();

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("friendId", friendId);
        map.put("nickname", nickname);
        friendMapper.updateFriend(map);

        result.put("msg", "修改成功！");
        result.put("status", 1);

        return result;
    }

    public Map<String, Object> getChatFrame(int userId, int chatId) {
        HashMap<String, Object> result = new HashMap<>();

        Chat chat = new Chat();
        chat.setUserId(userId);
        chat.setChatId(chatId);

        HashMap<String, Object> chatFrame = new HashMap<>();

        chatFrame.put("chatId", chatId);
        chatFrame.put("chatObjectPhoto", sysUserMapper.selectUserById((long) chatId).getAvatar());
        Friend friend = new Friend();
        friend.setFriendId(chatId);
        friend.setUserId(userId);
        Friend friend1 = friendMapper.selectOneFriend(friend);
        chatFrame.put("nickname", friend1.getNickname());
        chatFrame.put("blackList", friend1.getFriendBlacklist());
        chatFrame.put("isFriend", 1);
        // 获取每个聊天框最新一条消息
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("chatId", chatId);
        News lastNews = newsMapper.getLastNews(map);
        chatFrame.put("userPhoto", sysUserMapper.selectUserById((long) userId).getAvatar());
        chatFrame.put("lastNews", JSON.toJSONString(lastNews));

        result.put("chatFrame", chatFrame);
        result.put("msg", "请求成功！");
        result.put("status", 1);

        return result;
    }

    public Map<String, Object> addChatFrame(int userId, int chatId, int chatType) {
        HashMap<String, Object> result = new HashMap<>();

        Chat chat = new Chat();
        chat.setChatId(chatId);
        chat.setUserId(userId);
        chat.setChatType(chatType);
//        int r = -1;
//        int r2 = -1;
//        try {
//            r = chatMapper.addChatFrame(chat);
//            chat.setChatId(userId);
//            chat.setUserId(chatId);
//            r2 = chatMapper.addChatFrame(chat);
//        } catch (Exception e) {
//
//        }
//        if (r != -1 && r2 != -1) {
//            result.put("msg", "添加成功！");
//            result.put("status", 1);
//        } else {
//            result.put("msg", "添加失败！");
//            result.put("status", -1);
//        }
        Chat c = chatMapper.selectOneChatFrame(chat);
        if (c != null) {
            result.put("msg", "已有该聊天框！");
            result.put("status", 0);
        } else {
            int r = chatMapper.addChatFrame(chat);
            if (r != -1) {
                result.put("msg", "添加成功！");
                result.put("status", 1);
            } else {
                result.put("msg", "添加失败！");
                result.put("status", -1);
            }
        }

        return result;
    }

    public Map<String, Object> addVerification(int userId, int fromId, int toId, int verificationStatus, long verificationTime) {
        HashMap<String, Object> result = new HashMap<>();

        FriendVerification friendVerification = new FriendVerification(userId, fromId, toId, verificationStatus, verificationTime);

        int r = friendVerificationMapper.addVerification(friendVerification);
        if (r != -1) {
            result.put("msg", "添加成功！");
            result.put("status", 1);
        } else {
            result.put("msg", "添加失败！");
            result.put("status", -1);
        }

        return result;
    }

    public Map<String, Object> delVerification(int userId) {
        HashMap<String, Object> result = new HashMap<>();

        int r = friendVerificationMapper.delVerification(userId);
        if (r != -1) {
            result.put("msg", "删除成功！");
            result.put("status", 1);
        } else {
            result.put("msg", "删除失败！");
            result.put("status", -1);
        }

        return result;
    }

    public Map<String, Object> selectAllByUserId(int userId) {
        HashMap<String, Object> result = new HashMap<>();

        List<FriendVerification> friendVerifications = friendVerificationMapper.selectAllByUserId(userId);

        ArrayList<Map<String, Object>> list = new ArrayList<>();
        friendVerifications.forEach(item -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", item.getUserId());
            map.put("fromId", item.getFromId());
            map.put("toId", item.getToId());
            map.put("verificationStatus", item.getVerificationStatus());
            map.put("verificationTime", item.getVerificationTime());
            map.put("verificationType", 0);
            SysUser user = null;
            if (item.getUserId() == item.getFromId()) {
                user = sysUserMapper.selectUserById((long) item.getToId());
            } else {
                user = sysUserMapper.selectUserById((long) item.getFromId());
            }
            map.put("headPhoto", user.getAvatar());
            map.put("nickname", user.getNickName());
            list.add(map);
        });

        result.put("msg", "请求成功！");
        result.put("status", 1);
        result.put("friendVerifications", list);

        return result;
    }

    public Map<String, Object> updateVerification(int userId, int fromId, int toId, int verificationStatus, long currentTime) {
        HashMap<String, Object> result = new HashMap<>();

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("fromId", fromId);
        map.put("toId", toId);
        map.put("verificationStatus", verificationStatus);
        map.put("verificationTime", currentTime);
        int r = friendVerificationMapper.updateVerification(map);
        if (r != -1) {
            result.put("msg", "修改成功！");
            result.put("status", 1);
        } else {
            result.put("msg", "删除失败！");
            result.put("status", -1);
        }

        return result;
    }

    public Map<String, Object> selectOneVerification(int userId, int fromId, int toId, int verificationStatus) {
        HashMap<String, Object> result = new HashMap<>();

        FriendVerification friendVerification = new FriendVerification();
        friendVerification.setFromId(fromId);
        friendVerification.setToId(toId);
        friendVerification.setUserId(userId);
        friendVerification.setVerificationStatus(verificationStatus);
        FriendVerification friendVerification1 = friendVerificationMapper.selectOne(friendVerification);

        result.put("msg", "请求成功！");
        result.put("status", 1);
        result.put("friendVerification", friendVerification1);

        return result;
    }

    public Map<String, Object> addFriend(int userId, int friendId) {
        HashMap<String, Object> result = new HashMap<>();
        Friend friend1 = new Friend();
        friend1.setUserId(userId);
        friend1.setFriendId(friendId);
        if (friendMapper.selectOneFriend(friend1) != null) {
            result.put("status", 1);
            result.put("msg", "已是好友！");
            result.put("friendPhoto", sysUserMapper.selectUserById((long) friendId).getAvatar());
            result.put("userPhoto", sysUserMapper.selectUserById((long) userId).getAvatar());
            result.put("nickname", sysUserMapper.selectUserById((long) userId).getNickName());
            return result;
        }

        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        friend.setFriendBlacklist(0);
        friend.setNickname(sysUserMapper.selectUserById((long) friendId).getNickName());
        friendMapper.addFriend(friend);

        friend.setUserId(friendId);
        friend.setFriendId(userId);
        friendMapper.addFriend(friend);

        result.put("msg", "添加成功！");
        result.put("friendPhoto", sysUserMapper.selectUserById((long) friendId).getAvatar());
        result.put("userPhoto", sysUserMapper.selectUserById((long) userId).getAvatar());
        result.put("nickname", sysUserMapper.selectUserById((long) userId).getNickName());
        result.put("status", 1);

        return result;
    }

    public Map<String, Object> delGroupVerification(int userId) {
        HashMap<String, Object> result = new HashMap<>();

        int r = groupVerificationMapper.delVerification(userId);
        if (r != -1) {
            result.put("msg", "删除成功！");
            result.put("status", 1);
        } else {
            result.put("msg", "删除失败！");
            result.put("status", -1);
        }

        return result;
    }

    public Map<String, Object> selectAllGroupVerificationByUserId(int userId) {
        HashMap<String, Object> result = new HashMap<>();

        List<GroupVerification> groupVerifications = groupVerificationMapper.selectAllByUserId(userId);

        ArrayList<Map<String, Object>> list = new ArrayList<>();
        groupVerifications.forEach(item -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", item.getUserId());
            map.put("fromId", item.getFromId());
            map.put("toId", item.getToId());
            map.put("verificationStatus", item.getVerificationStatus());
            map.put("verificationTime", item.getVerificationTime());
            map.put("groupId", item.getGroupId());
            GroupChat group = groupChatMapper.selectOne(item.getGroupId());
            map.put("groupPhoto", group.getGroupPhoto());
            map.put("groupName", group.getGroupName());
            map.put("verificationType", 1);
            SysUser user = null;
            if (item.getUserId() == item.getFromId()) {
                user = sysUserMapper.selectUserById((long) item.getToId());
            } else {
                user = sysUserMapper.selectUserById((long) item.getFromId());
            }
            map.put("headPhoto", user.getAvatar());
            map.put("nickname", user.getNickName());
            list.add(map);
        });

        result.put("msg", "请求成功！");
        result.put("status", 1);
        result.put("groupVerifications", list);

        return result;
    }
}
