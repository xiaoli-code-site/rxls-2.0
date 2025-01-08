package cn.rx.system.service.impl;


import cn.rx.common.vo.sysMenu.SysMenuVO;
import cn.rx.common.vo.sysPermissions.SysMenuPermission;
import cn.rx.db.entity.SysMenu;
import cn.rx.db.mapper.SysMenuMapper;
import cn.rx.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 菜单(SysMenu)表服务实现类
 *
 */
@Service("menusService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;

    SysMenuServiceImpl(SysMenuMapper sysMenuMapper){
        this.sysMenuMapper = sysMenuMapper;
    }

    /**
     * 根据角色查询所有菜单
     *
     * @param roleNames 角色名称列表
     * @return 菜单列表
     */
    @Override
    public Set<SysMenuVO> selectAllByRoleName(List<String> roleNames) {
        return  sysMenuMapper.selectByRoleName(roleNames);
    }

    /**
     * 查询所有菜单权限信息
     */
    @Override
    public List<SysMenuPermission> selectAllMenuPermission() {
        return sysMenuMapper.selectMenuPermission();
    }
}

