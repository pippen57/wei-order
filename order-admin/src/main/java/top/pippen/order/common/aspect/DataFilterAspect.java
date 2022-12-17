/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.common.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.pippen.order.bean.enums.SuperAdminEnum;
import top.pippen.order.common.annotation.DataFilter;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.exception.ErrorCode;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.common.interceptor.DataScope;
import top.pippen.order.shiro.admin.user.SecurityUser;
import top.pippen.order.shiro.admin.user.UserDetail;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 数据过滤，切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Aspect
@Component
public class DataFilterAspect {

    @Pointcut("@annotation(top.pippen.order.common.annotation.DataFilter)")
    public void dataFilterCut() {

    }

    @Before("dataFilterCut()")
    public void dataFilter(JoinPoint point) {
        Object params = point.getArgs()[0];
        if(params != null && params instanceof Map){
            UserDetail user = SecurityUser.getUser();

            //如果是超级管理员，则不进行数据过滤
            if(user.getSuperAdmin() == SuperAdminEnum.YES.value()) {
                return ;
            }

            try {
                //否则进行数据过滤
                Map map = (Map)params;
                String sqlFilter = getSqlFilter(user, point);
                map.put(Constant.SQL_FILTER, new DataScope(sqlFilter));
            }catch (Exception e){

            }

            return ;
        }

        throw new RenException(ErrorCode.DATA_SCOPE_PARAMS_ERROR);
    }

    /**
     * 获取数据过滤的SQL
     */
    private String getSqlFilter(UserDetail user, JoinPoint point) throws Exception {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        DataFilter dataFilter = method.getAnnotation(DataFilter.class);

        //获取表的别名
        String tableAlias = dataFilter.tableAlias();
        if(StringUtils.isNotBlank(tableAlias)){
            tableAlias +=  ".";
        }

        StringBuilder sqlFilter = new StringBuilder();
        sqlFilter.append(" (");

        sqlFilter.append(tableAlias).append(dataFilter.userId()).append("=").append(user.getId());

        sqlFilter.append(")");

        return sqlFilter.toString();
    }
}
