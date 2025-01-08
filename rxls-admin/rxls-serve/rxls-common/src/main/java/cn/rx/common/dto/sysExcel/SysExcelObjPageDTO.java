package cn.rx.common.dto.sysExcel;


import lombok.Data;

/**
 * 导出excel分页导出参数
 */
@Data
public class SysExcelObjPageDTO {
    /**
     * 一共导出几页
     */
    private Integer page;

    /**
     * 每个sheet文件几条数据
     */
    private Integer size;
}
