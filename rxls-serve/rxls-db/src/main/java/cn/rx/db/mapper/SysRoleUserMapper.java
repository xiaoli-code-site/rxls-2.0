package cn.rx.db.mapper;


import cn.rx.db.entity.SysRoleUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色用户关联表(SysRoleUser)表数据库访问层
 *
 */
@Mapper
public interface SysRoleUserMapper extends BaseMapper<SysRoleUser> {

    /**
     * 批量插入用户角色
     * @param uid 用户ID
     * @param roleId 角色id
     */
    int insertByUserId(@Param("uid") Long uid,@Param("roleId") List<Integer> roleId);

}

