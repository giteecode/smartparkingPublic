package com.smart.module.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import  org.springframework.stereotype.Repository;
import com.smart.module.sys.entity.SysUserRole;

/**
 * sys_user_role Repository
 * Created by 小柒2012
 * Sun Oct 27 13:03:06 CST 2019
*/ 
@Repository 
public interface SysUserRoleRepository extends JpaRepository<SysUserRole, Long> {
}

