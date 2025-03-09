package cn.rx.boot.controller.admin;


import cn.rx.common.result.Result;
import cn.rx.core.encrypt.rsa.RSAUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RSA秘钥
 * @author 不想说话而已
 */
@RestController
@RequestMapping("/encrypt")
public class SysEncryptController {

    /**
     * 获取RSA公钥
     * @return
     */
    @GetMapping("/rsa")
    public Result<String> getRsaPublicKey() {
        return Result.success(RSAUtil.publicKey);
    }


}
