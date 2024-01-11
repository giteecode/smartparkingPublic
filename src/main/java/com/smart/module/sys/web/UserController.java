package com.smart.module.sys.web;

import com.smart.common.model.Result;
import com.smart.common.util.ShiroUtils;
import com.smart.module.sys.entity.SysUser;
import com.smart.module.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理

 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户列表
     */
    @PostMapping("/list")
    public Result list(SysUser user){
        return sysUserService.list(user);
    }

    /**
     * 获取
     */
    @PostMapping("/get")
    public Result get(Long userId){
        return sysUserService.get(userId);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresRoles("admin")
    public Result save(@RequestBody SysUser user){
        return sysUserService.save(user);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresRoles("admin")
    public Result delete(Long userId){
        return sysUserService.delete(userId);
    }

    /**
     * 修改密码
     */
    @PostMapping("/updatePwd")
    @RequiresRoles("admin")
    public Result updatePwd(SysUser user){
        return sysUserService.updatePwd(user);
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
    @RequiresRoles("admin")
    public Result update(@RequestBody SysUser user){
        return sysUserService.update(user);
    }

}
