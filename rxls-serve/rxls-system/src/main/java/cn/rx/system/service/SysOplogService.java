package cn.rx.system.service;


import cn.rx.common.dto.admin.sysOplog.SysOplogPageDTO;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.vo.admin.sysOplog.SysOplogPageVO;
import cn.rx.db.entity.SysOplog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 操作日志(SysOplog)表服务接口
 */
public interface SysOplogService extends IService<SysOplog> {

    /**
     * 登录日志
     *
     * @param userId      用户id
     * @param state       状态
     * @param description 描述信息
     * @param msg         返回信息
     */
    void login(Long userId, CommonStateEnum state, String description, String msg);


    /**
     * 分页查询日志信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    SysOplogPageVO selectPage(SysOplogPageDTO dto);


    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    Integer deleteInIdList(List<Long> idList);
}
