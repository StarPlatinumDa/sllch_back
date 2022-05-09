package com.vueadmin.web.controller.system;

import com.vueadmin.common.core.domain.AjaxResult;
import com.vueadmin.system.service.impl.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/8 17:12
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private NewsService newsService;

    @PostMapping("/getChatFrames")
    public AjaxResult getChatFrames(int userId) {
        return AjaxResult.success(newsService.getChatFrames(userId));
    }

    @PostMapping("/getAllNewsByUserId")
    public AjaxResult getAllNewsByUserId(int userId) {
        return AjaxResult.success(newsService.getAllNewsByUserId(userId));
    }

    @PostMapping("/updateBlackList")
    public AjaxResult updateBlackList(int userId, int chatId, int black) {
        return AjaxResult.success(newsService.updateBlackList(userId, chatId, black));
    }

    @PostMapping("/getFriends")
    public AjaxResult getFriends(int userId) {
        return AjaxResult.success(newsService.getFriends(userId));
    }

    @PostMapping("/delFriend")
    public AjaxResult delFriend(int userId, int friendId) {
        return AjaxResult.success(newsService.delFriend(userId, friendId));
    }

    @PostMapping("/delRecords")
    public AjaxResult delRecords(int userId, int chatId, int source) {
        return AjaxResult.success(newsService.delRecords(userId, chatId, source));
    }

    @PostMapping("/updateNickname")
    public AjaxResult updateNickname(int userId, int friendId, String nickname) {
        return AjaxResult.success(newsService.updateNickname(userId, friendId, nickname));
    }

    @PostMapping("/addChatFrame")
    public AjaxResult addChatFrame(int userId, int chatId, int chatType) {
        return AjaxResult.success(newsService.addChatFrame(userId, chatId, chatType));
    }

    @PostMapping("/delVerification")
    public AjaxResult delVerification(int userId) {
        return AjaxResult.success(newsService.delVerification(userId));
    }

    @PostMapping("/selectAllByUserId")
    public AjaxResult selectAllByUserId(int userId) {
        return AjaxResult.success(newsService.selectAllByUserId(userId));
    }

    @PostMapping("/delGroupVerification")
    public AjaxResult delGroupVerification(int userId) {
        return AjaxResult.success(newsService.delGroupVerification(userId));
    }

    @PostMapping("/selectAllGroupVerificationByUserId")
    public AjaxResult selectAllGroupVerificationByUserId(int userId) {
        return AjaxResult.success(newsService.selectAllGroupVerificationByUserId(userId));
    }

    @PostMapping("/selectOneFriend")
    public AjaxResult selectOneFriend(int userId, int friendId) {
        return AjaxResult.success(newsService.selectOneFriend(userId, friendId));
    }

    @PostMapping("/selectOneVerification")
    public AjaxResult selectOneVerification(int userId, int fromId, int toId, int verificationStatus) {
        return AjaxResult.success(newsService.selectOneVerification(userId, fromId, toId, verificationStatus));
    }

    @PostMapping("/addFriend")
    public AjaxResult addFriend(int userId, int friendId) {
        return AjaxResult.success(newsService.addFriend(userId, friendId));
    }

}
