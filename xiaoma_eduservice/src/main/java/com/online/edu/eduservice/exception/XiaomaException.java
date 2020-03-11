package com.online.edu.eduservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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
}
