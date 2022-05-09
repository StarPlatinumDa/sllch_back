package com.vueadmin.system.mapper;

import com.vueadmin.system.domain.FriendVerification;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @Author caiks
 * @Date 2022/5/8 13:48
 */
public interface FriendVerificationMapper {
    public int addVerification(FriendVerification friendVerification);

    public int delVerification(int userId);

    public List<FriendVerification> selectAllByUserId(int userId);

//    map: {userId, fromId, toId, verificationStatus, verificationTime}
    public int updateVerification(Map map);

    public FriendVerification selectOne(FriendVerification friendVerification);
}
