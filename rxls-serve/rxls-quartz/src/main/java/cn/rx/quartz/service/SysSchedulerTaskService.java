package cn.rx.quartz.service;


import cn.rx.common.dto.admin.sysSchedulerTask.SysSchedulerTaskAddDTO;
import cn.rx.common.dto.admin.sysSchedulerTask.SysSchedulerTaskPageDTO;
import cn.rx.common.dto.admin.sysSchedulerTask.SysSchedulerTaskUpdateDTO;
import cn.rx.common.vo.admin.sysSchedulerTask.SysSchedulerTaskPageVO;
import cn.rx.db.entity.SysSchedulerTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 定时任务(SysSchedulerTaskVO)表服务接口
 */
public interface SysSchedulerTaskService extends IService<SysSchedulerTask> {

    /**
     * 变更定时任务信息
     *
     * @param dto 参数对象
     */
    void updateTask(SysSchedulerTaskUpdateDTO dto);


    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    Integer deleteInIdList(List<Integer> idList);


    /**
     * 分页查询定时任务信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    SysSchedulerTaskPageVO selectPage(SysSchedulerTaskPageDTO dto);


    /**
     * 新增定时任务
     *
     * @param dto 参数对象
     */
    void addTask(SysSchedulerTaskAddDTO dto);


    /**
     * 任务状态变更
     *
     * @param id 任务id
     */
    void stateUpdate(Integer id);
}
