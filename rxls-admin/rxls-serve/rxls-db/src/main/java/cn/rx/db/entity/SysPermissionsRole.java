package cn.rx.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (SysPermissionsRole)表实体类
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_permissions_role")
public class SysPermissionsRole {
    @TableId(type = IdType.AUTO)
    //主键id    
    private Long id;

    //权限id
    private Integer permissionsId;
    //角色id
    private Integer roleId;
}
