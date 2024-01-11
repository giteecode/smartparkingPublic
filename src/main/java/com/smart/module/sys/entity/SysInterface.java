package com.smart.module.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart.common.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * sys_interface 接口管理
 * Created by 小柒2012
 * Sun Oct 27 13:01:25 CST 2019
 */
@Data
@Entity
@Table(name = "sys_interface")
public class SysInterface extends PageBean implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	/**
	 * 类型
	 */
	@Column(name = "type")
	private String type;

	/**
	 * 描述
	 */
	@Column(name = "description")
	private String description;

	/**
	 * 验证Token
	 */
	@Column(name = "token")
	private String token;

	/**
	 * 参数
	 */
	@Column(name = "params")
	private String params;


	/**
	 * 状态 0：禁用  1：启用
	 */
	@Column(name = "status")
	private Short status;

	/**
	 * 查询语句
	 */
	@Lob
	@Column(name = "query",columnDefinition="TEXT")
	private String query;

	/**
	 * 创建用户id
	 */
	@Column(name = "user_id_create")
	private Long userIdCreate;

	/**
	 * 创建时间
	 */
	@Column(name = "gmt_create")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp gmtCreate;

	/**
	 * 修改时间
	 */
	@Column(name = "gmt_modified")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp gmtModified;


}

