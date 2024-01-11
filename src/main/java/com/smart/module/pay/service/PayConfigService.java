package com.smart.module.pay.service;

import com.smart.common.model.Result;
import com.smart.module.pay.entity.AppPayConfig;

/**
 * 支付配置
 * @author 小柒2012
 */
public interface PayConfigService {

    /**
     * 保存
     * @param entity
     * @return
     */
    Result save(AppPayConfig entity);

}
