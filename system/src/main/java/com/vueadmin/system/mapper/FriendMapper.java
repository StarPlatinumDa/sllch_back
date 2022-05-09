package com.vueadmin.system.mapper;

import com.vueadmin.system.domain.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/8 13:47
 */
public interface FriendMapper {
    public int addFriend(Friend friend);

    public int delFriend(Friend friend);

    public Friend selectOneFriend(Friend friend);

    public List<Friend> selectFriends(int userId);

    public int updateFriend(Map map);
}
