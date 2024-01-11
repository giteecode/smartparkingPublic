package com.smart.module.sys.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * sys_landing_records 实体类
 * Created by 小柒2012
 * Sun Oct 27 12:59:38 CST 2019
 */
@Data
@Entity 
@Table(name = "sys_landing_records")
public class SysLandingRecords implements Serializable{
   /**
    * 主键 
    */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 11)
	private Integer id;

   /**
    * 用户ID 
    */ 
	@Column(name = "user_id", nullable = false, length = 20)
	private Long userId;

   /**
    * 最近登录时间 
    */ 
	@Column(name = "login_date", nullable = false)
	private Timestamp loginDate;

   /**
    * 最近登录地点 
    */ 
	@Column(name = "place", nullable = false, length = 10)
	private String place;

   /**
    * 最近登录IP 
    */ 
	@Column(name = "ip", nullable = false, length = 15)
	private String ip;

   /**
    * 登录方式 
    */ 
	@Column(name = "login_way", nullable = false, length = 10)
	private String loginWay;

}

