package cn.rx.db.mapper;

import cn.rx.db.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色菜单关联表(SysRoleMenu)表数据库访问层
 *
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    /**
     * 批量插入角色菜单
     * @param roleId 角色id
     * @param ids 菜单id列表
     */
    int insertByRoleId(@Param("roleId") Integer roleId, @Param("ids") List<Integer> ids);
}

