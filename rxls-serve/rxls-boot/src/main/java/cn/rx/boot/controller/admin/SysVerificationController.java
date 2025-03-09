package cn.rx.boot.controller.admin;

import cn.rx.common.result.Result;
import cn.rx.common.util.CaptchaUtil;
import cn.rx.system.entity.VerificationCode;
import cn.rx.system.service.SysVerificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 验证码
 */
@RestController
@RequestMapping("code")
public class SysVerificationController {

    @Resource
    private SysVerificationService sysVerificationService;

    /**
     * 获取普通验证码
     * @return 验证码信息
     */
    @GetMapping("/transit/ordinary")
    public Result<VerificationCode> getCode(){
        return Result.success(sysVerificationService.getCode(CaptchaUtil.CACHE));
    }


    /**
     * 发送校验验证码
     * @return 验证码信息
     */
    @GetMapping("/transit/verification")
    public Result<VerificationCode> getVerificationCode(){
        return Result.success(sysVerificationService.getCode(CaptchaUtil.VERIFICATION));
    }


    /**
     * 检查验证码
     * @return 是否存在
     */
    @PostMapping("/auth/check")
    public Result<Boolean> verification(VerificationCode code){
        return Result.success(sysVerificationService.verification(code));
    }

}
