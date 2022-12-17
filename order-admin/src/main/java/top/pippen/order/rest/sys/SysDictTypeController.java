/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.rest.sys;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.pippen.order.bean.dto.sys.SysDictTypeDTO;
import top.pippen.order.bean.model.sys.DictType;
import top.pippen.order.common.annotation.LogOperation;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.AssertUtils;
import top.pippen.order.common.validator.ValidatorUtils;
import top.pippen.order.common.validator.group.DefaultGroup;
import top.pippen.order.common.validator.group.UpdateGroup;
import top.pippen.order.service.SysDictTypeService;

import java.util.List;
import java.util.Map;

/**
 * 字典类型
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("sys/dict/type")
@Api(tags="字典类型")
public class SysDictTypeController {
    @Autowired
    private SysDictTypeService sysDictTypeService;

    @GetMapping("page")
    @ApiOperation("字典类型")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = "dictType", value = "字典类型", paramType = "query", dataType="String"),
        @ApiImplicitParam(name = "dictName", value = "字典名称", paramType = "query", dataType="String")
    })
    @RequiresPermissions("sys:dict:page")
    public Result<PageData<SysDictTypeDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        //字典类型
        PageData<SysDictTypeDTO> page = sysDictTypeService.page(params);

        return new Result<PageData<SysDictTypeDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("sys:dict:info")
    public Result<SysDictTypeDTO> get(@PathVariable("id") Long id){
        SysDictTypeDTO data = sysDictTypeService.get(id);

        return new Result<SysDictTypeDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:dict:save")
    public Result save(@RequestBody SysDictTypeDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, DefaultGroup.class);

        sysDictTypeService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:dict:update")
    public Result update(@RequestBody SysDictTypeDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        sysDictTypeService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:dict:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        sysDictTypeService.delete(ids);

        return new Result();
    }

    @GetMapping("all")
    @ApiOperation("所有字典数据")
    public Result<List<DictType>> all(){
        List<DictType> list = sysDictTypeService.getAllList();

        return new Result<List<DictType>>().ok(list);
    }

}
