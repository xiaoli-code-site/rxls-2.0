package cn.rx.common.vo.admin.sysMenu;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 菜单
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysMenuVO {

    //主键id
    private Integer menuId;
    //描述
    private String title;
    //权限值
    private String path;
    //父级id
    private Integer parentId;
    //图标icon
    private String icon;
    //路径
    private String src;
    //排序
    private Integer sort;
    //状态
    private Integer state;
    //更新时间
    private Date updateTime;
    //创建时间
    private Date createTime;

    /**
     * 子菜单
     */
    private List<SysMenuVO> menus;

}
