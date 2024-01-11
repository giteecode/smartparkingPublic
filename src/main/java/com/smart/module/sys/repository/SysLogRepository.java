package com.smart.module.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import  org.springframework.stereotype.Repository;
import com.smart.module.sys.entity.SysLog;

/**
 * sys_log Repository
 * Created by 小柒2012
 * Sun Oct 27 12:59:18 CST 2019
*/ 
@Repository 
public interface SysLogRepository extends JpaRepository<SysLog, Long> {
}

