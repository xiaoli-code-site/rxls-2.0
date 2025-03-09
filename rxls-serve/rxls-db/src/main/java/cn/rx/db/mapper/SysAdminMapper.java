package cn.rx.db.mapper;

import cn.rx.common.vo.admin.sysAdmin.SysAdminVO;
import cn.rx.db.entity.SysAdmin;
import cn.rx.db.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色用户关联表(sysAdmin)表数据库访问层
 *
 * @author 不想说话而已
 */
@Mapper
public interface SysAdminMapper extends BaseMapper<SysAdmin> {



    /**
     * 分页查询管理员信息
     * @param sysUser 参数对象
     * @param username 用户名
     * @param page 第几页
     * @param size 查几个
     * @param roleId 角色id
     * @return 管理员信息
     */
    List<SysAdminVO> selectPageAdmin(@Param("sysUser") SysUser sysUser,
                                     @Param("username") String username,
                                     @Param("page") Integer page,
                                     @Param("size") Integer size,
                                     @Param("roleId") Integer roleId);

    /**
     * 总量
     * @param sysUser 参数对象
     * @param username 用户名
     * @param roleId 角色id
     * @return 管理员信息
     */
    Long selectCountPageAdmin(@Param("sysUser") SysUser sysUser,
                              @Param("username") String username,
                              @Param("roleId") Integer roleId);



    /**
     * 根据id查询
     * @param ids id列表
     * @return 总数
     */
    List<SysAdminVO>  selectInIds(@Param("ids") List<Long> ids);
}

