package cn.rx.system.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件上传服务接口
 * @author 不想说话而已
 */
public interface SysUploadFileService {

    /**
     * 批量图片上传
     * @param files 文件内容
     * @return 多条上传的图片路径信息
     */
    List<String> uploadImages(List<MultipartFile>  files);

    /**
     * 批量删除文件
     * @param path 文件名
     * @return 成功数
     */
   Integer delFile(List<String> path);

    /**
     * 批量上传文件
     *
     * @param file 文件
     * @return 文件路径
     */
    List<String> uploadFile(List<MultipartFile> file);
}
