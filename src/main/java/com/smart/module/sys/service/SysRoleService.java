package com.smart.module.sys.service;

import com.smart.common.model.Result;
import com.smart.module.sys.entity.SysRole;

/**
 * 角色管理
 * @author 小柒2012
 */
public interface SysRoleService {

    Result list(SysRole role);

    Result select();

    Result save(SysRole role);

    Result delete(Long roleId);

    Result getMenu(Long roleId);

    Result saveMenu(SysRole role);

    Result getOrg(Long roleId);

    Result saveOrg(SysRole role);

}
