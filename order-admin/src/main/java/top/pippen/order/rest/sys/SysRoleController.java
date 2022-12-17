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
import top.pippen.order.bean.dto.sys.SysRoleDTO;
import top.pippen.order.common.annotation.LogOperation;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.AssertUtils;
import top.pippen.order.common.validator.ValidatorUtils;
import top.pippen.order.common.validator.group.AddGroup;
import top.pippen.order.common.validator.group.DefaultGroup;
import top.pippen.order.common.validator.group.UpdateGroup;
import top.pippen.order.service.SysRoleMenuService;
import top.pippen.order.service.SysRoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/role")
@Api(tags="角色管理")
public class SysRoleController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@GetMapping("page")
	@ApiOperation("分页")
	@ApiImplicitParams({
		@ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
		@ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
		@ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
		@ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String") ,
		@ApiImplicitParam(name = "name", value = "角色名", paramType = "query", dataType="String")
	})
	@RequiresPermissions("sys:role:page")
	public Result<PageData<SysRoleDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
		PageData<SysRoleDTO> page = sysRoleService.page(params);

		return new Result<PageData<SysRoleDTO>>().ok(page);
	}

	@GetMapping("list")
	@ApiOperation("列表")
	@RequiresPermissions("sys:role:list")
	public Result<List<SysRoleDTO>> list(){
		List<SysRoleDTO> data = sysRoleService.list(new HashMap<>(1));

		return new Result<List<SysRoleDTO>>().ok(data);
	}

	@GetMapping("{id}")
	@ApiOperation("信息")
	@RequiresPermissions("sys:role:info")
	public Result<SysRoleDTO> get(@PathVariable("id") Long id){
		SysRoleDTO data = sysRoleService.get(id);

		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.getMenuIdList(id);
		data.setMenuIdList(menuIdList);

		//查询角色对应的数据权限

		return new Result<SysRoleDTO>().ok(data);
	}

	@PostMapping
	@ApiOperation("保存")
	@LogOperation("保存")
	@RequiresPermissions("sys:role:save")
	public Result save(@RequestBody SysRoleDTO dto){
		//效验数据
		ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

		sysRoleService.save(dto);

		return new Result();
	}

	@PutMapping
	@ApiOperation("修改")
	@LogOperation("修改")
	@RequiresPermissions("sys:role:update")
	public Result update(@RequestBody SysRoleDTO dto){
		//效验数据
		ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

		sysRoleService.update(dto);

		return new Result();
	}

	@DeleteMapping
	@ApiOperation("删除")
	@LogOperation("删除")
	@RequiresPermissions("sys:role:delete")
	public Result delete(@RequestBody Long[] ids){
		//效验数据
		AssertUtils.isArrayEmpty(ids, "id");

		sysRoleService.delete(ids);

		return new Result();
	}
}
