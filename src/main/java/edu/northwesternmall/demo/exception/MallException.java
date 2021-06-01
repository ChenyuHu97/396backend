package edu.northwesternmall.demo.exception;

public class MallException extends Exception{
    private final Integer code;
    private final String message;

    public MallException(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public MallException(ExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
