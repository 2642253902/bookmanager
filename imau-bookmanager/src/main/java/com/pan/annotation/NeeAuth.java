package com.pan.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

// 表示这个注解在运行时依然有效（反射能获取到）
@Retention(value = RetentionPolicy.RUNTIME)

// 表示这个注解可以用在 方法 和 字段 上
@Target(value = {ElementType.METHOD, ElementType.FIELD})
public @interface NeeAuth {

    /**
     * 是否需要权限校验
     * 如果 true：说明此方法需要认证和角色判断
     * 如果 false：说明仅需登录校验（或者完全不校验）
     */
    public boolean neeAuth() default false;

    /**
     * 需要哪些角色
     * 例如：needRole = {"admin", "superuser"}
     * 表示调用此方法的用户必须拥有其中至少一个角色
     */
    public String[] needRole() default {};
}

