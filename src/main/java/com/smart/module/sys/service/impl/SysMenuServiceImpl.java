package com.smart.module.sys.service.impl;

import com.smart.common.constant.SystemConstant;
import com.smart.common.dynamicquery.DynamicQuery;
import com.smart.common.model.Result;
import com.smart.module.sys.entity.SysMenu;
import com.smart.module.sys.service.SysMenuService;
import com.smart.module.sys.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private DynamicQuery dynamicQuery;
    @Resource
    private SysUserService sysUserService;

    @Override
    public Result list(SysMenu menu) {
        String nativeSql = "SELECT * FROM sys_menu WHERE parent_id=?";
        String description = menu.getDescription();
        if(StringUtils.isNotBlank(description)){
            nativeSql +=" AND name like '"+description+"%'";
        }
        nativeSql += " ORDER BY order_num";
        List<SysMenu> menuList
                = dynamicQuery.query(SysMenu.class,nativeSql,new Object[]{menu.getParentId()});
        return Result.ok(menuList);
    }

    @Override
    public List<SysMenu> select(Long parentId) {
        String nativeSql = "SELECT * FROM sys_menu ";
        if(parentId!=null){
            nativeSql += " WHERE parent_id="+parentId;
        }
        nativeSql += " ORDER BY order_num";
        return dynamicQuery.query(SysMenu.class,nativeSql);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result delete(Long menuId) {
        String nativeSql = "SELECT * FROM sys_menu WHERE menu_id = ?";
        SysMenu menu =
                dynamicQuery.nativeQuerySingleResult(SysMenu.class,nativeSql,new Object[]{menuId});
        if(menu.getType().equals(SystemConstant.MenuType.CATALOG.getValue())){
            nativeSql = "SELECT COUNT(*) FROM sys_menu WHERE parent_id=?";
            Long count = dynamicQuery.nativeQueryCount(nativeSql,new Object[]{menuId});
            if(count>0){
                return Result.error("请先删除下级目录、菜单或者按钮");
            }
        }else if(menu.getType().equals(SystemConstant.MenuType.MENU.getValue())){
            /**
             * 删除下级按钮
             */
            nativeSql = "DELETE FROM sys_menu WHERE parent_id = ?";
            dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{menuId});
        }
        /**
         * 删除自身
         */
        nativeSql = "DELETE FROM sys_menu WHERE menu_id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{menuId});
        return Result.ok("删除成功");
    }

    @Override
    public List<SysMenu> getByUserId(Long userId) {
        String nativeSql = "SELECT DISTINCT m.* FROM sys_menu m ";
        nativeSql +="LEFT JOIN sys_role_menu rm ON m.menu_id = rm.menu_id ";
        nativeSql +="LEFT JOIN sys_role r ON r.role_id = rm.role_id ";
        nativeSql +="WHERE r.role_id IN (SELECT role_id FROM sys_user_role WHERE user_id=?) ";
        nativeSql +="AND m.TYPE = 0 ORDER BY m.order_num";
        List<SysMenu> list = dynamicQuery.query(SysMenu.class,nativeSql,new Object[]{userId});
        list.stream().forEach(menu->{
            String subSql = "SELECT DISTINCT m.* FROM sys_menu m ";
            subSql +="LEFT JOIN sys_role_menu rm ON m.menu_id = rm.menu_id ";
            subSql +="LEFT JOIN sys_role r ON r.role_id = rm.role_id ";
            subSql +="WHERE r.role_id IN (SELECT role_id FROM sys_user_role WHERE user_id=?) ";
            subSql +="AND m.parent_id = ? ORDER BY m.order_num";
            List<SysMenu> subList
                    = dynamicQuery.query(SysMenu.class,subSql,new Object[]{userId,menu.getMenuId()});
            menu.setList(subList);
        });
        return list;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result drop(Long parentId, Long menuId) {
        String nativeSql = "UPDATE sys_menu SET parent_id=? WHERE menu_id=?";
        int count = dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{parentId,menuId});
        if(count==0){
            return Result.error();
        }else{
            return Result.ok("更新成功");
        }
    }
}
