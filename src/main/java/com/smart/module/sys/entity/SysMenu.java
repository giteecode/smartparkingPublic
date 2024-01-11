package com.smart.module.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart.common.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * sys_menu 实体类
 * Created by 小柒2012
 * Sun Oct 27 13:02:31 CST 2019
 */
@Data
@Entity 
@Table(name = "sys_menu")
public class SysMenu extends PageBean implements Serializable{
   /**
    * 菜单id 
    */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id", nullable = false, length = 20)
	private Long menuId;

   /**
    * 父菜单ID，一级菜单为0 
    */ 
	@Column(name = "parent_id", length = 20)
	private Long parentId;

   /**
    * 菜单名称 
    */ 
	@Column(name = "name", length = 50)
	private String name;

   /**
    * 菜单URL 
    */ 
	@Column(name = "url", length = 200)
	private String url;

   /**
    * 授权(多个用逗号分隔，如：user:list,user:create) 
    */ 
	@Column(name = "perms", length = 500)
	private String perms;

   /**
    * 类型   0：目录   1：菜单   2：按钮 
    */ 
	@Column(name = "type", length = 11)
	private Integer type;

   /**
    * 菜单图标 
    */ 
	@Column(name = "icon", length = 50)
	private String icon;

   /**
    * 排序 
    */ 
	@Column(name = "order_num", length = 11)
	private Integer orderNum;

   /**
    * 创建时间 
    */
   	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "gmt_create")
	private Timestamp gmtCreate;

   /**
    * 修改时间 
    */ 
	@Column(name = "gmt_modified")
	private Timestamp gmtModified;

    @Transient
    private Boolean spread = false;

    @Transient
    private String parentName;

	@Transient
	private Boolean isParent;

	@Transient
	private List<SysMenu> list;

}

