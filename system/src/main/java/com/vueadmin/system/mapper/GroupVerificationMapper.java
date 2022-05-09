package com.vueadmin.system.mapper;

import com.vueadmin.system.domain.GroupVerification;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @Author caiks
 * @Date 2022/5/8 13:51
 */
public interface GroupVerificationMapper {

    public int addVerification(GroupVerification groupVerification);

    public int delVerification(int userId);

    public List<GroupVerification> selectAllByUserId(int userId);

    // map: {fromId, toId, verificationStatus, verificationTime, groupId}
    public int updateVerificationOfInvite(Map map);

    // map: {fromId, verificationStatus, verificationTime, groupId}
    public int updateVerificationOfApply(Map map);

    // userId, fromId, groupId
    public List<GroupVerification> selectGroupVerifications(GroupVerification groupVerification);

}
