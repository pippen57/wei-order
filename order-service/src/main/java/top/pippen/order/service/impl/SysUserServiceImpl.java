/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pippen.order.bean.dto.sys.SysUserDTO;
import top.pippen.order.bean.enums.SuperAdminEnum;
import top.pippen.order.bean.model.sys.SysUserEntity;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.password.PasswordUtils;
import top.pippen.order.common.service.impl.BaseServiceImpl;
import top.pippen.order.common.utils.ConvertUtils;
import top.pippen.order.dao.sys.SysUserDao;
import top.pippen.order.service.SysRoleUserService;
import top.pippen.order.service.SysUserService;
import top.pippen.order.shiro.admin.user.SecurityUser;
import top.pippen.order.shiro.admin.user.UserDetail;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysRoleUserService sysRoleUserService;

	@Override
	public PageData<SysUserDTO> page(Map<String, Object> params) {
		//转换成like
		paramsToLike(params, "username");

		//分页
		IPage<SysUserEntity> page = getPage(params, Constant.CREATE_DATE, false);

		//普通管理员，只能查询所属部门及子部门的数据
		UserDetail user = SecurityUser.getUser();
//		if(user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
//			params.put("deptIdList", sysDeptService.getSubDeptIdList(user.getDeptId()));
//		}

		//查询
		List<SysUserEntity> list = baseDao.getList(params);

		return getPageData(list, page.getTotal(), SysUserDTO.class);
	}

	@Override
	public List<SysUserDTO> list(Map<String, Object> params) {
		//普通管理员，只能查询所属部门及子部门的数据
		UserDetail user = SecurityUser.getUser();
//		if(user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
//			params.put("deptIdList", sysDeptService.getSubDeptIdList(user.getDeptId()));
//		}

		List<SysUserEntity> entityList = baseDao.getList(params);

		return ConvertUtils.sourceToTarget(entityList, SysUserDTO.class);
	}

	@Override
	public SysUserDTO get(Long id) {
		SysUserEntity entity = baseDao.getById(id);

		return ConvertUtils.sourceToTarget(entity, SysUserDTO.class);
	}

	@Override
	public SysUserDTO getByUsername(String username) {
		SysUserEntity entity = baseDao.getByUsername(username);
		return ConvertUtils.sourceToTarget(entity, SysUserDTO.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysUserDTO dto) {
		SysUserEntity entity = ConvertUtils.sourceToTarget(dto, SysUserEntity.class);

		//密码加密
		String password = PasswordUtils.encode(entity.getPassword());
		entity.setPassword(password);

		//保存用户
		entity.setSuperAdmin(SuperAdminEnum.NO.value());
		insert(entity);

		//保存角色用户关系
		sysRoleUserService.saveOrUpdate(entity.getId(), dto.getRoleIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysUserDTO dto) {
		SysUserEntity entity = ConvertUtils.sourceToTarget(dto, SysUserEntity.class);

		//密码加密
		if(StringUtils.isBlank(dto.getPassword())){
			entity.setPassword(null);
		}else{
			String password = PasswordUtils.encode(entity.getPassword());
			entity.setPassword(password);
		}

		//更新用户
		updateById(entity);

		//更新角色用户关系
		sysRoleUserService.saveOrUpdate(entity.getId(), dto.getRoleIdList());
	}

	@Override
	public void delete(Long[] ids) {
		//删除用户
		baseDao.deleteBatchIds(Arrays.asList(ids));

		//删除角色用户关系
		sysRoleUserService.deleteByUserIds(ids);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updatePassword(Long id, String newPassword) {
		newPassword = PasswordUtils.encode(newPassword);

		baseDao.updatePassword(id, newPassword);
	}

	@Override
	public int getCountByDeptId(Long deptId) {
		return baseDao.getCountByDeptId(deptId);
	}


}
