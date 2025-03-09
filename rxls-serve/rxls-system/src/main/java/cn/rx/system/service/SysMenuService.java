package cn.rx.system.service;


import cn.rx.common.vo.admin.sysMenu.SysAllMenuPermissionVO;
import cn.rx.common.vo.admin.sysMenu.SysMenuVO;
import cn.rx.common.vo.admin.sysPermissions.SysMenuPermissionsVO;
import cn.rx.db.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;


/**
 * 权限(SysMenu)表服务接口
 *
 * @author 不想说话而已
 */
@Mapper
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查询所有菜单
     * @param roleNames 角色名称列表
     */
    Set<SysMenuVO> selectAllByRoleName(List<String> roleNames);

    /**
     * 查询所有菜单权限信息
     */
    SysAllMenuPermissionVO selectAllMenuPermission();


    /**
     * 根据角色查询菜单权限信息
     *
     * @return 数据列表
     */
    Set<SysMenuVO> selectRoleMenuPermission(Integer roleId);
}
