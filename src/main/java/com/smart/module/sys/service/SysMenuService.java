package com.smart.module.sys.service;

import com.smart.common.model.Result;
import com.smart.module.sys.entity.SysMenu;

import java.util.List;
/**
 * 菜单管理
 * @author 小柒2012
 */
public interface SysMenuService {

    Result list(SysMenu menu);

    List<SysMenu> select(Long parentId);

    Result delete(Long menuId);

    List<SysMenu> getByUserId(Long userId);

    Result drop(Long parentId, Long menuId);
}
