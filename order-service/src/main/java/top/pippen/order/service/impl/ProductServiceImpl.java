package top.pippen.order.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pippen.order.bean.dto.biz.APIProductDTO;
import top.pippen.order.bean.dto.biz.CategoryDTO;
import top.pippen.order.bean.dto.biz.ProductDTO;
import top.pippen.order.bean.dto.biz.SkuDTO;
import top.pippen.order.bean.model.biz.ProductEntity;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.common.service.impl.CrudServiceImpl;
import top.pippen.order.common.utils.ConvertUtils;
import top.pippen.order.dao.biz.ProductDao;
import top.pippen.order.service.CategoryService;
import top.pippen.order.service.ProductService;
import top.pippen.order.service.SkuService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Service
public class ProductServiceImpl extends CrudServiceImpl<ProductDao, ProductEntity, ProductDTO> implements ProductService {

    private final CategoryService categoryService;
    private final SkuService skuService;

    public ProductServiceImpl(CategoryService categoryService, SkuService skuService) {
        this.categoryService = categoryService;
        this.skuService = skuService;
    }

    @Override
    public QueryWrapper<ProductEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String categoryId = (String) (params.get("categoryId"));
        Long[] categoryIds = (Long[]) params.get("categoryIds");
        String shopId = (String) (params.get("shopId"));

        QueryWrapper<ProductEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.in(categoryIds != null && categoryIds.length > 0, "category_id", categoryIds);
        wrapper.eq(StringUtils.isNotBlank(categoryId), "category_id", categoryId);
        wrapper.eq(StringUtils.isNotBlank(shopId), "shop_id", shopId);
        wrapper.eq("status", Constant.SUCCESS);
        return wrapper;
    }

    @Override
    public ProductDTO get(Long id, Long shopId) {

        ProductEntity productEntity = baseDao.selectOne(Wrappers.<ProductEntity>lambdaQuery().eq(ProductEntity::getId, id).eq(ProductEntity::getShopId, shopId));
        if (ObjectUtil.isNull(productEntity)) {
            throw new RenException("数据不存在");
        }
        ProductDTO productDTO = ConvertUtils.sourceToTarget(productEntity, currentDtoClass());
        Long categoryId = productDTO.getCategoryId();
        CategoryDTO categoryDTO = categoryService.get(categoryId);
        productDTO.setCategoryName(categoryDTO.getCategoryName());
        return productDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProduct(ProductDTO productDTO) {
        save(productDTO);
        if (CollectionUtil.isNotEmpty(productDTO.getSkuList())) {
            skuService.saveSku(productDTO.getSkuList(), productDTO.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(ProductDTO productDTO) {
        update(productDTO);
        // 删除所有SKU
        Long[] along = new Long[1];
        along[0] = productDTO.getId();
        skuService.deleteSku(along);
        if (CollectionUtil.isNotEmpty(productDTO.getSkuList())) {
            skuService.saveSku(productDTO.getSkuList(), productDTO.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delProduct(Long[] id, Long shopId) {

        baseDao.delete(Wrappers.<ProductEntity>lambdaUpdate().eq(ProductEntity::getShopId, shopId).in(ProductEntity::getId, id));
        skuService.deleteSku(id);
    }

    @Override
    public List<ProductDTO> getProduct(Long shopId, String categoryId) {
        HashMap<String, Object> map = new HashMap<>();
        Map<String, Object> paramsSku = new HashMap<>();

        map.put("categoryId", categoryId);
        map.put("shopId", shopId + "");
        List<ProductDTO> list = list(map);
        List<ProductDTO> productBeans = new ArrayList<>();
        for (ProductDTO dto : list) {
            paramsSku.put("productId", dto.getId() + "");
            paramsSku.put("status", Constant.SUCCESS + "");
            List<SkuDTO> skuList = skuService.list(paramsSku);
            dto.setSkuList(skuList);
            productBeans.add(dto);
        }


        return productBeans;
    }

    @Override
    public List<APIProductDTO> getProduct(Long shopId) {
        HashMap<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("shopId", shopId + "");
        categoryMap.put("status", Constant.SUCCESS + "");
        List<CategoryDTO> category = categoryService.list(categoryMap);
        List<APIProductDTO> apiProduct = new ArrayList<>();
        Map<String, Object> paramsSku = new HashMap<>();

        for (CategoryDTO entity : category) {
            Long id = entity.getId();
            String categoryName = entity.getCategoryName();
            APIProductDTO productDTO = new APIProductDTO();
            productDTO.setCategoryName(categoryName);
            productDTO.setCategoryId(id);
            HashMap<String, Object> map = new HashMap<>();
            map.put("categoryId", id + "");
            List<ProductDTO> list = list(map);
            for (ProductDTO dto : list) {
                APIProductDTO.ProductBean productBean = new APIProductDTO.ProductBean();
                productBean.setId(dto.getId());
                productBean.setProductName(dto.getProductName());
                productBean.setPic(dto.getPic());
                productBean.setPrice(dto.getPrice());
                productBean.setSoldNum(dto.getSoldNum());
                paramsSku.put("productId", dto.getId() + "");
                paramsSku.put("status", Constant.SUCCESS + "");
                List<SkuDTO> skuList = skuService.list(paramsSku);
                productBean.setSkuList(skuList);
                productDTO.getProductList().add(productBean);
            }
            apiProduct.add(productDTO);

        }
        return apiProduct;
    }
}
