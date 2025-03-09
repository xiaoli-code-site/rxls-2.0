package cn.rx.common.vo.admin.sysPermissions;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 权限
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysPermissionsVO {
    /**
     * 主键id
     */
    private Integer permissionsId;
    /**
     * 描述
     */
    private String description;
    /**
     * 权限值
     */
    private String url;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 子权限
     */
    private List<SysPermissionsVO> menus;
}
