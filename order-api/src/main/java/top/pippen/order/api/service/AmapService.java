package top.pippen.order.api.service;

/**
 * @author Pippen
 * @since 2022/11/28 19:54
 */
public interface AmapService {
    /**
     * 计算两点之间的直线距离
     * @param origins
     * @param destination
     * @return
     */
    String distance(String origins,String destination);
}

