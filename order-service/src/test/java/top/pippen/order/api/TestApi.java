package top.pippen.order.api;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

/**
 * @author Pippen
 * @since 2022/11/08 15:07
 */
public class TestApi {
    public static void main(String[] args) {
        DateTime parse = DateUtil.parse("2015-05-20T13:29:35+08:00", "yyyy-MM-dd'T'HH:mm:ssXXX");
        System.out.print(parse);

    }
}
