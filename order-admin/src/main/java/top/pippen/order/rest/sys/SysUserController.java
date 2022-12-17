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
import top.pippen.order.bean.dto.sys.PasswordDTO;
import top.pippen.order.bean.dto.sys.SysUserDTO;
import top.pippen.order.bean.excel.sys.SysUserExcel;
import top.pippen.order.common.annotation.LogOperation;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.exception.ErrorCode;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.password.PasswordUtils;
import top.pippen.order.common.utils.ConvertUtils;
import top.pippen.order.common.utils.ExcelUtils;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.AssertUtils;
import top.pippen.order.common.validator.ValidatorUtils;
import top.pippen.order.common.validator.group.AddGroup;
import top.pippen.order.common.validator.group.DefaultGroup;
import top.pippen.order.common.validator.group.UpdateGroup;
import top.pippen.order.service.SysRoleUserService;
import top.pippen.order.service.SysUserService;
import top.pippen.order.shiro.admin.user.SecurityUser;
import top.pippen.order.shiro.admin.user.UserDetail;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/user")
@Api(tags="用户管理")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleUserService sysRoleUserService;

	@GetMapping("page")
	@ApiOperation("分页")
	@ApiImplicitParams({
		@ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
		@ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
		@ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
		@ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String") ,
		@ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType="String"),
		@ApiImplicitParam(name = "gender", value = "性别", paramType = "query", dataType="String"),
		@ApiImplicitParam(name = "deptId", value = "部门ID", paramType = "query", dataType="String")
	})
	@RequiresPermissions("sys:user:page")
	public Result<PageData<SysUserDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
		PageData<SysUserDTO> page = sysUserService.page(params);

		return new Result<PageData<SysUserDTO>>().ok(page);
	}

	@GetMapping("{id}")
	@ApiOperation("信息")
	@RequiresPermissions("sys:user:info")
	public Result<SysUserDTO> get(@PathVariable("id") Long id){
		SysUserDTO data = sysUserService.get(id);

		//用户角色列表
		List<Long> roleIdList = sysRoleUserService.getRoleIdList(id);
		data.setRoleIdList(roleIdList);

		return new Result<SysUserDTO>().ok(data);
	}

	@GetMapping("info")
	@ApiOperation("登录用户信息")
	public Result<SysUserDTO> info(){
		SysUserDTO data = ConvertUtils.sourceToTarget(SecurityUser.getUser(), SysUserDTO.class);
		return new Result<SysUserDTO>().ok(data);
	}

	@PutMapping("password")
	@ApiOperation("修改密码")
	@LogOperation("修改密码")
	public Result password(@RequestBody PasswordDTO dto){
		//效验数据
		ValidatorUtils.validateEntity(dto);

		UserDetail user = SecurityUser.getUser();

		//原密码不正确
		if(!PasswordUtils.matches(dto.getPassword(), user.getPassword())){
			return new Result().error(ErrorCode.PASSWORD_ERROR);
		}

		sysUserService.updatePassword(user.getId(), dto.getNewPassword());

		return new Result();
	}

	@PostMapping
	@ApiOperation("保存")
	@LogOperation("保存")
	@RequiresPermissions("sys:user:save")
	public Result save(@RequestBody SysUserDTO dto){
		//效验数据
		ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

		sysUserService.save(dto);

		return new Result();
	}

	@PutMapping
	@ApiOperation("修改")
	@LogOperation("修改")
	@RequiresPermissions("sys:user:update")
	public Result update(@RequestBody SysUserDTO dto){
		//效验数据
		ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

		sysUserService.update(dto);

		return new Result();
	}

	@DeleteMapping
	@ApiOperation("删除")
	@LogOperation("删除")
	@RequiresPermissions("sys:user:delete")
	public Result delete(@RequestBody Long[] ids){
		//效验数据
		AssertUtils.isArrayEmpty(ids, "id");

		sysUserService.deleteBatchIds(Arrays.asList(ids));

		return new Result();
	}

	@GetMapping("export")
	@ApiOperation("导出")
	@LogOperation("导出")
	@RequiresPermissions("sys:user:export")
	@ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType="String")
	public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
		List<SysUserDTO> list = sysUserService.list(params);

		ExcelUtils.exportExcelToTarget(response, null, list, SysUserExcel.class);
	}
}
