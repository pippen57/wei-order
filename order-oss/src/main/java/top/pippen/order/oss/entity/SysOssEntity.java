/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.oss.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.pippen.order.common.entity.BaseEntity;

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_oss")
public class SysOssEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * URL地址
	 */
	private String url;

}
