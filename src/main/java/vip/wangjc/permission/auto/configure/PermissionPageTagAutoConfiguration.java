package vip.wangjc.permission.auto.configure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vip.wangjc.permission.auth.PermissionCheckService;
import vip.wangjc.permission.entity.PermissionPageType;
import vip.wangjc.permission.page.freemarker.FreemarkerConfigure;
import vip.wangjc.permission.page.thymeleaf.ThymeleafPermissionTagDirective;
import vip.wangjc.permission.register.PermissionRegister;

/**
 * 页面权限的解析
 * @author wangjc
 * @title: PermissionPageTagAutoConfiguration
 * @projectName wangjc-vip
 * @date 2020/12/30 - 14:31
 */
@Configuration
public class PermissionPageTagAutoConfiguration {

    /**
     * 配置freemarker权限解析
     */
    @Configuration
    @ConditionalOnClass(FreeMarkerProperties.class)
    static class FreemarkerPermissionConfigure{

        @Bean
        @ConditionalOnBean({freemarker.template.Configuration.class, PermissionCheckService.class})
        public FreemarkerConfigure freemarkerConfigure(freemarker.template.Configuration configuration, PermissionCheckService permissionCheckService){
            if(PermissionRegister.getPageType().equals(PermissionPageType.FREEMARKER)){
                return new FreemarkerConfigure(configuration, permissionCheckService);
            }
            return null;
        }
    }

    /**
     * 配置配置thymeleaf权限解析
     */
    @Configuration
    @ConditionalOnClass(ThymeleafProperties.class)
    static class ThymeleafPermissionConfigure{

        @Bean
        @ConditionalOnBean({PermissionCheckService.class})
        public ThymeleafPermissionTagDirective thymeleafPermissionTagDirective(PermissionCheckService permissionCheckService){
            if(PermissionRegister.getPageType().equals(PermissionPageType.THYMELEAF)){
                return new ThymeleafPermissionTagDirective(permissionCheckService);
            }
            return null;
        }
    }

}