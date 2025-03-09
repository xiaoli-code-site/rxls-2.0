package cn.rx.system.service;



import cn.rx.common.dto.admin.sysRole.SysRoleAddDTO;
import cn.rx.common.dto.admin.sysRole.SysRolePageDTO;
import cn.rx.common.dto.admin.sysRole.SysRoleUpdateDTO;
import cn.rx.common.vo.admin.sysRole.SysRolePageVO;
import cn.rx.common.vo.admin.sysRole.SysRoleVO;
import cn.rx.db.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 角色(SysRole)表服务接口
 *
 * @author 不想说话而已
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 获取所有的角色
     *
     * @return 角色列表
     */
    List<SysRoleVO> selectAll();

    /**
     * 新增角色(sysAdmin)
     *
     * @param dto 参数对象
     */
    void addRole(SysRoleAddDTO dto);


    /**
     * 分页查询角色信息(sysAdmin)
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    SysRolePageVO selectPage(SysRolePageDTO dto);

    /**
     * 根据id列表删除(sysAdmin)
     *
     * @param idList id列表
     * @return 成功数
     */
    Integer deleteInIdList(List<Integer> idList);

    /**
     * 变更角色信息(sysAdmin)
     *
     * @param dto 参数对象
     */
    void updateRole(SysRoleUpdateDTO dto);
}
