package cn.rx.system.service.impl;


import cn.rx.common.vo.admin.sysMenu.SysAllMenuPermissionVO;
import cn.rx.common.vo.admin.sysMenu.SysMenuVO;
import cn.rx.common.vo.admin.sysPermissions.SysMenuPermissionsVO;
import cn.rx.db.entity.SysMenu;
import cn.rx.db.mapper.SysMenuMapper;
import cn.rx.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        return  sysMenuMapper.selectByRoleNames(roleNames);
    }

    /**
     * 查询所有菜单权限信息
     */
    @Override
    public SysAllMenuPermissionVO selectAllMenuPermission() {
        SysAllMenuPermissionVO data = new SysAllMenuPermissionVO();
        List<SysMenuVO> menus = sysMenuMapper.selectMenu();
        List<SysMenuPermissionsVO> permissions = sysMenuMapper.selectMenuPermission();
        data.setPermissions(permissions);
        data.setMenus(new ArrayList<>(menus.stream().collect(Collectors.toMap(SysMenuVO::getMenuId, Function.identity(), (p1, p2) -> p1))
                .values()));
        return  data;
    }

    /**
     * 根据角色查询菜单权限信息
     *
     * @param roleId 角色id
     * @return 数据列表
     */
    @Override
    public Set<SysMenuVO> selectRoleMenuPermission(Integer roleId) {
        return sysMenuMapper.selectRoleMenu(roleId);

    }
}

