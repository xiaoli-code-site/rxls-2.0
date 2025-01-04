package cn.rx.system.service.impl;

import cn.rx.common.constant.ResourcesType;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.core.exception.BusinessException;
import cn.rx.db.entity.SysResources;
import cn.rx.db.mapper.SysResourcesMapper;
import cn.rx.system.service.SysResourcesService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 静态资源(SysResources)表服务实现类
 */
@Service("staticResourcesService")
public class SysResourcesServiceImpl extends ServiceImpl<SysResourcesMapper, SysResources> implements SysResourcesService {

    private final SysResourcesMapper sysResourcesMapper;

    SysResourcesServiceImpl(SysResourcesMapper sysResourcesMapper) {
        this.sysResourcesMapper = sysResourcesMapper;
    }

    /**
     * 批量删除
     *
     * @param urlList 资源标识
     * @return 成功个数
     */
    @Override
    @Transactional
    public Integer delListByUrl(List<String> urlList) {
        LambdaQueryWrapper<SysResources> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysResources::getId).in(SysResources::getUrl, urlList);
        List<Long> collect = sysResourcesMapper.selectList(queryWrapper).stream().map(SysResources::getId).collect(Collectors.toList());
        if (collect.isEmpty()) {
            throw new BusinessException(R.ERROR_DELETE);
        }
        LambdaQueryWrapper<SysResources> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysResources::getId, collect);
        int delete = sysResourcesMapper.delete(wrapper);
        if (delete < 1) {
            throw new BusinessException(R.ERROR_DELETE);
        }
        return delete;
    }

    /**
     * 删除单个
     *
     * @param url 资源标识
     */
    @Override
    @Transactional
    public void delByUrl(String url) {
        LambdaQueryWrapper<SysResources> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysResources::getUrl, url);
        int delete = sysResourcesMapper.delete(wrapper);
        if (delete < 1) {
            throw new BusinessException(R.ERROR_DELETE);
        }
    }

    /**
     * 批量存储
     *
     * @param sysResources 资源列表
     * @return 成功个数
     */
    @Override
    @Transactional
    public Integer addListStaticResources(List<SysResources> sysResources) {
        int i = 0;
        for (; i < sysResources.size(); i++) {
            SysResources resources = sysResources.get(i);
            addStaticResources(resources.getCategory(), resources.getServe(), resources.getUrl());
        }
        return i;
    }

    /**
     * 存储单个资源
     *
     * @param category 文件类型
     * @param serve    服务类型
     * @param url      资源标识
     */
    @Override
    @Transactional
    public void addStaticResources(String category, String serve, String url) {
        SysResources sysResources = new SysResources();
        sysResources.setCategory(category.toLowerCase());
        sysResources.setUrl(url);
        sysResources.setServe(serve);
        int delete = sysResourcesMapper.insert(sysResources);
        if (delete < 1) {
            throw new BusinessException(R.ERROR_ADD);
        }
    }

    /**
     * 资源持久
     *
     * @param url 资源标识
     */
    @Override
    public void lasting(String url) {
        LambdaQueryWrapper<SysResources> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysResources::getId).eq(SysResources::getUrl, url).ne(SysResources::getState, CommonStateEnum.OK.code);
        SysResources info = sysResourcesMapper.selectOne(queryWrapper);
        if (Objects.isNull(info)) {
            return;
        }
        LambdaUpdateWrapper<SysResources> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysResources::getId, info.getId()).ne(SysResources::getState, CommonStateEnum.OK.code);
        SysResources sysResources = new SysResources();
        sysResources.setState(CommonStateEnum.OK.code);
        sysResourcesMapper.update(sysResources, wrapper);
    }
}


