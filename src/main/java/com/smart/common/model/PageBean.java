package com.smart.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页模板

 */
public class PageBean<T> {

    private List<T> pageData = new ArrayList<>();
    private Integer pageSize = Integer.valueOf(10);
    private Integer pageNo = Integer.valueOf(1);
    private Long totalCount = 0L;
    private String description;
    private String beginTime;
    private String endTime;

    public PageBean(List<T> pageData, Long totalCount) {
        this.pageData = pageData;
        this.totalCount = totalCount;
    }

    public PageBean() {

    }

    public List<T> getPageData() {
        return this.pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageNo() {
        return this.pageNo - 1;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}