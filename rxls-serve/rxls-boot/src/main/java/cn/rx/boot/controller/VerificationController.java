package cn.rx.boot.controller;

import cn.rx.common.result.Result;
import cn.rx.common.util.CaptchaUtil;
import cn.rx.system.entity.VerificationCode;
import cn.rx.system.service.SysVerificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 验证码
 */
@RestController
public class VerificationController {

    @Resource
    private SysVerificationService sysVerificationService;

    /**
     * 获取普通验证码
     * @return 验证码信息
     */
    @GetMapping("/code")
    public Result<VerificationCode> getCode(){
        return Result.success(sysVerificationService.getCode(CaptchaUtil.CACHE));
    }


    /**
     * 获取校验验证码
     * @return 验证码信息
     */
    @GetMapping("/special/code")
    public Result<VerificationCode> getVerificationCode(){
        return Result.success(sysVerificationService.getCode(CaptchaUtil.VERIFICATION));
    }


    /**
     * 检查验证码
     * @return 是否存在
     */
    @PostMapping("/verification")
    public Result<Boolean> verification(VerificationCode code){
        return Result.success(sysVerificationService.verification(code));
    }

}
