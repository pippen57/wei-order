package top.pippen.order.rest.biz;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.binarywang.wxpay.bean.result.WxPayRefundQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import com.github.binarywang.wxpay.exception.WxPayException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.pippen.order.bean.dto.biz.OrderBalanceDTO;
import top.pippen.order.bean.dto.biz.OrderRefundDTO;
import top.pippen.order.bean.model.biz.OrderEntity;
import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.bean.vo.PayRefundNotifyResultVO;
import top.pippen.order.common.annotation.LogOperation;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.exception.ErrorCode;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.utils.ConvertUtils;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.AssertUtils;
import top.pippen.order.common.validator.ValidatorUtils;
import top.pippen.order.common.validator.group.AddGroup;
import top.pippen.order.pay.service.PayService;
import top.pippen.order.service.OrderRefundService;
import top.pippen.order.service.OrderService;
import top.pippen.order.service.SysParamsService;
import top.pippen.order.service.UserService;
import top.pippen.order.shiro.admin.user.SecurityUser;

import java.util.Date;
import java.util.Map;


/**
 * 支付退款
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-11-11
 */
@RestController
@RequestMapping("biz/orderrefund")
@Api(tags = "支付退款")
public class OrderRefundController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderRefundService orderRefundService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;
    @Autowired
    private UserService userService;
    @Autowired
    private SysParamsService sysParamsService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({@ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"), @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"), @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"), @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")})
    @RequiresPermissions("biz:orderrefund:page")
    public Result<PageData<OrderRefundDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        if (SecurityUser.isAdmin()) {
            String shopId = (String) params.get("shopId");
            if (StringUtils.isBlank(shopId)) {
                return new Result<PageData<OrderRefundDTO>>().error("参数有误");
            }
            params.put("shopId", shopId);
        } else {
            params.put("shopId", SecurityUser.getShopId() + "");
        }
        PageData<OrderRefundDTO> page = orderRefundService.page(params);

        return new Result<PageData<OrderRefundDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("biz:orderrefund:info")
    public Result<OrderRefundDTO> get(@PathVariable("id") Long id) {
        OrderRefundDTO data = orderRefundService.get(id);
        UserEntity userByUserId = userService.getUserByUserId(data.getUserId());
        data.setAvatar(userByUserId.getAvatar());
        data.setUsername(userByUserId.getUsername());
        data.setMobile(userByUserId.getMobile());
        return new Result<OrderRefundDTO>().ok(data);
    }


    @GetMapping("/balance")
    @ApiOperation("信息")
    @RequiresPermissions("biz:orderrefund:info")
    public Result<OrderBalanceDTO> balance(@RequestParam(required = false) Long shopId, @RequestParam(name = "orderId") Long orderId) {
        if (SecurityUser.isAdmin()) {
            if (ObjectUtil.isNull(shopId)) {
                return new Result<OrderBalanceDTO>().error("参数有误");
            }
        } else {
            shopId = SecurityUser.getShopId();
        }
        OrderBalanceDTO balance = orderRefundService.balance(orderId, shopId);
        return new Result<OrderBalanceDTO>().ok(balance);
    }

    @GetMapping("/refund_status/{refundId}")
    @ApiOperation("主动查询退款状态")
    public Result refundQuery(@PathVariable Long refundId) {
        OrderRefundDTO refundDTO = orderRefundService.get(refundId);
        if (ObjectUtil.isNotNull(refundDTO)){
            String refundNumber = refundDTO.getRefundNumber();
            try {
                WxPayRefundQueryV3Result refundQuery = payService.refundQuery(refundNumber);
                PayRefundNotifyResultVO notifyResult = ConvertUtils.sourceToTarget(refundQuery, PayRefundNotifyResultVO.class);
                PayRefundNotifyResultVO.Amount amount = ConvertUtils.sourceToTarget(refundQuery.getAmount(), PayRefundNotifyResultVO.Amount.class);
                notifyResult.setRefundStatus(refundQuery.getStatus());
                notifyResult.setAmount(amount);
                orderRefundService.parseRefundNotifyResult(notifyResult);
                return new Result();
            } catch (WxPayException e) {
                logger.debug("查询退款状态:"+e.toString());
                return new Result().error(e.getErrCodeDes());
            }

        }
        return new Result().error(ErrorCode.DB_RECORD_NOT_EXIST);
    }

    @PostMapping
    @ApiOperation("发起退款")
    @LogOperation("发起退款")
    @RequiresPermissions("biz:orderrefund:save")
    @Transactional(rollbackFor = Exception.class)
    public Result save(@RequestBody OrderRefundDTO dto) {
        if (SecurityUser.isAdmin()) {
            if (ObjectUtil.isNull(dto.getShopId())) {
                return new Result<>().error("请选择店铺");
            }
        } else {
            dto.setShopId(SecurityUser.getShopId());
        }
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class);
        OrderEntity orderEntity = orderService.getDao().selectOne(Wrappers.<OrderEntity>lambdaQuery().eq(OrderEntity::getId, dto.getOrderId()).eq(OrderEntity::getShopId, dto.getShopId()));
        if (ObjectUtil.isNull(orderEntity)) {
            throw new RenException(ErrorCode.DB_RECORD_NOT_EXIST, "订单数据不存在");
        }
        Integer isPayed = orderEntity.getIsPayed();
        if (isPayed == 0) {
            return new Result().error("订单未支付,不能发起退款");
        }

        String value = sysParamsService.getValue(Constant.REFUND_NOTIFY_URL);
        OrderRefundDTO refund = orderRefundService.refund(dto, orderEntity);
        WxPayRefundV3Result refundResult = null;
        try {
            refundResult = payService.refund(refund, value);
        } catch (WxPayException e) {
            return new Result<>().error(e.getErrCodeDes());
        }
        String status = refundResult.getStatus();
        if (Constant.WxPayRefundStatus.SUCCESS.getStatusStr().equals(status)) {
            orderEntity.setRefundStatus(2);
            refund.setReturnMoneySts(Constant.WxPayRefundStatus.SUCCESS.getStatus());
        } else if (Constant.WxPayRefundStatus.PROCESSING.getStatusStr().equals(status)) {
            orderEntity.setRefundStatus(1);
            refund.setReturnMoneySts(Constant.WxPayRefundStatus.PROCESSING.getStatus());
        } else if (Constant.WxPayRefundStatus.ABNORMAL.getStatusStr().equals(status)) {
            orderEntity.setRefundStatus(1);
            refund.setReturnMoneySts(Constant.WxPayRefundStatus.ABNORMAL.getStatus());
        } else if (Constant.WxPayRefundStatus.CLOSED.getStatusStr().equals(status)) {
            orderEntity.setRefundStatus(2);
            refund.setReturnMoneySts(Constant.WxPayRefundStatus.CLOSED.getStatus());
        }
        refund.setRefundId(refundResult.getRefundId());
        refund.setFundsAccount(refundResult.getFundsAccount());
        refund.setUserReceivedAccount(refundResult.getUserReceivedAccount());

        orderRefundService.save(refund);
        // 更新订单表状态
        orderEntity.setUpdateTime(new Date());
        orderService.getDao().updateById(orderEntity);
        return new Result();
    }


    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("biz:orderrefund:delete")
    public Result delete(@RequestBody Long[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        orderRefundService.delete(ids);

        return new Result();
    }

}
