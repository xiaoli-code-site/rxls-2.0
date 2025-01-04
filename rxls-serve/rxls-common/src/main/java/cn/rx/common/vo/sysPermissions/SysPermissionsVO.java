package cn.rx.common.vo.sysPermissions;


import lombok.Data;

import java.util.Date;

/**
 * 权限
 */
@Data
public class SysPermissionsVO {
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
