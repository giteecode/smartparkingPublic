package com.smart.module.pay.web;

import com.smart.common.model.Result;
import com.smart.module.pay.entity.AppPayConfig;
import com.smart.module.pay.repository.PayConfigRepository;
import com.smart.module.pay.service.PayConfigService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付配置

 */
@RestController
@RequestMapping("/pay/config")
public class PayConfigController {

    @Autowired
    private PayConfigService payConfigService;
    @Autowired
    private PayConfigRepository payConfigRepository;

    /**
     * 获取
     */
    @PostMapping("getByCarParkId")
    //@RequiresRoles(value={"admin","orgAdmin"},logical = Logical.OR)
    public Result getByCarParkId(Long carParkId){
        AppPayConfig entity = payConfigRepository.findByCarParkId(carParkId);
        return Result.ok(entity);
    }

    /**
     * 保存
     */
    @PostMapping("save")
    //@RequiresRoles(value={"admin","orgAdmin"},logical = Logical.OR)
    public Result save(@RequestBody AppPayConfig entity){
        return payConfigService.save(entity);
    }

    /**
     * 删除
     */
    @PostMapping("delete")
    //@RequiresRoles(value={"admin","orgAdmin"},logical = Logical.OR)
    public Result delete(Long id){
        payConfigRepository.deleteById(id);
        return Result.ok();
    }
}
