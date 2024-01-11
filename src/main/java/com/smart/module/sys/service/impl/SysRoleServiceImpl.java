package com.smart.module.sys.service.impl;

import com.smart.common.dynamicquery.DynamicQuery;
import com.smart.common.model.PageBean;
import com.smart.common.model.Result;
import com.smart.common.util.DateUtils;
import com.smart.common.util.ShiroUtils;
import com.smart.module.sys.entity.SysRole;
import com.smart.module.sys.entity.SysRoleMenu;
import com.smart.module.sys.entity.SysRoleOrg;
import com.smart.module.sys.repository.SysRoleRepository;
import com.smart.module.sys.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private DynamicQuery dynamicQuery;
    @Resource
    private SysRoleRepository sysRoleRepository;

    @Override
    public Result list(SysRole role) {
        String nativeSql = "SELECT COUNT(*) FROM sys_role ";
        nativeSql += common(role);
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<SysRole> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM sys_role ";
            nativeSql += common(role);
            nativeSql += "ORDER BY gmt_create desc ";
            Pageable pageable = PageRequest.of(role.getPageNo(),role.getPageSize());
            List<SysRole> list =  dynamicQuery.nativeQueryPagingList(SysRole.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }

    public String common(SysRole role){
        String description = role.getDescription();
        String commonSql = "";
        if(StringUtils.isNotBlank(description)){
            commonSql += "WHERE role_name like '"+description+"%' ";
            commonSql += "OR role_sign like '"+description+"%' ";
        }
        return commonSql;
    }

    @Override
    public Result select() {
        String nativeSql = "SELECT * FROM sys_role";
        List<SysRole> list = dynamicQuery.query(SysRole.class,nativeSql,new Object[]{});
        return Result.ok(list);
    }

    @Override
    @Transactional
    public Result save(SysRole role) {
        String nativeSql = "SELECT * FROM sys_role WHERE role_sign=?";
        SysRole sysRole =
                dynamicQuery.nativeQuerySingleResult(SysRole.class,nativeSql,new Object[]{role.getRoleSign()});
        if(sysRole!=null){
            if(!sysRole.getRoleSign().equals(role.getRoleSign())){
                return Result.error("角色代码重复");
            }
        }else{
            role.setGmtCreate(DateUtils.getTimestamp());
        }
        role.setGmtModified(DateUtils.getTimestamp());
        role.setOrgId((long)-1);
        role.setUserIdCreate(ShiroUtils.getUserId());
        sysRoleRepository.saveAndFlush(role);
        return Result.ok("保存成功");
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result delete(Long roleId) {
        String nativeSql = "DELETE FROM sys_role_menu WHERE role_id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{roleId});
        nativeSql = "DELETE FROM sys_role WHERE role_id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{roleId});
        nativeSql = "DELETE FROM sys_user_role WHERE role_id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{roleId});
        return Result.ok("删除成功");
    }

    @Override
    public Result getMenu(Long roleId) {
        String nativeSql = "SELECT menu_id FROM sys_role_menu WHERE role_id=?";
        List<String> list = dynamicQuery.query(nativeSql,new Object[]{roleId});
        return Result.ok(list);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result saveMenu(SysRole role) {
        Long roleId = role.getRoleId();
        String nativeSql = "DELETE FROM sys_role_menu WHERE role_id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{roleId});
        List<Long> menuIdList = role.getMenuIdList();
        if(menuIdList!=null){
            menuIdList.forEach(menuId->{
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setMenuId(menuId);
                roleMenu.setRoleId(roleId);
                dynamicQuery.save(roleMenu);
            });
        }
        return Result.ok("保存成功");
    }

    @Override
    public Result getOrg(Long roleId) {
        String nativeSql = "SELECT org_id FROM sys_role_org WHERE role_id=?";
        List<String> list = dynamicQuery.query(nativeSql,new Object[]{roleId});
        return Result.ok(list);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result saveOrg(SysRole role) {
        Long roleId = role.getRoleId();
        String nativeSql = "DELETE FROM sys_role_org WHERE role_id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{roleId});
        List<Long> orgIdList = role.getOrgIdList();
        if(orgIdList!=null){
            orgIdList.forEach(orgId->{
                SysRoleOrg roleOrg = new SysRoleOrg();
                roleOrg.setOrgId(orgId);
                roleOrg.setRoleId(roleId);
                dynamicQuery.save(roleOrg);
            });
        }
        return Result.ok("保存成功");
    }
}
