package io.github.zornx5.infrastructure.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * 响应信息枚举
 * <p>
 * 1-2 位：错误模块
 * 2-3 位：模块分类
 * 5-6 位：错误序号
 *
 * @author zornx5
 */
@Getter
public enum ResponseStatus {
    /**
     * 成功
     */
    SUCCESS("000000", HttpStatus.OK, "成功"),

    /**
     * 失败
     */
    ERROR("999999", HttpStatus.INTERNAL_SERVER_ERROR, "异常"),

    /**
     * 0001xx
     * 01 - 系统 -- 00 请求
     */
    METHOD_ARGUMENT_NOT_VALID("010001", HttpStatus.BAD_REQUEST, "方法参数无效"),
    CONSTRAINT_VIOLATION("010002", HttpStatus.BAD_REQUEST, "违反约束"),

    /**
     * 0101xx
     * 01 - 系统 -- 01 - 鉴权
     */
    BAD_CREDENTIALS("010101", HttpStatus.UNAUTHORIZED, "凭证验证失败"),

    PASSWORD_NOT_MATCH("010102", HttpStatus.PRECONDITION_FAILED, "密码不匹配"),

    /**
     * 0102xx
     * 01 - 系统 -- 02 - 用户
     */
    USER_EXIST("010201", HttpStatus.BAD_REQUEST, "用户已存在"),

    USER_NOT_FOUND("010202", HttpStatus.NOT_FOUND, "用户未找到"),

    USER_DISABLED("010203", HttpStatus.FORBIDDEN, "用户已禁用"),

    USER_LOCKED("010204", HttpStatus.FORBIDDEN, "用户已锁定"),

    /**
     * 0103xx
     * 01 - 系统 -- 03 - 角色
     */
    ROLE_EXIST("010301", HttpStatus.BAD_REQUEST, "角色已存在"),

    ROLE_NOT_FOUND("010302", HttpStatus.NOT_FOUND, "角色未找到"),

    /**
     * 0104xx
     * 01 - 系统 -- 04 - 资源
     */
    RESOURCE_EXIST("010401", HttpStatus.BAD_REQUEST, "资源已存在"),

    RESOURCE_NOT_FOUND("010402", HttpStatus.NOT_FOUND, "资源未找到"),
    ;

    private final String code;
    private final HttpStatus status;
    private final String message;

    ResponseStatus(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public static ResponseStatus of(String code) {
        for (ResponseStatus responseStatus : ResponseStatus.values()) {
            if (Objects.equals(code, responseStatus.getCode())) {
                return responseStatus;
            }
        }
        return ERROR;
    }
}
