package cn.rx.rxls.controller;

import cn.rx.common.annotations.Oplog;
import cn.rx.common.dto.sysLogin.SysLoginAccountDTO;
import cn.rx.common.dto.sysLogin.LoginWXDTO;
import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;
import cn.rx.common.result.Result;
import cn.rx.common.vo.sysLogin.SysLoginAdminVO;
import cn.rx.rxls.service.impl.AdminLoginImpl;
import cn.rx.rxls.service.impl.WXLoginImpl;
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
    private AdminLoginImpl adminLogin;

    /**
     * 微信登录
     * @param dto 参数对象
     * @return 登录信息
     */
    @PostMapping("/wx/login")
    public Result<?> wxLogin(@RequestBody LoginWXDTO dto){
        return Result.success(wxLogin.handler(dto));
    }

    /**
     * 账号登录
     * @param dto 参数对象
     * @return 登录信息
     */
    @PostMapping("/account/login")
    public Result<SysLoginAdminVO> accountLogin(@Valid @RequestBody SysLoginAccountDTO dto){
        return Result.success(adminLogin.handler(dto));
    }


    /**
     * 退出登录
     */
    @GetMapping("/account/logout")
    public Result<?> accountLogin(){
        adminLogin.logout();
        return Result.success();
    }

    /**
     * 退出登录
     */
    @GetMapping("/wx/logout")
    public Result<?> wxLogin(){
        wxLogin.logout();
        return Result.success();
    }


}
