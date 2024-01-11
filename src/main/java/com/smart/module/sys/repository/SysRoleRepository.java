package com.smart.module.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import  org.springframework.stereotype.Repository;
import com.smart.module.sys.entity.SysRole;

/**
 * sys_role Repository
 * Created by 小柒2012
 * Sun Oct 27 13:02:41 CST 2019
*/ 
@Repository 
public interface SysRoleRepository extends JpaRepository<SysRole, Long> {
}

