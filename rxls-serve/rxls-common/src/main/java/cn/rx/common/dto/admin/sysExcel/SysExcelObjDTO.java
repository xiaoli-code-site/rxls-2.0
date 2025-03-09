package cn.rx.common.dto.admin.sysExcel;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 导出文件下载
 */
@Data
public class SysExcelObjDTO<T> {

    /**
     * 文件名
     */
    @NotBlank(message = "{filenameNotEmpty}")
    private String fileName;

    /**
     * 导出的字段
     */
    @NotNull(message = "{exportFieldNotEmpty}")
    private List<String> fieldList;


    /**
     * 指定导出的数据id列表
     */
    private List<Long> idList;

    /**
     * 导出条件参数
     */
    private SysExcelObjPageDTO pageQuery;

    /**
     * 是否中文
     */
    @NotNull(message = "{languageTypeNotEmpty}")
    private boolean lang;

    /**
     * 查询参数
     */
    private T query;
}
