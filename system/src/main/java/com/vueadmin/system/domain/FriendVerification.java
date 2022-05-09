package com.vueadmin.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/8 13:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendVerification {

    private int userId;

    private int fromId;

    private int toId;

    private int verificationStatus;

    private long verificationTime;

}
