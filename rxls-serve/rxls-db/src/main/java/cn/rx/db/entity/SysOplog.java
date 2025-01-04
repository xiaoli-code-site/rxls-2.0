package cn.rx.db.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 操作日志(SysOplog)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_oplog")
public class SysOplog {
    @TableId(type = IdType.AUTO)
    //主键    
    private Long id;

    //用户id
    private Long userId;
    //结果1 成功   0失败
    private Integer state;
    //操作类型
    private Integer category;
    //描述
    private String description;
    //归属地址
    private String address;
    //模块
    private String model;
    //耗时
    private long times;
    //操作参数
    private String query;
    //ip地址
    private String ip;
    //操作结果
    private String result;
    //创建时间
    private Date createTime;
}
