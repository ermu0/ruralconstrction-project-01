package com.example.exception;

import com.example.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) //这里来指定捕获的异常类型
    public Result exception(Exception ex) {
        ex.printStackTrace();
        return Result.error("对不起，操作失败，请联系管理员");
    }
}
