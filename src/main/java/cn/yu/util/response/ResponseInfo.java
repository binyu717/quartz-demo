package cn.yu.util.response;


import java.io.Serializable;

/**
 * 请求返回信息
 * <p>
 * <typeparam name="LunarCalendar">数据类型</typeparam>
 */
public class ResponseInfo<T> implements Serializable {

    public ResponseInfo() {
    }

    private ResponseInfo(T data) {
        setSuccess(true);
        setData(data);
    }

    private ResponseInfo(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    //是否调用成功
    private boolean success;

    //错误信
    private String message = "";
    //数据
    private T data;


    public static <T> ResponseInfo<T> success(T data) {
        return new ResponseInfo<>(data);
    }

    public static <T> ResponseInfo<T> error(String message) {
        return new ResponseInfo<>(false, message, null);
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}