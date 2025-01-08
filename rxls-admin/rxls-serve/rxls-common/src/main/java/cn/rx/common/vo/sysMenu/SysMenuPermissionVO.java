package cn.rx.common.vo.sysMenu;

import lombok.Data;

import java.util.List;

@Data
public class SysMenuPermissionVO {
    private String title;
    private String key;
    private List<SysMenuPermissionVO> children;
}
