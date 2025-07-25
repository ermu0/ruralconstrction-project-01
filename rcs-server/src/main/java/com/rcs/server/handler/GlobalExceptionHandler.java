package com.rcs.server.handler;


import com.rcs.server.domain.pojo.Result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) //这里来指定捕获的异常类型
    public Result exception(Exception ex) {
        log.error("全局捕获，异常原因：{}", ex.getCause().getMessage(),ex);//打印异常原因、异常堆栈信息(包括了当前的异常消息、异常原因)
        return Result.error(ex.getMessage());
    }
}
