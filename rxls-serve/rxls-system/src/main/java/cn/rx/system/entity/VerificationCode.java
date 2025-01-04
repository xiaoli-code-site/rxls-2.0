package cn.rx.system.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerificationCode {

    /**
     * 验证码类型
     */
    private String types;

    /**
     * 验证码key
     */
    private String codeKey;

    /**
     * 验证码图片
     */
    private String codeImage;

    /**
     * 验证码值
     */
    private String text;

}
