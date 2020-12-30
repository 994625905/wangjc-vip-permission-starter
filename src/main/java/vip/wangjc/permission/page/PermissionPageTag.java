package vip.wangjc.permission.page;

/**
 * 权限页面的标签设置
 * @author wangjc
 * @title: PermissionPageTag
 * @projectName wangjc-vip
 * @date 2020/12/30 - 18:52
 */
public class PermissionPageTag {

    private static final String NAME = "wangjc-permission";
    private static final String PREFIX = "permission";
    private static final String TAG_NAME = "express";

    /**
     * 获取标签名称
     * @return
     */
    public static String getName(){
        return NAME;
    }

    /**
     * 获取标签前缀
     * @return
     */
    public static String getPrefix(){
        return PREFIX;
    }

    /**
     * 获取表达式名称
     * @return
     */
    public static String getTagName(){
        return TAG_NAME;
    }
}
