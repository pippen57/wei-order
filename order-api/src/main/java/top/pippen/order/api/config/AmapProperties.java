package top.pippen.order.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 高德地图配置
 * @author Pippen
 * @since 2022/11/28 19:51
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "amap")
public class AmapProperties {
    private String key;
}
