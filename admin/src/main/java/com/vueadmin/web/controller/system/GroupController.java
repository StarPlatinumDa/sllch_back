package com.vueadmin.web.controller.system;

import com.vueadmin.common.core.domain.AjaxResult;
import com.vueadmin.system.service.impl.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/8 19:48
 */
@RestController
@RequestMapping("/chat")
public class GroupController {

    @Autowired
    private GroupService groupService;

    // 创建群聊
    @PostMapping("/addGroup")
    public AjaxResult addGroup(int ownerId, String groupName) {
        return AjaxResult.success(groupService.addGroup(ownerId, groupName));
    }

    // 解散群聊
    @PostMapping("/delGroup")
    public AjaxResult delGroup(int groupId) {
        return AjaxResult.success(groupService.delGroup(groupId));
    }

    // 修改群名
    @PostMapping("/updateGroupName")
    public AjaxResult updateGroupName(int groupId, String groupName) {
        return AjaxResult.success(groupService.updateGroupName(groupId, groupName));
    }

    // 添加成员
    @PostMapping("/addMember")
    public AjaxResult addMember(int groupId, int memberId, String memberName){
        return AjaxResult.success(groupService.addMember(groupId, memberId, memberName));
    }

    // 删除成员
    @PostMapping("/delMember")
    public AjaxResult delMember(int groupId, int memberId) {
        return AjaxResult.success(groupService.delMember(groupId, memberId));
    }

    // 查找所有群成员
    @PostMapping("/selectAllMember")
    public AjaxResult selectAllMember(int groupId) {
        return AjaxResult.success(groupService.selectAllMember(groupId));
    }

    // 修改成员昵称
    @PostMapping("/updateMemberName")
    public AjaxResult updateMemberName(int groupId, int memberId, String memberName) {
        return AjaxResult.success(groupService.updateMemberName(groupId, memberId, memberName));
    }

    // 查找用户加入的群聊
    @PostMapping("/selectAllGroup")
    public AjaxResult selectAllGroup(int userId) {
        return AjaxResult.success(groupService.selectAllGroup(userId));
    }

    // 修改群头像
    @PostMapping("/updateGroupPhoto")
    public AjaxResult updateGroupPhoto(int groupId, String groupPhoto) {
        return AjaxResult.success(groupService.updateGroupPhoto(groupId, groupPhoto));
    }

    // 通过群号或群名称查找群
    @PostMapping("/selectGroupByIdOrName")
    public AjaxResult selectGroupByIdOrName(String content) {
        return AjaxResult.success(groupService.selectGroupByIdOrName(content));
    }

    // 查询群验证消息
    @PostMapping("/selectGroupVerifications")
    public AjaxResult selectGroupVerifications(int userId, int groupId, int fromId) {
        return AjaxResult.success(groupService.selectGroupVerifications(userId, groupId, fromId));
    }

    // 更新验证消息（申请进群）
    @PostMapping("/updateVerificationOfApply")
    public AjaxResult updateVerificationOfApply(int fromId, int groupId, int verificationStatus) {
        return AjaxResult.success(groupService.updateVerificationOfApply(fromId, groupId, verificationStatus));
    }

    // 更新验证消息（管理员邀请进群）
    @PostMapping("/updateVerificationOfInvite")
    public AjaxResult updateVerificationOfInvite(int fromId, int toId, int groupId, int verificationStatus) {
        return AjaxResult.success(groupService.updateVerificationOfInvite(fromId, toId, groupId, verificationStatus));
    }

}
