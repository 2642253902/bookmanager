package com.pan.config;

import cn.dev33.satoken.stp.StpUtil;
import com.pan.annotation.NeeAuth;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class NeeAuthConfig {

    @Pointcut("@annotation(com.pan.annotation.NeeAuth)")
    public void NeeAuth() {

    }

    @Before("NeeAuth()")
    public void beforeRequestInDirect(JoinPoint Point) throws IOException {

        MethodSignature sign = (MethodSignature) Point.getSignature();
        Method method = sign.getMethod();
        NeeAuth annotation = method.getAnnotation(NeeAuth.class);

        if (StpUtil.getLoginIdDefaultNull() == null) {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            ServletOutputStream writer = response.getOutputStream();
            response.setContentType("application/json;charset=utf-8");
            writer.println("需要授权");
            writer.close();
        } else {
            if (annotation.neeAuth()) {

                String[] needRole = annotation.needRole();
                System.out.println("needRole array length: " + needRole.length);
                System.out.println("needRole array contents: " + Arrays.toString(needRole));

                if (needRole.length == 0 || needRole[0].isBlank()) {
                    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                    ServletOutputStream writer = response.getOutputStream();
                    response.setContentType("application/json;charset=utf-8");
                    writer.println("needRole空");
                    writer.close();
                    return;
                }

                List<String> roleList = StpUtil.getRoleList();
                System.out.println("当前用户角色列表：" + roleList);
                roleList.forEach(i -> System.out.println(i));

                if (!StpUtil.hasRole(needRole[0])) {
                    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                    ServletOutputStream writer = response.getOutputStream();
                    response.setContentType("application/json;charset=utf-8");
                    writer.println("需要许可");
                    writer.close();
                }
            }
        }
    }

}
