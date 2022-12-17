package top.pippen.order.api.controller;

import cn.hutool.core.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.pippen.order.api.dto.ShopListParamDTO;
import top.pippen.order.api.service.AmapService;
import top.pippen.order.bean.dto.biz.ShopDTO;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.ValidatorUtils;
import top.pippen.order.service.ShopService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Pippen
 * @since 2022/11/23 14:32
 */
@RestController
@RequestMapping("/api/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private AmapService amapService;

    @PostMapping
    public Result<List<ShopDTO>> shopList(@RequestBody ShopListParamDTO param) {
        ValidatorUtils.validateEntity(param);
        Map<String, Object> params = new HashMap<>();
        params.put("shopName", param.getShopName());
        params.put("shopStatus", "0");

        List<ShopDTO> list = shopService.list(params);
        for (ShopDTO dto : list) {
            String distance = amapService
                    .distance(dto.getShopLng() + "," + dto.getShopLat(),
                            param.getLongitude() + "," + param.getLatitude());
            dto.setKml(distance);
        }
        List<ShopDTO> collect = list.stream().sorted((s, s2) -> {
            String kml = s.getKml();
            String kml1 = s2.getKml();
            return NumberUtil.compare(Double.parseDouble(kml), Double.parseDouble(kml1));
        }).collect(Collectors.toList());

        return new Result<List<ShopDTO>>().ok(collect);
    }

    @PostMapping("/{id}")
    public Result<ShopDTO> shop(@RequestBody ShopListParamDTO param, @PathVariable Long id){
        ValidatorUtils.validateEntity(param);
        ShopDTO dto = shopService.get(id);
        String distance = amapService
                .distance(dto.getShopLng() + "," + dto.getShopLat(),
                        param.getLongitude() + "," + param.getLatitude());
        dto.setKml(distance);
        return new Result<ShopDTO>().ok(dto);
    }
}
