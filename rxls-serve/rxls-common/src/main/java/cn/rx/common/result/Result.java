package cn.rx.common.result;


import cn.rx.common.enums.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 统一响应封装类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result <T>{



    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;





    public static Result<?> success(){
        return  new Result<>(R.SUCCESS.code,R.SUCCESS.msg ,null);
    }

    public static <T> Result<T>success(T data){
        return  new Result<>(R.SUCCESS.code,R.SUCCESS.msg,data);
    }

    public  static <T> Result<T> success(R r,T data){
        return  new Result<>(r.code,r.msg,data);
    }


    public static Result<?> error(){
        return  new Result<>();
    }

    public static Result<?>  error(R r){
        return  new Result<>(r.code,r.msg,null);
    }

    public static Result<?>  error(R r,String msg){
        return  new Result<>(r.code,msg,null);
    }


    public static Result<?> error(R r, List<String> msg) {
        return  new Result<>(r.code,String.join(",",msg),null);
    }

    public static Result<?> error(Integer code, String msg) {
        return  new Result<>(code,msg,null);
    }

}
