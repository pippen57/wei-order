package top.pippen.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.pippen.order.bean.dto.biz.CategoryDTO;
import top.pippen.order.bean.dto.biz.CategoryTreeDTO;
import top.pippen.order.bean.model.biz.CategoryEntity;
import top.pippen.order.common.service.impl.CrudServiceImpl;
import top.pippen.order.common.utils.ConvertUtils;
import top.pippen.order.common.utils.TreeUtils;
import top.pippen.order.dao.biz.CategoryDao;
import top.pippen.order.service.CategoryService;

import java.util.List;
import java.util.Map;

/**
 * 商品分类
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
@Service
public class CategoryServiceImpl extends CrudServiceImpl<CategoryDao, CategoryEntity, CategoryDTO> implements CategoryService {

    @Override
    public QueryWrapper<CategoryEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String categoryName = (String) params.get("categoryName");
        String status = (String) params.get("status");
        String shopId = (String) (params.get("shopId"));

        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.eq(StringUtils.isNotBlank(categoryName), "category_name", categoryName);
        wrapper.eq(StringUtils.isNotBlank(status), "status", status);
        wrapper.eq(StringUtils.isNotBlank(shopId), "shop_id", shopId);
        wrapper.orderByDesc("seq");

        return wrapper;
    }

    @Override
    public List<CategoryTreeDTO> tree(Map<String, Object> params) {
        params.put("status", 1+"");
        List<CategoryDTO> list = super.list(params);
        List<CategoryTreeDTO> categoryTree = ConvertUtils.sourceToTarget(list, CategoryTreeDTO.class);
        return TreeUtils.build(categoryTree);
    }

    @Override
    public CategoryDTO get(Long id, Long shopId) {

        CategoryEntity categoryEntity = baseDao.selectOne(Wrappers.<CategoryEntity>lambdaQuery().eq(CategoryEntity::getId, id)
                .eq(CategoryEntity::getShopId, shopId));

        CategoryDTO categoryDTO = ConvertUtils.sourceToTarget(categoryEntity, currentDtoClass());
        if (categoryEntity == null) {
            categoryDTO = new CategoryDTO();
            categoryDTO.setParentName("顶级分类");
            return categoryDTO;
        }
        Long pid = categoryDTO.getPid();
        if (pid != null && pid != 0L) {
            CategoryEntity categoryEntityP = baseDao.selectById(pid);
            categoryDTO.setParentName(categoryEntityP.getCategoryName());
        } else {
            categoryDTO.setParentName("顶级分类");
        }
        return categoryDTO;
    }

    @Override
    public void deleteById(Long[] ids, Long shopId) {

        for (Long id : ids) {
            baseDao.delete(Wrappers.<CategoryEntity>lambdaUpdate().eq(CategoryEntity::getId, id).eq(CategoryEntity::getShopId, shopId));
        }

    }
}
