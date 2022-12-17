package top.pippen.order.bean.model.biz;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.pippen.order.common.entity.BaseEntity;

import java.util.Date;

/**
 * 店铺
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("biz_shop")
public class ShopEntity extends BaseEntity {

    /**
     * 店铺名称(数字、中文，英文(可混合，不可有特殊字符)，可修改)、不唯一
     */
	private String shopName;
    /**
     * 店长用户id
     */
	private String userId;
    /**
     * 店长
     */
	private String shopOwner;
    /**
     * 店铺绑定的手机(登录账号：唯一)
     */
	private String mobile;
    /**
     * 店铺公告(可修改)
     */
	private String shopNotice;
    /**
     * 店铺简介(可修改)
     */
	private String intro;
    /**
     * 店铺联系电话
     */
	private String tel;
    /**
     * 店铺所在纬度(可修改)
     */
	private String shopLat;
    /**
     * 店铺所在经度(可修改)
     */
	private String shopLng;
    /**
     * 店铺详细地址
     */
	private String shopAddress;
    /**
     * 店铺所在省份（描述）
     */
	private String province;
    /**
     * 店铺所在城市（描述）
     */
	private String city;
    /**
     * 店铺所在区域（描述）
     */
	private String area;
    /**
     * 店铺logo(可修改)
     */
	private String shopLogo;
    /**
     * 店铺相册
     */
	private String shopPhotos;
    /**
     * 每天营业时间段(可修改)
     */
	private String openTime;
    /**
     * 店铺状态(-1:未开通 0: 停业中 1:营业中)，可修改
     */
	private Integer shopStatus;
    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updater;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;
}
