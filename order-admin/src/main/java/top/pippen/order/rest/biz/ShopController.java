package top.pippen.order.rest.biz;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.pippen.order.bean.dto.biz.ShopDTO;
import top.pippen.order.bean.excel.biz.ShopExcel;
import top.pippen.order.common.annotation.LogOperation;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.exception.ErrorCode;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.redis.RedisKeys;
import top.pippen.order.common.redis.RedisUtils;
import top.pippen.order.common.utils.ExcelUtils;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.AssertUtils;
import top.pippen.order.common.validator.ValidatorUtils;
import top.pippen.order.common.validator.group.AddGroup;
import top.pippen.order.common.validator.group.UpdateGroup;
import top.pippen.order.service.ShopService;
import top.pippen.order.shiro.admin.user.SecurityUser;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 店铺
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
@RestController
@RequestMapping("biz/shop")
@Api(tags = "店铺")
public class ShopController {
    @Autowired
    private ShopService bizShopService;

    @Autowired
    private RedisUtils redisUtils;
    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({@ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"), @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"), @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"), @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")})
    @RequiresPermissions("biz:shop:page")
    public Result<PageData<ShopDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        if (SecurityUser.isAdmin()) {
            params.put("shopId", null);
        } else {
            params.put("shopId", SecurityUser.getShopId()+"");
        }

        PageData<ShopDTO> page = bizShopService.page(params);

        return new Result<PageData<ShopDTO>>().ok(page);
    }

    @GetMapping("all")
    public Result<List<ShopDTO>> findAll() {
        if (SecurityUser.isAdmin()) {
            return new Result<List<ShopDTO>>().ok(bizShopService.findAll());
        } else {
            Long shopId = SecurityUser.getShopId();
            ShopDTO shopDTO = bizShopService.get(shopId);
            List<ShopDTO> list = new ArrayList<>();
            list.add(shopDTO);
            return new Result<List<ShopDTO>>().ok(list);
        }
    }
    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("biz:shop:info")
    public Result<ShopDTO> get(@PathVariable("id") Long id) {
        ShopDTO data = bizShopService.get(id);

        return new Result<ShopDTO>().ok(data);
    }

    @PutMapping("/shop_status")
    public Result<ShopDTO> updateStatus(@RequestParam Long shopId) {
        ShopDTO shopDTO = bizShopService.get(shopId);
        if (ObjectUtil.isNotNull(shopDTO)) {
            Integer shopStatus = shopDTO.getShopStatus();
            if (shopStatus == 0) {
                shopDTO.setShopStatus(1);
                bizShopService.update(shopDTO);
            } else if (shopStatus == 1) {
                // 清空店铺叫号
                redisUtils.delete(RedisKeys.getMealNumberKey(shopId));
                // 设置状态为休息中
                shopDTO.setShopStatus(0);
                bizShopService.update(shopDTO);
            }
            return new Result<ShopDTO>().ok(shopDTO);
        }
        return new Result<ShopDTO>().error("店铺状态更新失败");
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("biz:shop:save")
    public Result save(@RequestBody ShopDTO dto) {
        if (!SecurityUser.isAdmin()) {
            return new Result().error(ErrorCode.UNAUTHORIZED, "没有权限进行该操作");
        }
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class);

        bizShopService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("biz:shop:update")
    public Result update(@RequestBody ShopDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class);
        if (!SecurityUser.isAdmin()) {
            Long id = dto.getId();
            Long shopId = SecurityUser.getShopId();
            if (!id.equals(shopId)) {
                throw new RenException("当前店铺您不能修改");
            }
        }
        bizShopService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("biz:shop:delete")
    public Result delete(@RequestBody Long[] ids) {
        if (!SecurityUser.isAdmin()) {
            return new Result().error(ErrorCode.UNAUTHORIZED, "没有权限进行该操作");
        }
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        bizShopService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("biz:shop:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<ShopDTO> list = bizShopService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, ShopExcel.class);
    }

}
