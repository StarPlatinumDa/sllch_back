package com.vueadmin.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/8 13:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupVerification {

    private int userId;

    private int fromId;

    private int toId;

    private int verificationStatus;

    private long verificationTime;

    private int groupId;

}
