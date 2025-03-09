package cn.rx.auth.controller;

import cn.rx.common.dto.admin.sysLogin.SysLoginAccountDTO;
import cn.rx.common.dto.admin.sysLogin.LoginWXDTO;
import cn.rx.common.result.Result;
import cn.rx.common.vo.login.LoginUserVO;
import cn.rx.common.vo.login.SysLoginAdminVO;
import cn.rx.auth.service.impl.AccountLoginImpl;
import cn.rx.auth.service.impl.WXLoginImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * 登录
 * @author 不想说话而已
 */
@RestController
@Valid
public class LoginController {

    @Resource
    private WXLoginImpl wxLogin;

    @Resource
    private AccountLoginImpl adminLogin;

    /**
     * 微信登录
     * @param dto 参数对象
     * @return 登录信息
     */
    @PostMapping("/login/wx")
    public Result<LoginUserVO> wxLogin(@RequestBody LoginWXDTO dto){
        return Result.success(wxLogin.handler(dto));
    }

    /**
     * 账号登录
     * @param dto 参数对象
     * @return 登录信息
     */
    @PostMapping("/login/account")
    public Result<SysLoginAdminVO> accountLogin(@Valid @RequestBody SysLoginAccountDTO dto){
        return Result.success(adminLogin.handler(dto));
    }


    /**
     * 退出登录
     */
    @GetMapping("/logout/account")
    public Result<?> accountLogin(){
        adminLogin.logout();
        return Result.success();
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout/wx")
    public Result<?> wxLogin(){
        wxLogin.logout();
        return Result.success();
    }


    /**
     * wx登录token恢复用户信息
     * @return 用户登录信息
     */
    @GetMapping("/wx/token/info")
    public Result<LoginUserVO> userInfoByToken() {
        return Result.success(wxLogin.userInfoByToken());
    }

}
