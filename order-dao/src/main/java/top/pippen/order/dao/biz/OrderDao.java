package top.pippen.order.dao.biz;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.pippen.order.bean.model.biz.OrderEntity;
import top.pippen.order.common.dao.BaseDao;

import java.util.Date;
import java.util.List;

/**
 * 订单
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@Mapper
public interface OrderDao extends BaseDao<OrderEntity> {
    /**
     * 查询代付款订单
     * @param time 超时时间
     * @return
     */
    void obligationOrder(@Param("time") Date time);

    /**
     * 获取超时的订单
     * @param time /
     * @return /
     */
    List<OrderEntity> getObligationOrder(@Param("time") Date time);
    /**
     * 根据商户订单号进行查询
     * @param orderNumber 商户订单号
     * @return <code>OrderEntity</code>
     */
    OrderEntity findByOrderNumber(String orderNumber);
}
