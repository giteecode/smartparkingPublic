package com.smart.module.sys.service;

import com.smart.common.model.Result;
import com.smart.module.sys.entity.SysConfig;

/**
 * 配置管理
 * @author 小柒2012
 */
public interface SysConfigService {

    /**
     * 保存
     * @param config
     * @return
     */
    Result save(SysConfig config);

    /**
     * 获取
     * @param id
     * @return
     */
    Result get(Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 列表
     * @param config
     * @return
     */
    Result list(SysConfig config);

    /**
     * 获取 value 值
     * @param key
     * @return
     */
    Object getByKey(String key);
}
