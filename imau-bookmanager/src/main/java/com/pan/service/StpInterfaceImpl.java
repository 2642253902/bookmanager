package com.pan.service;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pan.dao.RoleDao;
import com.pan.dao.UserDao;
import com.pan.pojo.RolePojo;
import com.pan.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        Integer userId = Integer.valueOf(String.valueOf(loginId));
        System.out.println("loginId = " + loginId + ", class = " + loginId.getClass());

        UserPojo userPojo = userDao.selectById(userId);
        if (userPojo == null) {
            System.out.println("用户不存在，loginId=" + userId);
            return list; // 空列表
        }

        List<RolePojo> roleList = roleDao.selectList(new QueryWrapper<RolePojo>().eq("role_user_id", userId));
        if (roleList == null || roleList.isEmpty()) {
            System.out.println("该用户无角色，userId=" + userId);
            return list; // 空列表
        }

        for (RolePojo role : roleList) {
            if (userPojo.getUid().equals(role.getRoleUserId())) {
                list.add(role.getRoleName());
                System.out.println("加入角色：" + role.getRoleName());
            }
        }
        return list;
    }

}
