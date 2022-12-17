/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.bean.model.biz;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("biz_wx_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 密码
	 */
	@JsonIgnore
	private String openId;
	/**
	 * 创建时间
	 */
	private Date createDate;

}
