package edu.northwesternmall.demo.exception;

public enum ExceptionEnum {
    NEED_USER_NAME(10001,"empty username"),
    NEED_PASSWORD(10002,"empty password"),
    PASSWORD_TOSHORT(10003,"password too short"),
    NAME_EXISTED(10004,"User existed"),
    INSERT_FAILED(10005,"Insertion is not completed"),
    SYSTEM_ERROR(20000,"system error"),
    WRONG_PASSWORD(10006,"wrong password"),
    NEED_LOGIN(10007,"NEED LOGIN"),
    UPDATE_FAIL(10008,"UPDATE FAIL"),
    NOT_ADMIN(10009,"Not Admin"),
    NAME_NOT_NULL(10010,"parameter cannot be null"),
    CREATE_FAILED(100011,"create_failed"),
    REQUEST_PARAM_ERROR(10012,"PARAM NOT CORRECT"),
    DELETE_FAIL(10013,"Delte Fail");

    Integer code;
    String msg;


    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
