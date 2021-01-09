package vip.wangjc.permission.annotation;

import org.springframework.context.annotation.Import;
import vip.wangjc.permission.cache.expire.abstracts.AbstractCacheExpireBuilder;
import vip.wangjc.permission.cache.expire.DefaultCacheExpireBuilder;
import vip.wangjc.permission.entity.PermissionCacheType;
import vip.wangjc.permission.entity.PermissionPageType;
import vip.wangjc.permission.error.abstracts.AbstractPermissionError;
import vip.wangjc.permission.error.DefaultPermissionError;
import vip.wangjc.permission.register.PermissionRegister;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * wangjc-vip权限组件配置：缓存的选取，有效时间的刷新器
 * @author wangjc
 * @title: EnablePermission
 * @projectName wangjc-vip
 * @date 2020/12/29 - 18:00
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(PermissionRegister.class)
public @interface PermissionConfigure {

    /**
     * 页面类型（freemarker，thymeleaf），必填项
     * @return
     */
    PermissionPageType pageType();

    /**
     * 缓存类型
     * @return
     */
    PermissionCacheType cacheType() default PermissionCacheType.REDIS;

    /**
     * 缓存的有效时间构建器
     * @return
     */
    Class<? extends AbstractCacheExpireBuilder> expireBuilder() default DefaultCacheExpireBuilder.class;

    /**
     * 权限验证错误的处理
     * @return
     */
    Class<? extends AbstractPermissionError> errorBuilder() default DefaultPermissionError.class;

}
