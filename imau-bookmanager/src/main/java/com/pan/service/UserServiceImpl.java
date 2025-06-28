package com.pan.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pan.dao.RoleDao;
import com.pan.dao.UserDao;
import com.pan.pojo.RolePojo;
import com.pan.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl {

    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;


    public int Login(String username, String password) {
        UserPojo username1 = userDao.selectOne(new QueryWrapper<UserPojo>().eq("user_name", username));
        if (username1 == null) {
            return 0;
        } else if (!(username1.getPassWord().equals(password))) {
            return 0;
        } else {
            return username1.getUid();
        }

    }

    public String Reg(String username, String password) {
        if (username == null || password == null) {
            return "账号或密码为空";
        } else {
            UserPojo userName = userDao.selectOne(new QueryWrapper<UserPojo>().like("user_name", username));
            if (userName != null) {
                return "已有账号";
            } else {
                userDao.insert(new UserPojo(username, password, username));
                userName = userDao.selectOne(new QueryWrapper<UserPojo>().like("user_name", username));
                RolePojo admin = new RolePojo();
                admin.setRoleName("admin");
                admin.setRoleUserId(userName.getUid());

                roleDao.insert(admin);
                return "注册成功";
            }
        }
    }


}
