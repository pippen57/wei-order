package top.pippen.order.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pippen.order.bean.dto.biz.*;
import top.pippen.order.bean.model.biz.OrderEntity;
import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.bean.vo.PayOrderNotifyResultVO;
import top.pippen.order.common.exception.ErrorCode;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.redis.RedisKeys;
import top.pippen.order.common.redis.RedisUtils;
import top.pippen.order.common.service.impl.CrudServiceImpl;
import top.pippen.order.common.utils.ConvertUtils;
import top.pippen.order.common.utils.OrderUtils;
import top.pippen.order.dao.biz.OrderDao;
import top.pippen.order.msg.constant.MQPrefixConst;
import top.pippen.order.service.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hutool.core.date.DatePattern.UTC_WITH_XXX_OFFSET_PATTERN;

/**
 * 订单
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@Service
public class OrderServiceImpl extends CrudServiceImpl<OrderDao, OrderEntity, OrderDTO> implements OrderService {

    private final CartItemService cartItemService;
    private final OrderItemService orderItemService;
    private final OrderStatementService orderStatementService;
    private final RedisUtils redisUtils;
    private final RabbitTemplate rabbitTemplate;

    public OrderServiceImpl(CartItemService cartItemService, OrderItemService orderItemService, OrderStatementService orderStatementService, RedisUtils redisUtils, RabbitTemplate rabbitTemplate) {
        this.cartItemService = cartItemService;
        this.orderItemService = orderItemService;
        this.orderStatementService = orderStatementService;
        this.redisUtils = redisUtils;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public PageData<OrderInfoDTO> pageList(Map<String, Object> params) {
        PageData<OrderDTO> page = super.page(params);
        List<OrderDTO> orderList = page.getList();
        List<OrderInfoDTO> infoList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(orderList)) {
            Map<String, Object> orderItemParams = new HashMap<>();
            orderItemParams.put("shopId", params.get("shopId"));
            for (OrderDTO orderDTO : orderList) {
                Long id = orderDTO.getId();
                orderItemParams.put("orderId", id + "");
                List<OrderItemDTO> orderItemList = orderItemService.list(orderItemParams);
                OrderInfoDTO orderInfo = new OrderInfoDTO(orderDTO, orderItemList);
                infoList.add(orderInfo);
            }
            return new PageData<OrderInfoDTO>(infoList, page.getTotal());
        }


        return new PageData<OrderInfoDTO>(infoList, page.getTotal());
    }

    @Override
    public QueryWrapper<OrderEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String orderNumber = (String) params.get("orderNumber");
        String status = (String) params.get("status");
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        String userId = (String) params.get("userId");
        String deleteStatus = (String) params.get("deleteStatus");


        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.eq(StringUtils.isNotBlank(orderNumber), "order_number", orderNumber);
        wrapper.eq(StringUtils.isNotBlank(status), "status", status);
        wrapper.eq(StringUtils.isNotBlank(userId), "user_id", userId);
        wrapper.eq(StringUtils.isNotBlank(deleteStatus), "delete_status", deleteStatus);
        wrapper.between(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime), "create_time", startTime, endTime);
        return wrapper;
    }

    @Override
    public PreOrderDTO preOrder(Long userId, String phone, Long shopId) {
        if (userId == null) {
            throw new RenException(ErrorCode.TOKEN_INVALID);
        }
        // 生成预下单编号,
        String preId = RandomUtil.randomStringUpper(32);

        BigDecimal total = BigDecimal.valueOf(0.0);
        int totalCount = 0;

        PreOrderDTO preOrderDTO = new PreOrderDTO();
        // 获取购物车数据
        List<CartItemDTO> cartItem = cartItemService.listCart(userId, shopId);
        if (CollectionUtil.isEmpty(cartItem)) {
            throw new RenException("购物车中暂无数据");
        }
        preOrderDTO.setProdList(cartItem);
        preOrderDTO.setUserPhone(phone);

        for (CartItemDTO item : cartItem) {
            SkuDTO sku = item.getSku();
            Integer quantity = item.getQuantity();
            if (ObjectUtil.isNotNull(sku)) {
                BigDecimal price = sku.getPrice();
                BigDecimal mul = NumberUtil.mul(price, quantity);
                total = NumberUtil.add(mul, total);
            } else {
                ProductDTO product = item.getProduct();
                BigDecimal price = product.getPrice();
                BigDecimal mul = NumberUtil.mul(price, quantity);
                total = NumberUtil.add(mul, total);
            }

            totalCount += quantity;
        }
        preOrderDTO.setTotalMoney(total);
        preOrderDTO.setTotalCount(totalCount);
        preOrderDTO.setPreId(preId);
        // 设置预下单编号
        redisUtils.set(RedisKeys.getPreOrderIdKey(preId), preOrderDTO, RedisUtils.HOUR_ONE_EXPIRE);
        // 计算总金额 和数量
        return preOrderDTO;
    }

    @Override
    public void cencelOrder(Long orderId, Long userId) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(4);
        orderEntity.setCloseType(4);
        orderEntity.setCancelTime(new Date());
        baseDao.update(orderEntity, Wrappers.<OrderEntity>lambdaUpdate().eq(OrderEntity::getId, orderId).eq(OrderEntity::getUserId, userId));
    }

    @Override
    public void deleteOrder(Long orderId, Long userId) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDeleteStatus(1);
        baseDao.update(orderEntity, Wrappers.<OrderEntity>lambdaUpdate().eq(OrderEntity::getId, orderId).eq(OrderEntity::getUserId, userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderInfoDTO confirm(OrderDTO orderDTO) {

        orderDTO.setOrderNumber(OrderUtils.createOrderNo("SA"));
        // 总价
        BigDecimal total = BigDecimal.valueOf(0.0);
        // 数量
        int totalCount = 0;
        StringBuilder prodName = new StringBuilder();

        // 获取购物车数据
        List<CartItemDTO> cartItem = cartItemService.listCart(orderDTO.getUserId(), orderDTO.getShopId());
        if (CollectionUtil.isEmpty(cartItem)) {
            throw new RenException("购物车中暂无数据");
        }
        // 计算总价与数量
        for (CartItemDTO item : cartItem) {
            SkuDTO sku = item.getSku();
            Integer quantity = item.getQuantity();
            if (ObjectUtil.isNotNull(sku)) {
                BigDecimal price = sku.getPrice();
                BigDecimal mul = NumberUtil.mul(price, quantity);
                total = NumberUtil.add(mul, total);
                prodName.append(sku.getProductName()).append(",");
            } else {
                ProductDTO product = item.getProduct();
                BigDecimal price = product.getPrice();
                BigDecimal mul = NumberUtil.mul(price, quantity);
                total = NumberUtil.add(mul, total);
                prodName.append(product.getProductName()).append(",");
            }
            totalCount += quantity;

        }
        orderDTO.setTotal(total);
        orderDTO.setPayType(1);
        orderDTO.setStatus(1);
        orderDTO.setProductNums(totalCount);
        orderDTO.setCreateTime(new Date());
        orderDTO.setIsPayed(0);
        orderDTO.setDeleteStatus(0);
        orderDTO.setOrderSource(1);
        orderDTO.setRefundStatus(0);
        orderDTO.setProdName(prodName.substring(0, prodName.length() - 1));

        // 保存订单
        save(orderDTO);

        List<OrderItemDTO> items = cartItem.stream().map(s -> new OrderItemDTO(s, orderDTO.getShopId(), orderDTO.getUserId(), orderDTO.getId(), s.getProduct())).collect(Collectors.toList());

        // 保存订单商品信息
        orderItemService.saveList(items);
        // 清空购物车
        cartItemService.clearCartItem(orderDTO.getUserId(), orderDTO.getShopId());

        return new OrderInfoDTO(orderDTO, items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void parseOrderNotifyResult(PayOrderNotifyResultVO payOrderResult, UserEntity user) {
        String outTradeNo = payOrderResult.getOutTradeNo();
        String transactionId = payOrderResult.getTransactionId();
        String successTime = payOrderResult.getSuccessTime();
        DateTime payTime = DateUtil.parse(successTime, UTC_WITH_XXX_OFFSET_PATTERN);
        String tradeState = payOrderResult.getTradeState();
        OrderEntity orderEntity = baseDao.findByOrderNumber(outTradeNo);
        redisUtils.delete(RedisKeys.getOrderWxPayPrepayIdKey(outTradeNo));
        if (orderEntity != null) {
            if (orderEntity.getIsPayed() == 1) {
                return;
            }
            if (PayOrderNotifyResultVO.TradeState.SUCCESS.equals(tradeState)) {
                redisUtils.delete(RedisKeys.getOrderWxPayPrepayIdKey(outTradeNo));
                orderEntity.setPayTime(payTime);
                orderEntity.setStatus(3);
                orderEntity.setUpdateTime(new Date());
                orderEntity.setIsPayed(1);
                if (StringUtils.isBlank(orderEntity.getMealNumber())) {
                    String autoId = redisUtils.getAutoId(RedisKeys.getMealNumberKey(orderEntity.getShopId()));
                    orderEntity.setMealNumber(autoId);
                }

                baseDao.updateById(orderEntity);
                OrderStatementDTO orderStatementDTO = new OrderStatementDTO();
                orderStatementDTO.setPayNo(transactionId);
                orderStatementDTO.setOrderNumber(outTradeNo);
                orderStatementDTO.setPayType(1);
                orderStatementDTO.setPayTypeName("微信支付");
                BigDecimal feeNo = new BigDecimal(payOrderResult.getAmount().getPayerTotal());
                feeNo = feeNo.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                orderStatementDTO.setPayAmount(feeNo);
                orderStatementDTO.setIsClearing(0);
                orderStatementDTO.setUserId(user.getId());
                orderStatementDTO.setCreateTime(new Date());
                orderStatementDTO.setPayStatus(1);
                orderStatementService.save(orderStatementDTO);
                OrderDTO orderDTO = ConvertUtils.sourceToTarget(orderEntity, OrderDTO.class);
//                msgService.sendOrderSuccessMsg(orderDTO, shopDTO, user, "59rCkTAc-w3onTN1QKZcu2OEBmHSjefWqXiaCFNwYPY");
                rabbitTemplate.convertAndSend(MQPrefixConst.ORDER_SUBMIT_EXCHANGE, "*", new Message(JSONUtil.toJsonStr(orderDTO).getBytes(StandardCharsets.UTF_8), new MessageProperties()));
            }

        }


    }

    @Override
    public List<OrderEntity> getObligationOrder(Date time) {
        return baseDao.getObligationOrder(time);
    }

    @Override
    public OrderDao getDao() {
        return baseDao;
    }

    @Override
    public void obligationOrder(OrderEntity orderEntity) {

        orderEntity.setUpdateTime(new Date());
        orderEntity.setCancelTime(new Date());
        orderEntity.setCloseType(1);
        orderEntity.setStatus(4);

        baseDao.updateById(orderEntity);
    }
}
