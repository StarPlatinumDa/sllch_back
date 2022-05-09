package com.vueadmin.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/8 13:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {

    private int groupId;

    private int memberId;

    private String memberName;

}
