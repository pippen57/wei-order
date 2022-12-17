/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.shiro.admin.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class UserDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String realName;
    private Long shopId;
    private String headUrl;
    private Integer gender;
    private String email;
    private String mobile;
    private String password;
    private Integer status;
    private Integer superAdmin;

}
