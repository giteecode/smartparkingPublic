package com.smart.module.sys.service.impl;

import com.smart.common.constant.SystemConstant;
import com.smart.common.dynamicquery.DynamicQuery;
import com.smart.common.model.PageBean;
import com.smart.common.model.Result;
import com.smart.common.util.DateUtils;
import com.smart.common.util.MD5Utils;
import com.smart.common.util.ShiroUtils;
import com.smart.module.sys.entity.SysOrg;
import com.smart.module.sys.entity.SysUser;
import com.smart.module.sys.entity.SysUserRole;
import com.smart.module.sys.repository.SysUserRepository;
import com.smart.module.sys.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private DynamicQuery dynamicQuery;
    @Resource
    private SysUserRepository sysUserRepository;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result save(SysUser user) {
        String nativeSql = "SELECT * FROM sys_user WHERE username=?";
        SysUser sysUser =  dynamicQuery.nativeQuerySingleResult(
                SysUser.class,nativeSql,new Object[]{user.getUsername()});
        if(sysUser!=null){
            if(!sysUser.getUserId().equals(user.getUserId())){
                return Result.error("用户名重复");
            }
        }else{
            user.setGmtCreate(DateUtils.getTimestamp());
            user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
            user.setAvatarStatus(SystemConstant.AVATAR_STATUS_NO);
        }
        user.setGmtModified(DateUtils.getTimestamp());
        user.setUserIdCreate(ShiroUtils.getUserId());
        sysUserRepository.saveAndFlush(user);
        nativeSql = "DELETE FROM sys_user_role WHERE user_id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{user.getUserId()});
        List<Object> roleList = user.getRoleIdList();
        if(roleList!=null){
            roleList.forEach(roleId->{
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(Long.parseLong(roleId.toString()));
                dynamicQuery.save(userRole);
            });
        }
        return Result.ok("保存成功");
    }

    @Override
    public Result get(Long userId) {
        /**
         * 用户信息
         */
        String nativeSql = "SELECT * FROM sys_user WHERE user_id=?";
        SysUser user = dynamicQuery.nativeQuerySingleResult(
                SysUser.class,nativeSql,new Object[]{userId});
        /**
         * 机构信息
         */
        nativeSql = "SELECT * FROM sys_org WHERE org_id=?";
        SysOrg sysOrg = dynamicQuery.nativeQuerySingleResult(
                SysOrg.class,nativeSql,new Object[]{user.getOrgId()});
        if(sysOrg!=null){
            user.setOrgName(sysOrg.getName());
        }
        /**
         * 角色信息
         */
        nativeSql = "SELECT role_id FROM sys_user_role WHERE user_id=?";
        List<Object> roleIdList = dynamicQuery.query(nativeSql,new Object[]{userId});
        user.setRoleIdList(roleIdList);
        nativeSql = "SELECT role_name FROM sys_role WHERE role_id IN (SELECT role_id FROM sys_user_role WHERE user_id=?)";
        List<Object> roleNameList = dynamicQuery.query(nativeSql,new Object[]{userId});
        user.setRoleNameList(roleNameList);
        return Result.ok(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long userId) {
        String nativeSql = "DELETE FROM sys_user_role WHERE user_id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{userId});
        nativeSql = "DELETE FROM sys_user WHERE user_id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{userId});
        return Result.ok("删除成功");
    }

    @Override
    public SysUser getUser(String username) {
        String nativeSql = "SELECT * FROM sys_user u WHERE username = ?";
        return dynamicQuery.nativeQuerySingleResult(SysUser.class,nativeSql,new Object[]{username});
    }

    @Override
    public Result list(SysUser user) {
        String nativeSql = "SELECT COUNT(*) FROM sys_user ";
        nativeSql += common(user);
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<SysUser> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM sys_user ";
            nativeSql += common(user);
            nativeSql += "ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(user.getPageNo(),user.getPageSize());
            List<SysUser> list =  dynamicQuery.nativeQueryPagingList(SysUser.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }

    public String common(SysUser user){
        String description = user.getDescription();
        String commonSql = "";
        if(StringUtils.isNotBlank(description)){
            commonSql += "WHERE username like '"+description+"%' ";
            commonSql += "OR nickname like '"+description+"%' ";
        }
        return commonSql;
    }

    @Override
    public List<String> listUserRoles(Long userId) {
        String nativeSql = "SELECT r.role_sign FROM sys_user u ";
        nativeSql +=" LEFT JOIN sys_user_role ur ON u.user_id = ur.user_id";
        nativeSql +=" LEFT JOIN sys_role r ON r.role_id = ur.role_id";
        nativeSql +=" WHERE u.user_id = ?";
        List<String> list = dynamicQuery.query(nativeSql,new Object[]{userId});
        return list;
    }

    @Override
    public List<String> listUserPerms(Long userId) {
        String nativeSql = "SELECT DISTINCT m.perms FROM sys_user_role ur";
        nativeSql +=" LEFT JOIN sys_role_menu rm ON ur.role_id = rm.role_id";
        nativeSql +=" LEFT JOIN sys_menu m ON rm.menu_id = m.menu_id";
        nativeSql +=" WHERE ur.user_id = ?";
        List<String> list = dynamicQuery.query(nativeSql,new Object[]{userId});
        return list;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result updatePwd(SysUser user) {
        String password = MD5Utils.encrypt(user.getUsername(),user.getPassword());
        String nativeSql = "UPDATE sys_user  SET password=? WHERE user_id=?";
        int count = dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{password,user.getUserId()});
        if(count==1){
            return Result.ok("修改成功");
        }else{
            return Result.ok("修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result update(SysUser user) {
        String nativeSql = "UPDATE sys_user  SET nickname=?,email=?,mobile=? WHERE user_id=?";
        Object[] params = new Object[]{user.getNickname(),user.getEmail(),user.getMobile(),user.getUserId()};
        int count = dynamicQuery.nativeExecuteUpdate(nativeSql,params);
        if(count==1){
            return Result.ok("更新成功");
        }else{
            return Result.ok("更新成功");
        }
    }

    @Override
    public List<SysUser> listUserByRole(String roleSign) {
        return null;
    }
}
