package vip.wangjc.permission.util;

/**
 * @author wangjc
 * @title: PermissionConstant
 * @projectName wangjc-vip
 * @date 2020/12/29 - 19:57
 */
public class PermissionConstant {

    private static final String USER_KEY_PREFIX = "wangjc_permission_user_";//用户的签名key前缀
    private static final String EXPRESS_KEY_PREFIX = "wangjc_permission_list_";//权限列表的key前缀

    /**
     * 获取权限列表的key
     * @param userId
     * @return
     */
    public static String expressListKey(String userId){
        return EXPRESS_KEY_PREFIX + userId;
    }

    /**
     * 获取用户签名的key
     * @param userId
     * @return
     */
    public static String userSignKey(String userId){
        return USER_KEY_PREFIX + userId;
    }

    /**
     * 当前客户端cookie的存储key
     * @return
     */
    public static String cookieUserSignKey(){
        return USER_KEY_PREFIX;
    }
}
