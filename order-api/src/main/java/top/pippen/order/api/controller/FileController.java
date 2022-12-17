package top.pippen.order.api.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.pippen.order.api.annotation.Login;
import top.pippen.order.common.exception.ErrorCode;
import top.pippen.order.common.utils.Result;
import top.pippen.order.oss.cloud.OSSFactory;
import top.pippen.order.oss.entity.SysOssEntity;
import top.pippen.order.oss.service.SysOssService;
import top.pippen.order.service.SysParamsService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pippen
 * @since 2022/12/13 15:23
 */
@RestController
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    private SysOssService sysOssService;
    @Autowired
    private SysParamsService sysParamsService;


    @Login
    @PostMapping("upload")
    public Result<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return new Result<Map<String, Object>>().error(ErrorCode.UPLOAD_FILE_EMPTY);
        }

        //上传文件
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String url = OSSFactory.build().uploadSuffix(file.getBytes(), extension);

        //保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        sysOssService.insert(ossEntity);

        Map<String, Object> data = new HashMap<>(1);
        data.put("src", url);

        return new Result<Map<String, Object>>().ok(data);
    }
}
