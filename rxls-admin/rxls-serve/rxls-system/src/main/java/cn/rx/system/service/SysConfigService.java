package cn.rx.system.service;


import cn.rx.common.dto.sysConfig.SysConfigAddDTO;
import cn.rx.common.dto.sysConfig.SysConfigPageDTO;
import cn.rx.common.dto.sysConfig.SysConfigUpdateDTO;
import cn.rx.common.vo.sysConfig.SysConfigPageVO;
import cn.rx.db.entity.SysConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 系统配置(SysConfig)表服务接口
 *
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 新增配置
     *
     * @param dto 参数对象
     */
    void addConfig(SysConfigAddDTO dto);

    /**
     * 分页查询配置信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    SysConfigPageVO selectPage(SysConfigPageDTO dto);


    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    Integer deleteInIdList(List<Integer> idList);


    /**
     * 变更配置信息
     *
     * @param dto 参数对象
     */
    void updateConfig(SysConfigUpdateDTO dto);

    /**
     * 配置状态变更
     *
     * @param id 配置id
     */
    void stateUpdateConfig(Integer id);
}
