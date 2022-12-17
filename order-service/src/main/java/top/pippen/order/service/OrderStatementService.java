package top.pippen.order.service;


import top.pippen.order.bean.dto.biz.OrderStatementDTO;
import top.pippen.order.bean.model.biz.OrderStatementEntity;
import top.pippen.order.common.service.CrudService;
import top.pippen.order.dao.biz.OrderStatementDao;

/**
 * 支付清算
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
public interface OrderStatementService extends CrudService<OrderStatementEntity, OrderStatementDTO> {

    OrderStatementDao getDao();
}
