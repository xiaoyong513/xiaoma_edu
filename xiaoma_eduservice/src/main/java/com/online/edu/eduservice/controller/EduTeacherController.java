package com.online.edu.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.dto.QueryTeacher;
import com.online.edu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author xiaoma
 * @since 2020-02-29
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 根据id修改教师
     *
     * @param eduTeacher
     * @return
     */
    @PutMapping("/{id}")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.updateTeacherById(eduTeacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 根据id查询教师
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getTeacherById(@PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    /**
     * 添加教师
     *
     * @param eduTeacher
     * @return
     */
    @PostMapping()
    public R createTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.createTeacher(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 多条件分页查询教师
     *
     * @param page
     * @param limit
     * @param queryTeacher
     * @return
     */
    @PostMapping("/pageList/{page}/{limit}")
    public R getMoreConditionPageTeacherList(@PathVariable Long page,
                                             @PathVariable Long limit,
                                             @RequestBody QueryTeacher queryTeacher) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);

        eduTeacherService.getMoreConditionPageTeacherList(teacherPage, queryTeacher);

        return R.ok().data("total", teacherPage.getTotal()).data("items", teacherPage.getRecords());

    }


    /**
     * 分页查询
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/pageList/{page}/{limit}")
    public R getPageTeacherList(@PathVariable Long page,
                                @PathVariable Long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);

        eduTeacherService.page(teacherPage, null);

        return R.ok().data("total", teacherPage.getTotal()).data("items", teacherPage.getRecords());

    }

    /**
     * 获取所有老师
     *
     * @return
     */
    @GetMapping
    public R getAllTeacherList() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    /**
     * 逻辑删除教师
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R deleteTeacherById(@PathVariable String id) {
        boolean b = eduTeacherService.deleteTeacherById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error().message("系统异常");
        }
    }
}

