package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.dto.form.CourseInfoForm;
import com.online.edu.eduservice.entity.vo.CourseInfoVO;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xiaoma
 * @since 2020-03-21
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程信息
     * @param courseInfoForm
     * @return
     */
    String  insertCourseInfo(CourseInfoForm courseInfoForm);

    /**
     * 根据id获取课程信息
     * @param id
     * @return
     */
    CourseInfoVO getCourseInfoById(String id);

    /**
     * 修改课程信息
     * @param courseInfoForm
     * @return
     */
    String updateCourseInfo(CourseInfoForm courseInfoForm);
}
