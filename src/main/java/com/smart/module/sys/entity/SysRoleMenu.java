package com.smart.module.sys.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

/**
 * sys_role_menu 实体类
 * Created by 小柒2012
 * Sun Oct 27 13:02:47 CST 2019
 */
@Data
@Entity 
@Table(name = "sys_role_menu")
public class SysRoleMenu implements Serializable{
   /**
    * 记录id 
    */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 20)
	private Long id;

   /**
    * 角色ID 
    */ 
	@Column(name = "role_id", length = 20)
	private Long roleId;

   /**
    * 菜单ID 
    */ 
	@Column(name = "menu_id", length = 20)
	private Long menuId;

}

