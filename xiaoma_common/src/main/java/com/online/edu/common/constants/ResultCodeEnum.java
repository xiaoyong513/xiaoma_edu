package com.online.edu.common.constants;

import lombok.Getter;

/**
 * @Author: changyong
 * @Date: create in 19:58 2020/3/15
 * @Description:
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(true, 20000, "成功"),

    UNKNOWN_REASON(false, 20001, "未知错误"),

    FILE_UPLOAD_ERROR(false, 20004, "文件上传错误"),

    /**
     * subject code 20100-20199
     */
    IMPORT_SUBJECT_EXCEL_FILE_ERROR(false, 20100, "上传课程表文件错误"),

    CREATE_SUBJECT_TITLE_NAME_IS_NULL_ERROR(false, 20101, "上传课程表文件错误"),

     /**
     * course code 20200-20299
     */
    INSERT_COURSE_ERROR(false, 20200, "添加课程失败"),


    ;

    private Boolean success;

    private Integer code;

    private String message;

    private ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
