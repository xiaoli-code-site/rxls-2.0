package cn.rx.db.mapper;

import cn.rx.common.vo.admin.sysPermissions.SysPermissionsVO;
import cn.rx.db.entity.SysPermissions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;


/**
 * 权限(sysRole)表数据库访问层
 *
 */
@Mapper
public interface SysPermissionsMapper extends BaseMapper<SysPermissions> {

    /**
     * 根据角色id查询权限
     *
     * @param roleId 角色id
     * @return 权限列表
     */
    Set<SysPermissionsVO> selectAllByRoleId(@Param("roleId") Integer roleId);

    /**
     * 根据角色id列表查询权限
     *
     * @param ids 角色id列表
     * @return 权限列表
     */
    Set<SysPermissions> selectByRoleIdList(@Param("ids") List<Integer> ids);

    /**
     * 根据角色查询权限
     *
     * @param roleName 角色名称列表
     * @return 权限列表
     */
    Set<SysPermissions> selectByRoleName(@Param("names") List<String> roleName);

}
