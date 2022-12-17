package top.pippen.order.rest.biz;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.common.utils.Result;
import top.pippen.order.service.UserService;

/**
 * @author Pippen
 * @since 2022/11/14 16:04
 */
@RestController
@RequestMapping("biz/wxuser")
@Api(tags = "微信用户")
public class WxUserController {
    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("biz:order:info")
    public Result<UserEntity> get(@PathVariable("id") Long id) {

        UserEntity data = userService.getUserByUserId(id);
        return new Result<UserEntity>().ok(data);
    }


}
