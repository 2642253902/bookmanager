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

// 表示这是一个组件，会被Spring扫描到容器里
@Component
public class StpInterfaceImpl implements StpInterface {

    // 注入用户表DAO
    @Autowired
    UserDao userDao;

    // 注入角色表DAO
    @Autowired
    RoleDao roleDao;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 如果不使用权限控制，可以返回空
        return List.of();
    }

    /**
     * 返回指定账号id拥有的角色集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型（多账号体系时用）
     * @return 角色列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 创建空列表，存储角色
        List<String> list = new ArrayList<>();

        // loginId是Object类型，先转成Integer
        Integer userId = Integer.valueOf(String.valueOf(loginId));
        System.out.println("loginId = " + loginId + ", class = " + loginId.getClass());

        // 查询用户
        UserPojo userPojo = userDao.selectById(userId);
        if (userPojo == null) {
            System.out.println("用户不存在，loginId=" + userId);
            return list; // 空列表
        }

        // 查询该用户的所有角色
        List<RolePojo> roleList = roleDao.selectList(new QueryWrapper<RolePojo>().eq("role_user_id", userId));
        if (roleList == null || roleList.isEmpty()) {
            System.out.println("该用户无角色，userId=" + userId);
            return list; // 空列表
        }

        // 把每个角色名加入列表
        for (RolePojo role : roleList) {
            // 再次确认用户id匹配（一般可以省略）
            if (userPojo.getUid().equals(role.getRoleUserId())) {
                list.add(role.getRoleName());
                System.out.println("加入角色：" + role.getRoleName());
            }
        }
        // 返回所有角色
        return list;
    }
}
