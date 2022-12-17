package top.pippen.order.api.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.pippen.order.api.config.AmapProperties;
import top.pippen.order.api.service.AmapService;

/**
 * @author Pippen
 * @since 2022/11/28 19:53
 */
@Service
public class AmapServiceImpl implements AmapService {

    @Autowired
    private AmapProperties amapProperties;

    private final String DISTANCE_URL = "https://restapi.amap.com/v3/distance?" +
            "key={}&origins={}&destination={}&type=1";

    @Override
    public String distance(String origins, String destination){
        String url = StrUtil.format(DISTANCE_URL, amapProperties.getKey(), origins, destination);
        String body = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(body);
        if ("1".equals(jsonObject.getStr("status"))){
            JSONArray results = jsonObject.getJSONArray("results");
            JSONObject parseObj = JSONUtil.parseObj(results.get(0));
            return parseObj.getStr("distance");
        }
        return "0";
    }
}
