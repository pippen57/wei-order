package top.pippen.order.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import top.pippen.order.api.annotation.Login;
import top.pippen.order.api.annotation.LoginUser;
import top.pippen.order.bean.dto.biz.CategoryDTO;
import top.pippen.order.bean.dto.biz.ProductDTO;
import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.bean.dto.biz.APIProductDTO;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.utils.Result;
import top.pippen.order.service.CategoryService;
import top.pippen.order.service.ProductService;

import java.util.HashMap;
import java.util.List;

/**
 * @author Pippen
 * @since 2022/10/24 10:23
 */
@RestController
@RequestMapping("/api")
@Api(tags="商品")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Login
    @GetMapping("/product/list/{shopId}/{categoryId}")
    @ApiOperation(value="获取商品列表", response= UserEntity.class)
    public Result<List<ProductDTO>> productList(@ApiIgnore @LoginUser UserEntity user, @PathVariable Long shopId, @PathVariable String categoryId){
        return new Result<List<ProductDTO>>().ok(productService.getProduct(shopId, categoryId));
    }
    @Login
    @GetMapping("/product/list/{shopId}")
    @ApiOperation(value="获取商品列表", response= UserEntity.class)
    public Result<List<APIProductDTO>> productList(@ApiIgnore @LoginUser UserEntity user, @PathVariable Long shopId){
        return new Result<List<APIProductDTO>>().ok(productService.getProduct(shopId ));
    }

    /**
     * 获取分类
     * @param shopId /
     * @return /
     */
    @Login
    @GetMapping("/product/category_all/{shopId}")
    public Result<List<CategoryDTO>> categoryAll(@PathVariable Long shopId){
        HashMap<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("shopId", shopId + "");
        categoryMap.put("status", Constant.SUCCESS + "");
        List<CategoryDTO> category = categoryService.list(categoryMap);
        return new Result<List<CategoryDTO>>().ok(category);

    }
}
