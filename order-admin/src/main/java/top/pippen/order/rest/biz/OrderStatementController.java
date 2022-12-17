package top.pippen.order.rest.biz;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.pippen.order.bean.dto.biz.OrderStatementDTO;
import top.pippen.order.bean.excel.biz.OrderStatementExcel;
import top.pippen.order.common.annotation.LogOperation;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.utils.ExcelUtils;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.AssertUtils;
import top.pippen.order.service.OrderStatementService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 支付清算
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@RestController
@RequestMapping("biz/orderstatement")
@Api(tags="支付清算")
public class OrderStatementController {
    @Autowired
    private OrderStatementService orderStatementService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("biz:orderstatement:page")
    public Result<PageData<OrderStatementDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<OrderStatementDTO> page = orderStatementService.page(params);

        return new Result<PageData<OrderStatementDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("biz:orderstatement:info")
    public Result<OrderStatementDTO> get(@PathVariable("id") Long id){
        OrderStatementDTO data = orderStatementService.get(id);

        return new Result<OrderStatementDTO>().ok(data);
    }


    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("biz:orderstatement:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        orderStatementService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("biz:orderstatement:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<OrderStatementDTO> list = orderStatementService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, OrderStatementExcel.class);
    }

}
