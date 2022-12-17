/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.common.redis;

/**
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
public class RedisKeys {
    /**
     * 系统参数Key
     */
    public static String getSysParamsKey(){
        return "sys:params";
    }

    /**
     * 验证码Key
     */
    public static String getCaptchaKey(String uuid){
        return "sys:captcha:" + uuid;
    }

    /**
     * 登录用户Key
     */
    public static String getSecurityUserKey(Long id){
        return "sys:security:user:" + id;
    }

    /**
     * 系统日志Key
     */
    public static String getSysLogKey(){
        return "sys:log";
    }

    /**
     * 系统资源Key
     */
    public static String getSysResourceKey(){
        return  "sys:resource";
    }

    /**
     * 用户菜单导航Key
     */
    public static String getUserMenuNavKey(Long userId){
        return "sys:user:nav:" + userId;
    }

    /**
     * 用户权限标识Key
     */
    public static String getUserPermissionsKey(Long userId){
        return "sys:user:permissions:" + userId;
    }

    /**
     * 微信支付 prepayId Key
     * @param orderNumber 订单编号
     */
    public static String getOrderWxPayPrepayIdKey(String orderNumber){
        return "biz:pay:prepay:id:" + orderNumber;
    }

    /**
     * 获取预下单id
     * @param preId /
     * @return /
     */
    public static String getPreOrderIdKey(String preId){
        return "biz:order:pre:id:" + preId;
    }

    public static String getMealNumberKey(Long shopId){
        return "biz:order:meal:number:" + shopId;
    }
}
