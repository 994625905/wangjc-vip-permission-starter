package vip.wangjc.permission.page.freemarker;

import vip.wangjc.permission.auth.PermissionCheckService;
import vip.wangjc.permission.page.PermissionPageTag;

import javax.annotation.PostConstruct;

/**
 * free marker的权限标签配置
 * @author wangjc
 * @title: FreemarkerConfigure
 * @projectName wangjc-vip
 * @date 2020/12/30 - 12:53
 */

public class FreemarkerConfigure {

    public final freemarker.template.Configuration configuration;

    public final PermissionCheckService permissionCheckService;

    public FreemarkerConfigure(freemarker.template.Configuration configuration,PermissionCheckService permissionCheckService){
        this.configuration = configuration;
        this.permissionCheckService = permissionCheckService;
    }

    /**
     * 初始化
     */
    @PostConstruct
    public void autoInit(){
        configuration.setSharedVariable(PermissionPageTag.getPrefix(),new FreemarkerPermissionTagDirective(this.permissionCheckService));
    }
}
