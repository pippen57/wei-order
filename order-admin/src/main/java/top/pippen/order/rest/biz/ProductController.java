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
import top.pippen.order.bean.dto.biz.ProductDTO;
import top.pippen.order.bean.dto.biz.SkuDTO;
import top.pippen.order.bean.excel.biz.ProductExcel;
import top.pippen.order.common.annotation.LogOperation;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.utils.ExcelUtils;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.AssertUtils;
import top.pippen.order.common.validator.ValidatorUtils;
import top.pippen.order.common.validator.group.AddGroup;
import top.pippen.order.common.validator.group.DefaultGroup;
import top.pippen.order.common.validator.group.UpdateGroup;
import top.pippen.order.service.ProductService;
import top.pippen.order.service.SkuService;
import top.pippen.order.shiro.admin.user.SecurityUser;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 商品
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@RestController
@RequestMapping("biz/product")
@Api(tags = "商品")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private SkuService skuService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({@ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"), @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"), @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"), @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")})
    @RequiresPermissions("biz:product:page")
    public Result<PageData<ProductDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        if (SecurityUser.isAdmin()) {
            String shopId = (String) params.get("shopId");
            if (StringUtils.isBlank(shopId)) {
                return new Result<PageData<ProductDTO>>().error("参数有误");
            }
            params.put("shopId", shopId);
        } else {
            params.put("shopId", SecurityUser.getShopId() + "");
        }
        PageData<ProductDTO> page = productService.page(params);

        return new Result<PageData<ProductDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("biz:product:info")
    public Result<ProductDTO> get(@PathVariable("id") Long id, @RequestParam(required = false) Long shopId) {
        if (SecurityUser.isAdmin()) {
            if (ObjectUtil.isNull(shopId)) {
                throw new RenException("请选择店铺");
            }
        } else {
            shopId = SecurityUser.getShopId();
        }
        ProductDTO data = productService.get(id, shopId);
        if (ObjectUtil.isNotNull(data)) {
            List<SkuDTO> skuList = skuService.listByProdId(data.getId());
            data.setSkuList(skuList);
        } else {
            return new Result<ProductDTO>().error("暂无信息");
        }
        return new Result<ProductDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("biz:product:save")
    public Result save(@RequestBody ProductDTO dto) {
        if (SecurityUser.isAdmin()) {
            if (ObjectUtil.isNull(dto.getShopId())) {
                return new Result<>().error("请选择店铺");
            }
        } else {
            dto.setShopId(SecurityUser.getShopId());
        }
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
        checkParam(dto);

        productService.saveProduct(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("biz:product:update")
    public Result update(@RequestBody ProductDTO dto) {
        if (SecurityUser.isAdmin()) {
            if (ObjectUtil.isNull(dto.getShopId())) {
                return new Result<>().error("请选择店铺");
            }
        } else {
            dto.setShopId(SecurityUser.getShopId());
        }
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        checkParam(dto);

        productService.updateProduct(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("biz:product:delete")
    public Result delete(@RequestBody Long[] ids, @RequestParam(required = false) Long shopId) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");
        if (SecurityUser.isAdmin()) {
            if (ObjectUtil.isNull(shopId)) {
                throw new RenException("请选择店铺");
            }
        } else {
            shopId = SecurityUser.getShopId();
        }
        productService.delProduct(ids, shopId);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("biz:product:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<ProductDTO> list = productService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, ProductExcel.class);
    }


    private void checkParam(ProductDTO dto) {

        List<SkuDTO> skuList = dto.getSkuList();
        boolean isAllUnUse = true;
        for (SkuDTO sku : skuList) {
            if (sku.getStatus() == 1) {
                isAllUnUse = false;
            }
        }
        if (isAllUnUse) {
            throw new RenException("至少要启用一种商品规格");
        }
    }
}
