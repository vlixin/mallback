package com.lixin.litemall.common.api;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(0, "操作成功"),
    FAILED(500, "操作失败"),

    parameterError(300, "参数错误"),

    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    TOKENFAILED(402, "token解析失败"),
    WrongPassword(403, "密码错误"),

    DuplicateUserName(601, "用户名重复"),
    TheEmailVerificationCodeIsIncorrect(602, "邮箱验证码不正确"),
    MailAddressIncorrect(603, "邮箱地址不对"),
    usernameOrPasswordIsIncorrect(604, "用户名或密码错误"),

    FORBIDDEN(403, "没有相关权限");


    private final long code;
    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {

        return code;
    }

    public String getMessage() {
        return message;
    }
}
