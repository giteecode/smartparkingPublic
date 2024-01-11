package com.smart.module.pay.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart.common.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 支付配置
 */
@Data
@Entity 
@Table(name = "app_pay_config")
public class AppPayConfig extends PageBean implements Serializable{
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 20)
	private Long id;

    /**
     * 停车场
     */
    @Column(name = "car_park_id")
    private Long carParkId;

    /**
     * 商户号
     */
    @Column(name = "mch_id ", length = 500)
    private String mchId ;


    /**
     * 秘钥
     */
    @Column(name = "secret_key", length = 500)
    private String secretKey;

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

    /**
     * 创建用户id
     */
    @Column(name = "user_id_create")
    private Long userIdCreate;

}

