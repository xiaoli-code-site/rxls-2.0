package cn.rx.boot.controller;

import cn.rx.common.result.Result;
import cn.rx.system.service.SysUploadFileService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统文件
 */
@RestController
@RequestMapping("/upload")
@Validated
public class SysUploadController {

    @Resource
    SysUploadFileService sysUploadFileService;

    /**
     * 批量上传图片
     *
     * @param file 图片
     * @return 文件路径
     */
    @PostMapping("/auth/images")
    public Result<List<String>> uploadImages(@RequestParam(value = "image_file", required = false) List<MultipartFile> file) {
        return Result.success(sysUploadFileService.uploadImages(file));
    }

    /**
     * 批量上传文件
     *
     * @param file 文件
     * @return 文件路径
     */
    @PostMapping("/auth/files")
    public Result<List<String>> uploadFiles(@RequestParam(value = "file", required = false) List<MultipartFile> file) {
        return Result.success(sysUploadFileService.uploadImages(file));
    }

    /**
     * 上传单张图片
     *
     * @param file 图片
     * @return 文件路径
     */
    @PostMapping("/auth/image")
    public Result<String> uploadImage(@RequestParam(value = "image_file", required = false) MultipartFile file) {
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);
        String path = sysUploadFileService.uploadImages(files).get(0);
        return Result.success(path);
    }


    /**
     * 上传单个文件
     *
     * @param file 文件
     * @return 文件路径
     */
    @PostMapping("/auth/file")
    public Result<String> uploadFile(@RequestParam(value = "file", required = false) @NotNull(message = "uploadNoEmpty") MultipartFile file) {
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);
        String path = sysUploadFileService.uploadImages(files).get(0);
        return Result.success(path);
    }


    /**
     * 批量删除文件
     *
     * @param paths 需要删除的文件名集合
     * @return 成功数
     */
    @PostMapping("/auth/deleteFile")
    @RequiresPermissions("sys:resources:del")
    public Result<Integer> delFile(@RequestBody List<String> paths) {
        return Result.success(sysUploadFileService.delFile(paths));
    }

}
