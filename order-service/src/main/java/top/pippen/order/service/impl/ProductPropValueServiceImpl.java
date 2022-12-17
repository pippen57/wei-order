package top.pippen.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.pippen.order.bean.dto.biz.ProductPropValueDTO;
import top.pippen.order.bean.model.biz.ProductPropValueEntity;
import top.pippen.order.common.service.impl.CrudServiceImpl;
import top.pippen.order.dao.biz.ProductPropValueDao;
import top.pippen.order.service.ProductPropValueService;

import java.util.List;
import java.util.Map;

/**
 * 规格值
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Service
public class ProductPropValueServiceImpl extends CrudServiceImpl<ProductPropValueDao, ProductPropValueEntity, ProductPropValueDTO> implements ProductPropValueService {

    @Override
    public QueryWrapper<ProductPropValueEntity> getWrapper(Map<String, Object> params) {
        String propId = (String) params.get("propId");

        QueryWrapper<ProductPropValueEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(propId ), "prop_id", propId);
        return wrapper;
    }


    @Override
    public void savePropValue(List<ProductPropValueDTO> propValue, Long propId) {
        for (ProductPropValueDTO values : propValue) {
            values.setPropId(propId);
            save(values);
        }
    }

    @Override
    public void deletePropValue(Long propId) {
        baseDao.deleteByPropId(propId);
    }
}
