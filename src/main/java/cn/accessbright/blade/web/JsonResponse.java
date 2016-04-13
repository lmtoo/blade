package cn.accessbright.blade.web;

/**
 * Created by Administrator on 2016/4/12.
 */
public class JsonResponse {
    private int code;
    private String message;

    public JsonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
