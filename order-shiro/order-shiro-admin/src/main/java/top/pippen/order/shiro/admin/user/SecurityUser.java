/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.shiro.admin.user;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public class SecurityUser {

    public static Subject getSubject() {
        try {
            return SecurityUtils.getSubject();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取用户信息
     */
    public static UserDetail getUser() {
        Subject subject = getSubject();
        if(subject == null){
            return new UserDetail();
        }

        UserDetail user = (UserDetail)subject.getPrincipal();
        if(user == null){
            return new UserDetail();
        }

        return user;
    }
    public static  Long getShopId() {
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin() == 1) {
            return null;
        }
        return user.getShopId();

    }

    public static boolean isAdmin(){
        UserDetail user = getUser();
        return user.getSuperAdmin() == 1;
    }
    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        return getUser().getId();
    }

}
