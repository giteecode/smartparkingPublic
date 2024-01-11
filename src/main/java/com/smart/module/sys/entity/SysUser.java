package com.smart.module.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart.common.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * sys_user 实体类
 * Created by 小柒2012
 * Sun Oct 27 13:03:00 CST 2019
 */
@Data
@Entity 
@Table(name = "sys_user")
public class SysUser extends PageBean implements Serializable{
   /**
    * 用户id 
    */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false, length = 20)
	private Long userId;

   /**
     * 所属机构
     */
    @Column(name = "org_id", nullable = false, length = 20)
    private Long orgId;

   /**
    * 用户名 
    */
	@Column(name = "username", nullable = false, length = 50)
	private String username;

   /**
    * 密码 
    */
    @Column(name = "password", nullable = false, length = 50)
	private String password;

   /**
    * 姓名(昵称) 
    */ 
	@Column(name = "nickname", length = 50)
	private String nickname;

   /**
    * 邮箱 
    */
	@Column(name = "email", length = 100)
	private String email;

   /**
    * 手机号 
    */
	@Column(name = "mobile", length = 100)
	private String mobile;

   /**
    * 状态 0:禁用，1:正常 
    */ 
	@Column(name = "status", length = 4)
	private Short status;

   /**
    * 头像上传 0:未上传 1:上传 
    */ 
	@Column(name = "avatar_status", nullable = false, length = 4)
	private Short avatarStatus;

   /**
    * 备注 
    */ 
	@Column(name = "remark", length = 500)
	private String remark;

   /**
    * 创建用户id 
    */ 
	@Column(name = "user_id_create")
	private Long userIdCreate;

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

   /**
    * 是否修改过初始密码 
    */ 
	@Column(name = "is_modify_pwd", length = 4)
	private Short isModifyPwd;

	/**
	 * 机构名称
	 */
	@Transient
	private String orgName;

	/**
	 * 角色ID
	 */
	@Transient
	private List<Object> roleIdList;

	/**
	 * 角色名称
	 */
	@Transient
	private List<Object> roleNameList;

    @Transient
    private String oldPassword;

}

