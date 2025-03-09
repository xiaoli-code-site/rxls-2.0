package cn.rx.db.mapper;

import cn.rx.db.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色(SysRole)表数据库访问层
 *
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户id查询角色信息
     * @param userId 用户id
     * @return 角色信息
     */
    List<SysRole> selectByUserId(@Param("userId") Long userId);



    /**
     * 根据id列表查询角色信息
     * @param idList id列表
     * @return 角色信息列表
     */
    List<SysRole> selectInUserId(@Param("idList") List<Long> idList);



}

