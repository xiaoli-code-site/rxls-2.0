package cn.rx.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员表(sysAdmin)表实体类
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_admin")
public class SysAdmin {
    @TableId(type = IdType.AUTO)
    /**
     * 主键id
      */
    private Long adminId;

    /**
     * 用户名、账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 逻辑删除
     */
    private Integer deleted;

}
