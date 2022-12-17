package top.pippen.order.service;


import top.pippen.order.bean.dto.biz.OrderInfoDTO;
import top.pippen.order.bean.dto.biz.OrderDTO;
import top.pippen.order.bean.dto.biz.PreOrderDTO;
import top.pippen.order.bean.model.biz.OrderEntity;
import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.bean.vo.PayOrderNotifyResultVO;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.service.CrudService;
import top.pippen.order.dao.biz.OrderDao;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
public interface OrderService extends CrudService<OrderEntity, OrderDTO> {


    PageData<OrderInfoDTO> pageList(Map<String, Object> params);
    /**
     * 预下单
     * 计算金额,订单详情.
     *
     * @param userId userId
     * @param phone  phone
     * @param shopId
     */
    PreOrderDTO preOrder(Long userId, String phone,Long shopId);

    /**
     * 下单
     *
     * @param orderDTO
     * @return
     */
    OrderInfoDTO confirm(OrderDTO orderDTO);


    /**
     * 用户主动关闭订单
     * @param orderId
     * @param userId
     */
    void cencelOrder(Long orderId,Long userId);


    /**
     * 用户删除订单 这个删除是逻辑删除
     * @param orderId
     * @param userId
     */
    void deleteOrder(Long orderId, Long userId);

    /**
     * 更新订单状态
     *
     * @param orderEntity /
     * @return /
     */
   void obligationOrder(OrderEntity orderEntity);

    /**
     * 获取超时订单
     * @param time
     * @return
     */
   List<OrderEntity> getObligationOrder(Date time);

    OrderDao getDao();
    /**
     * 支付结果通知
     *
     * @param payOrderResult 微信支付返回信息
     * @param user
     */
    void parseOrderNotifyResult(PayOrderNotifyResultVO payOrderResult, UserEntity user);
}
