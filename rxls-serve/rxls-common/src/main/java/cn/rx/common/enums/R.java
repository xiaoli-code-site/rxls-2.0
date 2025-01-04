package cn.rx.common.enums;

import lombok.AllArgsConstructor;

/**
 * 请求响应枚举状态
 */
@AllArgsConstructor
public enum R {
    /**
     * 权限不为空
     */
    ERROR_PERMISSION_EMPTY(1001, "permissionNoEmpty"),
    /**
     * 存储服务不可用
     */
    ERROR_STORAGE(1001, "errorStorage"),

    /**
     * 文件不为空
     */
    ERROR_NO_FILE(1001, "uploadNoEmpty"),
    /**
     * 文件数限制
     */
    ERROR_FILE_LIMIT(1001, "filesGreater1Less10"),
    /**
     * 配置不存在
     */
    ERROR_CONFIG_NO(1001, "errorConfig"),

    /**
     * 加密失败
     */
    ERROR_ENCRYPT(1001, "errorEncrypt"),
    /**
     * 解密失败
     */
    ERROR_DECRYPT(1001, "errorDecrypt"),
    /**
     * 导出excel失败
     */
    ERROR_EXPORT_EXCEL(1001, "exportExcelFailed"),

    /**
     * 手机号已被使用
     */
    ERROR_PHONE_USED(1001, "phoneNumberHasBeenUsed"),

    /**
     * 添加失败
     */
    ERROR_ADD(1001, "errorAdd"),
    /**
     * 删除失败
     */
    ERROR_DELETE(1001, "errorDelete"),

    /**
     * 无效账户
     */
    ERROR_INVALID_ACCOUNTS(1001, "invalidAccounts"),
    /**
     * 名称格式有误
     */
    ERROR_NAME_FORMAT(1001, "nicknameFormatError"),
    /**
     * 手机号格式有误
     */
    ERROR_PHONE_FORMAT(1001, "phoneNumberFormatError"),
    /**
     * 账号格式有误
     */
    ERROR_ACCOUNT_FORMAT(1001, "errorAccountFormat"),
    /**
     * 密码格式有误
     */
    ERROR_PASSWORD_FORMAT(1001, "errorPasswordFormat"),
    /**
     * 账号已存在
     */
    ERROR_ACCOUNT_REPEAT(1001, "accountRepeat"),


    /**
     * 密码错误
     */
    ERROR_PASSWORD(1001, "errorPassword"),


    /**
     * 注册失败
     */
    ERROR_ENROLL(1001, "errorEnroll"),

    /**
     * 上传图片限制5M以内
     */
    ERROR_UPLOAD_IMG_SIZE(1001, "uploadMaxSize"),
    /**
     * 上传类型错误
     */
    ERROR_UPLOAD_TYPE(1001, "uploadType"),
    /**
     * 更新失败
     */
    ERROR_UPDATE(1001, "errorUpdate"),

    /**
     * 请先登录
     */
    ERROR_LOGIN_NOT(1001, "errorLogin"),
    /**
     * 用户不存在
     */
    ERROR_USER(1001, "errorUser"),


    /**
     * 认证失败
     */
    ERROR_ATTESTATION_NO(1001, "authenticationFailed"),
    /**
     * 验证码错误
     */
    ERROR_CACHE(1001, "errorCache"),
    /**
     * 无权操作
     */
    ERROR_PERMISSION(1031, "unauthorizedOperation"),
    /**
     * 参数校验失败
     */
    ERROR_QUERY(1021, "errorQuery"),
    /**
     * 授权失败
     */
    ERROR_AUTHORIZED(1011, "errorAuthorized"),
    /**
     * 系统繁忙
     */
    ERROR_EXCEPTION(1001, "systemBusy"),
    /**
     * 响应成功
     */
    SUCCESS(200, "success"),

    /**
     * 响应失败
     */
    ERROR(1000, "fail");


    /**
     * 状态
     */
    public final Integer code;

    /**
     * 描述信息
     */
    public final String msg;


}
