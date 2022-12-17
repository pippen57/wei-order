package top.pippen.order.pay.service.impl;

import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayRefundQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.pippen.order.bean.dto.biz.OrderInfoDTO;
import top.pippen.order.bean.dto.biz.OrderRefundDTO;
import top.pippen.order.bean.dto.biz.ShopDTO;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.pay.service.PayService;

/**
 * @author Pippen
 * @since 2022/11/07 14:21
 */
@Service
public class PayServiceImpl implements PayService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final WxPayService wxPayService;


    public PayServiceImpl(WxPayService wxPayService) {
        this.wxPayService = wxPayService;
    }

    @Override
    public WxPayUnifiedOrderV3Result createOrder(OrderInfoDTO confirm, ShopDTO shopDTO, String userOpenId, String notifyUrl) {
        if (StringUtils.isBlank(notifyUrl)) {
            throw new RenException("支付异常,请联系管理员");
        }
        WxPayUnifiedOrderV3Request wx = new WxPayUnifiedOrderV3Request();
        wx.setDescription(shopDTO.getShopName());
        wx.setOutTradeNo(confirm.getOrder().getOrderNumber());
        wx.setNotifyUrl(notifyUrl);
        WxPayUnifiedOrderV3Request.Amount amount = new WxPayUnifiedOrderV3Request.Amount();
        amount.setCurrency("CNY");
        amount.setTotal(confirm.getOrder().getTotal().movePointRight(2).intValue());
        wx.setAmount(amount);
        WxPayUnifiedOrderV3Request.Payer payer = new WxPayUnifiedOrderV3Request.Payer();
        payer.setOpenid(userOpenId);
        wx.setPayer(payer);
        try {
            return wxPayService.unifiedOrderV3(TradeTypeEnum.JSAPI, wx);
        } catch (WxPayException e) {
            logger.error("微信支付下单异常:{}", e.getXmlString());
            throw new RenException("支付失败,请稍后再试");
        }
    }

    @Override
    public WxPayOrderQueryV3Result queryOrder(String transactionId, String outTradeNo) throws WxPayException {
        return wxPayService.queryOrderV3(transactionId, outTradeNo);
    }

    @Override
    public void closeOrder(String outTradeNo) throws WxPayException {
        wxPayService.closeOrderV3(outTradeNo);
    }

    @Override
    public WxPayRefundQueryV3Result refundQuery(String outRefundNo) throws WxPayException {
        return wxPayService.refundQueryV3(outRefundNo);

    }

    @Override
    public WxPayConfig getWxConfig() {
        return wxPayService.getConfig();
    }

    @Override
    public WxPayRefundV3Result refund(OrderRefundDTO refund, String refundNotifyUrl) throws WxPayException {
        if (StringUtils.isBlank(refundNotifyUrl)) {
            throw new RenException("退款异常,请联系管理员");
        }
        WxPayRefundV3Request request = new WxPayRefundV3Request();
        request.setOutTradeNo(refund.getOrderNumber());
        request.setOutRefundNo(refund.getRefundNumber());
        request.setReason(StringUtils.isNotBlank(refund.getBuyerMsg()) ? refund.getBuyerMsg() : null);
        request.setNotifyUrl(refundNotifyUrl);
        WxPayRefundV3Request.Amount amount = new WxPayRefundV3Request.Amount();
        amount.setCurrency("CNY");
        amount.setTotal(refund.getOrderAmount().movePointRight(2).intValue());
        amount.setRefund(refund.getRefundAmount().movePointRight(2).intValue());
        request.setAmount(amount);

        return wxPayService.refundV3(request);
    }
}
