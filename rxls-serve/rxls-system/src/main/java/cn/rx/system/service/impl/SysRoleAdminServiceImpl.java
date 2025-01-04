package cn.rx.system.service.impl;


import cn.rx.db.entity.SysRoleUser;
import cn.rx.db.mapper.SysRoleUserMapper;
import cn.rx.system.service.SysRoleAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色用户关联表(SysRoleUser)表服务实现类
 *
 */
@Service("roleAdminService")
public class SysRoleAdminServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements SysRoleAdminService {

}

