package com.pan.controller;

import cn.dev33.satoken.stp.StpUtil;       // Sa-Token核心工具类，登录、权限等操作
import cn.dev33.satoken.util.SaResult;     // Sa-Token封装的结果返回
import com.pan.annotation.NeeAuth;         // 你自定义的注解，用来声明需要权限
import com.pan.service.UserServiceImpl;    // 你的业务逻辑Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController      // 表示这是一个控制器，返回值直接是JSON
@CrossOrigin         // 允许跨域请求
public class UserController {

    @Autowired
    UserServiceImpl userService;   // 注入业务逻辑类


    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public String doLogin(@RequestParam String username,
                          @RequestParam String password) {
        // 调用业务逻辑登录方法
        int login = userService.Login(username, password);

        // 判断登录是否成功（非0表示成功）
        if (login != 0) {
            // Sa-Token执行登录，传入用户ID
            StpUtil.login(login);
            // 返回JSON字符串：登录成功
            return SaResult.ok("登录成功").toString();
        } else {
            // 返回JSON字符串：登录失败
            return SaResult.error("登录失败").toString();
        }
    }

    /**
     * 这里 @NeeAuth(neeAuth = true, needRole = {"admin"})
     * 表示：
     *   1. 必须登录
     *   2. 必须拥有 "admin" 角色
     *
     * 如果不满足，会在 AOP 切面直接拦截返回
     */
    @NeeAuth(neeAuth = true, needRole = {"admin"})
    @RequestMapping(value = "/Heollo", method = RequestMethod.GET)
    public String doHeollo() {
        return "hello";
    }
}
