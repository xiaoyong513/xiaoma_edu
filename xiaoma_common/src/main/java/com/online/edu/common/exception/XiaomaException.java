package com.online.edu.common.exception;

import com.online.edu.common.constants.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: changyong
 * @Date: create in 18:30 2020/3/1
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XiaomaException extends RuntimeException {

    private int code;

    private String message;

    //public XiaomaException(ResultCodeEnum resultCodeEnum) {
    //    this.code= resultCodeEnum.getCode();
    //    this.message = resultCodeEnum.getMessage();
    //}
}
