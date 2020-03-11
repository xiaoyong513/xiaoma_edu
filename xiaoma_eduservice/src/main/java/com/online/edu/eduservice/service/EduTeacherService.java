package com.online.edu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.dto.QueryTeacher;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author xiaoma
 * @since 2020-02-29
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 多条件分页查询
     * @param teacherPage
     * @param queryTeacher
     */
    void getMoreConditionPageTeacherList(Page<EduTeacher> teacherPage, QueryTeacher queryTeacher);
}
