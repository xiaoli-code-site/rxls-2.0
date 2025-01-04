package cn.rx.common.util;

import cn.rx.common.enums.R;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 导出工具类
 */
public class ExcelUtil {

    /**
     * 导出excel文件
     * @param response HttpServletResponse
     * @param fieldList 需要导出的导出字段
     * @param dataList 数据列表
     * @param type excel类型或样式
     */
    public static <T> void export(HttpServletResponse response, List<String> fieldList, List<List<T>> dataList, Class<?> type) throws IOException {

        try (OutputStream outputStream = response.getOutputStream()) {

            // 根据用户传入需要导出的字段
            Set<String> includeColumnFiledNames = new HashSet<>(fieldList);

            //必须放到循环外，否则会刷新流
            ExcelWriter excelWriter = EasyExcel.write(outputStream).includeColumnFieldNames(includeColumnFiledNames).build();

            //开始分批查询分次写入
            for (int i = 0; i < dataList.size(); i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "Sheet" + (i + 1)).head(type).build();
                excelWriter.write(dataList.get(i), writeSheet);
            }

            // 下载EXCEL，返回给前段stream流
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + "sysUser.xlsx");
            excelWriter.finish();
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, Object> map = MapUtils.newHashMap();
            map.put("code", R.ERROR_EXPORT_EXCEL.code);
            map.put("message", R.ERROR_EXPORT_EXCEL.msg);
            map.put("data", null);
            response.getWriter().println(JSON.toJSONString(map));
        }

    }

}

