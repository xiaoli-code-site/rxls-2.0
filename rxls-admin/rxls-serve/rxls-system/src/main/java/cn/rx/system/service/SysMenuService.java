package cn.rx.system.service;


import cn.rx.common.vo.sysMenu.SysMenuVO;
import cn.rx.common.vo.sysPermissions.SysMenuPermission;
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
    List<SysMenuPermission> selectAllMenuPermission();
}
