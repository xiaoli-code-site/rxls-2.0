package cn.rx.system.service;


import cn.rx.common.vo.sysPermissions.SysPermissionsVO;
import cn.rx.db.entity.SysPermissions;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;


/**
 * 权限(Authentication)表服务接口
 *
 * @author 不想说话而已
 */
public interface SysPermissionsService extends IService<SysPermissions> {

    /**
     * 根据角色查询权限
     *
     * @param roleId 角色id
     */
    Set<SysPermissionsVO> selectAllByRoleId(Integer roleId);

    /**
     * 获取所有的权限
     *
     * @return 权限列表
     */
    List<SysPermissionsVO> selectAll();

}
