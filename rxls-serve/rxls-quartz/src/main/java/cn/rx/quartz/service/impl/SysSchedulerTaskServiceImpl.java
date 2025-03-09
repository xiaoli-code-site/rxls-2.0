package cn.rx.quartz.service.impl;

import cn.rx.common.dto.admin.sysSchedulerTask.SysSchedulerTaskAddDTO;
import cn.rx.common.dto.admin.sysSchedulerTask.SysSchedulerTaskPageDTO;
import cn.rx.common.dto.admin.sysSchedulerTask.SysSchedulerTaskUpdateDTO;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.common.util.HttpUtil;
import cn.rx.common.vo.admin.sysSchedulerTask.SysSchedulerTaskPageVO;
import cn.rx.common.vo.admin.sysSchedulerTask.SysSchedulerTaskVO;
import cn.rx.core.exception.BusinessException;
import cn.rx.db.entity.SysSchedulerTask;
import cn.rx.db.mapper.SysSchedulerTaskMapper;
import cn.rx.quartz.config.SchedulerTaskContext;
import cn.rx.quartz.entity.SchedulerTask;
import cn.rx.quartz.service.SysSchedulerTaskService;
import cn.rx.quartz.util.TriggerUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 定时任务(SysSchedulerTaskVO)表服务实现类
 */
@Service("sysSchedulerTaskService")
public class SysSchedulerTaskServiceImpl extends ServiceImpl<SysSchedulerTaskMapper, SysSchedulerTask> implements SysSchedulerTaskService {

    private final SysSchedulerTaskMapper sysSchedulerTaskMapper;

    private final SchedulerTaskContext schedulerTaskContext;

    SysSchedulerTaskServiceImpl(SysSchedulerTaskMapper sysSchedulerTaskMapper, SchedulerTaskContext schedulerTaskContext) {
        this.sysSchedulerTaskMapper = sysSchedulerTaskMapper;
        this.schedulerTaskContext = schedulerTaskContext;
    }

    /**
     * 变更定时任务信息
     *
     * @param dto 参数对象
     */
    @Override
    public void updateTask(SysSchedulerTaskUpdateDTO dto) {
        LambdaUpdateWrapper<SysSchedulerTask> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysSchedulerTask::getSchedulerTaskId, dto.getSchedulerTaskId());
        SysSchedulerTask schedulerTask = new SysSchedulerTask();
        BeanUtils.copyProperties(dto, schedulerTask);
        if (sysSchedulerTaskMapper.update(schedulerTask, wrapper) < 1) {
            throw new BusinessException(R.ERROR_UPDATE);
        }
        if (CommonStateEnum.OK.code.equals(dto.getState())) {
            SchedulerTask task = new SchedulerTask();
            BeanUtils.copyProperties(dto, task);
            task.setScheduleBuilder(TriggerUtil.get(task.getModel(),task.getExpression(),task.getUnit()));
            schedulerTaskContext.resetTask(task);
        }


    }

    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    @Override
    public Integer deleteInIdList(List<Integer> idList) {
        LambdaQueryWrapper<SysSchedulerTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysSchedulerTask::getSchedulerTaskId, idList);
        List<SysSchedulerTask> sysSchedulerTasks = sysSchedulerTaskMapper.selectList(wrapper);
        if (sysSchedulerTasks.isEmpty()) {
            return 0;
        }
        int delete = sysSchedulerTaskMapper.delete(wrapper);
        if (delete < 1) {
            throw new BusinessException(R.ERROR_DELETE);
        }
        sysSchedulerTasks.forEach(s -> {
            schedulerTaskContext.delTask(s.getJobCode(), s.getJobName(), s.getGroupName());
        });
        return delete;
    }

    /**
     * 分页查询定时任务信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @Override
    public SysSchedulerTaskPageVO selectPage(SysSchedulerTaskPageDTO dto) {
        SysSchedulerTaskPageVO result = new SysSchedulerTaskPageVO();
        Long count = sysSchedulerTaskMapper.countSysSchedulerTask(dto.getUsername(),dto.getJobName(),dto.getGroupName());
        if (count.equals(0L)) {
            return result;
        }
        result.setTotal(count);
        dto.setPage((dto.getPage() - 1) * dto.getSize());
        List<SysSchedulerTaskVO> list = sysSchedulerTaskMapper.selectPageSysSchedulerTask(dto.getPage(), dto.getSize(), dto.getUsername(), dto.getJobName(),dto.getGroupName());
        list.forEach(s -> {
            SysSchedulerTaskVO data = new SysSchedulerTaskVO();
            BeanUtils.copyProperties(s, data);
            result.getDataList().add(data);
        });
        return result;
    }

    /**
     * 新增定时任务
     *
     * @param dto 参数对象
     */
    @Override
    public void addTask(SysSchedulerTaskAddDTO dto) {
        LambdaQueryWrapper<SysSchedulerTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSchedulerTask::getJobName,dto.getJobName()).eq(SysSchedulerTask::getGroupName,dto.getGroupName());
        SysSchedulerTask info = sysSchedulerTaskMapper.selectOne(wrapper);
        if(Objects.nonNull(info)){
            throw new BusinessException(R.ERROR_ADD);
        }
        SysSchedulerTask schedulerTask = new SysSchedulerTask();
        BeanUtils.copyProperties(dto, schedulerTask);
        String subject = Objects.requireNonNull(HttpUtil.getUserLoginInfo()).getSubject();
        schedulerTask.setUserId(Long.valueOf(subject));
        if (sysSchedulerTaskMapper.insert(schedulerTask) < 1) {
            throw new BusinessException(R.ERROR_ADD);
        }
    }

    /**
     * 、
     * 任务状态变更
     *
     * @param id 任务id
     */
    @Override
    public void stateUpdate(Integer id) {
        LambdaQueryWrapper<SysSchedulerTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysSchedulerTask::getSchedulerTaskId, id);
        SysSchedulerTask info = sysSchedulerTaskMapper.selectOne(queryWrapper);
        Optional.ofNullable(info).orElseThrow(() -> new BusinessException(R.ERROR_TASK_NO));
        LambdaUpdateWrapper<SysSchedulerTask> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysSchedulerTask::getSchedulerTaskId, id);
        info.setState(info.getState().equals(CommonStateEnum.OK.code) ? CommonStateEnum.ERROR.code : CommonStateEnum.OK.code);
        if (sysSchedulerTaskMapper.update(info, wrapper) < 1) {
            throw new BusinessException(R.ERROR_UPDATE);
        }
        if (CommonStateEnum.OK.code.equals(info.getState())) {
            SchedulerTask task = new SchedulerTask();
            BeanUtils.copyProperties(info, task);
            task.setScheduleBuilder(TriggerUtil.get(task.getModel(),task.getExpression(),task.getUnit()));
            schedulerTaskContext.resumeTask(task);
        } else {
            schedulerTaskContext.pauseTask(info.getJobCode(), info.getJobName(), info.getGroupName());
        }
    }
}

