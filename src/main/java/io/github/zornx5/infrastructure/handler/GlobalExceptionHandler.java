package io.github.zornx5.infrastructure.handler;

import io.github.zornx5.infrastructure.common.Content;
import io.github.zornx5.infrastructure.common.enums.ResponseStatus;
import io.github.zornx5.infrastructure.common.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author zornx5
 */
@ResponseBody
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理方法参数无效异常
     *
     * @param ex 业务异常
     * @return 响应实体
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("Handle method argument not valid exception, message: [{}], requestUrl: [{}]",
                ex.getMessage(), request.getRequestURI(), ex);
        Map<String, String> errors = new HashMap<>(8);
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        Map<String, Object> body = Map.of(
                Content.RESPONSE_CODE, ResponseStatus.METHOD_ARGUMENT_NOT_VALID.getCode(),
                Content.RESPONSE_MESSAGE, errors.entrySet().stream()
                        .map(m -> m.getKey() + " " + m.getValue())
                        .collect(Collectors.joining(", ")),
                Content.RESPONSE_ERRORS, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * 处理违反约束异常
     *
     * @param ex 业务异常
     * @return 响应实体
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {
        log.error("Handle constraint violation exception, message: [{}], requestUrl: [{}]",
                ex.getMessage(), request.getRequestURI(), ex);
        Map<String, String> errors = new HashMap<>(8);
        ex.getConstraintViolations().forEach(error -> {
            String property = error.getPropertyPath().toString();
            String errorMessage = error.getMessage();
            errors.put(property, errorMessage);
        });
        Map<String, Object> body = Map.of(
                Content.RESPONSE_CODE, ResponseStatus.CONSTRAINT_VIOLATION.getCode(),
                Content.RESPONSE_MESSAGE, errors.entrySet().stream()
                        .map(m -> m.getKey() + " " + m.getValue())
                        .collect(Collectors.joining(", ")),
                Content.RESPONSE_ERRORS, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * 处理业务异常
     *
     * @param ex 业务异常
     * @return 响应实体
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Map<String, Object>> handleBaseException(BaseException ex, HttpServletRequest request) {
        log.error("Handle base exception, message: [{}], requestUrl: [{}]", ex.getMessage(), request.getRequestURI(), ex);
        Map<String, Object> body = Map.of(
                Content.RESPONSE_CODE, ex.getStatus().getCode(),
                Content.RESPONSE_MESSAGE, ex.getMessage());
        return ResponseEntity.status(ex.getStatus().getStatus()).body(body);
    }

    /**
     * 处理业务异常
     *
     * @param ex 业务异常
     * @return 响应实体
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, HttpServletRequest request) {
        log.error("Handle exception, message: [{}], requestUrl: [{}]", ex.getMessage(), request.getRequestURI(), ex);
        ResponseStatus status = ResponseStatus.ERROR;
        Map<String, Object> body = Map.of(
                Content.RESPONSE_CODE, status.getCode(),
                Content.RESPONSE_MESSAGE, ex.getMessage());
        return ResponseEntity.status(status.getStatus()).body(body);
    }
}
