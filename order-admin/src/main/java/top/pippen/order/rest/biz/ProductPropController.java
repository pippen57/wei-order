package top.pippen.order.rest.biz;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.pippen.order.bean.dto.biz.ProductPropDTO;
import top.pippen.order.common.annotation.LogOperation;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.ValidatorUtils;
import top.pippen.order.common.validator.group.AddGroup;
import top.pippen.order.common.validator.group.UpdateGroup;
import top.pippen.order.service.ProductPropService;
import top.pippen.order.shiro.admin.user.SecurityUser;

import java.util.List;
import java.util.Map;


/**
 * 规格
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@RestController
@RequestMapping("biz/productprop")
@Api(tags = "规格")
public class ProductPropController {
    @Autowired
    private ProductPropService productPropService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({@ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"), @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"), @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"), @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")})
    @RequiresPermissions("biz:productprop:page")
    public Result<PageData<ProductPropDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        if (SecurityUser.isAdmin()) {
            String shopId = (String) params.get("shopId");
            if (StringUtils.isBlank(shopId)) {
                return new Result<PageData<ProductPropDTO>>().error("参数有误");
            }
            params.put("shopId", shopId);
        } else {
            params.put("shopId", SecurityUser.getShopId()+"");
        }
        PageData<ProductPropDTO> page = productPropService.page(params);

        return new Result<PageData<ProductPropDTO>>().ok(page);
    }

    @GetMapping("all")
    @ApiOperation("全部数据")
    @RequiresPermissions("biz:productprop:info")
    public Result<List<ProductPropDTO>> allData(@RequestParam(required = false) Long shopId) {
        if (SecurityUser.isAdmin()) {
            if (ObjectUtil.isNull(shopId)) {
                return new Result<List<ProductPropDTO>>().error("参数有误");
            }
        } else {
            shopId = SecurityUser.getShopId();
        }
        return new Result<List<ProductPropDTO>>().ok(productPropService.getPropAll(shopId));
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("biz:productprop:info")
    public Result<ProductPropDTO> get(@PathVariable("id") Long id, @RequestParam(required = false) Long shopId) {
        if (SecurityUser.isAdmin()) {
            if (ObjectUtil.isNull(shopId)) {
                throw new RenException("请选择店铺");
            }
        } else {
            shopId = SecurityUser.getShopId();
        }
        ProductPropDTO data = productPropService.get(id, shopId);

        return new Result<ProductPropDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("biz:productprop:save")
    public Result save(@RequestBody ProductPropDTO dto) {
        if (SecurityUser.isAdmin()) {
            if (ObjectUtil.isNull(dto.getShopId())) {
                return new Result<>().error("请选择店铺");
            }
        } else {
            dto.setShopId(SecurityUser.getShopId());
        }
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class);

        productPropService.savePropAndValues(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("biz:productprop:update")
    public Result update(@RequestBody ProductPropDTO dto) {
        if (SecurityUser.isAdmin()) {
            if (ObjectUtil.isNull(dto.getShopId())) {
                return new Result<>().error("请选择店铺");
            }
        } else {
            dto.setShopId(SecurityUser.getShopId());
        }
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class);

        productPropService.updatePropAndValues(dto);

        return new Result();
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("biz:productprop:delete")
    public Result delete(@PathVariable("id") Long id, @RequestParam(required = false) Long shopId) {

        productPropService.deleteProdPropAndValues(id, shopId);

        return new Result();
    }


}
