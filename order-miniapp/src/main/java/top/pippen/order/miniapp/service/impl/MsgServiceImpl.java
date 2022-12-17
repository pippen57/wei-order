package top.pippen.order.miniapp.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.pippen.order.bean.dto.biz.OrderDTO;
import top.pippen.order.bean.dto.biz.ShopDTO;
import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.miniapp.service.MsgService;

/**
 * @author Pippen
 * @since 2022/12/08 09:27
 */
@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private WxMaService wxMaService;


    @Override
    public void sendOrderSuccessMsg(OrderDTO order, ShopDTO shop, UserEntity user, String templateId) {
        WxMaMsgService msgService = wxMaService.getMsgService();
        try {
            msgService.sendSubscribeMsg(WxMaSubscribeMessage.builder().templateId(templateId)
                    .data(Lists.newArrayList(new WxMaSubscribeMessage.MsgData("character_string1", order.getMealNumber()),
                            new WxMaSubscribeMessage.MsgData("thing2", shop.getShopName().length() > 20 ? shop.getShopName().substring(0, 20) : shop.getShopName()),
                            new WxMaSubscribeMessage.MsgData("thing6", order.getProdName().length() > 20 ? order.getProdName().substring(0, 20) : order.getProdName()),
                            new WxMaSubscribeMessage.MsgData("time7", DateUtil.formatDateTime(order.getCreateTime()))))
                    .toUser(user.getOpenId()).build());
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendOrderCancelMsg(OrderDTO order, ShopDTO shop, UserEntity user, String templateId) {
        WxMaMsgService msgService = wxMaService.getMsgService();
        String thing3;
        switch (order.getCloseType()) {
            case 1:
                thing3 = "超时未支付";
                break;
            case 2:
                thing3 = "退款关闭";
                break;
            default:
                thing3 = "买家取消";
                break;
        }
        try {
            msgService.sendSubscribeMsg(WxMaSubscribeMessage.builder().templateId(templateId)
                    .data(Lists.newArrayList(new WxMaSubscribeMessage.MsgData("character_string5", order.getOrderNumber()),
                            new WxMaSubscribeMessage.MsgData("thing3", thing3),
                            new WxMaSubscribeMessage.MsgData("time2", DateUtil.formatDateTime(order.getCancelTime()))))
                    .toUser(user.getOpenId()).build());
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }

    }
}
