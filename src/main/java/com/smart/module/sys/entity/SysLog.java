package com.smart.module.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart.common.model.PageBean;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.sql.*;

/**
 * sys_log 实体类
 * Created by 小柒2012
 * Sun Oct 27 12:59:18 CST 2019
 */
@Data
@Entity 
@Table(name = "sys_log")
public class SysLog extends PageBean implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 20)
	private Long id;

   /**
    * 用户id 
    */ 
	@Column(name = "user_id", length = 20)
	private Long userId;

   /**
    * 用户名 
    */ 
	@Column(name = "username", length = 50)
	private String username;

   /**
    * 用户操作 
    */ 
	@Column(name = "operation", length = 50)
	private String operation;

    /**
    * 响应时间 
    */ 
	@Column(name = "time", length = 11)
	private Integer time;

   /**
    * 请求方法 
    */ 
	@Column(name = "method", length = 200)
	private String method;

   /**
    * 请求参数 
    */ 
	@Column(name = "params")
	private String params;

   /**
    * IP地址 
    */ 
	@Column(name = "ip", length = 64)
	private String ip;

   /**
    * 访问方式 0:PC 1:手机 2:未知 
    */ 
	@Column(name = "device_type", nullable = false, length = 4)
	private Short deviceType;

   /**
    * 类型 0: 一般日志记录 1: 异常错误日志 
    */ 
	@Column(name = "log_type", nullable = false, length = 4)
	private Short logType;

   /**
    * 异常详细信息 
    */ 
	@Column(name = "exception_detail")
	private String exceptionDetail;

   /**
    * 创建时间 
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "gmt_create")
	private Timestamp gmtCreate;

}

