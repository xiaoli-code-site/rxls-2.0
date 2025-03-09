package cn.rx.system.service;


import cn.rx.common.dto.admin.sysAdmin.SysAdminAddDTO;
import cn.rx.common.dto.admin.sysAdmin.SysAdminPageDTO;
import cn.rx.common.dto.admin.sysAdmin.SysAdminUpdateDTO;
import cn.rx.common.dto.admin.sysExcel.SysSysExcelAdminDTO;
import cn.rx.common.vo.admin.sysAdmin.SysAdminPageVO;
import cn.rx.db.entity.SysAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * 角色用户关联表(sysAdmin)表服务接口
 *
 * @author 不想说话而已
 */
public interface SysAdminService extends IService<SysAdmin> {

    /**
     * 新增管理员
     * @param sysAdminAddDTO 参数对象
     */
    void addAdmin(SysAdminAddDTO sysAdminAddDTO);

    /**
     * 分页查询管理员信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    SysAdminPageVO selectPage(SysAdminPageDTO dto);


    /**
     * 根据id列表删除(sysAdmin)
     *
     * @param idList id列表
     * @return 成功数
     */
    Integer deleteInIdList(List<Long> idList);


    /**
     * 变更管理员信息
     *
     * @param user 参数对象
     */
    void updateAdmin(SysAdminUpdateDTO user);


    /**
     * 变更管理员自己的信息
     *
     * @param user 参数对象
     */
    void updateInfo(SysAdminUpdateDTO user);

    /**
     * 导出报表
     *
     * @param excelObj 导出对象
     * @param response 网络请求对象
     */
    void export(SysSysExcelAdminDTO excelObj, HttpServletResponse response) throws IOException;
}
