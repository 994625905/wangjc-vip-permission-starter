package vip.wangjc.permission.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.wangjc.permission.register.PermissionRegister;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangjc
 * @title: PermissionUtil
 * @projectName wangjc-vip
 * @date 2020/12/29 - 16:53
 */
public class PermissionUtil {

    private final static Logger logger = LoggerFactory.getLogger(PermissionUtil.class);
    private final static String USERID = "userId";
    private final static String USERNAME = "userName";

    /**
     * 用户签名：userName+password = token
     * @param userId
     * @param userName
     * @return
     */
    public static String userSign(Object userId, Object userName){
        if(userId == null || userName == null){
            logger.error("userSign error:userId null,userName null");
            return null;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(userName.toString());
            return JWT.create().withClaim(USERID, userId.toString()).withClaim(USERNAME, userName.toString()).sign(algorithm);
        }catch (Exception e){
            logger.error("userSign error:reason[{}]",e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据签名token解析用户名
     * @param token
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getUserName(String token, Class<T> clazz){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim(USERNAME).as(clazz);
    }

    /**
     * 根据签名token解析用户名
     * @param token
     * @return
     */
    public static String getUserName(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim(USERNAME).asString();
    }

    /**
     * 根据签名token解析密码
     * @param token
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getUserId(String token, Class<T> clazz){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim(USERID).as(clazz);
    }

    /**
     * 根据签名解析密码
     * @param token
     * @return
     */
    public static Integer getUserId(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim(USERID).as(Integer.class);
    }

    /**
     * 设置
     * @param response
     * @param name
     * @param value
     */
    public static void setCookie(HttpServletResponse response, String name, String value){
        setCookie(response, name, value, null, "/", PermissionRegister.getExpire().intValue(), false);
    }

    /**
     * 根据name，查询该cookie的value
     * @param request
     * @param name
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String name){
        Cookie cookie = getCookie(request, name);
        if(cookie != null){
            return cookie.getValue();
        }else{
            return null;
        }
    }

    /**
     * 根据name删除cookie,设置maxAge为0
     * @param request
     * @param response
     * @param name
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name){
        Cookie cookie = getCookie(request, name);
        if(cookie != null){
            setCookie(response,name,"",null,"/",0,true);
        }
    }

    /**
     * 根据name查询cookie
     * @param request
     * @param name
     * @return
     */
    private static Cookie getCookie(HttpServletRequest request, String name){
        Cookie[] cookiesArray = request.getCookies();
        if(cookiesArray != null && cookiesArray.length >0){
            for (Cookie cookie:cookiesArray){
                if(cookie.getName().equals(name)){
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 设置cookie
     * @param response
     * @param name，新建cookie的name
     * @param value,新建cookie的value
     * @param domain，跨域共享信任的站点
     * @param path，同一应用服务内共享
     * @param maxAge，如果设置为负值的话，则为浏览器进程Cookie(内存中保存)，关闭浏览器就失效；如果设置为0，则立即删除该Cookie。正值则为保存时间
     * @param isHttpOnly，增加对xss防护的安全系数，如果为true，只能通过HTTP请求，JavaScript无法请求到
     */
    private static void setCookie(HttpServletResponse response, String name, String value, String domain, String path, int maxAge, boolean isHttpOnly){
        Cookie cookie = new Cookie(name,value);
        if(domain != null){
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(isHttpOnly);
        response.addCookie(cookie);
    }

}
