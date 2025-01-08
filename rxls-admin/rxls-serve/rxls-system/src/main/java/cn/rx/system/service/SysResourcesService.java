package cn.rx.system.service;


import cn.rx.db.entity.SysResources;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 静态资源(SysResources)表服务接口
 *
 */
public interface SysResourcesService extends IService<SysResources> {

    /**
     * 批量删除
     * @param urlList 资源标识
     * @return 成功个数
     */
    Integer delListByUrl(List<String> urlList);

    /**
     * 删除单个
     * @param url 资源标识
     */
    void delByUrl(String url);


    /**
     * 批量存储
     * @param sysResources 资源列表
     * @return 成功个数
     */
    Integer addListStaticResources(List<SysResources> sysResources);

    /**
     * 存储单个资源
     * @param category 文件类型
     * @param serve 服务类型
     * @param url 资源标识
     */
    void addStaticResources(String category,String serve,String url);

    /**
     * 资源持久
     * @param url 资源标识
     */
    void lasting(String url);
}
