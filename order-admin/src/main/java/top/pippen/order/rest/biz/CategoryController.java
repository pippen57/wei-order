package top.pippen.order.rest.biz;

import cn.hutool.core.collection.CollectionUtil;
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
import top.pippen.order.bean.dto.biz.CategoryDTO;
import top.pippen.order.bean.dto.biz.CategoryTreeDTO;
import top.pippen.order.bean.dto.biz.ProductDTO;
import top.pippen.order.common.annotation.LogOperation;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.AssertUtils;
import top.pippen.order.common.validator.ValidatorUtils;
import top.pippen.order.common.validator.group.AddGroup;
import top.pippen.order.common.validator.group.DefaultGroup;
import top.pippen.order.common.validator.group.UpdateGroup;
import top.pippen.order.service.CategoryService;
import top.pippen.order.service.ProductService;
import top.pippen.order.shiro.admin.user.SecurityUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品分类
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
@RestController
@RequestMapping("biz/category")
@Api(tags = "商品分类")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({@ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"), @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"), @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"), @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")})
    @RequiresPermissions("biz:category:page")
    public Result<PageData<CategoryDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        if (SecurityUser.isAdmin()) {
            String shopId = (String) params.get("shopId");
            if (StringUtils.isBlank(shopId)) {
                return new Result<PageData<CategoryDTO>>().error("参数有误");
            }
            params.put("shopId", shopId);
        } else {
            params.put("shopId", SecurityUser.getShopId() + "");
        }

        PageData<CategoryDTO> page = categoryService.page(params);

        return new Result<PageData<CategoryDTO>>().ok(page);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("sys:category:list")
    public Result<List<CategoryTreeDTO>> list(@RequestParam(required = false) String shopId) {
        HashMap<String, Object> objectObjectHashMap = new HashMap<>(2);
        if (SecurityUser.isAdmin()) {
            if (StringUtils.isBlank(shopId)) {
                return new Result<List<CategoryTreeDTO>>().error("请选择商铺");
            }
        } else {
            objectObjectHashMap.put("shopId", SecurityUser.getShopId() + "");
        }
        List<CategoryTreeDTO> tree = categoryService.tree(objectObjectHashMap);
        return new Result<List<CategoryTreeDTO>>().ok(tree);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("biz:category:info")
    public Result<CategoryDTO> get(@PathVariable("id") Long id, @RequestParam(required = false) Long shopId) {
        if (SecurityUser.isAdmin()) {
            if (ObjectUtil.isNull(shopId)) {
                throw new RenException("请选择店铺");
            }
        } else {
            shopId = SecurityUser.getShopId();
        }
        CategoryDTO data = categoryService.get(id, shopId);

        return new Result<CategoryDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("biz:category:save")
    public Result save(@RequestBody CategoryDTO dto) {
        if (SecurityUser.isAdmin()) {
            if (ObjectUtil.isNull(dto.getShopId())) {
                return new Result<>().error("请选择商铺");
            }
        } else {
            dto.setShopId(SecurityUser.getShopId());
        }

        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        categoryService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("biz:category:update")
    public Result update(@RequestBody CategoryDTO dto) {
        if (SecurityUser.isAdmin()) {
            if (ObjectUtil.isNull(dto.getShopId())) {
                return new Result<>().error("请选择商铺");
            }
        } else {
            dto.setShopId(SecurityUser.getShopId());
        }
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        categoryService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("biz:category:delete")
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
        HashMap<String, Object> map = new HashMap<>();
        map.put("shopId", shopId);
        map.put("categoryIds", ids);
        List<ProductDTO> list = productService.list(map);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new RenException("存在关联商品,无法删除");
        }
        categoryService.deleteById(ids, shopId);

        return new Result();
    }


}
