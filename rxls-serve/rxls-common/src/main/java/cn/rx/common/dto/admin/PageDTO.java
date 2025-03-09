package cn.rx.common.dto.admin;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;



/**
 * 通用分页查询
 */
@Data
public class PageDTO {
    /**
     * 第几页
     */
    @Min(value = 1,message = "{illegalPageNumbers}")
    @NotNull(message = "{pageNumbersNoEmpty}")
    private  Integer page;

    /**
     * 查几个
     */

    @NotNull(message = "{numberQueriesNotEmpty}")
    private  Integer size;
}
