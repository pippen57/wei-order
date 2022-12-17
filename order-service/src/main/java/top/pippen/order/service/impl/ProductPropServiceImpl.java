package top.pippen.order.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pippen.order.bean.dto.biz.ProductPropDTO;
import top.pippen.order.bean.dto.biz.ProductPropValueDTO;
import top.pippen.order.bean.model.biz.ProductPropEntity;
import top.pippen.order.common.exception.ErrorCode;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.common.page.PageData;
import top.pippen.order.common.service.impl.CrudServiceImpl;
import top.pippen.order.common.utils.ConvertUtils;
import top.pippen.order.dao.biz.ProductPropDao;
import top.pippen.order.service.ProductPropService;
import top.pippen.order.service.ProductPropValueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规格
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Service
@RequiredArgsConstructor
public class ProductPropServiceImpl extends CrudServiceImpl<ProductPropDao, ProductPropEntity, ProductPropDTO> implements ProductPropService {

    private final ProductPropValueService productPropValueService;

    @Override
    public QueryWrapper<ProductPropEntity> getWrapper(Map<String, Object> params) {
        String propName = (String) params.get("propName");
        String shopId = (String) (params.get("shopId"));
        QueryWrapper<ProductPropEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(propName), "prop_name", propName);
        wrapper.eq(StringUtils.isNotBlank(shopId), "shop_id", shopId);
        return wrapper;
    }

    @Override
    public PageData<ProductPropDTO> page(Map<String, Object> params) {
        PageData<ProductPropDTO> page = super.page(params);
        List<ProductPropDTO> list = page.getList();
        HashMap<String, Object> param = new HashMap<>(1);
        for (ProductPropDTO productPropDTO : list) {
            Long id = productPropDTO.getId();
            param.put("propId", id + "");
            List<ProductPropValueDTO> propValues = productPropValueService.list(param);
            productPropDTO.setPropValue(propValues);
        }
        page.setList(list);
        return page;
    }

    @Override
    public List<ProductPropDTO> getPropAll(Long shopId) {
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("shopId", shopId + "");
        List<ProductPropDTO> list = list(objectObjectHashMap);
        HashMap<String, Object> param = new HashMap<>(1);
        for (ProductPropDTO productPropDTO : list) {
            Long id = productPropDTO.getId();
            param.put("propId", id + "");
            List<ProductPropValueDTO> propValues = productPropValueService.list(param);
            productPropDTO.setPropValue(propValues);
        }
        return list;
    }


    @Override
    public ProductPropDTO get(Long id, Long shopId) {

        ProductPropEntity productPropEntity = baseDao.selectOne(Wrappers.<ProductPropEntity>lambdaQuery()
                .eq(ProductPropEntity::getId, id).eq(ProductPropEntity::getShopId, shopId));
        if (ObjectUtil.isNull(productPropEntity)) {
            throw new RenException("数据不存在");
        }
        ProductPropDTO productPropDTO = ConvertUtils.sourceToTarget(productPropEntity, currentDtoClass());
        HashMap<String, Object> param = new HashMap<>(1);
        param.put("propId", id + "");
        List<ProductPropValueDTO> propValues = productPropValueService.list(param);
        productPropDTO.setPropValue(propValues);
        return productPropDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePropAndValues(ProductPropDTO propDTO) {
        ProductPropEntity daoProductProp = baseDao.getByPropNameAndShopId(propDTO.getPropName(), propDTO.getShopId());
        if (daoProductProp != null) {
            throw new RenException(ErrorCode.DB_RECORD_EXISTS);
        }
        save(propDTO);
        if (CollectionUtil.isEmpty(propDTO.getPropValue())) {
            return;
        }
        Long propId = propDTO.getId();

        productPropValueService.savePropValue(propDTO.getPropValue(), propId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePropAndValues(ProductPropDTO propDTO) {
        Long id = propDTO.getId();
        if (id == null) {
            throw new RenException(ErrorCode.PARAMS_GET_ERROR);
        }
        update(propDTO);
        productPropValueService.deletePropValue(id);
        if (CollectionUtil.isEmpty(propDTO.getPropValue())) {
            return;
        }
        productPropValueService.savePropValue(propDTO.getPropValue(), id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProdPropAndValues(Long propId, Long shopId) {
        int deleteRows = baseDao.deleteByPropId(propId, shopId);
        if (deleteRows == 0) {
            return;
        }
        productPropValueService.deletePropValue(propId);
    }
}
