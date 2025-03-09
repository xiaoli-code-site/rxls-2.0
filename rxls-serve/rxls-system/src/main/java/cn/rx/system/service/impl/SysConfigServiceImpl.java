package cn.rx.system.service.impl;

import cn.rx.common.dto.admin.sysConfig.SysConfigAddDTO;
import cn.rx.common.dto.admin.sysConfig.SysConfigPageDTO;
import cn.rx.common.dto.admin.sysConfig.SysConfigUpdateDTO;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.common.vo.admin.sysConfig.SysConfigPageVO;
import cn.rx.common.vo.admin.sysConfig.SysConfigVO;
import cn.rx.core.config.GeneralConfiguration;
import cn.rx.core.exception.BusinessException;
import cn.rx.db.entity.SysConfig;
import cn.rx.db.mapper.SysConfigMapper;
import cn.rx.system.service.SysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 系统配置(SysConfig)表服务实现类
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    private final SysConfigMapper sysConfigMapper;

    SysConfigServiceImpl(SysConfigMapper sysConfigMapper) {
        this.sysConfigMapper = sysConfigMapper;
    }

    /**
     * 新增配置
     *
     * @param dto 参数对象
     */
    @Override
    @Transactional
    public void addConfig(SysConfigAddDTO dto) {
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(dto, sysConfig);
        String replaced = sysConfig.getSysValue().replaceAll("，", ",");
        sysConfig.setSysValue(replaced);
        if (sysConfigMapper.insert(sysConfig) < 1) {
            throw new BusinessException(R.ERROR_UPDATE);
        }
        GeneralConfiguration.update(dto.getSysKey(), dto.getSysValue());
   }

    /**
     * 分页查询配置信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @Override
    public SysConfigPageVO selectPage(SysConfigPageDTO dto) {
        SysConfigPageVO result = new SysConfigPageVO();
        Page<SysConfig> page = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Objects.nonNull(dto.getDescription()), SysConfig::getDescription, dto.getDescription())
                .eq(Objects.nonNull(dto.getState()), SysConfig::getState, dto.getState())
                .orderByDesc(SysConfig::getUpdateTime);

        Page<SysConfig> data = sysConfigMapper.selectPage(page, wrapper);
        if (data.getTotal() == 0L) {
            return result;
        }
        result.setTotal(data.getTotal());
        data.getRecords().forEach(s -> {
            SysConfigVO temp = new SysConfigVO();
            BeanUtils.copyProperties(s, temp);
            result.getDataList().add(temp);
        });
        return result;
    }

    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    @Override
    @Transactional
    public Integer deleteInIdList(List<Integer> idList) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysConfig::getId, idList);
        int delete = sysConfigMapper.delete(wrapper);
        if (delete < 1) {
            throw new BusinessException(R.ERROR_DELETE);
        }
        return delete;
    }

    /**
     * 变更配置信息
     *
     * @param dto 参数对象
     */
    @Override
    @Transactional
    public void updateConfig(SysConfigUpdateDTO dto) {
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysConfig::getId, dto.getId());
        SysConfig sysConfig = sysConfigMapper.selectOne(queryWrapper);
        Optional.ofNullable(sysConfig).orElseThrow(() -> new BusinessException(R.ERROR_CONFIG_NO));
        LambdaUpdateWrapper<SysConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysConfig::getId, dto.getId());
        BeanUtils.copyProperties(dto, sysConfig);
        String replaced = sysConfig.getSysValue().replaceAll("，", ",");
        sysConfig.setSysValue(replaced);

        if (sysConfigMapper.update(sysConfig, wrapper) < 1) {
            throw new BusinessException(R.ERROR_UPDATE);
        }
        if (CommonStateEnum.OK.code.equals(dto.getState())) {
            GeneralConfiguration.update(sysConfig.getSysKey(), sysConfig.getSysValue());
        }else{
            GeneralConfiguration.del(sysConfig.getSysKey());
        }
    }

    /**
     * 配置状态变更
     *
     * @param id 配置id
     */
    @Override
    @Transactional
    public void stateUpdateConfig(Integer id) {
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysConfig::getId, id);
        SysConfig info = sysConfigMapper.selectOne(queryWrapper);
        Optional.ofNullable(info).orElseThrow(() -> new BusinessException(R.ERROR_CONFIG_NO));
        LambdaUpdateWrapper<SysConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysConfig::getId, id);
        info.setState(info.getState().equals(CommonStateEnum.OK.code) ? CommonStateEnum.ERROR.code : CommonStateEnum.OK.code);
        String replaced = info.getSysValue().replaceAll("，", ",");
        info.setSysValue(replaced);
        if (sysConfigMapper.update(info, wrapper) < 1) {
            throw new BusinessException(R.ERROR_UPDATE);
        }
        if (CommonStateEnum.OK.code.equals(info.getState())) {
            GeneralConfiguration.update(info.getSysKey(), info.getSysValue());
        }else{
            GeneralConfiguration.del(info.getSysKey());
        }
    }



}

