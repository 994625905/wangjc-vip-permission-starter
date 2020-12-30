package vip.wangjc.permission.auto.configure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import vip.wangjc.permission.aop.PermissionAnnotationAdvisor;
import vip.wangjc.permission.aop.PermissionInterceptor;
import vip.wangjc.permission.auth.PermissionAuthService;
import vip.wangjc.permission.auth.PermissionCheckService;
import vip.wangjc.permission.cache.PermissionCacheServiceBuilder;
import vip.wangjc.permission.register.PermissionRegister;
import vip.wangjc.permission.template.PermissionTemplate;

/**
 * 权限验证的自动配置项
 * @author wangjc
 * @title: PermissionAutoConfiguration
 * @projectName wangjc-vip
 * @date 2020/12/30 - 10:50
 */
@Configuration
public class PermissionAutoConfiguration {

    /**
     * 配置缓存服务生成器
     */
    @Configuration
    @ConditionalOnClass({RedisOperations.class})
    static class PermissionCacheServiceBuilderConfigure{

        @Bean
        public PermissionCacheServiceBuilder permissionCacheServiceBuilder(RedisTemplate redisTemplate){
            return new PermissionCacheServiceBuilder(redisTemplate);
        }
    }

    /**
     * 注册权限认证服务bean
     * @param permissionCacheServiceBuilder
     * @return
     */
    @Bean
    @ConditionalOnBean(PermissionCacheServiceBuilder.class)
    public PermissionAuthService permissionAuthService(PermissionCacheServiceBuilder permissionCacheServiceBuilder){
        return new PermissionAuthService(permissionCacheServiceBuilder.getCacheService(PermissionRegister.getCacheType()));
    }

    /**
     * 注册权限检查服务bean
     * @param permissionCacheServiceBuilder
     * @return
     */
    @Bean
    @ConditionalOnBean(PermissionCacheServiceBuilder.class)
    public PermissionCheckService permissionCheckService(PermissionCacheServiceBuilder permissionCacheServiceBuilder){
        return new PermissionCheckService(permissionCacheServiceBuilder.getCacheService(PermissionRegister.getCacheType()));
    }

    /**
     * 注册权限模板方法bean
     * @return
     */
    @Bean
    public PermissionTemplate permissionTemplate(){
        return new PermissionTemplate();
    }

    /**
     * 注册权限拦截器
     * @param permissionTemplate
     * @return
     */
    @Bean
    @ConditionalOnBean(PermissionTemplate.class)
    public PermissionInterceptor permissionInterceptor(PermissionTemplate permissionTemplate){
        return new PermissionInterceptor(permissionTemplate);
    }

    /**
     * 注册权限的aop通知
     * @param permissionInterceptor
     * @return
     */
    @Bean
    @ConditionalOnBean(PermissionInterceptor.class)
    public PermissionAnnotationAdvisor permissionAnnotationAdvisor(PermissionInterceptor permissionInterceptor){
        return new PermissionAnnotationAdvisor(permissionInterceptor,199);
    }


}
