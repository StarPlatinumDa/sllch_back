package com.vueadmin.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.vueadmin.common.core.controller.BaseController;
import com.vueadmin.common.core.domain.AjaxResult;
import com.vueadmin.common.core.domain.model.RegisterBody;
import com.vueadmin.common.utils.StringUtils;
import com.vueadmin.framework.web.service.SysRegisterService;

/**
 * 注册验证
 * 
 * @author vueadmin
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(false)))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
