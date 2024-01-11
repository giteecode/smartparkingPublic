package com.smart.module.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart.common.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * sys_org 实体类
 * Created by 小柒2012
 * Sun Oct 27 13:02:24 CST 2019
 */
@Data
@Entity 
@Table(name = "sys_org")
public class SysOrg extends PageBean implements Serializable{
   /**
    * 机构id 
    */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "org_id", nullable = false, length = 20)
	private Long orgId;

   /**
    * 上级机构ID，一级机构为0 
    */ 
	@Column(name = "parent_id", length = 20)
	private Long parentId;

   /**
    * 机构编码 
    */ 
	@Column(name = "code", length = 100)
	private String code;

   /**
    * 机构名称 
    */ 
	@Column(name = "name", length = 100)
	private String name;

   /**
    * 机构名称(全称) 
    */ 
	@Column(name = "full_name", length = 100)
	private String fullName;

   /**
    * 机构负责人 
    */ 
	@Column(name = "director", length = 100)
	private String director;

   /**
    * 联系邮箱 
    */ 
	@Column(name = "email", length = 100)
	private String email;

   /**
    * 联系电话 
    */ 
	@Column(name = "phone", length = 100)
	private String phone;

   /**
    * 地址 
    */ 
	@Column(name = "address", length = 200)
	private String address;

   /**
    * 排序 
    */ 
	@Column(name = "order_num", length = 11)
	private Integer orderNum;

   /**
    * 可用标识  1：可用  0：不可用 
    */ 
	@Column(name = "status", length = 4)
	private Short status;

   /**
    * 创建时间 
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "gmt_create")
	private Timestamp gmtCreate;

   /**
    * 修改时间 
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "gmt_modified")
	private Timestamp gmtModified;

	@Transient
	private String parentName;

}

