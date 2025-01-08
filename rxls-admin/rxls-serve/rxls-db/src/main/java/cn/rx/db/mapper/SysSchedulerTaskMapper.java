package cn.rx.db.mapper;

import cn.rx.common.vo.sysSchedulerTask.SysSchedulerTaskVO;
import cn.rx.db.entity.SysSchedulerTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 定时任务(SysSchedulerTaskVO)表数据库访问层
 *
 */
@Mapper
public interface SysSchedulerTaskMapper extends BaseMapper<SysSchedulerTask> {

    /**
     * 分页查询任务
     * @param page 第几页
     * @param size 查几个
     * @param username 发布人
     * @param jobName 任务名称
     * @param groupName 分组名称
     * @return 任务信息
     */
    List<SysSchedulerTaskVO> selectPageSysSchedulerTask(@Param("page") Integer page,
                                                        @Param("size") Integer size,
                                                        @Param("username") String username,
                                                        @Param("jobName") String jobName,
                                                        @Param("groupName") String groupName);

    /**
     * 统计任务量
     * @param username 发布人
     * @param jobName 任务名称
     * @param groupName 分组名称
     * @return 任务信息
     */
    Long countSysSchedulerTask(@Param("username") String username, @Param("jobName") String jobName,
                               @Param("groupName") String groupName);
}

