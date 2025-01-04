package cn.rx.core.config;

import cn.rx.common.enums.CommonStateEnum;
import cn.rx.db.entity.SysConfig;
import cn.rx.db.mapper.SysConfigMapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用配置
 */
@Component
public class GeneralConfiguration {

    @Resource
    private SysConfigMapper sysConfigMapper;

    /**
     * 通用配置内容
     */
    private static  Map<String, String> configMap = new HashMap<>();


    /**
     * 上传的文件最大大小
     */
    public static final String FILE_MAX_SIZE = "fileMaxSize";
    /**
     * 上传的图片最大大小
     */
    public static final String IMG_MAX_SIZE = "imgMaxSize";

    /**
     * 可用的文件类型
     */
    public static final String FILE_TYPE = "fileType";

    /**
     * 可用的图片类型
     */
    public static final String IMG_TYPE = "imgType";

    /**
     * 资源存储的目录名称
     */
    public static final String STORAGE_PATH = "storagePath";

    /**
     * 当前使用的存储服务类型
     */
    public static final String STORAGE_TYPE = "storageType";


    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        LambdaUpdateWrapper<SysConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysConfig::getState, CommonStateEnum.OK.code);
        List<SysConfig> sysConfigs = sysConfigMapper.selectList(wrapper);
        sysConfigs.forEach(s -> {
            configMap.put(s.getSysKey(), s.getSysValue());
        });
    }

    /**
     * 更新配置
     * @param key 键名
     * @param value 值
     */
    public static void update(String key,String value) {
        configMap.put(key, value);
    }

    /**
     * 删除配置
     * @param key 键名
     */
    public static void del(String key) {
        configMap.remove(key);
    }


    /**
     * 根据KEY获取配置
     * @param key 键名
     * @return 值
     */
    public static String get(String key) {
       return configMap.get(key);
    }

}
