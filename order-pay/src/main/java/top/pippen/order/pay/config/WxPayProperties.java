package top.pippen.order.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * wxpay pay properties.
 *
 * @author Binary Wang
 */
@Data
@ConfigurationProperties(prefix = "wx.pay")
public class WxPayProperties {
    /**
     * 设置微信公众号或者小程序等的appid
     */
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * apiV3 秘钥值.
     */
    private String apiV3Key;


    /**
     * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String keyPath;
    /**
     * apiclient_key.pem证书文件的绝对路径或者以classpath:开头的类路径.
     */
    private String privateKeyPath;
    /**
     * apiclient_cert.pem证书文件的绝对路径或者以classpath:开头的类路径.
     */
    private String privateCertPath;
    /**
     * apiV3 证书序列号值
     */
    private String certSerialNo;



}
