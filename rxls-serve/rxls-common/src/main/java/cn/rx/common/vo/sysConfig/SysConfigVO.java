package cn.rx.common.vo.sysConfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


/**
 * 系统配置
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysConfigVO {
    /**
     * 主键
      */
    private Integer id;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 键名
     */
    private String sysKey;
    /**
     * 值
     */
    private String sysValue;
    /**
     * 描述
     */
    private String description;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
