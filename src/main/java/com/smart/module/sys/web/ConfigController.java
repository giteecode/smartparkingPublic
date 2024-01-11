package com.smart.module.sys.web;

import com.smart.common.model.Result;
import com.smart.module.sys.entity.SysConfig;
import com.smart.module.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参数设置

 */
@RestController
@RequestMapping("/sys/config")
public class ConfigController  {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 参数列表
     */
    @PostMapping("/list")
    public Result list(SysConfig config){
        return sysConfigService.list(config);
    }

    /**
     * 获取
     */
    @PostMapping("/get")
    public Result get(Long id){
        return sysConfigService.get(id);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresRoles("admin")
    public Result save(@RequestBody SysConfig config){
        return sysConfigService.save(config);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresRoles("admin")
    public Result delete(Long id){
        return sysConfigService.delete(id);
    }

}
