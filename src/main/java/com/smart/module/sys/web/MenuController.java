package com.smart.module.sys.web;

import com.smart.common.model.Result;
import com.smart.common.util.ShiroUtils;
import com.smart.module.sys.entity.SysMenu;
import com.smart.module.sys.repository.SysMenuRepository;
import com.smart.module.sys.service.SysMenuService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单管理

 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysMenuRepository sysMenuRepository;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(SysMenu menu){
        return sysMenuService.list(menu);
    }

    /**
     * 树结构
     */
    @RequestMapping("/select")
    @RequiresRoles("admin")
    public Result select(Long parentId){
        List<SysMenu> list = sysMenuService.select(parentId);
        return Result.ok(list);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresRoles("admin")
    public Result save(@RequestBody SysMenu menu){
        sysMenuRepository.saveAndFlush(menu);
        return Result.ok("保存成功");
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresRoles("admin")
    public Result delete(Long menuId){
        return sysMenuService.delete(menuId);
    }


    /**
     * 获取菜单
     */
    @RequestMapping("/getByUser")
    public List<SysMenu> getByUser(){
        return sysMenuService.getByUserId(ShiroUtils.getUserId());
    }


    /**
     * 列表
     */
    @RequestMapping("/drop")
    @RequiresRoles("admin")
    public Result drop(Long parentId,Long menuId){
        return sysMenuService.drop(parentId,menuId);
    }
}
