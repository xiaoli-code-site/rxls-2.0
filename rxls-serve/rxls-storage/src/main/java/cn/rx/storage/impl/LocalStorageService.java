package cn.rx.storage.impl;

import cn.rx.common.enums.R;
import cn.rx.common.util.HttpUtil;
import cn.rx.core.exception.BusinessException;
import cn.rx.storage.StorageService;

import cn.rx.storage.enums.StorageType;
import cn.rx.storage.util.StorageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 本地存储
 */
@Service
public class LocalStorageService implements StorageService {


    /**
     * 批量上传
     *
     * @param files       图片文件
     * @param storageType 文件类型
     * @return 图片路径
     */
    @Override
    public List<String> save(List<MultipartFile> files, StorageType storageType) {
        Optional.ofNullable(files).orElseThrow(() -> new BusinessException(R.ERROR));
        //检查所有的上传文件类型是否是一个图片，并且大小是否合适
        files.forEach(f -> {
            StorageUtil.checkUsed(f, StorageType.IMG);
        });
        //返回的图片路径集合
        List<String> urlList = new ArrayList<>();
        String storageName = StorageUtil.getStorageName();
        //存储图片
        files.forEach(f -> {
            String fileName = StorageUtil.createName(f);
            File uploadFile = StorageUtil.createUploadFile(storageName, fileName);
            try {
                f.transferTo(uploadFile);
                urlList.add(getPath(fileName));
            } catch (IOException e) {
                throw new BusinessException(R.ERROR, e);
            }
        });
        return urlList;
    }

    /**
     * 批量删除
     *
     * @param name 文件名称列表
     * @return 已删除的图片路径
     */
    @Override
    public List<String> del(List<String> name) {
        //删除成功数
         List<String> strings = new ArrayList<>();
        //前端页面处理路径，
        name.forEach(p -> {
            int lastIndexOf = p.lastIndexOf("/");
            String imgPath = p.substring(lastIndexOf + 1);
            String path = System.getProperty("user.dir");
            File fileInfo = new File(path,StorageUtil.getStorageName());
            File file = new File(fileInfo.getAbsolutePath(),imgPath);
            if (file.exists()) {//文件是否存在
                if (file.delete()) {//存在就删了，返回1
                    strings.add(p);
                }
            }
        });
        return strings;
    }

    /**
     * 获取访问路径
     *
     * @param name 文件名称列表
     * @return 图片路径
     */
    @Override
    public String getPath(String name) {
        HttpServletRequest request = HttpUtil.getHttpInfo();
        return new StringBuffer().append(request.getScheme()) //获取协议号
                .append("://").append(request.getServerName()) //获取IP地址
                .append(":").append(request.getServerPort()) //获取端口号
                .append(request.getContextPath()) //获取工程路径
                .append("/").append(StorageUtil.getStorageName()).append("/")
                .append(name)
                .toString();
    }

    /**
     * 获取服务名称
     *
     * @return 服务名称
     */
    @Override
    public String getServeName() {
        return "LOCAL";
    }



    /**
     * 是否支持该类型
     *
     * @param type
     * @return 是否支持
     */
    @Override
    public boolean supports(String type) {
        return getServeName().equals(type);
    }

}
