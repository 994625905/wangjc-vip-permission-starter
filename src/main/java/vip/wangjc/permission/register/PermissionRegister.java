package vip.wangjc.permission.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import vip.wangjc.permission.annotation.PermissionConfigure;
import vip.wangjc.permission.cache.expire.abstracts.AbstractCacheExpireBuilder;
import vip.wangjc.permission.entity.PermissionCacheType;
import vip.wangjc.permission.entity.PermissionPageType;
import vip.wangjc.permission.error.abstracts.AbstractPermissionError;

/**
 * permission的注册器
 * @author wangjc
 * @title: PermissionRegister
 * @projectName wangjc-vip
 * @date 2020/12/29 - 18:58
 */
public class PermissionRegister implements ImportBeanDefinitionRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(PermissionRegister.class);
    private static PermissionCacheType CACHE_TYPE = null;
    private static PermissionPageType pageType = null;
    private static AbstractCacheExpireBuilder expireBuilder = null;
    private static AbstractPermissionError permissionError = null;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(PermissionConfigure.class.getName()));

        /** 页面模板类型 */
        pageType = attributes.getEnum("pageType");

        /** 缓存组件的选择 */
        CACHE_TYPE = attributes.getEnum("cacheType");
        try {

            /** 有效时间的构建器 */
            expireBuilder = (AbstractCacheExpireBuilder) attributes.getClass("expireBuilder").newInstance();
            /** 错误处理 */
            permissionError = (AbstractPermissionError) attributes.getClass("errorBuilder").newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 权限验证的错误处理
     * @return
     */
    public static AbstractPermissionError getPermissionError(){
        if(permissionError == null){
            throw new IllegalArgumentException("AbstractPermissionError is null,please check it");
        }
        return permissionError;
    }

    /**
     * 获取页面模板类型
     * @return
     */
    public static PermissionPageType getPageType(){
        if(pageType == null){
            throw new IllegalArgumentException("PermissionPageType is null,please check it");
        }
        return pageType;
    }

    /**
     * 获取缓存类型
     * @return
     */
    public static PermissionCacheType getCacheType(){
        if(CACHE_TYPE == null){
            throw new IllegalArgumentException("PermissionCacheType is null,please check it");
        }
        return CACHE_TYPE;
    }

    /**
     * 获取缓存有效时间
     * @return
     */
    public static Long getExpire(){
        if(expireBuilder == null){
            throw new IllegalArgumentException("AbstractCacheExpireBuilder is null,please check it");
        }
        return expireBuilder.expire();
    }
}
