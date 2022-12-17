package top.pippen.order.service;

import top.pippen.order.bean.dto.biz.CategoryDTO;
import top.pippen.order.bean.dto.biz.CategoryTreeDTO;
import top.pippen.order.bean.model.biz.CategoryEntity;
import top.pippen.order.common.service.CrudService;

import java.util.List;
import java.util.Map;

/**
 * 商品分类
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
public interface CategoryService extends CrudService<CategoryEntity, CategoryDTO> {

    /**
     * 分类树
     *
     * @param params /
     * @return /
     */
    List<CategoryTreeDTO> tree(Map<String, Object> params);

    CategoryDTO get(Long id, Long shopId);

    /**
     * 删除分类
     * @param ids
     * @param shopId
     */
    void deleteById(Long[] ids, Long shopId);
}
