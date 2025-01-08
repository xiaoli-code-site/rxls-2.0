package cn.rx.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 通用枚举
 */
public enum CommonStateEnum {

    /**
     * 是，正确，启用，成功
     */
    OK(1, "ok"),
    /**
     * 否，删除，禁用，失败
     */
    ERROR(0, "no");


    /**
     * 状态码
     */
    public final Integer code;

    /**
     * 描述
     */
    public final String msg;

    CommonStateEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


   public static Boolean check(Integer code) {
        List<CommonStateEnum> collect = Arrays.stream(CommonStateEnum.values()).filter(s -> Objects.equals(s.code, code)).collect(Collectors.toList());
        return collect.size() > 0;
    }
}
