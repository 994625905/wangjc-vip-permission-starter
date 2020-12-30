package vip.wangjc.permission.page.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import vip.wangjc.permission.auth.PermissionCheckService;
import vip.wangjc.permission.entity.PermissionExpress;
import vip.wangjc.permission.page.PermissionPageTag;

import java.io.IOException;
import java.util.Map;

/**
 * freemarker权限标签
 * @author wangjc
 * @title: AbstractPermission
 * @projectName wangjc-vip
 * @date 2020/12/30 - 11:43
 */
public class FreemarkerPermissionTagDirective implements TemplateDirectiveModel {

    private final PermissionCheckService permissionCheckService;

    public FreemarkerPermissionTagDirective(PermissionCheckService permissionCheckService){
        this.permissionCheckService = permissionCheckService;
    }

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        Object perm = map.get(PermissionPageTag.getTagName());

        if(perm != null){
            /** 权限验证 */
            if(this.check(perm.toString())){
                templateDirectiveBody.render(environment.getOut());//显示
            }
        }
    }

    /**
     * 验证权限
     * @param express
     * @return
     */
    protected Boolean check(String express){
        Boolean res = false;
        for(PermissionExpress permissionExpress : this.permissionCheckService.getPermissionExpressList()){
            if(express.equals(permissionExpress.getExpress())){
                res = true;
                break;
            }
        }
        return res;
    };

}
