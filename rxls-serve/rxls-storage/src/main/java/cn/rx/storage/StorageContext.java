package cn.rx.storage;

import cn.rx.common.enums.R;
import cn.rx.core.config.GeneralConfiguration;
import cn.rx.core.exception.BusinessException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * 存储策略对象
 */
@Component
public class StorageContext {
    @Resource
    private List<StorageService> storageServices;

    public StorageService get() {
        return storageServices.stream()
                .filter(service -> service.supports(GeneralConfiguration.get(GeneralConfiguration.STORAGE_TYPE)))
                .findFirst()
                .orElseThrow(() -> new BusinessException(R.ERROR_STORAGE));
    }



}
