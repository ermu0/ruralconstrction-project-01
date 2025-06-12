package com.example.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.pojo.Result;
import com.example.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * 登录校验拦截器
 */

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 获取请求的url
        String url = request.getRequestURI();
        log.info("请求的url：{}",url);

        //2. 判断请求是否为登录请求，如果是则不需要进行下面的步骤
        if (url.contains("login")) {
            log.info("登录操作，放行……");
            return true;
        }

        //3. 如果不是登录操作就需要获取请求头中的token（也就是JWT令牌）
        String jwt = request.getHeader("token");

        //4. 判断JWT令牌是否存在，如果不存在则返回且不进行下面操作
        if (!StringUtils.hasLength(jwt)) {
            log.info("请求头中的token为空，请返回登录");
            Result error = Result.error("NOT_LOGIN");
            //将对象转为JSON格式
            String notLogin = JSONObject.toJSONString(error);
            //将未登录信息写进响应体里返回给浏览器
            response.getWriter().write(notLogin);
            //不放行
            return false;
        }

        //5. 如果JWT令牌存在，则解析看是否合法
        try {
            //JWT相关API在解析jwt合法性后会自动抛出异常
            JwtUtils.parseJwt(jwt);
        }catch(Exception e){
            //因此我们只需要对抛出的异常处理就好
            e.printStackTrace();
            //这里的异常处理和上面一致，后续看能不能统一异常处理
            log.info("JWT令牌失效，请重新登录");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        //6. JWT令牌存在且解析成功
        log.info("JWT令牌合法，放行……");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
