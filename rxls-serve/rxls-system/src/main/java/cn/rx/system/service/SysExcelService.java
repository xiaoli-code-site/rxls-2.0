package cn.rx.system.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * excel导出接口
 * @author 不想说话而已
 */
public interface SysExcelService<T> {
    /**
     * 导出报表
     *
     * @param excelObj 导出对象
     * @param response 网络请求对象
     */
     void export(T excelObj, HttpServletResponse response) throws IOException;
}
