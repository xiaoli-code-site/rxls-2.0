package cn.rx.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 菜单(SysMenu)表实体类
 *
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
public class SysMenu {
    @TableId(type = IdType.AUTO)
    //主键id    
    private Integer menId;

    //描述
    private String title;
    //权限值
    private String path;
    //父级id
    private Integer parentId;
    //图标icon
    private String icon;
    //排序
    private Integer sort;
    //状态
    private Integer state;
    //更新时间
    private Date updateTime;
    //创建时间
    private Date createTime;
    //路径
    private String src;

    /**
     * 子菜单
     */
    private List<SysMenu> sysMenus;

}
