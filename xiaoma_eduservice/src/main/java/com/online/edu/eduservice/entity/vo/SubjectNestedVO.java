package com.online.edu.eduservice.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: changyong
 * @Date: create in 19:24 2020/3/20
 * @Description:
 */
@Data
public class SubjectNestedVO {
    private String id;

    private String title;

    private List<SubjectVo> children = new ArrayList<>();
}
