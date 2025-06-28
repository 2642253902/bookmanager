package com.pan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName(value = "role")
public class RolePojo {

    @TableId(value = "role_id", type = IdType.ASSIGN_ID)
    private Integer roleId;


    @TableField(value = "role_name")
    private String roleName;

    @TableField(value = "role_user_id")
    private Integer roleUserId;

//    public RolePojo(String roleName, Integer roleUserId) {
//        this.roleName = roleName;
//        this.roleUserId = roleUserId;
//    }


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleUserId() {
        return roleUserId;
    }

    public void setRoleUserId(Integer roleUserId) {
        this.roleUserId = roleUserId;
    }
}
