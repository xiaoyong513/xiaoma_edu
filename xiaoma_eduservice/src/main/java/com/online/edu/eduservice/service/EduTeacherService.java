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

    /**
     * 根据id删除讲师
     * @param id
     * @return
     */
    boolean deleteTeacherById(String id);

    /**
     * 添加讲师
     * @param eduTeacher
     * @return
     */
    boolean createTeacher(EduTeacher eduTeacher);

    /**
     * 修改讲师
     * @param eduTeacher
     * @return
     */
    boolean updateTeacherById(EduTeacher eduTeacher);
}
