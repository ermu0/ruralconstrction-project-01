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
        ex.printStackTrace();
        log.info("异常信息：{}",ex.getMessage());
        return Result.error(ex.getMessage());
    }
}
