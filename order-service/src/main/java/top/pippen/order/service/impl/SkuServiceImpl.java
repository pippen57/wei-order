package top.pippen.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.pippen.order.bean.dto.biz.SkuDTO;
import top.pippen.order.bean.model.biz.SkuEntity;
import top.pippen.order.common.service.impl.CrudServiceImpl;
import top.pippen.order.dao.biz.SkuDao;
import top.pippen.order.service.SkuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SKU
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-11
 */
@Service
public class SkuServiceImpl extends CrudServiceImpl<SkuDao, SkuEntity, SkuDTO> implements SkuService {

    @Override
    public QueryWrapper<SkuEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String productId = (String) params.get("productId");
        String status = (String) params.get("status");

        QueryWrapper<SkuEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.eq(StringUtils.isNotBlank(status), "status", status);
        wrapper.eq(StringUtils.isNotBlank(productId), "product_id", productId);

        return wrapper;
    }

    @Override
    public void saveSku(List<SkuDTO> skuList, Long productId) {
        for (SkuDTO sku : skuList) {
            if (StringUtils.isNotBlank(sku.getProperties())){
                sku.setProductId(productId);
                save(sku);
            }
        }
    }

    @Override
    public List<SkuDTO> listByProdId(Long productId) {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId + "");
        return list(params);
    }

    @Override
    public void deleteSku(Long[] productIds) {
        if (productIds.length > 0) {
            baseDao.delete(Wrappers.<SkuEntity>lambdaUpdate().in(SkuEntity::getProductId, productIds));
        }

    }
}
