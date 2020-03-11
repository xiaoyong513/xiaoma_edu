package com.online.edu.eduservice.handler;

import com.online.edu.common.R;
import com.online.edu.eduservice.exception.XiaomaException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: changyong
 * @Date: create in 18:23 2020/3/1
 * @Description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("全局异常");
    }


    @ExceptionHandler(XiaomaException.class)
    @ResponseBody
    public R myError(XiaomaException e) {
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMessage());
    }
}
