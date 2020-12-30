package vip.wangjc.permission.entity;

/**
 * 权限页面的类型
 * @author wangjc
 * @title: PermissionPageType
 * @projectName wangjc-vip
 * @date 2020/12/30 - 11:32
 */
public enum PermissionPageType {

    FREEMARKER(".ftl"),
    THYMELEAF(".html");

    private String suffix;

    private PermissionPageType(String suffix){
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
