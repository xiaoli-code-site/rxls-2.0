package cn.rx.db.mapper;

import cn.rx.common.vo.sysPermissions.SysPermissionsVO;
import cn.rx.db.entity.SysPermissions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;


/**
 * 权限(sysPermissions)表数据库访问层
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
     * 根据角色查询权限
     *
     * @param roleName 角色名称列表
     * @return 权限列表
     */
    Set<SysPermissions> selectByRoleName(@Param("names") List<String> roleName);

}
