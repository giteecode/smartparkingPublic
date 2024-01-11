package com.smart.module.sys.service.impl;

import com.smart.common.constant.SystemConstant;
import com.smart.common.dynamicquery.DynamicQuery;
import com.smart.common.model.PageBean;
import com.smart.common.model.Result;
import com.smart.common.util.DateUtils;
import com.smart.common.util.ShiroUtils;
import com.smart.module.sys.entity.SysConfig;
import com.smart.module.sys.entity.SysInterface;
import com.smart.module.sys.repository.SysInterfaceRepository;
import com.smart.module.sys.service.SysInterfaceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SysInterfaceServiceImpl implements SysInterfaceService {

    @Resource
    private DynamicQuery dynamicQuery;
    @Resource
    private SysInterfaceRepository sysInterfaceRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(SysInterface entity) {
        String nativeSql = "SELECT * FROM sys_interface WHERE type=?";
        SysInterface sysInterface =  dynamicQuery.nativeQuerySingleResult(
                SysInterface.class,nativeSql,new Object[]{entity.getType()});
        if(sysInterface!=null){
            if(!entity.getId().equals(sysInterface.getId())){
                return Result.error("配置类型重复");
            }
        }else{
            entity.setGmtCreate(DateUtils.getTimestamp());
        }
        entity.setUserIdCreate(ShiroUtils.getUserId());
        sysInterfaceRepository.saveAndFlush(entity);
        return Result.ok("保存成功");
    }

    @Override
    public Result get(Long id) {
        SysInterface entity = sysInterfaceRepository.getOne(id);
        return Result.ok(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {
        sysInterfaceRepository.deleteById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result list(SysInterface entity) {
        String nativeSql = "SELECT COUNT(*) FROM sys_interface ";
        nativeSql += common(entity);
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<SysInterface> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM sys_interface ";
            nativeSql += common(entity);
            nativeSql += "ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(entity.getPageNo(),entity.getPageSize());
            List<SysInterface> list =  dynamicQuery.nativeQueryPagingList(SysInterface.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }

    @Override
    @Transactional(readOnly = true)
    public Result query(SysInterface entity) {
        SysInterface sysInterface = sysInterfaceRepository.getByType(entity.getType());
        if(sysInterface!=null){
            if(sysInterface.getStatus().shortValue() == SystemConstant.StatusType.ENABLE.getValue()){
                String query = sysInterface.getQuery();
                Object[] params = null;
                if(StringUtils.isNotBlank(entity.getParams())){
                    params = entity.getParams().split(";");
                }
                List<Map<String,Object>> list = dynamicQuery.nativeQueryListMap(query,params);
                return Result.ok(list);
            }else{
                return Result.error("接口已被禁用");
            }
        }else{
            return Result.error("接口类型不存在");
        }
    }

    public String common(SysInterface entity){
        String description = entity.getDescription();
        String commonSql = "";
        if(StringUtils.isNotBlank(description)){
            commonSql += "WHERE type like '"+description+"%' ";
            commonSql += "OR name like '"+description+"%' ";
        }
        return commonSql;
    }
}
