package cn.rx.common.vo.admin.sysUser;

import cn.rx.common.vo.admin.sysRole.SysRoleVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


/**
 * 用户信息
 * @author 不想说话而已
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUserVO {

    /**
     * 主键id
     */
    private Long userId;

    /**
     * 微信唯一标识
     */
    private String openid;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date born;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户名称
     */
    private String nickname;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 状态
     */
    private Integer state;
    /**
     * 1男  0女
     */
    private Integer sex;

    /**
     * 用户角色
     */
    private List<SysRoleVO> roles;

}
