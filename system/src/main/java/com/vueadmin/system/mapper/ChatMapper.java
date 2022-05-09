package com.vueadmin.system.mapper;

import com.vueadmin.system.domain.Chat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 
 * @Author caiks
 * @Date 2022/5/8 13:41
 */
public interface ChatMapper {
    public int addChatFrame(Chat chat);

    public int delChatFrame(Chat chat);

//    public int updateChatFrame(Map map);

    public Chat selectOneChatFrame(Chat chat);

    public List<Chat> selectChatFrames(int userId);

    public int delGroupChatFrames(int groupId);
}
