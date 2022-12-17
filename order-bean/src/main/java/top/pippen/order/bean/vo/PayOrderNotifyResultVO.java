package top.pippen.order.bean.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Pippen
 * @since 2022/11/08 14:24
 */
@Data
public class PayOrderNotifyResultVO {



    private String appid;

    /**
     * <pre>
     * 字段名：商户号
     * 变量名：mchid
     * 是否必填：是
     * 类型：string[1,32]
     * 描述：
     *  商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一。
     *  特殊规则：最小字符长度为6
     *  示例值：1217752501201407033233368018
     * </pre>
     */
    private String mchid;
    /**
     * <pre>
     * 字段名：商户订单号
     * 变量名：out_trade_no
     * 是否必填：是
     * 类型：string[6,32]
     * 描述：
     *  商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一。
     *  特殊规则：最小字符长度为6
     *  示例值：1217752501201407033233368018
     * </pre>
     */
    private String outTradeNo;
    /**
     * <pre>
     * 字段名：微信支付订单号
     * 变量名：transaction_id
     * 是否必填：是
     * 类型：string[1,32]
     * 描述：
     *  微信支付系统生成的订单号。
     *  示例值：1217752501201407033233368018
     * </pre>
     */
    private String transactionId;
    /**
     * <pre>
     * 字段名：交易类型
     * 变量名：trade_type
     * 是否必填：是
     * 类型：string[1,16]
     * 描述：
     *  交易类型，枚举值：
     *  JSAPI：公众号支付
     *  NATIVE：扫码支付
     *  APP：APP支付
     *  MICROPAY：付款码支付
     *  MWEB：H5支付
     *  FACEPAY：刷脸支付
     *  示例值：MICROPAY
     * </pre>
     */
    private String tradeType;
    /**
     * <pre>
     * 字段名：交易状态
     * 变量名：trade_state
     * 是否必填：是
     * 类型：string[1,32]
     * 描述：
     *  交易状态，枚举值：
     *  SUCCESS：支付成功
     *  REFUND：转入退款
     *  NOTPAY：未支付
     *  CLOSED：已关闭
     *  REVOKED：已撤销（付款码支付）
     *  USERPAYING：用户支付中（付款码支付）
     *  PAYERROR：支付失败(其他原因，如银行返回失败)
     *  示例值：SUCCESS
     * </pre>
     */
    private String tradeState;
    /**
     * <pre>
     * 字段名：交易状态描述
     * 变量名：trade_state_desc
     * 是否必填：是
     * 类型：string[1,256]
     * 描述：
     *  交易状态描述
     *  示例值：支付成功
     * </pre>
     */
    private String tradeStateDesc;
    /**
     * <pre>
     * 字段名：付款银行
     * 变量名：bank_type
     * 是否必填：是
     * 类型：string[1,16]
     * 描述：
     *  银行类型，采用字符串类型的银行标识。银行标识请参考《银行类型对照表》https://pay.weixin.qq.com/wiki/doc/apiv3/terms_definition/chapter1_1_3.shtml#part-6
     *  示例值：CMC
     * </pre>
     */
    private String bankType;
    /**
     * <pre>
     * 字段名：附加数据
     * 变量名：attach
     * 是否必填：否
     * 类型：string[1,128]
     * 描述：
     *  附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     *  示例值：自定义数据
     * </pre>
     */
    private String attach;
    /**
     * <pre>
     * 字段名：支付完成时间
     * 变量名：success_time
     * 是否必填：是
     * 类型：string[1,64]
     * 描述：
     *  支付完成时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     *  示例值：2018-06-08T10:34:56+08:00
     * </pre>
     */
    private String successTime;
    /**
     * <pre>
     * 字段名：支付者
     * 变量名：payer
     * 是否必填：是
     * 类型：object
     * 描述：
     *  支付者信息
     * </pre>
     */
    private Payer payer;
    /**
     * <pre>
     * 字段名：订单金额
     * 变量名：amount
     * 是否必填：否
     * 类型：object
     * 描述：
     *  订单金额信息
     * </pre>
     */
    private Amount amount;

    public static class TradeState {

        public static final String SUCCESS="SUCCESS";
        public static final String REFUND="REFUND";
        public static final String NOTPAY="NOTPAY";
        public static final String CLOSED="CLOSED";
        public static final String REVOKED="REVOKED";
        public static final String USERPAYING="USERPAYING";
        public static final String PAYERROR="PAYERROR";

    }
    @Data
    @NoArgsConstructor
    public static class Payer implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * <pre>
         * 字段名：用户标识
         * 变量名：openid
         * 是否必填：是
         * 类型：string[1,128]
         * 描述：
         *  用户在直连商户appid下的唯一标识。
         *  示例值：oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
         * </pre>
         */
        @SerializedName(value = "openid")
        private String openid;
    }
    @Data
    @NoArgsConstructor
    public static class Amount implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * <pre>
         * 字段名：总金额
         * 变量名：total
         * 是否必填：否
         * 类型：int
         * 描述：
         *  订单总金额，单位为分。
         *  示例值：100
         * </pre>
         */
        @SerializedName(value = "total")
        private Integer total;
        /**
         * <pre>
         * 字段名：用户支付金额
         * 变量名：payer_total
         * 是否必填：否
         * 类型：int
         * 描述：
         *  用户支付金额，单位为分。
         *  示例值：100
         * </pre>
         */
        @SerializedName(value = "payer_total")
        private Integer payerTotal;
        /**
         * <pre>
         * 字段名：货币类型
         * 变量名：currency
         * 是否必填：否
         * 类型：string[1,16]
         * 描述：
         *  CNY：人民币，境内商户号仅支持人民币。
         *  示例值：CNY
         * </pre>
         */
        @SerializedName(value = "currency")
        private String currency;
        /**
         * <pre>
         * 字段名：用户支付币种
         * 变量名：payer_currency
         * 是否必填：否
         * 类型：string[1,16]
         * 描述：
         *  用户支付币种
         *  示例值： CNY
         * </pre>
         */
        @SerializedName(value = "payer_currency")
        private String payerCurrency;
    }

}
