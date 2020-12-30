package vip.wangjc.permission.page.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import vip.wangjc.permission.auth.PermissionCheckService;
import vip.wangjc.permission.entity.PermissionExpress;
import vip.wangjc.permission.page.PermissionPageTag;

import java.util.List;

/**
 * @author wangjc
 * @title: ThymeleafConfigure
 * @projectName wangjc-vip
 * @date 2020/12/30 - 14:10
 */
public class ThymeleafPermissionProcessor extends AbstractAttributeTagProcessor {

    private final PermissionCheckService permissionCheckService;
    private static final int PRECEDENCE = 999;

    public ThymeleafPermissionProcessor(PermissionCheckService permissionCheckService, String dialectPrefix){
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix, // Prefix to be applied to name for matching
                null, // No tag name: match any tag name
                false, // No prefix to be applied to tag name
                PermissionPageTag.getTagName(), // Name of the attribute that will be matched
                true, // Apply dialect prefix to attribute name
                PRECEDENCE, // Precedence (inside dialect's precedence)
                true); // Remove the matched attribute afterwards
        this.permissionCheckService = permissionCheckService;
    }


    @Override
    protected void doProcess(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, AttributeName attributeName, String value, IElementTagStructureHandler iElementTagStructureHandler) {
        List<PermissionExpress> list = this.permissionCheckService.getPermissionExpressList();

        Boolean flag = false;
        for(PermissionExpress permissionExpress : list){
            if(permissionExpress.getExpress().equals(value)){
                flag = true;
                break;
            }
        }
        if(flag){
            iElementTagStructureHandler.removeAttribute(attributeName);
        }else{
            iElementTagStructureHandler.removeElement();
        }
    }
}
