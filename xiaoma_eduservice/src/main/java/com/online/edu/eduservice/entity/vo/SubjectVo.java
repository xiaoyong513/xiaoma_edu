package com.online.edu.eduservice.entity.vo;

import com.online.edu.eduservice.entity.EduSubject;
import lombok.Data;

/**
 * @Author: changyong
 * @Date: create in 19:21 2020/3/20
 * @Description:
 */
@Data
public class SubjectVo {

    private String id;

    private String title;

    public SubjectVo() {
    }

    public SubjectVo(EduSubject eduSubject) {
        this.id = eduSubject.getId();
        this.title = eduSubject.getTitle();
    }
}
