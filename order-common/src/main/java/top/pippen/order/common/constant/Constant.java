/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package top.pippen.order.common.constant;

import lombok.AllArgsConstructor;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface Constant {
    /**
     * 成功
     */
    int SUCCESS = 1;
    /**
     * 失败
     */
    int FAIL = 0;
    /**
     * 菜单根节点标识
     */
    Long MENU_ROOT = 0L;
    /**
     * 部门根节点标识
     */
    Long DEPT_ROOT = 0L;
    /**
     *  升序
     */
    String ASC = "asc";
    /**
     * 降序
     */
    String DESC = "desc";
    /**
     * 创建时间字段名
     */
    String CREATE_DATE = "create_date";

    /**
     * 数据权限过滤
     */
    String SQL_FILTER = "sqlFilter";
    /**
     * 当前页码
     */
    String PAGE = "page";
    /**
     * 每页显示记录数
     */
    String LIMIT = "limit";
    /**
     * 排序字段
     */
    String ORDER_FIELD = "orderField";
    /**
     * 排序方式
     */
    String ORDER = "order";
    /**
     * token header
     */
    String TOKEN_HEADER = "token";

    /**
     * 云存储配置KEY
     */
    String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";

    /**
     * 订单超时时间
     */
    String ORDER_CANCEL_KEY = "ORDER_CANCEL_KEY";

    /**
     * 订单通知地址
     */
    String ORDER_NOTIFY_URL = "ORDER_NOTIFY_URL";
    /**
     * 退款通知地址
     */
    String REFUND_NOTIFY_URL = "REFUND_NOTIFY_URL";

    /**
     * 下单成功通知模版ID
     */
    String ORDER_SUCCESS_NOTICE_TEMPID = "ORDER_SUCCESS_NOTICE_TEMPID";

    /**
     *  订单状态通知模版
     */
    String ORDER_STATUS_NOTICE_TEMPID = "ORDER_STATUS_NOTICE_TEMPID";

    /**
     * 定时任务状态
     */
    enum ScheduleStatus {
        /**
         * 暂停
         */
        PAUSE(0),
        /**
         * 正常
         */
        NORMAL(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * SUCCESS：退款成功
     *       CLOSED：退款关闭
     *       PROCESSING：退款处理中
     *       ABNORMAL：退款异常
     */

    @AllArgsConstructor
    enum WxPayRefundStatus {
        SUCCESS(1,"SUCCESS"),
        CLOSED(2,"CLOSED"),
        PROCESSING(0,"PROCESSING"),
        ABNORMAL(-1,"ABNORMAL");
        private int status;
        private String statusStr;

        public int getStatus() {
            return status;
        }


        public String getStatusStr() {
            return statusStr;
        }

    }

    /**
     * 云服务商
     */
    enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
