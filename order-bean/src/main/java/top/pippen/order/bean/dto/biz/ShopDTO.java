package top.pippen.order.bean.dto.biz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.pippen.order.common.validator.group.AddGroup;
import top.pippen.order.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * 店铺
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
@Data
@ApiModel(value = "店铺")
public class ShopDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "店铺id")
	@NotNull(message = "必要参数为空",groups = UpdateGroup.class)
	private Long id;

	@ApiModelProperty(value = "店铺名称(数字、中文，英文(可混合，不可有特殊字符)，可修改)、不唯一")
	@NotBlank(message = "店铺名称不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private String shopName;

	@ApiModelProperty(value = "店长用户id")
	private Long userId;

	@ApiModelProperty(value = "店长")
	private String shopOwner;

	@ApiModelProperty(value = "店铺绑定的手机(登录账号：唯一)")
	private String mobile;

	@ApiModelProperty(value = "店铺公告(可修改)")
	private String shopNotice;

	@ApiModelProperty(value = "店铺简介(可修改)")
	private String intro;

	@ApiModelProperty(value = "店铺联系电话")
	private String tel;

	@ApiModelProperty(value = "店铺所在纬度(可修改)")
	@NotBlank(message = "店铺纬度不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private String shopLat;

	@ApiModelProperty(value = "店铺所在经度(可修改)")
	@NotBlank(message = "店铺经度不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private String shopLng;

	@ApiModelProperty(value = "店铺详细地址")
	@NotBlank(message = "店铺详细地址不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private String shopAddress;

	@ApiModelProperty(value = "店铺所在省份（描述）")
	private String province;

	@ApiModelProperty(value = "店铺所在城市（描述）")
	private String city;

	@ApiModelProperty(value = "店铺所在区域（描述）")
	private String area;

	@ApiModelProperty(value = "店铺logo(可修改)")
	private String shopLogo;

	@ApiModelProperty(value = "店铺相册")
	private String shopPhotos;

	@ApiModelProperty(value = "每天营业时间段(可修改)")
	private String openTime;

	// 距离
	private String kml;
	@ApiModelProperty(value = "店铺状态(-1:未开通 0: 停业中 1:营业中)，可修改")
	private Integer shopStatus;

	@ApiModelProperty(value = "创建者")
	private Long creator;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "更新者")
	private Long updater;

	@ApiModelProperty(value = "更新时间")
	private Date updateDate;


}
