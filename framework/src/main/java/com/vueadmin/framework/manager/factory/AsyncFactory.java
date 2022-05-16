package com.vueadmin.framework.manager.factory;

import java.util.TimerTask;

import com.vueadmin.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.vueadmin.common.constant.Constants;
import com.vueadmin.common.utils.LogUtils;
import com.vueadmin.common.utils.ServletUtils;
import com.vueadmin.common.utils.StringUtils;
import com.vueadmin.common.utils.ip.AddressUtils;
import com.vueadmin.common.utils.ip.IpUtils;
import com.vueadmin.common.utils.spring.SpringUtils;
import com.vueadmin.system.domain.SysLogininfor;
import com.vueadmin.system.domain.SysOperLog;
import com.vueadmin.system.service.ISysLogininforService;
import com.vueadmin.system.service.ISysOperLogService;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

/**
 * 异步工厂（产生任务用）
 * 
 * @author vueadmin
 */
public class AsyncFactory
{
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    public static final ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL = ThreadLocal.withInitial(ServletUtils::getRequest);

    /**
     * 记录登录信息
     * 
     * @param username 用户名
     * @param status 状态
     * @param message 消息
     * @param args 列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message,
            final Object... args)
    {
        final HttpServletRequest request = REQUEST_THREAD_LOCAL.get();
        final String header = request.getHeader("User-Agent");
        final UserAgent userAgent = UserAgent.parseUserAgentString(header);
        final String ip = IpUtils.getIpAddr(request);
        REQUEST_THREAD_LOCAL.remove();
        return new TimerTask()
        {
            @Override
            public void run()
            {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // 打印信息到日志
                sys_user_logger.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                SysLogininfor logininfor = new SysLogininfor();
                logininfor.setUserName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                // 日志状态
                if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER))
                {
                    logininfor.setStatus(Constants.SUCCESS);
                }
                else if (Constants.LOGIN_FAIL.equals(status))
                {
                    logininfor.setStatus(Constants.FAIL);
                }
                logininfor.setLoginTime(DateUtils.getNowDate());
                // 插入数据
                SpringUtils.getBean(ISysLogininforService.class).insertLogininfor(logininfor);
            }
        };
    }

    /**
     * 操作日志记录
     * 
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final SysOperLog operLog)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                // 远程查询操作地点
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                operLog.setOperTime(DateUtils.getNowDate());
                SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
            }
        };
    }
}
