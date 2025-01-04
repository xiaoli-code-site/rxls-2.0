package cn.rx.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 权限(sysPermissions)表实体类
 *
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_permissions")
public class SysPermissions {
    @TableId(type = IdType.AUTO)
    //主键id    
    private Integer permissionsId;

    //描述
    private String description;
    //权限值
    private String url;
    //状态
    private Integer state;
    //更新时间
    private Date updateTime;
    //创建时间
    private Date createTime;
}
