package top.pippen.order.pay.service;

import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayRefundQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import top.pippen.order.bean.dto.biz.OrderInfoDTO;
import top.pippen.order.bean.dto.biz.OrderRefundDTO;
import top.pippen.order.bean.dto.biz.ShopDTO;

/**
 * @author Pippen
 * @since 2022/11/07 14:21
 */
public interface PayService {
    /**
     * 微信下单
     *
     * @param confirm
     * @param shopDTO
     * @param userOpenId
     * @param notifyUrl
     * @return
     */
    WxPayUnifiedOrderV3Result createOrder(OrderInfoDTO confirm, ShopDTO shopDTO, String userOpenId, String notifyUrl);

    /**
     * 查询订单
     *
     * @param transactionId 微信订单号
     * @param outTradeNo 商户订单号
     */
    WxPayOrderQueryV3Result queryOrder(String transactionId, String outTradeNo) throws WxPayException;

    /**
     * 关闭订单
     * @param outTradeNo
     */
    void closeOrder(String outTradeNo) throws WxPayException;

    /**
     * 查询单笔退款
     *
     * @param outRefundNo 商户退款单号
     * @return WxPayRefundQueryV3Result
     * @throws WxPayException
     */
    WxPayRefundQueryV3Result refundQuery(String outRefundNo) throws WxPayException;

    WxPayConfig getWxConfig();
    /**
     * 订单退款
     * @return WxPayRefundV3Result
     * @throws WxPayException
     */
    WxPayRefundV3Result refund(OrderRefundDTO refund,String refundNotifyUrl) throws WxPayException;
}
