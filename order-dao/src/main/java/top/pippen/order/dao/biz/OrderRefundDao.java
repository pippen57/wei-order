package top.pippen.order.dao.biz;

import org.apache.ibatis.annotations.Mapper;
import top.pippen.order.bean.model.biz.OrderRefundEntity;
import top.pippen.order.common.dao.BaseDao;

/**
 * 支付退款
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-11-11
 */
@Mapper
public interface OrderRefundDao extends BaseDao<OrderRefundEntity> {

}
