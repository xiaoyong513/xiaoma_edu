package com.online.edu.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: changyong
 * @Date: create in 19:16 2020/2/29
 * @Description:
 */
@Data
public class R {

    private Boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<>();

    private R() {
    }


    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("操作成功");
        return r;
    }


    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("操作失败");
        return r;
    }


    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }


    public R message(String message) {
        this.setMessage(message);
        return this;
    }


    public R code(Integer code) {
        this.setCode(code);
        return this;
    }


    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }


    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
