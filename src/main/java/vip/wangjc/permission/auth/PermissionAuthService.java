package vip.wangjc.permission.auth;

import com.alibaba.fastjson.JSON;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vip.wangjc.permission.cache.service.PermissionCacheService;
import vip.wangjc.permission.entity.PermissionExpress;
import vip.wangjc.permission.entity.PermissionUserAuth;
import vip.wangjc.permission.register.PermissionRegister;
import vip.wangjc.permission.util.PermissionConstant;
import vip.wangjc.permission.util.PermissionUtil;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限认证的服务
 * @author wangjc
 * @title: PermissionAuthService
 * @projectName wangjc-vip
 * @date 2020/12/29 - 19:37
 */
public class PermissionAuthService {

    private final PermissionCacheService cacheService;

    public PermissionAuthService(PermissionCacheService cacheService){
        this.cacheService = cacheService;
    }

    /**
     * 设置登录
     * @param userId
     * @param userName
     */
    public String authPermissionUser(Object userId,Object userName){
        String userSign = PermissionUtil.userSign(userId, userName);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        PermissionUtil.setCookie(response,PermissionConstant.cookieUserSignKey(),userSign);

        /** 创建登录用户 */
        PermissionUserAuth userAuth = new PermissionUserAuth(userId,userName);
        this.cacheService.set(PermissionConstant.userSignKey(userId.toString()),userAuth,PermissionRegister.getExpire() );

        return userSign;
    }

    /**
     * 设置权限验证list
     * @param list
     * @param permissionField
     * @param requestUrlField
     * @param userSign：用户签名
     */
    public void authPermissionList(List<?> list, String permissionField, String requestUrlField, String userSign) {
        try {
            List<PermissionExpress> expressList = new ArrayList<>();
            for (Object entity : list) {
                Field field_p = entity.getClass().getDeclaredField(permissionField);
                Field field_r = entity.getClass().getDeclaredField(requestUrlField);

                field_p.setAccessible(true);
                field_r.setAccessible(true);
                Object permission = field_p.get(entity);
                Object request = field_r.get(entity);
                if (permission != null && request != null) {
                    expressList.add(new PermissionExpress(permission.toString(), request.toString()));
                }
            }
            if(expressList.size() > 0){

                String userId = PermissionUtil.getUserId(userSign, String.class);

                this.cacheService.set(PermissionConstant.expressListKey(userId), JSON.toJSONString(list), PermissionRegister.getExpire());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
