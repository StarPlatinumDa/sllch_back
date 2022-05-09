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
public class GroupChat {

    private int groupId;

    private int ownerId;

    private String groupName;

    private String groupPhoto;

}
