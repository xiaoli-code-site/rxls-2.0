package cn.rx.core.exception;

import cn.rx.common.enums.R;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义业务异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException{
    private Integer code;
    private String args; //请求参数
    private Exception exception; //异常


    public BusinessException(Integer code, String message) {
        super(message);
        this.code=code;
    }
    public BusinessException(R resultEnum) {
        super(resultEnum.msg);
        this.code=resultEnum.code;
    }

    public BusinessException(Integer code, String message, String args, Throwable cause) {
        super(message,cause);
        this.code=code;
        this.args=args;
    }

    public BusinessException(Integer code, String args, Exception exception) {
        this.code=code;
        this.args=args;
        this.exception=exception;
    }

    public BusinessException(R resultEnum, Exception exception) {
        super(resultEnum.msg);
        this.code=resultEnum.code;
        this.exception=exception;
    }


}