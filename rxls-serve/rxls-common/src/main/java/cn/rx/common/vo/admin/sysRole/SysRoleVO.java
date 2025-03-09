package cn.rx.common.vo.admin.sysRole;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


/**
 * 角色
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRoleVO {
    /**
     * 主键id
     */
    private Integer roleId;

    /**
     * 角色名
     */
    private String roleName;


    /**
     * 状态
     */
    private Integer state;


    /**
     * 权限值
     */
    private Integer roleValue;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
