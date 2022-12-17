package top.pippen.order.api.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.v3.util.SignUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import top.pippen.order.api.annotation.Login;
import top.pippen.order.api.annotation.LoginUser;
import top.pippen.order.bean.dto.biz.*;
import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.exception.ErrorCode;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.redis.RedisKeys;
import top.pippen.order.common.redis.RedisUtils;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.ValidatorUtils;
import top.pippen.order.common.validator.group.AddGroup;
import top.pippen.order.msg.constant.MQPrefixConst;
import top.pippen.order.pay.service.PayService;
import top.pippen.order.service.OrderRefundService;
import top.pippen.order.service.OrderService;
import top.pippen.order.service.ShopService;
import top.pippen.order.service.SysParamsService;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单
 *
 * @author Pippen
 * @since 2022/10/31 16:58
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private PayService payService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SysParamsService sysParamsService;
    @Autowired
    private OrderRefundService orderRefundService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 预下单 计算商品总金额与数量,等待用户确认支付
     *
     * @param user
     * @return
     */
    @Login
    @GetMapping("/pre_order/{shopId}")
    public Result<PreOrderDTO> preOrder(@LoginUser UserEntity user, @PathVariable Long shopId) {
        ShopDTO shopDTO = shopService.get(shopId);
        if (ObjectUtil.isNull(shopDTO)) {
            return new Result<PreOrderDTO>().error("所选商家不存在");
        }
        if (shopDTO.getShopStatus() == 1) {
            return new Result<PreOrderDTO>().ok(orderService.preOrder(user.getId(), user.getMobile(), shopId));
        }
        return new Result<PreOrderDTO>().error(ErrorCode.SHOP_NO_BUSINESS, "商家暂未营业");
    }

    @Login
    @GetMapping("/cencel_order")
    public Result cencelOrder(@RequestParam(name = "orderId") Long orderId, @LoginUser UserEntity user) {
        OrderDTO orderDTO = orderService.get(orderId);
        try {
            payService.closeOrder(orderDTO.getOrderNumber());
            orderService.cencelOrder(orderId, user.getId());
            rabbitTemplate.convertAndSend(MQPrefixConst.ORDER_STATUS_EXCHANGE, "*",
                    new Message(JSONUtil.toJsonStr(orderDTO).getBytes(StandardCharsets.UTF_8), new MessageProperties()));
        } catch (WxPayException e) {
            return new Result().error(e.getErrCodeDes());
        }
        return new Result();
    }

    @Login
    @DeleteMapping
    public Result deleteOrder(@RequestParam(name = "orderId") Long orderId, @LoginUser UserEntity user) {
        orderService.deleteOrder(orderId, user.getId());
        return new Result();
    }

    @Login
    @GetMapping
    public Result<PageData<OrderInfoDTO>> orderList(@LoginUser UserEntity user) {
        Map<String, Object> params = new HashMap<>();
        Date now = new Date();
        String endTime = DateUtil.formatDateTime(now);
        String startTime = DateUtil.formatDateTime(DateUtil.offsetDay(now, -180));
        params.put("userId", user.getId() + "");
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("orderField", "create_time");
        params.put("order", "desc");
        params.put("deleteStatus", 0 + "");
        PageData<OrderInfoDTO> page = orderService.pageList(params);
        List<OrderInfoDTO> list = page.getList();
        for (OrderInfoDTO dto : list) {
            OrderBalanceDTO balance = orderRefundService.balance(dto.getOrder().getId(), dto.getOrder().getShopId());
            ShopDTO shopDTO = shopService.get(dto.getOrder().getShopId());
            dto.setShop(shopDTO);
            dto.setBalance(balance);
        }
        return new Result<PageData<OrderInfoDTO>>().ok(page);
    }


    @Login
    @GetMapping("/pay/{orderNumber}")
    public Result<WxPayUnifiedOrderV3Result.JsapiResult> pay(@PathVariable String orderNumber) {
        String prepayId = (String) redisUtils.get(RedisKeys.getOrderWxPayPrepayIdKey(orderNumber));
        if (StringUtils.isNotBlank(prepayId)) {
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            String nonceStr = SignUtils.genRandomStr();
            String appId = payService.getWxConfig().getAppId();
            PrivateKey privateKey = payService.getWxConfig().getPrivateKey();
            WxPayUnifiedOrderV3Result.JsapiResult jsapiResult = new WxPayUnifiedOrderV3Result.JsapiResult();
            jsapiResult.setAppId(appId).setTimeStamp(timestamp).setPackageValue("prepay_id=" + prepayId).setNonceStr(nonceStr)
                    //签名类型，默认为RSA，仅支持RSA。
                    .setSignType("RSA").setPaySign(SignUtils.sign(String.format("%s\n%s\n%s\n%s\n", appId, timestamp, nonceStr, jsapiResult.getPackageValue()), privateKey));
            return new Result<WxPayUnifiedOrderV3Result.JsapiResult>().ok(jsapiResult);
        } else {
            return new Result<WxPayUnifiedOrderV3Result.JsapiResult>().error("订单已取消");
        }
    }

    @Login
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public Result<WxPayUnifiedOrderV3Result.JsapiResult> confirm(@RequestBody OrderDTO orderDTO, @LoginUser UserEntity user) {
        //效验数据
        orderDTO.setUserId(user.getId());
        ValidatorUtils.validateEntity(orderDTO, AddGroup.class);
        Object o = redisUtils.get(RedisKeys.getPreOrderIdKey(orderDTO.getPreId()));
        if (ObjectUtil.isNull(o)) {
            throw new RenException("订单结算超时,请重新下单结算.");
        } else {
            redisUtils.delete(RedisKeys.getPreOrderIdKey(orderDTO.getPreId()));
        }
        ShopDTO shopDTO = shopService.get(orderDTO.getShopId());
        if (ObjectUtil.isNull(shopDTO)) {
            return new Result<WxPayUnifiedOrderV3Result.JsapiResult>().error("所选商家不存在");
        }
        if (shopDTO.getShopStatus() != 1) {
            return new Result<WxPayUnifiedOrderV3Result.JsapiResult>().error(ErrorCode.SHOP_NO_BUSINESS, "商家暂未营业");
        }
        OrderInfoDTO confirm = orderService.confirm(orderDTO);
        String notifyUrl = sysParamsService.getValue(Constant.ORDER_NOTIFY_URL);
        WxPayUnifiedOrderV3Result order = payService.createOrder(confirm, shopDTO, user.getOpenId(), notifyUrl);
        WxPayConfig wxConfig = payService.getWxConfig();
        WxPayUnifiedOrderV3Result.JsapiResult payInfo = order.getPayInfo(TradeTypeEnum.JSAPI, wxConfig.getAppId(), wxConfig.getMchId(), wxConfig.getPrivateKey());

        String value = sysParamsService.getValue(Constant.ORDER_CANCEL_KEY);
        int time = Integer.parseInt(StringUtils.isBlank(value) ? "5" : value);
        redisUtils.set(RedisKeys.getOrderWxPayPrepayIdKey(confirm.getOrder().getOrderNumber()), order.getPrepayId(), 60L * time);

        return new Result<WxPayUnifiedOrderV3Result.JsapiResult>().ok(payInfo);
    }
}
