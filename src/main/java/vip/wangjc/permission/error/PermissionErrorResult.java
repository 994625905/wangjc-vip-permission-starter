package vip.wangjc.permission.error;

/**
 * @author wangjc
 * @title: PermissionErrorResult
 * @projectName wangjc-vip
 * @date 2020/12/30 - 18:02
 */
public class PermissionErrorResult {

    private static final Integer REFUSE_CODE = 403;
    private static final Integer EXCEPTION_CODE = 500;
    private static final String REFUSE_MESSAGE = "permission check refuse!";
    private static final String EXCEPTION_MESSAGE = "permission check exception!";

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误内容
     */
    private String message;
    /**
     * 错误携带异常
     */
    private Throwable exception;

    private PermissionErrorResult(){

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

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public static PermissionErrorResult error(){
        PermissionErrorResult result = new PermissionErrorResult();
        result.setCode(REFUSE_CODE);
        result.setMessage(REFUSE_MESSAGE);
        return result;
    }

    public static PermissionErrorResult error(String message){
        PermissionErrorResult result = new PermissionErrorResult();
        result.setCode(REFUSE_CODE);
        result.setMessage(message);
        return result;
    }

    public static PermissionErrorResult exception(Throwable e){
        PermissionErrorResult result = new PermissionErrorResult();
        result.setCode(EXCEPTION_CODE);
        result.setMessage(EXCEPTION_MESSAGE);
        result.setException(e);
        return result;
    }

    public static PermissionErrorResult exception(String message, Throwable e){
        PermissionErrorResult result = new PermissionErrorResult();
        result.setCode(EXCEPTION_CODE);
        result.setMessage(message);
        result.setException(e);
        return result;
    }

}
