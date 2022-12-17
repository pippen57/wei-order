package top.pippen.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.pippen.order.bean.dto.biz.OrderStatementDTO;
import top.pippen.order.bean.model.biz.OrderStatementEntity;
import top.pippen.order.common.service.impl.CrudServiceImpl;
import top.pippen.order.dao.biz.OrderStatementDao;
import top.pippen.order.service.OrderStatementService;

import java.util.Map;

/**
 * 支付清算
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@Service
public class OrderStatementServiceImpl extends CrudServiceImpl<OrderStatementDao, OrderStatementEntity, OrderStatementDTO> implements OrderStatementService {

    @Override
    public QueryWrapper<OrderStatementEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<OrderStatementEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public OrderStatementDao getDao() {
        return baseDao;
    }
}
