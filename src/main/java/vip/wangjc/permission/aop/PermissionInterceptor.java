package vip.wangjc.permission.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.wangjc.permission.annotation.Permission;
import vip.wangjc.permission.template.PermissionTemplate;

/**
 * 权限验证的拦截器
 * @author wangjc
 * @title: PermissionInterceptor
 * @projectName wangjc-vip
 * @date 2020/12/30 - 10:45
 */
public class PermissionInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);
    private final PermissionTemplate permissionTemplate;

    public PermissionInterceptor(PermissionTemplate permissionTemplate){
        this.permissionTemplate = permissionTemplate;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            Permission permission = methodInvocation.getMethod().getAnnotation(Permission.class);
            return this.permissionTemplate.check(methodInvocation, permission);
        }catch (Exception e){
            logger.error("PermissionInterceptor invoke,reason:[{}]",e.getMessage());
            throw e;
        }
    }
}
