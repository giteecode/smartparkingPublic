package com.smart.module.sys.service;

import com.smart.common.model.Result;
import com.smart.module.sys.entity.SysInterface;

/**
 * 接口管理
 * @author 小柒2012
 */
public interface SysInterfaceService {

    /**
     * 保存
     * @param entity
     * @return
     */
    Result save(SysInterface entity);

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
     * @param entity
     * @return
     */
    Result list(SysInterface entity);

    /**
     * 删除
     * @param entity
     * @return
     */
    Result query(SysInterface entity);
}
