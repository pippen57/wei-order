package top.pippen.order.bean.dto.biz;

import top.pippen.order.common.utils.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;


/**
 * 商品分类
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
@ApiModel(value = "商品分类")
public class CategoryTreeDTO extends TreeNode<CategoryTreeDTO> implements Serializable {
    private static final long serialVersionUID = 1L;




	@ApiModelProperty(value = "产品类目名称")
	private String categoryName;


	@ApiModelProperty(value = "排序")
	private Integer seq;

	@ApiModelProperty(value = "类目图标")
	private String icon;

	@ApiModelProperty(value = "类目的显示图片")
	private String pic;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;
	@ApiModelProperty(value = "上级部门名称")
	private String parentName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", CategoryTreeDTO.class.getSimpleName() + "[", "]")
				.add("categoryName='" + categoryName + "'")
				.add("seq=" + seq)
				.add("icon='" + icon + "'")
				.add("pic='" + pic + "'")
				.add("createDate=" + createDate)
				.add("parentName='" + parentName + "'")
				.toString();
	}
}
