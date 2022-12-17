package top.pippen.order.common.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;

import java.util.Date;

/**
 * @author Pippen
 * @since 2022/11/02 09:44
 */
public class OrderUtils {

    private static Snowflake snowflake = new Snowflake(3, 5);

    /**
     * 生成永不重复的订单号
     *
     * @param startLetter 订单号开头字符串
     * @return
     */
    public static String createOrderNo(String startLetter) {

        String format = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);


        String orderNo = startLetter + format + snowflake.nextIdStr();

        return orderNo;
    }

}
