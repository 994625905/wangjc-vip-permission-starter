package vip.wangjc.permission.template;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vip.wangjc.permission.annotation.Permission;
import vip.wangjc.permission.auth.PermissionCheckService;
import vip.wangjc.permission.entity.PermissionExpress;
import vip.wangjc.permission.error.PermissionErrorResult;
import vip.wangjc.permission.register.PermissionRegister;

import java.util.List;

/**
 * 权限的模板方法
 * @author wangjc
 * @title: PermissionTemplate
 * @projectName wangjc-vip
 * @date 2020/12/29 - 20:40
 */
public class PermissionTemplate {

    private static final Logger logger = LoggerFactory.getLogger(PermissionTemplate.class);

    @Autowired(required = false)
    private PermissionCheckService permissionCheckService;

    /**
     * 检查放行
     * @param methodInvocation
     * @param permission
     * @return
     */
    public Object check(MethodInvocation methodInvocation, Permission permission){

        if(this.permissionCheckService == null){
            logger.error("permissionCheckService bean is null,please check redisTemplate");
            return true;
        }
        try {
            String express = permission.express();
            String requestUrl = permission.requestUrl();
            List<PermissionExpress> list = this.permissionCheckService.getPermissionExpressList();

            if(express == null || express == ""){
                return methodInvocation.proceed();
            }

            Boolean flag = false;
            /** 若express可能重复，则区分requestUrl */
            if(requestUrl == null || requestUrl == ""){
                for(PermissionExpress permissionExpress : list){
                    if(express.equals(permissionExpress.getExpress())){
                        flag = true;
                        break;
                    }
                }
            }else{
                for(PermissionExpress permissionExpress : list){
                    if(express.equals(permissionExpress.getExpress()) || requestUrl.equals(permissionExpress.getRequestUrl())){
                        flag = true;
                        break;
                    }
                }
            }

            /** 判断 */
            if(flag){
                return methodInvocation.proceed();
            }else{
                return PermissionRegister.getPermissionError().error(permission);
            }
        }catch (Throwable throwable){
            return PermissionErrorResult.exception(throwable);
        }
    }
}
