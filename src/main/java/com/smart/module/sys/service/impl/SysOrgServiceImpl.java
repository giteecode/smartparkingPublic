package com.smart.module.sys.service.impl;

import com.smart.common.dynamicquery.DynamicQuery;
import com.smart.common.model.PageBean;
import com.smart.common.model.Result;
import com.smart.common.util.DateUtils;
import com.smart.module.sys.entity.SysOrg;
import com.smart.module.sys.repository.SysOrgRepository;
import com.smart.module.sys.service.SysOrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysOrgServiceImpl implements SysOrgService {

    @Resource
    private DynamicQuery dynamicQuery;
    @Resource
    private SysOrgRepository sysOrgRepository;

    @Override
    public Result list(SysOrg org) {
        String nativeSql = "SELECT COUNT(*) FROM sys_org WHERE parent_id=? ";
        String description = org.getDescription();
        if(StringUtils.isNotBlank(description)){
            nativeSql +="AND name like '"+description+"%'";
        }
        Long count = dynamicQuery.nativeQueryCount(nativeSql,new Object[]{org.getParentId()});
        PageBean<SysOrg> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM sys_org WHERE parent_id=? ";
            if(StringUtils.isNotBlank(description)){
                nativeSql +="AND name like '"+description+"%' ";
            }
            nativeSql +="ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(org.getPageNo(),org.getPageSize());
            List<SysOrg> list =
                    dynamicQuery.nativeQueryPagingList(SysOrg.class,pageable,nativeSql,new Object[]{org.getParentId()});
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }

    @Override
    public Result select(Long parentId) {
        String nativeSql = "SELECT * FROM sys_org";
        if(parentId!=null){
            nativeSql += " WHERE parent_id="+parentId;
        }
        nativeSql += " ORDER BY order_num desc";
        List<SysOrg> list = dynamicQuery.query(SysOrg.class,nativeSql);
        return Result.ok(list);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result save(SysOrg org) {
        String nativeSql = "SELECT * FROM sys_org WHERE code=?";
        SysOrg sysOrg =  dynamicQuery.nativeQuerySingleResult(
                SysOrg.class,nativeSql,new Object[]{org.getCode()});
        if(sysOrg==null){
            org.setGmtCreate(DateUtils.getTimestamp());
        }else{
            if(!sysOrg.getOrgId().equals(org.getOrgId())){
                return Result.error("机构代码重复");
            }
        }
        org.setGmtModified(DateUtils.getTimestamp());
        if(org.getParentId()==null){
            org.setParentId(0L);
        }
        sysOrgRepository.saveAndFlush(org);
        return Result.ok("保存成功");
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result delete(Long orgId) {
        String nativeSql = "SELECT COUNT(*) FROM sys_org WHERE parent_id=?";
        Long count = dynamicQuery.nativeQueryCount(nativeSql,new Object[]{orgId});
        if(count>0){
            return Result.error("请先删除下级机构");
        }else{
            nativeSql = "DELETE FROM sys_org WHERE org_id=?";
            dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{orgId});
        }
        return Result.ok("删除成功");
    }
}
