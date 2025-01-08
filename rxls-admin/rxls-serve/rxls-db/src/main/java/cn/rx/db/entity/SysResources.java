package cn.rx.db.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 静态资源(SysResources)表实体类
 *
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_resources")
public class SysResources {
    @TableId(type = IdType.AUTO)
    //静态资源    
    private Long id;

    //是否持久
    private Integer state;
    //存储类型
    private String category;
    //存储标识
    private String url;
    //使用的服务类型
    private String serve;
    //创建时间
    private Date createTime;
}
