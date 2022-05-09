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
public class News {
    private int newsId;
    private String newsContent;
    private long newsTime;
    private int newsSenderid;
    private int newsRecipienterid;
    private String newsType;
    private int playTime;
    private int source;
}
