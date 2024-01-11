package com.smart.module.sys.repository;

import com.smart.module.sys.entity.SysLandingRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * sys_landing_records Repository
 * Created by 小柒2012
 * Sun Oct 27 12:59:38 CST 2019
*/ 
@Repository 
public interface SysLandingRecordsRepository extends JpaRepository<SysLandingRecords, Long> {
}

