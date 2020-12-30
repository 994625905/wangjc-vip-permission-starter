package vip.wangjc.permission.annotation;

import vip.wangjc.permission.entity.PermissionRequestType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求的权限验证注解
 * @author wangjc
 * @title: Permission
 * @projectName wangjc-vip
 * @date 2020/12/29 - 17:48
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    /**
     * 权限表达式，必填项
     * @return
     */
    String express();

    /**
     * 非必填项：假日权限表达式不唯一的话，采用requestUrl二次验证
     * @return
     */
    String requestUrl() default "";

    /**
     * 请求类型：默认是数据接口
     * @return
     */
    PermissionRequestType request() default PermissionRequestType.DATA;
}
