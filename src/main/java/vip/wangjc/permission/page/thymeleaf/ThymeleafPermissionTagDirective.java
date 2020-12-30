package vip.wangjc.permission.page.thymeleaf;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import vip.wangjc.permission.auth.PermissionCheckService;
import vip.wangjc.permission.page.PermissionPageTag;
import vip.wangjc.permission.page.thymeleaf.processor.ThymeleafPermissionProcessor;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Thymeleaf权限标签类
 * @author wangjc
 * @title: ThymeleafPermissionTagDirective
 * @projectName wangjc-vip
 * @date 2020/12/30 - 14:03
 */
public class ThymeleafPermissionTagDirective extends AbstractProcessorDialect {

    private final PermissionCheckService permissionCheckService;

    public ThymeleafPermissionTagDirective(PermissionCheckService permissionCheckService){
        super(PermissionPageTag.getName(),PermissionPageTag.getPrefix(), StandardDialect.PROCESSOR_PRECEDENCE);
        this.permissionCheckService = permissionCheckService;
    }


    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        LinkedHashSet<IProcessor> processors = new LinkedHashSet<IProcessor>();

        /** 此处可以根据具体需求添加多个 */
        processors.add(new ThymeleafPermissionProcessor(this.permissionCheckService,PermissionPageTag.getPrefix()));

        return processors;
    }
}
