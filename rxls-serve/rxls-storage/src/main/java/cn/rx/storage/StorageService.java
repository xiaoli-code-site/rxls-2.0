package cn.rx.storage;

import cn.rx.storage.enums.StorageType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    /**
     * 批量上传
     *
     * @param files 文件列表
     * @return 图片路径
     */
    List<String> save(List<MultipartFile> files, StorageType storageType);

    /**
     * 批量删除
     *
     * @param name 文件名称列表
     * @return 已删除的图片路径
     */
    List<String> del(List<String> name);

    /**
     * 获取访问路径
     *
     * @param name 文件名称列表
     * @return 图片路径
     */
    String getPath(String name);


    /**
     * 获取服务名称
     *
     * @return 服务名称
     */
    String getServeName();



    /**
     * 是否支持该类型
     *
     * @return 是否支持
     */
    boolean supports(String type);
}
