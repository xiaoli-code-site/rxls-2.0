package cn.rx.storage.util;

import cn.rx.common.enums.R;
import cn.rx.common.util.StrUtil;
import cn.rx.core.config.GeneralConfiguration;
import cn.rx.core.exception.BusinessException;
import cn.rx.storage.enums.StorageType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class StorageUtil {


    /**
     * 检查上传内容
     *
     * @param file 文件
     * @param type 文件类型
     */
    public static void checkUsed(MultipartFile file, StorageType type) {
        //如果上传文件为空
        Optional.ofNullable(file).orElseThrow(() -> new BusinessException(R.ERROR));
        //检查所有的上传文件类型是否是一个图片，并且大小是否合适
        String originalFilename = file.getOriginalFilename();
        Optional.ofNullable(originalFilename).orElseThrow(() -> new BusinessException(R.ERROR_UPLOAD_TYPE));
        String intercept = StrUtil.intercept(originalFilename.toLowerCase(), '.');
        long fileSizeInBytes = file.getSize();
        long fileSizeInMegabytes = fileSizeInBytes / (1024 * 1024); // 转换为MB

        if (type.equals(StorageType.IMG)) {
            if (!checkType(intercept, GeneralConfiguration.IMG_TYPE)) {
                throw new BusinessException(R.ERROR_UPLOAD_TYPE);
            }
            if (fileSizeInMegabytes > getMaxImgSize()) {
                throw new BusinessException(R.ERROR_UPLOAD_IMG_SIZE);
            }
        } else {
            if (!checkType(intercept, GeneralConfiguration.FILE_TYPE)) {
                throw new BusinessException(R.ERROR_UPLOAD_TYPE);
            }
            if (fileSizeInMegabytes > getMaxFileSize()) {
                throw new BusinessException(R.ERROR_UPLOAD_IMG_SIZE);
            }
        }
    }


    /**
     * 检查类型是否合适
     *
     * @param name 文件名称
     * @return 结果
     */
    public static Boolean checkType(String name, String type) {
        String str = GeneralConfiguration.get(type);
        String[] split = str.split(",");
        if (split.length == 0) {
            return true;
        }
        Set<String> collect = Arrays.stream(split).filter(s -> s.equals(name)).collect(Collectors.toSet());
        return collect.size() > 0;
    }

    /**
     * 获取单个文件上传最大容量（MB）
     *
     * @return 结果
     */
    public static long getMaxFileSize() {
        String s = GeneralConfiguration.get(GeneralConfiguration.FILE_MAX_SIZE);
        long v = Long.parseLong(s);
        return v > 0 ? v : 10 ;
    }

    /**
     * 获取单张图片上传最大容量（MB）
     *
     * @return 结果
     */
    public static long getMaxImgSize() {
        String s = GeneralConfiguration.get(GeneralConfiguration.IMG_MAX_SIZE);
        long v = Long.parseLong(s);
        return v > 0 ? v : 3 ;
    }

    /**
     * 获取存储目录名称
     *
     * @return 存储目录名称
     */
    public static String getStorageName() {
        String folder = GeneralConfiguration.get(GeneralConfiguration.STORAGE_PATH);
        if (Objects.isNull(folder)) {
            folder = "upload";
        }
        return folder;
    }


    /**
     * 生成文件名称
     *
     * @param file 文件
     */
    public static String createName(MultipartFile file) {
        String intercept = StrUtil.intercept(Objects.requireNonNull(file.getOriginalFilename()).toLowerCase(), '.');
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + intercept;
    }


    /**
     * 生成存储文件对象
     *
     * @param folder   运行目录下的文件夹名称
     * @param fileName 文件名称
     */
    public static File createUploadFile(String folder, String fileName) {
        String path = System.getProperty("user.dir");
        File uploadDir = new File(path, folder);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        return new File(uploadDir, fileName);
    }

}
