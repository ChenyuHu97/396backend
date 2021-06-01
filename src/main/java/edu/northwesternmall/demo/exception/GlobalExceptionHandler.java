package edu.northwesternmall.demo.exception;


import edu.northwesternmall.demo.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handlerException(Exception e){
        log.error("Default Exception",e);
        return ApiRestResponse.error(ExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(MallException.class)
    @ResponseBody
    public Object handlerMallException(MallException mallException){
        log.error("Default Exception",mallException);
        return ApiRestResponse.error(mallException.getCode(),mallException.getMessage());
    }

    private ApiRestResponse handleBindingResult(BindingResult bindingResult){
        List<String> list = new ArrayList<>();
        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for(int i= 0; i<allErrors.size();i++){
                ObjectError objectError = allErrors.get(i);
                String message =objectError.getDefaultMessage();
                list.add(message);
            }
        }
        if(list.size() == 0){
            return ApiRestResponse.error(ExceptionEnum.REQUEST_PARAM_ERROR);
        }

        return ApiRestResponse.error(ExceptionEnum.REQUEST_PARAM_ERROR.getCode(),list.toString());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException",e);
        return handleBindingResult(e.getBindingResult());
    }

}
