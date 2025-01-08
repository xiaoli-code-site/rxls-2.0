package cn.rx.db.mapper;

import cn.rx.db.entity.SysPermissionsRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * (SysPermissionsRole)表数据库访问层
 *
 */
@Mapper
public interface SysPermissionsRoleMapper extends BaseMapper<SysPermissionsRole> {

    /**
     * 批量插入角色权限
     * @param roleId 角色id
     * @param ids 权限id列表
     */
    int insertByRoleId(@Param("roleId") Integer roleId, @Param("ids") List<Integer> ids);
}

