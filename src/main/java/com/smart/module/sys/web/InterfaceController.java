package com.smart.module.sys.web;

import cn.hutool.core.io.FileUtil;
import com.smart.common.model.Result;
import com.smart.common.util.ExcelExport;
import com.smart.common.util.ShiroUtils;
import com.smart.module.sys.entity.SysInterface;
import com.smart.module.sys.service.SysInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 接口配置

 */
@Slf4j
@RestController
@RequestMapping("/sys/interface")
public class InterfaceController {

    @Autowired
    private SysInterfaceService sysInterfaceService;

    /**
     * 接口列表
     */
    @PostMapping("/list")
    @RequiresRoles("admin")
    public Result list(SysInterface entity){
        return sysInterfaceService.list(entity);
    }

    /**
     * 获取
     */
    @PostMapping("/get")
    @RequiresRoles("admin")
    public Result get(Long id){
        return sysInterfaceService.get(id);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresRoles("admin")
    public Result save(@RequestBody SysInterface entity){
        return sysInterfaceService.save(entity);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresRoles("admin")
    public Result delete(Long id){
        return sysInterfaceService.delete(id);
    }

    /**
     * 查询
     */
    @PostMapping("/query")
    @RequiresRoles("admin")
    public Result query(SysInterface entity){
        return sysInterfaceService.query(entity);
    }

    /**
     * 查询根据机构
     */
    @PostMapping("/queryByOrgId")
    @RequiresRoles("admin")
    public Result queryByOrgId(SysInterface entity){
        entity.setParams(ShiroUtils.getUserEntity().getOrgId().toString());
        return sysInterfaceService.query(entity);
    }

    /**
     * 导出任务
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "export")
    @RequiresRoles(value={"admin","orgAdmin"})
    public String export(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> listMap = list.get(0);
            /**
             * 数据
             */
            Map<String, Integer> dataMap = new LinkedHashMap<>();
            /**
             * 表头 可以自定义 AS
             */
            Map<String, Object> headerMap = new LinkedHashMap<>();
            for (String key : listMap.keySet()) {
                headerMap.put(key,key);
                dataMap.put(key, ExcelExport.CELL_ALIGN_LEFT);
            }
            list.add(0, headerMap);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            InputStream stream = ClassUtils.getDefaultClassLoader()
                    .getResourceAsStream("static/file/taskTemplate.xlsx");
            ExcelExport excelExport = new ExcelExport(
                    FileUtil.writeFromStream(stream, new File("file/taskTemplate.xlsx")), -1);
            excelExport.setDataList(list,dataMap, false, "").writeTemplate(response, request,
                    "任务查询信息-" + sdf.format(new Date()) + ".xlsx");
        } catch (Exception e) {
            log.error("任务查询信息导出Excel出错", e);
        }
        return null;
    }
}
