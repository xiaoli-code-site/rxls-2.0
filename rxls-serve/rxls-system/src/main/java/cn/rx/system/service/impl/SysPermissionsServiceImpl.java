package cn.rx.system.service.impl;


import cn.rx.common.vo.sysPermissions.SysPermissionsVO;
import cn.rx.db.entity.SysPermissions;
import cn.rx.db.mapper.SysPermissionsMapper;
import cn.rx.system.service.SysPermissionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 权限(Authentication)表服务实现类
 */
@Service("permissionsService")
public class SysPermissionsServiceImpl extends ServiceImpl<SysPermissionsMapper, SysPermissions> implements SysPermissionsService {

    private SysPermissionsMapper sysPermissionsMapper;

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

}

