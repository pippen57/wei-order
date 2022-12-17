package top.pippen.order.bean.excel.biz;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 店铺
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
@Data
public class ShopExcel {
    @Excel(name = "店铺id")
    private Long id;
    @Excel(name = "店铺名称(数字、中文，英文(可混合，不可有特殊字符)，可修改)、不唯一")
    private String shopName;
    @Excel(name = "店长用户id")
    private String userId;
    @Excel(name = "店长")
    private String shopOwner;
    @Excel(name = "店铺绑定的手机(登录账号：唯一)")
    private String mobile;
    @Excel(name = "店铺公告(可修改)")
    private String shopNotice;
    @Excel(name = "店铺简介(可修改)")
    private String intro;
    @Excel(name = "店铺联系电话")
    private String tel;
    @Excel(name = "店铺所在纬度(可修改)")
    private String shopLat;
    @Excel(name = "店铺所在经度(可修改)")
    private String shopLng;
    @Excel(name = "店铺详细地址")
    private String shopAddress;
    @Excel(name = "店铺所在省份（描述）")
    private String province;
    @Excel(name = "店铺所在城市（描述）")
    private String city;
    @Excel(name = "店铺所在区域（描述）")
    private String area;
    @Excel(name = "店铺logo(可修改)")
    private String shopLogo;
    @Excel(name = "店铺相册")
    private String shopPhotos;
    @Excel(name = "每天营业时间段(可修改)")
    private String openTime;
    @Excel(name = "店铺状态(-1:未开通 0: 停业中 1:营业中)，可修改")
    private Integer shopStatus;
    @Excel(name = "创建者")
    private Long creator;
    @Excel(name = "创建时间")
    private Date createDate;
    @Excel(name = "更新者")
    private Long updater;
    @Excel(name = "更新时间")
    private Date updateDate;

}
