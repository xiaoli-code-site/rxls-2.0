package cn.rx.system.service.impl;

import cn.rx.common.dto.sysRole.SysRoleAddDTO;
import cn.rx.common.dto.sysRole.SysRolePageDTO;
import cn.rx.common.dto.sysRole.SysRoleUpdateDTO;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.common.enums.RoleEnum;
import cn.rx.common.vo.sysRole.SysRolePageVO;
import cn.rx.common.vo.sysRole.SysRoleVO;
import cn.rx.core.exception.BusinessException;
import cn.rx.db.entity.SysPermissionsRole;
import cn.rx.db.entity.SysRole;
import cn.rx.db.entity.SysRoleUser;
import cn.rx.db.mapper.SysPermissionsRoleMapper;
import cn.rx.db.mapper.SysRoleMapper;
import cn.rx.db.mapper.SysRoleUserMapper;
import cn.rx.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 角色(SysRole)表服务实现类
 */
@Service("roleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    /**
     * 基础角色id
     */
    private final List<Integer> foundationRole;

    private final SysRoleMapper sysRoleMapper;
    private final SysPermissionsRoleMapper sysPermissionsRoleMapper;

    private final SysRoleUserMapper sysRoleUserMapper;


    SysRoleServiceImpl(SysRoleMapper sysRoleMapper, SysRoleUserMapper sysRoleUserMapper, SysPermissionsRoleMapper sysPermissionsRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysPermissionsRoleMapper = sysPermissionsRoleMapper;
        this.sysRoleUserMapper = sysRoleUserMapper;
        foundationRole = Arrays.asList(1, 2, 3);
    }

    /**
     * 获取所有的角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRoleVO> selectAll() {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SysRole::getRoleId, SysRole::getRoleName, SysRole::getRoleValue);
        List<SysRole> sysRoles = sysRoleMapper.selectList(wrapper);
        List<SysRoleVO> result = new ArrayList<>();
        sysRoles.forEach(s -> {
            SysRoleVO data = new SysRoleVO();
            BeanUtils.copyProperties(s, data);
            result.add(data);
        });
        return result;
    }

    /**
     * 新增角色
     *
     * @param dto 参数对象
     */
    @Override
    @Transactional
    public void addRole(SysRoleAddDTO dto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        sysRole.setState(CommonStateEnum.OK.code);
        if (dto.getPermissions().isEmpty()) {
            throw new BusinessException(R.ERROR_PERMISSION_EMPTY);
        }
        if (sysRoleMapper.insert(sysRole) < 1) {
            throw new BusinessException(R.ERROR_ADD);
        }
        sysPermissionsRoleMapper.insertByRoleId(sysRole.getRoleId(), dto.getPermissions());
    }

    /**
     * 分页查询角色信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @Override
    public SysRolePageVO selectPage(SysRolePageDTO dto) {
        SysRolePageVO result = new SysRolePageVO();
        Page<SysRole> page = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(dto.getState()), SysRole::getState, dto.getState()).like(Objects.nonNull(dto.getRoleName()), SysRole::getRoleName, dto.getRoleName()).orderByDesc(SysRole::getRoleValue).orderByDesc(SysRole::getUpdateTime);
        Page<SysRole> list = sysRoleMapper.selectPage(page, wrapper);
        result.setTotal(list.getTotal());
        list.getRecords().forEach(s -> {
            SysRoleVO data = new SysRoleVO();
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
    @Transactional
    public Integer deleteInIdList(List<Integer> idList) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysRole::getRoleId, idList).notIn(SysRole::getRoleId, foundationRole);
        int delete = sysRoleMapper.delete(wrapper);
        if (delete < 1) {
            throw new BusinessException(R.ERROR_DELETE);
        }
        LambdaQueryWrapper<SysPermissionsRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysPermissionsRole::getRoleId, idList);
        sysPermissionsRoleMapper.delete(queryWrapper);
        LambdaQueryWrapper<SysRoleUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.in(SysRoleUser::getRoleId, idList);
        sysRoleUserMapper.delete(userLambdaQueryWrapper);
        return delete;

    }

    /**
     * 变更角色信息
     *
     * @param dto 参数对象
     */
    @Override
    @Transactional
    public void updateRole(SysRoleUpdateDTO dto) {

        LambdaUpdateWrapper<SysRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRole::getRoleId, dto.getRoleId());
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        //基础角色权限值不可更改
        foundationRole.stream().filter(s -> s.equals(dto.getRoleId())).findFirst().ifPresent(s -> {
            sysRole.setRoleValue(null);
        });
        //超级管理员角色权限不可更改
        if (RoleEnum.SUPER_ADMIN.value.equals(dto.getRoleId())) {
            dto.setPermissions(null);
        }
        if (sysRoleMapper.update(sysRole, wrapper) < 1) {
            throw new BusinessException(R.ERROR_UPDATE);
        }
        if (Objects.nonNull(dto.getPermissions()) && dto.getPermissions().size() > 0) {
            LambdaQueryWrapper<SysPermissionsRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysPermissionsRole::getRoleId, dto.getRoleId());
            sysPermissionsRoleMapper.delete(queryWrapper);
            sysPermissionsRoleMapper.insertByRoleId(dto.getRoleId(), dto.getPermissions());
        }
    }
}

