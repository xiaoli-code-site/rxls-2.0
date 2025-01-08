package cn.rx.storage.config;

import cn.rx.core.config.GeneralConfiguration;
import cn.rx.storage.util.StorageUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;


/**
 * 资源存储路径映射
 *
 * @author 不想说话而已
 */
@Configuration
public class UploadConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String folder = StorageUtil.getStorageName();
        registry.addResourceHandler("/" + folder + "/**").addResourceLocations("file:" + folder + "/");
    }
}
