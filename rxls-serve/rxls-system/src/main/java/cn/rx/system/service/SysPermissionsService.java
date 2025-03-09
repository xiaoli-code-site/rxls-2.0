package cn.rx.system.service;


import cn.rx.common.dto.admin.sysPermissions.SysPermissionsAddDTO;
import cn.rx.common.dto.admin.sysPermissions.SysPermissionsPageDTO;
import cn.rx.common.dto.admin.sysPermissions.SysPermissionsUpdateDTO;
import cn.rx.common.vo.admin.sysPermissions.SysPermissionsPageVO;
import cn.rx.common.vo.admin.sysPermissions.SysPermissionsVO;
import cn.rx.db.entity.SysPermissions;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;


/**
 * 权限(Permissions)表服务接口
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

    /**
     * 新增
     *
     * @param dto 参数对象
     */
    void add(SysPermissionsAddDTO dto);

    /**
     * 分页查询信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    SysPermissionsPageVO selectPage(SysPermissionsPageDTO dto);

    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    Integer deleteInIdList(List<Integer> idList);

    /**
     * 变更信息
     *
     * @param dto 参数对象
     */
    void alter(SysPermissionsUpdateDTO dto);
}
