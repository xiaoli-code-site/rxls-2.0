package cn.rx.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 角色菜单关联表(SysRoleMenu)表实体类
 *
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
public class SysRoleMenu {
    @TableId
    //主键    
    private Integer roleMenuId;

    //菜单id
    private Integer menuId;
    //角色id
    private Integer roleId;
}
