package cn.rx.db.mapper;

import cn.rx.common.vo.admin.sysUser.SysUserVO;
import cn.rx.db.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户表(SysUser)表数据库访问层
 *
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据微信唯一标识查询用户信息
     * @param openid 微信唯一标识
     * @return 用户信息
     */
    SysUserVO selectByOpenid(@Param("openid") String openid);

    /**
     * 查询用户信息
     * @param uid 用户id
     * @return 用户信息
     */
    SysUserVO selectUserById (@Param("uid") Long uid);


    /**
     * 分页查询管理员信息
     * @param sysUser 参数对象
     * @param page 第几页
     * @param size 查几个
     * @param roleId 角色id
     * @return 用户信息
     */
    List<SysUserVO> selectPageWithRoles(@Param("user") SysUser sysUser,
                                        @Param("page") Integer page,
                                        @Param("size") Integer size,
                                        @Param("roleId") Integer roleId);


    /**
     * 总量
     * @param sysUser 参数对象
     * @param roleId 角色id
     * @return 总数
     */
    Long countPageWithRoles(@Param("user") SysUser sysUser,
                              @Param("roleId") Integer roleId);

    /**
     * 根据id查询
     * @param ids id列表
     * @return 总数
     */
    List<SysUserVO>  selectInIds(@Param("ids") List<Long> ids);

    /**
     * 根据id、权限值查询
     * @param uid 用户id
     * @param value 权限值
     * @return 总数
     */
    SysUser selectByUserRole(@Param("id") Long uid, @Param("value")Integer value);


    /**
     * 用户统计
     */
    Integer statistics();
}

