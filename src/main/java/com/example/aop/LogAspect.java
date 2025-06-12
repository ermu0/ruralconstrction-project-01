package com.example.aop;


import com.example.mapper.OperateLogMapper;
import com.example.pojo.OperateLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LogAspect {

    @Autowired
    OperateLogMapper operateLogMapper;

    //暂时没写，看后面需求再做补充
    @Around("@annotation(com.example.anno.Log)") //用自定义的Log注解来动态挑选环绕通知的对象
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {

        Object object = joinPoint.proceed();

//        //记录操作日志
//        OperateLog operateLog = new OperateLog();
//        operateLogMapper.insert(operateLog);

        return object;
    }

}
