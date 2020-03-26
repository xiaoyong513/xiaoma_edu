package com.online.edu.eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.dto.form.CourseInfoForm;
import com.online.edu.eduservice.entity.vo.CourseInfoVO;
import com.online.edu.eduservice.service.EduCourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xiaoma
 * @since 2020-03-21
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;


    @PostMapping()
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        String id = eduCourseService.insertCourseInfo(courseInfoForm);
        if (StringUtils.isNotBlank(id)) {
            return R.ok().message("添加成功").data("id", id);
        } else {
            return R.error().message("添加失败");
        }
    }

    @PutMapping()
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        String id = eduCourseService.updateCourseInfo(courseInfoForm);
        if (StringUtils.isNotBlank(id)) {
            return R.ok().message("修改成功").data("id", id);
        } else {
            return R.error().message("修改失败");
        }
    }

    @GetMapping("/{id}")
    public R getCourseInfoById(@PathVariable("id") String id) {
        CourseInfoVO courseInfoVO = eduCourseService.getCourseInfoById(id);
        return R.ok().data("course", courseInfoVO);
    }
}

