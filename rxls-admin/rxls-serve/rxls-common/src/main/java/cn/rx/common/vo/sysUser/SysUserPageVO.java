package cn.rx.common.vo.sysUser;

import cn.rx.common.vo.PageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询用户信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserPageVO extends PageVO<SysUserVO> {

}
