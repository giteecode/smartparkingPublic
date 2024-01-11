package com.smart.module.sys.web;

import com.wf.captcha.utils.CaptchaUtil;
import com.smart.common.model.Result;
import com.smart.common.util.DateUtils;
import com.smart.common.util.IPUtils;
import com.smart.common.util.MD5Utils;
import com.smart.common.util.ShiroUtils;
import com.smart.module.sys.entity.SysLog;
import com.smart.module.sys.entity.SysUser;
import com.smart.module.sys.service.SysLogService;
import com.smart.module.sys.service.SysUserService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录

 */
@Controller
@RequestMapping("/sys")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysLogService sysLogService;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Result login(String username, String password,String verCode,
                        boolean rememberMe,HttpServletRequest request){
        try{
            if (CaptchaUtil.ver(verCode, request)) {
                Subject subject = ShiroUtils.getSubject();
                password = MD5Utils.encrypt(username, password);
                UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);
                subject.login(token);
                saveLog();
            }else{
                CaptchaUtil.clear(request);
                return Result.error(400,"验证码错误");
            }
        }catch (UnknownAccountException e) {
            return Result.error("账户不存在");
        }catch (IncorrectCredentialsException e) {
            return Result.error("密码不正确");
        }
        return Result.ok("登录成功");
    }

    @Async
    public void saveLog(){
        SysLog log = new SysLog();
        SysUser user = ShiroUtils.getUserEntity();
        log.setUserId(user.getUserId());
        log.setUsername(user.getNickname());
        log.setGmtCreate(DateUtils.getTimestamp());
        log.setOperation("登录");
        log.setMethod("");
        log.setParams("");
        log.setExceptionDetail("");
        log.setTime(10);
        log.setIp(IPUtils.getIpAddr());
        log.setDeviceType((short)0);
        log.setLogType((short)0);
        sysLogService.save(log);
    }

    /**
     * 退出
     * @return
     */
    @GetMapping("/logout")
    public String logout(){
        ShiroUtils.logout();
        return "redirect:/login.html";
    }

    /**
     * 验证码生成
     * @param request 请求报文
     * @param response 响应报文
     * */
    @RequestMapping("captcha")
    public void generate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }
}
