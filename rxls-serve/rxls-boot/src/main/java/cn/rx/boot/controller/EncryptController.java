package cn.rx.boot.controller;


import cn.rx.common.result.Result;
import cn.rx.core.encrypt.rsa.RSAUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 加密解密
 */
@RestController
@RequestMapping("/encrypt")
public class EncryptController {

    /**
     * 获取RSA公钥
     * @return
     */
    @GetMapping("/rsa")
    public Result<String> getRsaPublicKey() {
        return Result.success(RSAUtil.publicKey);
    }


}
