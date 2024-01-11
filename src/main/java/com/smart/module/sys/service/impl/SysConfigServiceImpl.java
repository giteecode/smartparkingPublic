package com.smart.module.sys.service.impl;

import com.smart.common.dynamicquery.DynamicQuery;
import com.smart.common.model.PageBean;
import com.smart.common.model.Result;
import com.smart.common.util.DateUtils;
import com.smart.common.util.ShiroUtils;
import com.smart.module.sys.entity.SysConfig;
import com.smart.module.sys.repository.SysConfigRepository;
import com.smart.module.sys.service.SysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Resource
    private DynamicQuery dynamicQuery;
    @Resource
    private SysConfigRepository sysConfigRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(SysConfig config) {
        String nativeSql = "SELECT * FROM sys_config WHERE config_key=?";
        SysConfig sysConfig =  dynamicQuery.nativeQuerySingleResult(
                SysConfig.class,nativeSql,new Object[]{config.getKey()});
        if(sysConfig!=null){
            if(!config.getId().equals(sysConfig.getId())){
                return Result.error("配置键重复");
            }
        }else{
            config.setGmtCreate(DateUtils.getTimestamp());
        }
        config.setUserIdCreate(ShiroUtils.getUserId());
        sysConfigRepository.saveAndFlush(config);
        return Result.ok("保存成功");
    }

    @Override
    public Result get(Long id) {
        SysConfig config = sysConfigRepository.getOne(id);
        return Result.ok(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {
        sysConfigRepository.deleteById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result list(SysConfig config) {
        String nativeSql = "SELECT COUNT(*) FROM sys_config ";
        nativeSql += common(config);
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<SysConfig> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM sys_config ";
            nativeSql += common(config);
            nativeSql += "ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(config.getPageNo(),config.getPageSize());
            List<SysConfig> list =  dynamicQuery.nativeQueryPagingList(SysConfig.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }

    @Override
    public Object getByKey(String key) {
        String nativeSql = "SELECT config_value FROM sys_config WHERE config_key=?";
        return dynamicQuery.querySingleResult(nativeSql,new Object[]{key});
    }

    public String common(SysConfig config){
        String description = config.getDescription();
        String commonSql = "";
        if(StringUtils.isNotBlank(description)){
            commonSql += "WHERE config_key like '"+description+"%' ";
            commonSql += "OR config_value like '"+description+"%' ";
        }
        return commonSql;
    }
}
