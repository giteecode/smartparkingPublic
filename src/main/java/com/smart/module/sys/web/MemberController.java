package com.smart.module.sys.web;

import com.smart.common.model.Result;
import com.smart.common.util.MD5Utils;
import com.smart.common.util.ShiroUtils;
import com.smart.module.sys.entity.SysUser;
import com.smart.module.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人设置

 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 修改密码
     */
    @PostMapping("/updatePwd")
    public Result updatePwd(@RequestBody SysUser user){
        SysUser entity = ShiroUtils.getUserEntity();
        String password = MD5Utils.encrypt(entity.getUsername(),user.getOldPassword());
        if(entity.getPassword().equals(password)){
            entity.setPassword(user.getPassword());
            return sysUserService.updatePwd(entity);
        }else{
            return Result.error("原密码不正确");
        }
    }

    /**
     * 获取当前用户信息
     */
    @PostMapping("/info")
    public Result info(){
        return sysUserService.get(ShiroUtils.getUserId());
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody SysUser user){
        SysUser entity = ShiroUtils.getUserEntity();
        entity.setEmail(user.getEmail());
        entity.setMobile(user.getMobile());
        entity.setNickname(user.getNickname());
        return sysUserService.update(user);
    }
}
