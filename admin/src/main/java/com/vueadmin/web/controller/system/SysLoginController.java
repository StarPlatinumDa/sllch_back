package com.vueadmin.web.controller.system;

import java.util.List;
import java.util.Set;

import com.vueadmin.system.mapper.SysUserMapper;
import com.vueadmin.system.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.vueadmin.common.constant.Constants;
import com.vueadmin.common.core.domain.AjaxResult;
import com.vueadmin.common.core.domain.entity.SysMenu;
import com.vueadmin.common.core.domain.entity.SysUser;
import com.vueadmin.common.core.domain.model.LoginBody;
import com.vueadmin.common.utils.SecurityUtils;
import com.vueadmin.framework.web.service.SysLoginService;
import com.vueadmin.framework.web.service.SysPermissionService;
import com.vueadmin.system.service.ISysMenuService;

/**
 * 登录验证
 * 
 * @author vueadmin
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        if (sysUserRoleMapper.getroleTypeById(sysUserMapper.selectUserByUserName(loginBody.getUsername()).getUserId())!=null){
            if((!sysUserRoleMapper.getroleTypeById(sysUserMapper.selectUserByUserName(loginBody.getUsername()).getUserId()).equals("admin"))&&(!sysUserRoleMapper.getroleTypeById(sysUserMapper.selectUserByUserName(loginBody.getUsername()).getUserId()).equals("spadmin"))){
                AjaxResult ajax = AjaxResult.error("无登录权限，请联系管理员！");
                return ajax;
            }
            AjaxResult ajax = AjaxResult.success();
            // 生成令牌
            String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                    loginBody.getUuid());
            ajax.put(Constants.TOKEN, token);
            return ajax;
        }
        else {
            AjaxResult ajax = AjaxResult.error("无登录权限，请联系管理员！");
            return ajax;
        }
    }
    @PostMapping("/mobilelogin")
    public AjaxResult mobileLogin(String userName,String passWord)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.mobileLogin(userName, passWord, null,
                null);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
