package vip.wangjc.permission.entity;

/**
 * 权限拦截的结果
 * @author wangjc
 * @title: PermissionResult
 * @projectName wangjc-vip
 * @date 2020/12/29 - 16:37
 */
public enum PermissionResult {

    SUCCESS(200,"success"),
    FAIL(0,"fail"),
    ERROR(500,"system exception error");

    private Integer code;
    private String message;

    private PermissionResult(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
