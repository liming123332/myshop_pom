package com.myshop.aop;

import com.myshop.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;


@Aspect
public class IsLoginAop {

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(IsLogin)")
    public Object isLogin(ProceedingJoinPoint joinPoint)  {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Cookie[] cookies = request.getCookies();
        String login_token=null;
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if ("login_token".equals(cookie.getName())) {
                    login_token = cookie.getValue();
                    break;
                }
            }
        }
        User user=null;
        if(login_token!=null) {
             user = (User) redisTemplate.opsForValue().get(login_token);
        }
        if(null==user){
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            IsLogin isLogin = method.getAnnotation(IsLogin.class);
            boolean flag = isLogin.mustLogin();
            if(flag){

                //获得当前的路径
                String returnUrl = request.getRequestURL().toString();
                //System.out.println(returnUrl);
                //获得当前的请求参数
                String params = request.getQueryString();//拿到请求?后面的参数字符串
                //System.out.println(params);

//                    request.getParameterMap();//获得post请求体中的参数
                returnUrl = returnUrl + "?" + params;
                try {
                    returnUrl = URLEncoder.encode(returnUrl, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //跳转到登录页面
                return "redirect:http://localhost:8084/sso/toLogin?returnUrl=" + returnUrl;
            }
        }
        //System.out.println(user);
        Object[] args = joinPoint.getArgs();
        //System.out.println(args);
        for (int i = 0; i <args.length ; i++) {
            if(args[i] != null && args[i].getClass() == User.class){
                args[i] = user;
                break;
            }
        }
        try {
            Object result = joinPoint.proceed(args);
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
