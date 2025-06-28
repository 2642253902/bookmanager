package com.pan.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.pan.annotation.NeeAuth;
import com.pan.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public String doLogin(@RequestParam String username, @RequestParam String password) {
        int login = userService.Login(username, password);
        if (login != 0) {
            StpUtil.login(login);
            return SaResult.ok("登录成功").toString();
        } else {
            return SaResult.error("登录失败").toString();
        }


    }

    @NeeAuth(neeAuth = true, needRole = {"admin"})
    @RequestMapping(value = "/Heollo", method = RequestMethod.GET)
    public String doHeollo() {

        return "hello";
    }

}
