package com.smart.module.sys.repository;

import com.smart.module.sys.entity.SysInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * sys_interface Repository
 * Created by 小柒2012
 * Sun Oct 27 13:03:00 CST 2019
*/ 
@Repository 
public interface SysInterfaceRepository extends JpaRepository<SysInterface, Long> {

    SysInterface getByType(String type);

}

