package top.pippen.order.dao.biz;

import org.apache.ibatis.annotations.Mapper;
import top.pippen.order.bean.model.biz.OrderStatementEntity;
import top.pippen.order.common.dao.BaseDao;

/**
 * 支付清算
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@Mapper
public interface OrderStatementDao extends BaseDao<OrderStatementEntity> {

}
