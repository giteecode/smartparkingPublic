package com.smart.module.sys.service;

import com.smart.common.model.Result;
import com.smart.module.sys.entity.SysLog;

/**
 * 日志管理
 * @author 小柒2012
 */
public interface SysLogService {

    /**
     * 日志查询
     * @param log
     * @return
     */
    Result list(SysLog log);

    /**
     * 日志保存
     * @param log
     */
    void save(SysLog log);
}
