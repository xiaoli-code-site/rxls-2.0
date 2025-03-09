package cn.rx.db.mapper;

import cn.rx.common.vo.admin.sysMenu.SysMenuVO;
import cn.rx.common.vo.admin.sysPermissions.SysMenuPermissionsVO;
import cn.rx.db.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;


/**
 * 权限(SysMenu)表数据库访问层
 *
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据角色名称列表查询菜单
     * @param names 角色名称
     */
    Set<SysMenuVO> selectByRoleNames(@Param("names") List<String> names);


    /**
     * 根据角色id列表查询菜单
     * @param ids 角色名称
     */
    Set<SysMenuVO> selectByRoleIds(@Param("ids") List<Integer> ids);


    /**
     * 查询所有菜单的权限信息
     * @return 菜单的权限信息列表
     */
    List<SysMenuPermissionsVO> selectMenuPermission();

    /**
     * 查询所有菜单信息
     * @return 菜单信息
     */
    List<SysMenuVO> selectMenu();

    /**
     * 根据角色查询菜单信息
     * @param roleId 角色id
     * @return 角色菜单信息
     */
    Set<SysMenuVO> selectRoleMenu(@Param("roleId") Integer roleId);
}

