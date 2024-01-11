package com.smart.module.pay.service.impl;

import com.smart.common.model.Result;
import com.smart.common.util.DateUtils;
import com.smart.common.util.ShiroUtils;
import com.smart.module.pay.entity.AppPayConfig;
import com.smart.module.pay.repository.PayConfigRepository;
import com.smart.module.pay.service.PayConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class PayConfigServiceImpl implements PayConfigService {

    @Resource
    private PayConfigRepository payConfigRepository;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result save(AppPayConfig entity) {
        entity.setUserIdCreate(ShiroUtils.getUserId());
        if(entity.getId()==null){
            entity.setGmtCreate(DateUtils.getTimestamp());
            entity.setGmtModified(entity.getGmtCreate());
        }else{
            entity.setGmtModified(DateUtils.getTimestamp());
        }
        payConfigRepository.saveAndFlush(entity);
        return Result.ok("保存成功");
    }

}
