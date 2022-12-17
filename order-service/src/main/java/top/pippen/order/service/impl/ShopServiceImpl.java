package top.pippen.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.pippen.order.bean.dto.biz.ShopDTO;
import top.pippen.order.bean.model.biz.ShopEntity;
import top.pippen.order.common.service.impl.CrudServiceImpl;
import top.pippen.order.common.utils.ConvertUtils;
import top.pippen.order.dao.biz.ShopDao;
import top.pippen.order.service.ShopService;

import java.util.List;
import java.util.Map;

/**
 * 店铺
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
@Service
public class ShopServiceImpl extends CrudServiceImpl<ShopDao, ShopEntity, ShopDTO> implements ShopService {

    @Override
    public QueryWrapper<ShopEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("shopId");
        String shopName = (String) params.get("shopName");
        String shopStatus = (String) params.get("shopStatus");

        QueryWrapper<ShopEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.like(StringUtils.isNotBlank(shopName), "shop_name", shopName);
        wrapper.in(StringUtils.isNotBlank(shopStatus)&&StringUtils.equals("0",shopStatus),"shop_status",0,1);

        return wrapper;
    }

    @Override
    public List<ShopDTO> findAll() {
        List<ShopEntity> shopEntities = baseDao.selectList(Wrappers.lambdaQuery());
        return ConvertUtils.sourceToTarget(shopEntities, currentDtoClass());
    }

    @Override
    public ShopDao getDao() {
        return baseDao;
    }


}
