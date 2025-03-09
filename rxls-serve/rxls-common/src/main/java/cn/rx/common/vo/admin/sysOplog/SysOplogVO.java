package cn.rx.common.vo.admin.sysOplog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


/**
 * 用户信息
 * @author 不想说话而已
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysOplogVO {

    //主键
    private Long id;
    //用户名
    private String username;
    //归属地址
    private String address;
    //用户id
    private Long userId;
    //结果1 成功   0失败
    private Integer state;
    //操作类型
    private Integer category;
    //描述
    private String description;
    //耗时
    private long times;
    //操作参数
    private String query;
    //模块
    private String model;
    //ip地址
    private String ip;
    //操作结果
    private String result;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;



}
