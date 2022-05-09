package com.vueadmin.web.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vueadmin.common.core.domain.entity.SysUser;
import com.vueadmin.system.domain.*;
import com.vueadmin.system.mapper.*;
import com.vueadmin.system.service.impl.NewsService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/8 13:03
 */
@ChannelHandler.Sharable
@Component
public class MyWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Resource
    private NewsMapper newsMapper;
    @Resource
    private NewsService newsService;
    @Resource
    private FriendMapper friendMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private GroupMemberMapper groupMemberMapper;
    @Resource
    private GroupChatMapper groupChatMapper;
    @Resource
    private GroupVerificationMapper groupVerificationMapper;
    @Resource
    private ChatMapper chatMapper;
//    @Resource
//    private AgentVerificationMapper agentVerificationMapper;
//    @Resource
//    private HouseMapper houseMapper;
//    @Resource
//    private SellMapper sellMapper;
//    @Resource
//    private RentingMapper rentingMapper;

    private static Map<String, Channel> loginUser = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        long currentTime = System.currentTimeMillis();
        // 解析json字符串（客户端的消息）
        JSONObject jsonObject = JSON.parseObject(textWebSocketFrame.text());
//        if ("agentVerification".equals(jsonObject.getString("type"))) {
//            int userId = Integer.valueOf(jsonObject.get("userId").toString());
//            int fromId = Integer.valueOf(jsonObject.get("fromId").toString());
//            int toId = Integer.valueOf(jsonObject.get("toId").toString());
//            int verificationStatus = Integer.valueOf(jsonObject.get("verificationStatus").toString());
//            int houseId = jsonObject.getInteger("houseId");
//            int houseSellOrRent = jsonObject.getInteger("houseSellOrRent");
//            if ("accept".equals(jsonObject.get("option").toString()) ||
//                    "refuse".equals(jsonObject.get("option").toString())) {
//                HashMap<String, Object> map = new HashMap<>();
//                map.put("userId", fromId);
//                map.put("fromId", fromId);
//                map.put("toId", toId);
//                map.put("verificationStatus", verificationStatus);
//                map.put("verificationTime", currentTime);
//                map.put("option", jsonObject.get("option").toString());
//                map.put("type", jsonObject.get("type").toString());
//                map.put("houseId", houseId);
//                map.put("houseSellOrRent", houseSellOrRent);
//                User user = userMapper.getUserById(toId);
//                map.put("headPhoto", user.getUserPhoto());
//                map.put("nickname", user.getUserNickname());
//                Channel channel = loginUser.get(String.valueOf(fromId));
//                try {
//                    HashMap<String, Object> message = new HashMap<>();
//                    //fromId, fromId, toId, verificationStatus, currentTime
//                    message.put("fromId", fromId);
//                    message.put("toId", toId);
//                    message.put("houseId", houseId);
//                    message.put("verificationStatus", verificationStatus);
//                    message.put("userId", fromId);
//                    agentVerificationMapper.updateVerification(message);
//                    message.put("userId", toId);
//                    agentVerificationMapper.updateVerification(message);
//                    if ("accept".equals(jsonObject.get("option").toString())) {
//                        House house = houseMapper.getHouseById(houseId);
//                        if (fromId == house.getUserId()) {
//                            // 0.sell 1.rent
//                            if (houseSellOrRent == 0) {
//                                Sell sell = sellMapper.getSellByHouseId(houseId);
//                                sell.setAgentId(toId);
//                                sellMapper.updateSellById(sell);
//                            } else {
//                                Renting renting = rentingMapper.getRentingByHouseId(houseId);
//                                renting.setAgentId(toId);
//                                rentingMapper.updateRentingById(renting);
//                            }
//                        } else {
//                            // 0.sell 1.rent
//                            if (houseSellOrRent == 0) {
//                                Sell sell = sellMapper.getSellByHouseId(houseId);
//                                sell.setAgentId(fromId);
//                                sellMapper.updateSellById(sell);
//                            } else {
//                                Renting renting = rentingMapper.getRentingByHouseId(houseId);
//                                renting.setAgentId(fromId);
//                                rentingMapper.updateRentingById(renting);
//                            }
//                        }
//                    }
//                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(map)));
//                } catch (Exception e) {
//
//                }
//            } else {
//                HashMap<String, Object> map = new HashMap<>();
//                map.put("fromId", fromId);
//                map.put("verificationStatus", verificationStatus);
//                map.put("verificationTime", currentTime);
//                map.put("type", jsonObject.get("option").toString());
//                map.put("userId", toId);
//                map.put("toId", toId);
//                map.put("houseId", houseId);
//                map.put("headPhoto", userMapper.getUserById(fromId).getUserPhoto());
//                map.put("houseSellOrRent", houseSellOrRent);
//                User user = userMapper.getUserById(toId);
//                map.put("nickname", user.getUserNickname());
//                Channel channel = loginUser.get(toId);
//                try {
//                    //***************************
//                    House house = houseMapper.getHouseById(houseId);
//                    if (fromId == house.getUserId()) {
//                        // 0.sell 1.rent
//                        if (houseSellOrRent == 0) {
//                            Sell sell = sellMapper.getSellByHouseId(houseId);
//                            sell.setAgentId(toId);
//                            sellMapper.updateSellById(sell);
//                        } else {
//                            Renting renting = rentingMapper.getRentingByHouseId(houseId);
//                            renting.setAgentId(toId);
//                            rentingMapper.updateRentingById(renting);
//                        }
//                    } else {
//                        // 0.sell 1.rent
//                        if (houseSellOrRent == 0) {
//                            Sell sell = sellMapper.getSellByHouseId(houseId);
//                            sell.setAgentId(fromId);
//                            sellMapper.updateSellById(sell);
//                        } else {
//                            Renting renting = rentingMapper.getRentingByHouseId(houseId);
//                            renting.setAgentId(fromId);
//                            rentingMapper.updateRentingById(renting);
//                        }
//                    }
//                    //***************************
//                    AgentVerification agentVerification = new AgentVerification(userId, fromId, toId, 0, currentTime, houseId);
//                    agentVerificationMapper.addVerification(agentVerification);
//                    agentVerification.setUserId(toId);
//                    agentVerificationMapper.addVerification(agentVerification);
//                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(map)));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            return;
//        }

        if ("media".equals(jsonObject.get("type").toString())) {

            Integer source = Integer.valueOf(jsonObject.get("source").toString());
            Integer fromId = Integer.valueOf(jsonObject.get("fromId").toString());
            Integer toId = Integer.valueOf(jsonObject.get("toId").toString());
            List<Integer> list = getNewsIdList(source, fromId, toId);

            if ("voice".equals(jsonObject.get("newsType").toString())) {
                String voicePath = jsonObject.get("msg").toString();

                HashMap<String, Object> message = new HashMap<>();
                message.put("fromId", fromId);
                message.put("toId", toId);
                message.put("msg", voicePath);
                message.put("time", currentTime);
                message.put("newsType", jsonObject.get("newsType").toString());
                message.put("type", jsonObject.get("type").toString());
                message.put("playTime", jsonObject.get("playTime").toString());
                message.put("source", Integer.valueOf(jsonObject.get("source").toString()));
                list.forEach(userId -> {
                    if (userId != fromId) {
                        Channel channel = loginUser.get(userId.toString());
                        try {
                            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
                        } catch (Exception e) {

                        }
                    }
                });

                String newsType = jsonObject.get("newsType").toString();
                Integer playTime = Integer.valueOf(jsonObject.get("playTime").toString());
                saveNews(fromId, toId, voicePath, currentTime, newsType, playTime, list, source);
                return;
            }
            if ("img".equals(jsonObject.get("newsType").toString())) {
                String voicePath = jsonObject.get("msg").toString();

                HashMap<String, Object> message = new HashMap<>();
                message.put("fromId", fromId);
                message.put("toId", toId);
                message.put("msg", voicePath);
                message.put("time", currentTime);
                message.put("newsType", jsonObject.get("newsType").toString());
                message.put("type", jsonObject.get("type").toString());
                message.put("playTime", jsonObject.get("playTime").toString());
                message.put("source", Integer.valueOf(jsonObject.get("source").toString()));
                list.forEach(userId -> {
                    if (userId != fromId) {
                        Channel channel = loginUser.get(userId.toString());
                        try {
                            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
                        } catch (Exception e) {

                        }
                    }
                });

                String newsType = jsonObject.get("newsType").toString();
                Integer playTime = Integer.valueOf(jsonObject.get("playTime").toString());
                saveNews(fromId, toId, voicePath, currentTime, newsType, playTime, list, source);
                return;
            }
            return;
        }

        if ("verification".equals(jsonObject.get("type").toString())) {
            int userId = Integer.valueOf(jsonObject.get("userId").toString());
            int fromId = Integer.valueOf(jsonObject.get("fromId").toString());
            int toId = Integer.valueOf(jsonObject.get("toId").toString());
            int verificationStatus = Integer.valueOf(jsonObject.get("verificationStatus").toString());

            if ("accept".equals(jsonObject.get("option").toString()) ||
                "refuse".equals(jsonObject.get("option").toString())) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("userId", fromId);
                map.put("fromId", fromId);
                map.put("toId", toId);
                map.put("verificationStatus", verificationStatus);
                map.put("verificationTime", currentTime);
                map.put("type", jsonObject.get("option").toString());
                Integer verificationType = jsonObject.getInteger("verificationType");
                map.put("verificationType", verificationType);

                if (verificationType == 1) {
                    int groupId = jsonObject.getInteger("groupId");
                    map.put("groupId", groupId);
                    int memberId = -1;
                    if (isGroupMember(userId, groupId)) {
                        HashMap<String, Object> m = new HashMap<>();
                        m.put("verificationStatus", verificationStatus);
                        m.put("groupId", groupId);
                        m.put("fromId", fromId);
                        memberId = fromId;
                        groupVerificationMapper.updateVerificationOfApply(m);
                    } else {
                        HashMap<String, Object> m = new HashMap<>();
                        m.put("verificationStatus", verificationStatus);
                        m.put("groupId", groupId);
                        m.put("fromId", fromId);
                        m.put("toId", toId);
                        memberId = toId;
                        groupVerificationMapper.updateVerificationOfInvite(m);
                    }
                    if ("accept".equals(jsonObject.get("option").toString())) {
                        GroupMember groupMember = new GroupMember();
                        groupMember.setGroupId(groupId);
                        groupMember.setMemberId(memberId);
                        groupMember.setMemberName(sysUserMapper.selectUserById(Long.valueOf(memberId)).getNickName());
                        groupMemberMapper.addMember(groupMember);
                        Chat chat = new Chat();
                        chat.setChatType(1);
                        chat.setChatId(groupId);
                        chat.setUserId(memberId);
                        chatMapper.addChatFrame(chat);
                    }
                    Channel channel = loginUser.get(String.valueOf(fromId));
                    try {
                        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(map)));
                    } catch (Exception e) {

                    }
                    return;
                }

                Channel channel = loginUser.get(String.valueOf(fromId));
                try {
                    newsService.updateVerification(fromId, fromId, toId, verificationStatus, currentTime);
                    newsService.updateVerification(toId, fromId, toId, verificationStatus, currentTime);
                    if ("accept".equals(jsonObject.get("option").toString())) {
                        Friend friend = new Friend();
                        friend.setFriendId(fromId);
                        friend.setUserId(toId);
                        friend.setFriendBlacklist(0);
                        friend.setNickname(sysUserMapper.selectUserById((long) fromId).getNickName());
                        friendMapper.addFriend(friend);

                        friend.setUserId(fromId);
                        friend.setFriendId(toId);
                        friend.setNickname(sysUserMapper.selectUserById((long) toId).getNickName());
                        friendMapper.addFriend(friend);
                    }
                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(map)));
                } catch (Exception e) {

                }
            } else if ("add".equals(jsonObject.get("option").toString())){
                HashMap<String, Object> map = new HashMap<>();
                map.put("fromId", fromId);
                map.put("verificationStatus", verificationStatus);
                map.put("verificationTime", currentTime);
                map.put("type", jsonObject.get("option").toString());
                map.put("verificationType", jsonObject.getInteger("verificationType"));
                if (jsonObject.get("verificationType")!=null && "1".equals(jsonObject.get("verificationType").toString())) {
                    int groupId = Integer.valueOf(jsonObject.get("groupId").toString());
                    map.put("groupPhoto", groupChatMapper.selectOne(groupId).getGroupPhoto());
                    map.put("groupId", groupId);
                    map.put("groupName", jsonObject.getString("groupName"));
                    if ("invite".equals(jsonObject.get("source").toString())) {
                        map.put("headPhoto", sysUserMapper.selectUserById((long) fromId).getAvatar());
                        map.put("toId", toId);
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(toId);
                        addGroupVerification(list, groupId, fromId, currentTime, verificationStatus);
                        try {
                            Channel channel = loginUser.get(toId);
                            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(map)));
                        } catch (Exception e) {

                        }
                        return;
                    }
                    List<Integer> list = getManagers(groupId);
                    addGroupVerification(list, groupId, fromId, currentTime, verificationStatus);
                    list.forEach(managerId -> {
                        map.put("toId", managerId);
                        try {
                            Channel channel = loginUser.get(managerId);
                            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(map)));
                        } catch (Exception e) {

                        }
                    });
                    return;
                }
                map.put("userId", toId);
                map.put("toId", toId);
                map.put("headPhoto", sysUserMapper.selectUserById((long) fromId).getAvatar());
                map.put("nickname", jsonObject.getString("nickname"));
                Channel channel = loginUser.get(toId);
                try {
                    newsService.addVerification(userId, fromId, toId, verificationStatus, currentTime);
                    newsService.addVerification(toId, fromId, toId, verificationStatus, currentTime);
                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(map)));
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }

            return;
        }

        if (jsonObject.get("id") != null) {
            if (jsonObject.get("isFirst").getClass() == Integer.class && (Integer) jsonObject.get("isFirst") == 1) {
                if (loginUser.get(jsonObject.get("id").toString()) != null) {
                    loginUser.remove(jsonObject.get("id").toString());
                }
                loginUser.put(jsonObject.get("id").toString(), channelHandlerContext.channel());
            } else {
                Integer source = Integer.valueOf(jsonObject.get("source").toString());
                Integer fromId = Integer.valueOf(jsonObject.get("id").toString());
                Integer toId = Integer.valueOf(jsonObject.get("chatId").toString());
                List<Integer> list = getNewsIdList(source, fromId, toId);
                long l = System.currentTimeMillis();

                if (jsonObject.get("chatId") != null) {
                    HashMap<String, Object> message = new HashMap<>();
                    message.put("fromId", Integer.valueOf(jsonObject.get("id").toString()));
                    message.put("toId", Integer.valueOf(jsonObject.get("chatId").toString()));
                    message.put("msg", jsonObject.get("msg"));
                    message.put("time", l);
                    message.put("newsType", jsonObject.get("newsType").toString());
                    message.put("type", "news");
                    message.put("source", Integer.valueOf(jsonObject.get("source").toString()));
                    list.forEach(userId -> {
                        if (userId != fromId) {
                            Channel channel = loginUser.get(String.valueOf(userId));
                            try {
                                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
                            } catch (Exception e) {

                            }
                        }
                    });
                }

                String msg = jsonObject.get("msg").toString();
                String newsType = jsonObject.get("newsType").toString();
                saveNews(fromId, toId, msg, l, newsType, 0, list, source);

            }
        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client connect...");
        System.out.println(ctx.channel().remoteAddress());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client close...");
        Channel channel = ctx.channel();

//        System.out.println(loginUser);

        Set<Map.Entry<String, Channel>> entries = loginUser.entrySet();
        Iterator<Map.Entry<String, Channel>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Channel> next = iterator.next();
            if (next.getValue() == channel) iterator.remove();
        }

//        System.out.println(loginUser);

    }

    public void saveNews(int fromId, int toId, String msg, long l, String newsType, int playTime, List<Integer> list, int source) {
        News news = new News();
        news.setNewsContent(msg);
        news.setNewsRecipienterid(toId);
        news.setNewsSenderid(fromId);
        news.setNewsTime(l);
        news.setNewsType(newsType);
        news.setPlayTime(playTime);
        news.setSource(source);

        list.forEach(newsId -> {
            news.setNewsId(newsId);
            newsMapper.addNews(news);
        });
    }

    public List<Integer> getNewsIdList(int source, int fromId, int toId) {
        ArrayList<Integer> list = new ArrayList<>();
        if (source == 0) {
            list.add(fromId);
            list.add(toId);
        } else {
            Integer groupId = Integer.valueOf(toId);
            List<SysUser> users = groupMemberMapper.selectAllMember(groupId);
            users.forEach(user -> {
                list.add(user.getUserId().intValue());
            });
        }
        return list;
    }

    public List<Integer> getManagers(int groupId) {
        ArrayList<Integer> list = new ArrayList<>();

        list.add(groupChatMapper.selectOne(groupId).getOwnerId());

        return list;
    }

    public void addGroupVerification(List<Integer> list, int groupId, int fromId, long time, int verificationStatus) {
        GroupVerification groupVerification = new GroupVerification();
        groupVerification.setGroupId(groupId);
        groupVerification.setFromId(fromId);
        groupVerification.setVerificationTime(time);
        groupVerification.setVerificationStatus(verificationStatus);
        list.forEach(id -> {
            groupVerification.setToId(id);
            groupVerification.setUserId(id);
            groupVerificationMapper.addVerification(groupVerification);
            groupVerification.setUserId(fromId);
            groupVerificationMapper.addVerification(groupVerification);
        });
    }

    public boolean isGroupMember(int userId, int groupId) {
        boolean res = false;
        List<SysUser> users = groupMemberMapper.selectAllMember(groupId);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == userId) {
                res = true;
                break;
            }
        }
        return res;
    }
}