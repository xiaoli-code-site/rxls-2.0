package cn.rx.db.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 系统配置(SysConfig)表实体类
 *
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_config")
public class SysConfig {
    @TableId(type = IdType.AUTO)
    //主键    
    private Integer id;

    //key名
    private String sysKey;
    /**
     * 状态
     */
    private Integer state;
    //值
    private String sysValue;
    //描述
    private String description;
    //更新时间
    private LocalDateTime updateTime;
}
