package cn.rx.system.excelObj;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户excel对象
 */
@Getter
@Setter
@EqualsAndHashCode
@ContentRowHeight(25)
@HeadRowHeight(30)
@ColumnWidth(20)
public class ExcelAdmin {

    /**
     * 账号
     */
    @ExcelProperty({"sysAdmin", "username"})
    private String username;

    @ExcelProperty(value = {"SysUser"})
    private String string;
    /**
     * 主键id
     */
    @ExcelProperty({"sysAdmin", "id"})
    private Long userId;

    /**
     * 用户头像
     */
    @ExcelProperty({"sysAdmin", "avatar"})
    private String avatar;
    /**
     * 用户名称
     */
    @ExcelProperty({"sysAdmin", "nickname"})
    private String nickname;
    /**
     * 创建时间
     */
    @ExcelProperty({"sysAdmin", "createTime"})
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ExcelProperty({"sysAdmin", "updateTime"})
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /**
     * 状态
     */
    @ExcelProperty({"sysAdmin", "orderState"})
    private Integer state;
    /**
     * 1男  0女
     */
    @ExcelProperty({"sysAdmin", "sex"})
    private Integer sex;
    /**
     * 角色
     */
    @ExcelProperty({"sysAdmin", "sysPermissions"})
    private String role;

}
