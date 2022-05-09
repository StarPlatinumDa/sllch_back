package com.vueadmin.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 
 * @Author caiks
 * @Date 2022/5/8 13:44
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Friend {
    private int userId;
    private int friendId;
    private int friendBlacklist;
    private String nickname;
}
