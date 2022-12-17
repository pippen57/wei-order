package top.pippen.order.rest.biz;

import cn.hutool.core.util.ObjectUtil;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
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
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.pippen.order.bean.dto.biz.OrderBalanceDTO;
import top.pippen.order.bean.dto.biz.OrderDTO;
import top.pippen.order.bean.dto.biz.OrderInfoDTO;
import top.pippen.order.bean.excel.biz.OrderExcel;
import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.bean.vo.PayOrderNotifyResultVO;
import top.pippen.order.common.annotation.LogOperation;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.exception.ErrorCode;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.utils.ConvertUtils;
import top.pippen.order.common.utils.ExcelUtils;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.AssertUtils;
import top.pippen.order.pay.service.PayService;
import top.pippen.order.service.OrderRefundService;
import top.pippen.order.service.OrderService;
import top.pippen.order.service.UserService;
import top.pippen.order.shiro.admin.user.SecurityUser;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 订单
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@RestController
@RequestMapping("biz/order")
@Api(tags = "订单")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRefundService orderRefundService;
    @Autowired
    private PayService payService;

    @Autowired
    private UserService userService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({@ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"), @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"), @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"), @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")})
    @RequiresPermissions("biz:order:page")
    public Result<PageData<OrderInfoDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        if (SecurityUser.isAdmin()) {
            String shopId = (String) params.get("shopId");
            if (StringUtils.isBlank(shopId)) {
                return new Result<PageData<OrderInfoDTO>>().error("参数有误");
            }
            params.put("shopId", shopId);
        } else {
            params.put("shopId", SecurityUser.getShopId() + "");
        }
        PageData<OrderInfoDTO> page = orderService.pageList(params);
        List<OrderInfoDTO> list = page.getList();
        for (OrderInfoDTO dto : list) {
            OrderBalanceDTO balance = orderRefundService.balance(dto.getOrder().getId(), dto.getOrder().getShopId());
            dto.setBalance(balance);
        }
        return new Result<PageData<OrderInfoDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("biz:order:info")
    public Result<OrderDTO> get(@PathVariable("id") Long id) {
        OrderDTO data = orderService.get(id);

        return new Result<OrderDTO>().ok(data);
    }

    @GetMapping("/pay/{id}")
    public Result orderPayStatus(@PathVariable("id") Long id) {
        // 获取商户订单号
        OrderDTO data = orderService.get(id);
        if (ObjectUtil.isNotNull(data)) {
            String orderNumber = data.getOrderNumber();
            try {
                WxPayOrderQueryV3Result queryOrder = payService.queryOrder(null, orderNumber);
                // 交易状态
                Long userId = data.getUserId();
                UserEntity userByUserId = userService.getUserByUserId(userId);
                PayOrderNotifyResultVO notifyResult = ConvertUtils.sourceToTarget(queryOrder, PayOrderNotifyResultVO.class);
                WxPayOrderQueryV3Result.Payer payer = queryOrder.getPayer();
                WxPayOrderQueryV3Result.Amount amount = queryOrder.getAmount();
                PayOrderNotifyResultVO.Payer p = new PayOrderNotifyResultVO.Payer();
                PayOrderNotifyResultVO.Amount a = new PayOrderNotifyResultVO.Amount();
                p.setOpenid(payer.getOpenid());
                a.setTotal(amount.getTotal());
                a.setPayerCurrency(amount.getPayerCurrency());
                a.setCurrency(amount.getCurrency());
                a.setPayerTotal(amount.getPayerTotal());
                notifyResult.setPayer(p);
                notifyResult.setAmount(a);
                orderService.parseOrderNotifyResult(notifyResult, userByUserId);
                return new Result();
            } catch (WxPayException e) {
                logger.debug("查询支付状态:"+e.toString());
                return new Result().error(e.getErrCodeDes());
            }
        }
        return new Result().error(ErrorCode.DB_RECORD_NOT_EXIST);
    }


    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("biz:order:delete")
    public Result delete(@RequestBody Long[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        orderService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("biz:order:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<OrderDTO> list = orderService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, OrderExcel.class);
    }

}
