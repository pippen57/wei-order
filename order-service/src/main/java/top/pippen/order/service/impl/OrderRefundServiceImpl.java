package top.pippen.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pippen.order.bean.dto.biz.OrderBalanceDTO;
import top.pippen.order.bean.dto.biz.OrderDTO;
import top.pippen.order.bean.dto.biz.OrderRefundDTO;
import top.pippen.order.bean.model.biz.OrderEntity;
import top.pippen.order.bean.model.biz.OrderRefundEntity;
import top.pippen.order.bean.model.biz.OrderStatementEntity;
import top.pippen.order.bean.vo.PayRefundNotifyResultVO;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.common.service.impl.CrudServiceImpl;
import top.pippen.order.common.utils.OrderUtils;
import top.pippen.order.dao.biz.OrderRefundDao;
import top.pippen.order.service.OrderRefundService;
import top.pippen.order.service.OrderService;
import top.pippen.order.service.OrderStatementService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.date.DatePattern.UTC_WITH_XXX_OFFSET_PATTERN;

/**
 * 支付退款
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-11-11
 */
@Service
public class OrderRefundServiceImpl extends CrudServiceImpl<OrderRefundDao, OrderRefundEntity, OrderRefundDTO> implements OrderRefundService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderStatementService orderStatementService;


    @Override
    public QueryWrapper<OrderRefundEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String shopId = (String) params.get("shopId");
        String orderId = (String) params.get("orderId");
        QueryWrapper<OrderRefundEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.eq(StringUtils.isNotBlank(shopId), "shop_id", shopId);
        wrapper.eq(StringUtils.isNotBlank(orderId), "order_id", orderId);

        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderRefundDTO refund(OrderRefundDTO dto, OrderEntity orderEntity) {

        BigDecimal refundAmount = dto.getRefundAmount();
        BigDecimal total = orderEntity.getTotal();
        if (refundAmount.compareTo(total) > 0) {
            throw new RenException("退款金额不能大于" + total.doubleValue());
        }
        OrderStatementEntity orderStatementEntity = orderStatementService.getDao().selectOne(Wrappers.<OrderStatementEntity>lambdaQuery().eq(OrderStatementEntity::getOrderNumber, orderEntity.getOrderNumber()));
        dto.setOrderNumber(orderEntity.getOrderNumber());
        dto.setOrderAmount(orderEntity.getTotal());
        String join = CollUtil.join(dto.getOrderItems(), ",");
        dto.setOrderItemId(join);
        dto.setRefundNumber(OrderUtils.createOrderNo("TK"));
        dto.setPayNo(orderStatementEntity.getPayNo());
        dto.setPayType(1);
        dto.setPayTypeName("微信支付");
        dto.setUserId(orderEntity.getUserId());
        dto.setGoodsNum(dto.getOrderItems().size());
        dto.setReturnMoneySts(0);
        dto.setCreateTime(new Date());
        return dto;
    }

    @Override
    public OrderBalanceDTO balance(Long orderId, Long shopId) {
        OrderDTO orderDTO = orderService.get(orderId);
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", shopId + "");
        params.put("orderId", orderId + "");
        List<OrderRefundDTO> list = list(params);
        OrderBalanceDTO balanceDTO = new OrderBalanceDTO();
        balanceDTO.setRefund(CollectionUtil.isNotEmpty(list));
        BigDecimal totalBalance = BigDecimal.valueOf(0.0);
        for (OrderRefundDTO orderRefundDTO : list) {
            BigDecimal refundAmount = orderRefundDTO.getRefundAmount();
            totalBalance = NumberUtil.add(totalBalance, refundAmount);
        }
        balanceDTO.setTotal(orderDTO.getTotal());
        balanceDTO.setTotalBalance(totalBalance);
        return balanceDTO;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void parseRefundNotifyResult(PayRefundNotifyResultVO result) {
        OrderRefundEntity refundEntity = baseDao.selectOne(Wrappers.<OrderRefundEntity>lambdaQuery().eq(OrderRefundEntity::getRefundNumber, result.getOutRefundNo()));

        if (refundEntity == null) {
            OrderEntity order = orderService.getDao().selectOne(Wrappers.<OrderEntity>lambdaQuery().eq(OrderEntity::getOrderNumber, result.getOutTradeNo()));
            if (order == null) {
                return;
            }
            refundEntity = new OrderRefundEntity();
            refundEntity.setOrderId(order.getId());
            refundEntity.setShopId(order.getShopId());
            refundEntity.setOrderNumber(order.getOrderNumber());
            refundEntity.setOrderAmount(order.getTotal());
            refundEntity.setRefundNumber(result.getOutRefundNo());
            refundEntity.setPayNo(result.getTransactionId());
            refundEntity.setPayType(1);
            refundEntity.setPayTypeName("微信支付");
            refundEntity.setUserId(order.getUserId());
            refundEntity.setCreateTime(new Date());
        }
        if (Constant.WxPayRefundStatus.SUCCESS.getStatus() == refundEntity.getReturnMoneySts()) {
            return;
        }
        String status = result.getRefundStatus();
        String successTime = result.getSuccessTime();
        OrderEntity orderEntity = new OrderEntity();
        if (Constant.WxPayRefundStatus.SUCCESS.getStatusStr().equals(status)) {
            DateTime refundTime = DateUtil.parse(successTime, UTC_WITH_XXX_OFFSET_PATTERN);
            refundEntity.setRefundTime(refundTime);
            refundEntity.setReturnMoneySts(Constant.WxPayRefundStatus.SUCCESS.getStatus());
        } else if (Constant.WxPayRefundStatus.ABNORMAL.getStatusStr().equals(status)) {
            refundEntity.setReturnMoneySts(Constant.WxPayRefundStatus.ABNORMAL.getStatus());
        } else if (Constant.WxPayRefundStatus.CLOSED.getStatusStr().equals(status)) {
            refundEntity.setReturnMoneySts(Constant.WxPayRefundStatus.CLOSED.getStatus());
        }
        Integer refund = result.getAmount().getRefund();
        BigDecimal feeNo = new BigDecimal(refund);
        feeNo = feeNo.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        refundEntity.setRefundAmount(feeNo);
        orderEntity.setRefundStatus(2);
        orderEntity.setUpdateTime(new Date());
        refundEntity.setUserReceivedAccount(result.getUserReceivedAccount());
        if (refundEntity.getId() == null) {
            baseDao.insert(refundEntity);
        } else {
            baseDao.updateById(refundEntity);
        }
        orderService.update(orderEntity, Wrappers.<OrderEntity>lambdaUpdate().eq(OrderEntity::getOrderNumber, result.getOutTradeNo()));
    }
}
