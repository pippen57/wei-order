package top.pippen.order.service;

import top.pippen.order.bean.dto.biz.ShopDTO;
import top.pippen.order.bean.model.biz.ShopEntity;
import top.pippen.order.common.service.CrudService;
import top.pippen.order.dao.biz.ShopDao;

import java.util.List;

/**
 * 店铺
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
public interface ShopService extends CrudService<ShopEntity, ShopDTO> {


    List<ShopDTO> findAll();

    /**
     * 获取Dao对象
     * @return SdhoDao
     */
    ShopDao getDao();

}
