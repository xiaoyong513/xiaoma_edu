package com.online.edu.eduservice.service.impl;

import com.online.edu.common.ResultCode;
import com.online.edu.common.constants.ResultCodeEnum;
import com.online.edu.common.exception.XiaomaException;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduCourseDescription;
import com.online.edu.eduservice.entity.EduSubject;
import com.online.edu.eduservice.entity.dto.form.CourseInfoForm;
import com.online.edu.eduservice.entity.vo.CourseInfoVO;
import com.online.edu.eduservice.mapper.EduCourseMapper;
import com.online.edu.eduservice.service.EduCourseDescriptionService;
import com.online.edu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xiaoma
 * @since 2020-03-21
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduSubjectService eduSubjectService;

    @Override
    @Transactional
    public String insertCourseInfo(CourseInfoForm courseInfoForm) {
        EduCourse course = new EduCourse();

        BeanUtils.copyProperties(courseInfoForm, course);

        int insert = baseMapper.insert(course);
        if (insert == 0) {
            throw new XiaomaException(ResultCodeEnum.INSERT_COURSE_ERROR.getCode(), "添加课程信息失败");
        }

        EduCourseDescription description = new EduCourseDescription();

        description.setDescription(courseInfoForm.getDescription());
        description.setId(course.getId());
        boolean save = eduCourseDescriptionService.save(description);

        if (save) {
            return course.getId();
        } else {
            return null;
        }
    }

    @Override
    public CourseInfoVO getCourseInfoById(String id) {
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        EduCourse course = baseMapper.selectById(id);
        Optional.ofNullable(course).orElseThrow(() -> new XiaomaException(ResultCode.COURSE_IS_NULL, "课程不存在"));
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);
        BeanUtils.copyProperties(course, courseInfoVO);
        courseInfoVO.setDescription(eduCourseDescription.getDescription());
        EduSubject subject = eduSubjectService.getById(course.getSubjectId());
        //EduSubject subjectParent = eduSubjectService.getById(subject.getParentId());
        courseInfoVO.setSubjectParentId(subject.getParentId());
        return courseInfoVO;
    }

    @Override
    public String updateCourseInfo(CourseInfoForm courseInfoForm) {
        EduCourse course = baseMapper.selectById(courseInfoForm.getId());
        Optional.ofNullable(course).orElseThrow(() -> new XiaomaException(ResultCode.COURSE_IS_NULL, "课程不存在"));
        BeanUtils.copyProperties(courseInfoForm, course);
        int i = baseMapper.updateById(course);
        return i > 0 ? course.getId() : null;
    }
}
