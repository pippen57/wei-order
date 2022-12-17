package top.pippen.order.dao.biz;

import org.apache.ibatis.annotations.Mapper;
import top.pippen.order.bean.model.biz.CategoryEntity;
import top.pippen.order.common.dao.BaseDao;

/**
 * 商品分类
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
@Mapper
public interface CategoryDao extends BaseDao<CategoryEntity> {

}
