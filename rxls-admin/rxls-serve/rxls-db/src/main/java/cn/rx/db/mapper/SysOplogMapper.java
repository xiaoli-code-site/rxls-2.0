package cn.rx.db.mapper;

import cn.rx.common.vo.sysOplog.SysOplogVO;
import cn.rx.db.entity.SysOplog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 操作日志(SysOplog)表数据库访问层
 *
 */
@Mapper
public interface SysOplogMapper extends BaseMapper<SysOplog> {

    /**
     * 分页查询 日志信息
     * @param page 第几页
     * @param size 查几个
     * @param leve 等级
     */
    List<SysOplogVO> selectOplogPage(@Param("page") Integer page, @Param("size") Integer size, @Param("leve") Integer leve);

    /**
     * 日志信息总数
     * @param leve 等级
     */
    long countOplogPage(@Param("leve") Integer leve);
}

