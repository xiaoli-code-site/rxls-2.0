package cn.rx.system.service;


import cn.rx.common.dto.admin.sysExcel.SysExcelUserDTO;
import cn.rx.common.dto.admin.sysUser.SysUserAddDTO;
import cn.rx.common.dto.admin.sysUser.SysUserPageDTO;
import cn.rx.common.dto.admin.sysUser.SysUserUpdateDTO;
import cn.rx.common.vo.admin.sysUser.StatisticsVO;
import cn.rx.common.vo.admin.sysUser.SysUserPageVO;
import cn.rx.db.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用户表(SysUser)表服务接口
 * @author 不想说话而已
 */
public interface SysUserService extends IService<SysUser> {



    /**
     * 新增用户
     *
     * @param dto 参数对象
     */
    void addUser(SysUserAddDTO dto);


    /**
     * 分页查询用户信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    /**
     * 分页查询用户信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    SysUserPageVO selectPage(SysUserPageDTO dto);


    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    Integer deleteInIdList(List<Long> idList);


    /**
     * 变更用户信息
     *
     * @param user 用户参数
     */
    void updateUser(SysUserUpdateDTO user);



    /**
     * 导出报表
     *
     * @param excelObj 导出对象
     * @param response 网络请求对象
     */
    void export(SysExcelUserDTO excelObj, HttpServletResponse response) throws IOException;

    /**
     * 检查用户
     *
     * @param uid 用户id
     * @param value 权限值
     */
    Boolean checkUser(Long  uid, Integer value);

    /**
     * 用户统计
     *
     */
    StatisticsVO statistics();
}
