package cn.rx.system.service.impl;


import cn.rx.common.dto.admin.sysPermissions.SysPermissionsAddDTO;
import cn.rx.common.dto.admin.sysPermissions.SysPermissionsPageDTO;
import cn.rx.common.dto.admin.sysPermissions.SysPermissionsUpdateDTO;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.common.vo.admin.sysPermissions.SysPermissionsPageVO;
import cn.rx.common.vo.admin.sysPermissions.SysPermissionsVO;
import cn.rx.common.vo.admin.sysRole.SysRolePageVO;
import cn.rx.common.vo.admin.sysRole.SysRoleVO;
import cn.rx.core.exception.BusinessException;
import cn.rx.db.entity.SysPermissions;
import cn.rx.db.entity.SysRole;
import cn.rx.db.entity.SysRoleUser;
import cn.rx.db.entity.SysUser;
import cn.rx.db.mapper.SysPermissionsMapper;
import cn.rx.system.service.SysPermissionsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 权限(Permissions)表服务实现类
 */
@Service("permissionsService")
public class SysPermissionsServiceImpl extends ServiceImpl<SysPermissionsMapper, SysPermissions> implements SysPermissionsService {

    private final SysPermissionsMapper sysPermissionsMapper;

    SysPermissionsServiceImpl(SysPermissionsMapper sysPermissionsMapper) {
        this.sysPermissionsMapper = sysPermissionsMapper;
    }

    /**
     * 根据角色查询权限
     *
     * @param roleId 角色id
     * @return 权限列表
     */
    @Override
    public Set<SysPermissionsVO> selectAllByRoleId(Integer roleId) {
        return sysPermissionsMapper.selectAllByRoleId(roleId);
    }

    /**
     * 获取所有的权限
     *
     * @return 权限列表
     */
    @Override
    public List<SysPermissionsVO> selectAll() {
        return null;
    }

    /**
     * 新增
     *
     * @param dto 参数对象
     */
    @Override
    public void add(SysPermissionsAddDTO dto) {
        SysPermissions sysPermissions = new SysPermissions();
        BeanUtils.copyProperties(dto,sysPermissions);
        sysPermissions.setState(CommonStateEnum.OK.code);
        if(sysPermissionsMapper.insert(sysPermissions) <1){
            throw new BusinessException(R.ERROR_ADD);
        }
    }

    /**
     * 分页查询信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @Override
    public SysPermissionsPageVO selectPage(SysPermissionsPageDTO dto) {
        SysPermissionsPageVO result = new SysPermissionsPageVO();
        Page<SysPermissions> page = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<SysPermissions> wrapper = new LambdaQueryWrapper<>();
        wrapper .like(Objects.nonNull(dto.getUrl()), SysPermissions::getUrl, dto.getUrl())
                .eq(Objects.nonNull(dto.getState()),
                        SysPermissions::getState, dto.getState())
                .like(Objects.nonNull(dto.getDescription()), SysPermissions::getDescription, dto.getDescription())
                .orderByDesc(SysPermissions::getUpdateTime);
        Page<SysPermissions> list = sysPermissionsMapper.selectPage(page, wrapper);
        result.setTotal(list.getTotal());
        list.getRecords().forEach(s -> {
            SysPermissionsVO data = new SysPermissionsVO();
            BeanUtils.copyProperties(s, data);
            result.getDataList().add(data);
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
    public Integer deleteInIdList(List<Integer> idList) {
        if (idList.size() < 1) {
            return 0;
        }
        LambdaQueryWrapper<SysPermissions> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysPermissions::getPermissionsId, idList);
        int delete = sysPermissionsMapper.delete(wrapper);
        if (delete < 1) {
            throw new BusinessException(R.ERROR_DELETE);
        }
        return delete;
    }

    /**
     * 变更信息
     *
     * @param dto 参数对象
     */
    @Override
    public void alter(SysPermissionsUpdateDTO dto) {
        LambdaUpdateWrapper<SysPermissions> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysPermissions::getPermissionsId,dto.getPermissionsId());
        SysPermissions sysPermissions = new SysPermissions();
        BeanUtils.copyProperties(dto,sysPermissions);
        if(sysPermissionsMapper.update(sysPermissions,wrapper) <1){
            throw new BusinessException(R.ERROR_ADD);
        }
    }

}

