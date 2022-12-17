package top.pippen.order.api.controller;

import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Response;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.bean.vo.PayOrderNotifyResultVO;
import top.pippen.order.bean.vo.PayRefundNotifyResultVO;
import top.pippen.order.common.redis.RedisUtils;
import top.pippen.order.common.utils.ConvertUtils;
import top.pippen.order.service.OrderRefundService;
import top.pippen.order.service.OrderService;
import top.pippen.order.service.UserService;

/**
 * @author Pippen
 * @since 2022/11/07 15:17
 */
@RestController
@RequestMapping("/pay")
public class PayController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private OrderRefundService orderRefundService;

    @PostMapping("/notify/order")
    public String parseOrderNotifyResult(@RequestBody String xmlData, @RequestHeader("Wechatpay-Nonce") String nonce, @RequestHeader("Wechatpay-Signature") String signature, @RequestHeader("Wechatpay-Timestamp") String timestamp, @RequestHeader("Wechatpay-Serial") String serial) throws WxPayException {

        WxPayOrderNotifyV3Result notifyResult = null;
        try {
            notifyResult = this.wxPayService.parseOrderNotifyV3Result(xmlData, new SignatureHeader(timestamp, nonce, signature, serial));

            UserEntity userEntity = userService.getByOpenId(notifyResult.getResult().getPayer().getOpenid());

            WxPayOrderNotifyV3Result.Amount wxAmount = notifyResult.getResult().getAmount();
            //  根据自己业务场景需要构造返回对象
            PayOrderNotifyResultVO notifyResultVO = ConvertUtils.sourceToTarget(notifyResult.getResult(), PayOrderNotifyResultVO.class);
            PayOrderNotifyResultVO.Amount amount = new PayOrderNotifyResultVO.Amount();
            amount.setCurrency(wxAmount.getCurrency());
            amount.setPayerCurrency(wxAmount.getPayerCurrency());
            amount.setTotal(wxAmount.getTotal());
            amount.setPayerTotal(wxAmount.getPayerTotal());
            notifyResultVO.setAmount(amount);

            orderService.parseOrderNotifyResult(notifyResultVO, userEntity);
        } catch (WxPayException e) {
            return WxPayNotifyV3Response.fail(e.getErrCodeDes());
        }

        logger.debug("支付回调通知处理:{}", notifyResult);
        return WxPayNotifyV3Response.success("成功");
    }

    @PostMapping("/notify/refund")
    public String parseRefundNotifyResult(@RequestBody String xmlData, @RequestHeader("Wechatpay-Nonce") String nonce, @RequestHeader("Wechatpay-Signature") String signature, @RequestHeader("Wechatpay-Timestamp") String timestamp, @RequestHeader("Wechatpay-Serial") String serial) {
        WxPayRefundNotifyV3Result notifyResult = null;
        try {
            notifyResult = this.wxPayService.parseRefundNotifyV3Result(xmlData, new SignatureHeader(timestamp, nonce, signature, serial));
            PayRefundNotifyResultVO payRefundNotifyResultVO = ConvertUtils.sourceToTarget(notifyResult.getResult(), PayRefundNotifyResultVO.class);
            WxPayRefundNotifyV3Result.Amount wxAmount = notifyResult.getResult().getAmount();
            PayRefundNotifyResultVO.Amount amount = new PayRefundNotifyResultVO.Amount();
            amount.setTotal(wxAmount.getTotal());
            amount.setRefund(wxAmount.getRefund());
            amount.setPayerTotal(wxAmount.getPayerTotal());
            amount.setPayerRefund(wxAmount.getPayerRefund());
            payRefundNotifyResultVO.setAmount(amount);
            orderRefundService.parseRefundNotifyResult(payRefundNotifyResultVO);
        } catch (WxPayException e) {
            return WxPayNotifyV3Response.fail(e.getErrCodeDes());
        }
        logger.debug("退款回调通知处理:{}", notifyResult);
        return WxPayNotifyV3Response.success("成功");

    }
}

