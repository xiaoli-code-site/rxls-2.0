package cn.rx.system.service.impl;


import cn.rx.db.entity.SysPermissionsRole;
import cn.rx.db.mapper.SysPermissionsRoleMapper;
import cn.rx.system.service.SysPermissionsRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (SysPermissionsRole)表服务实现类
 *
 */
@Service("permissionsRoleService")
public class SysPermissionsRoleServiceImpl extends ServiceImpl<SysPermissionsRoleMapper, SysPermissionsRole> implements SysPermissionsRoleService {

}

