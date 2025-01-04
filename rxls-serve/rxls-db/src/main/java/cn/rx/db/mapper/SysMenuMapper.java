package cn.rx.db.mapper;

import cn.rx.common.vo.sysMenu.SysMenuVO;
import cn.rx.common.vo.sysPermissions.SysMenuPermission;
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
     * 根据角色查询菜单
     * @param names 角色名称
     */
    Set<SysMenuVO> selectByRoleName(@Param("names") List<String> names);


    /**
     * 查询菜单权限信息
     * @return 菜单权限信息列表
     */
    List<SysMenuPermission> selectMenuPermission();
}

