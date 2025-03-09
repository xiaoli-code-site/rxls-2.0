package cn.rx.system.service.impl;


import cn.rx.common.enums.R;
import cn.rx.common.util.StrUtil;
import cn.rx.core.exception.BusinessException;
import cn.rx.db.entity.SysResources;
import cn.rx.storage.StorageContext;
import cn.rx.storage.StorageService;
import cn.rx.storage.enums.StorageType;
import cn.rx.system.service.SysResourcesService;
import cn.rx.system.service.SysUploadFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文件上传服务
 */
@Service("uploadFileService")
@Transactional(rollbackFor = Exception.class)
public class SysUploadFileServiceImpl implements SysUploadFileService {


    @Resource
    private SysResourcesService resourcesService;


    @Resource
    private StorageContext storageContext;

    /**
     * 批量图片上传
     *
     * @param files 上传的文件列表
     * @return 多条上传的图片路径信息
     */
    @Override
    public List<String> uploadImages(List<MultipartFile> files) {
        Optional.ofNullable(files).orElseThrow(() -> new BusinessException(R.ERROR_NO_FILE));
        if (files.size() == 0 || files.size() > 10) {
            throw new BusinessException(R.ERROR_FILE_LIMIT);
        }
        List<String> save = storageContext.get().save(files, StorageType.IMG);
        save.forEach(s -> {
            SysResources sysResources = new SysResources();
            String url = StrUtil.interceptBefore(s, '.');
            sysResources.setUrl(url);
            sysResources.setCategory(StorageType.IMG.name());
            sysResources.setServe(storageContext.get().getServeName());
            resourcesService.save(sysResources);
        });
        return save;
    }


    /**
     * 批量删除文件
     *
     * @param path 文件名
     * @return 成功数
     */
    @Override
    public Integer delFile(List<String> path) {
        List<String> del = storageContext.get().del(path);
        List<String> collect = del.stream().map(s -> StrUtil.interceptBefore(s, '.')).collect(Collectors.toList());
        resourcesService.delListByUrl(collect);
        return del.size();
    }

    /**
     * 上传文件
     *
     * @param files 文件
     * @return 文件路径
     */
    @Override
    public List<String> uploadFile(List<MultipartFile> files) {
        Optional.ofNullable(files).orElseThrow(() -> new BusinessException(R.ERROR_NO_FILE));
        if (files.size() == 0 || files.size() > 10) {
            throw new BusinessException(R.ERROR_FILE_LIMIT);
        }
        List<String> save = storageContext.get().save(files, StorageType.FILE);
        save.forEach(s -> {
            SysResources sysResources = new SysResources();
            String url = StrUtil.intercept(s, '.');
            sysResources.setUrl(s);
            sysResources.setCategory(StorageType.FILE.name());
            sysResources.setServe(storageContext.get().getServeName());
            resourcesService.save(sysResources);
        });
        return save;
    }


}
