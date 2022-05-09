package com.vueadmin.system.mapper;

import com.vueadmin.system.domain.News;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @Author caiks
 * @Date 2022/5/8 13:37
 */
public interface NewsMapper {

    // 获取某用户的所有聊天记录
    public List<News> getNewsByUserId(int userId);

    // 获取某用户与另一用户的最新一条记录（map里为各自id:[userId, chatId, source]）
    public News getLastNews(Map map);

    // 获取群的最新一条记录（map里为各自id:[userId, chatId, source]）
    public News getGroupLastNews(Map map);

    // 添加消息
    public int addNews(News news);

    //删除消息(map->{userId, chatId})
    public int delNews(Map map);

    // 修改消息
    public int updateNews(News news);

}
