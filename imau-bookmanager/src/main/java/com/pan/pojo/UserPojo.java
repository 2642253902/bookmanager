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
@TableName(value = "user")
public class UserPojo {

    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "pass_word")
    private String passWord;

    @TableField(value = " nick_name")
    private String nickName;

    public UserPojo(String userName, String passWord, String nickName) {
        this.userName = userName;
        this.passWord = passWord;
        this.nickName = nickName;
    }


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassWord() {
        return passWord;
    }


}
