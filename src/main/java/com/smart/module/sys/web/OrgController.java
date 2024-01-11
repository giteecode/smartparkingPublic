package com.smart.module.sys.web;

import com.smart.common.model.Result;
import com.smart.module.sys.entity.SysOrg;
import com.smart.module.sys.service.SysOrgService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构管理

 */
@RestController
@RequestMapping("/sys/org")
public class OrgController {

    @Autowired
    private SysOrgService sysOrgService;

    /**
     * 机构列表
     */
    @PostMapping("/list")
    public Result list(SysOrg sysOrg){
        return sysOrgService.list(sysOrg);
    }

    /**
     * 树结构
     */
    @RequestMapping("/select")
    public Result select(Long parentId){
        return sysOrgService.select(parentId);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresRoles("admin")
    public Result save(@RequestBody SysOrg org){
        return sysOrgService.save(org);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresRoles("admin")
    public Result delete(Long orgId){
        return sysOrgService.delete(orgId);
    }
}
