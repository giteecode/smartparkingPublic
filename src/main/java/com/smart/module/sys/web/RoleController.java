package com.smart.module.sys.web;

import com.smart.common.model.Result;
import com.smart.module.sys.entity.SysRole;
import com.smart.module.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理

 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 角色列表
     */
    @PostMapping("/list")
    public Result list(SysRole role){
        return sysRoleService.list(role);
    }

    /**
     * 角色选择
     */
    @PostMapping("/select")
    public Result select(){
        return sysRoleService.select();
    }

    /**
     * 角色选择
     */
    @PostMapping("/selectByUser")
    public Result selectByUser(){
        return sysRoleService.select();
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresRoles("admin")
    public Result save(@RequestBody SysRole role){
        return sysRoleService.save(role);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresRoles("admin")
    public Result delete(Long roleId){
        return sysRoleService.delete(roleId);
    }

    /**
     * 根据角色ID获取菜单
     */
    @PostMapping("/getMenu")
    public Result getMenu(Long roleId){
        return sysRoleService.getMenu(roleId);
    }

    /**
     * 根据角色保存菜单
     */
    @PostMapping("/saveMenu")
    @RequiresRoles("admin")
    public Result saveMenu(@RequestBody SysRole role){
        return sysRoleService.saveMenu(role);
    }

    /**
     * 根据角色ID获取机构
     */
    @PostMapping("/getOrg")
    public Result getOrg(Long roleId){
        return sysRoleService.getOrg(roleId);
    }

    /**
     * 根据角色保存机构
     */
    @PostMapping("/saveOrg")
    @RequiresRoles("admin")
    public Result saveOrg(@RequestBody SysRole role){
        return sysRoleService.saveOrg(role);
    }
}
