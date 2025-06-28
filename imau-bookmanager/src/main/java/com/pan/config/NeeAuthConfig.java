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

@Aspect                    // 表示这是一个AOP切面类
@Component                 // 注入到Spring容器中
public class NeeAuthConfig {

    /**
     * 切点定义：
     * 这里的意思是：凡是使用了 @NeeAuth 注解的方法，都会被拦截到
     */
    @Pointcut("@annotation(com.pan.annotation.NeeAuth)")
    public void NeeAuth() {
        // 切点方法体可以留空
    }

    /**
     * 前置通知：
     * 在方法执行前进行拦截和逻辑处理
     */
    @Before("NeeAuth()")
    public void beforeRequestInDirect(JoinPoint Point) throws IOException {

        // 获取被拦截方法的签名
        MethodSignature sign = (MethodSignature) Point.getSignature();
        // 获取方法对象
        Method method = sign.getMethod();
        // 获取方法上的 @NeeAuth 注解
        NeeAuth annotation = method.getAnnotation(NeeAuth.class);

        // 第一步：判断是否已经登录
        if (StpUtil.getLoginIdDefaultNull() == null) {
            // 未登录，直接返回响应
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            ServletOutputStream writer = response.getOutputStream();
            response.setContentType("application/json;charset=utf-8");
            writer.println("需要授权");   // 这里写返回内容（中文建议改用 writer.write()，否则会乱码）
            writer.close();
        } else {
            // 已登录，再判断是否需要角色权限
            if (annotation.neeAuth()) {

                // 取出需要的角色数组
                String[] needRole = annotation.needRole();
                System.out.println("needRole array length: " + needRole.length);
                System.out.println("needRole array contents: " + Arrays.toString(needRole));

                // 如果没有配置任何角色
                if (needRole.length == 0 || needRole[0].isBlank()) {
                    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                    ServletOutputStream writer = response.getOutputStream();
                    response.setContentType("application/json;charset=utf-8");
                    writer.println("needRole空");
                    writer.close();
                    return;
                }

                // 获取当前登录用户的角色列表
                List<String> roleList = StpUtil.getRoleList();
                System.out.println("当前用户角色列表：" + roleList);
                roleList.forEach(i -> System.out.println(i));

                // 判断是否拥有第一个角色
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
