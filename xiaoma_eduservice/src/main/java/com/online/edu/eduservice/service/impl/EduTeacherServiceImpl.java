package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.entity.dto.QueryTeacher;
import com.online.edu.eduservice.mapper.EduTeacherMapper;
import com.online.edu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author xiaoma
 * @since 2020-02-29
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     * 多条件分页查询
     *
     * @param teacherPage
     * @param queryTeacher
     */
    @Override
    public void getMoreConditionPageTeacherList(Page<EduTeacher> teacherPage, QueryTeacher queryTeacher) {
        if (queryTeacher == null) {
            baseMapper.selectPage(teacherPage, null);
            return;
        }

        String name = queryTeacher.getName();

        String level = queryTeacher.getLevel();

        String startTime = queryTeacher.getStartTime();

        String endTime = queryTeacher.getEndTime();

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(name)) {
            wrapper.like("name", name);
        }

        if (StringUtils.isNotBlank(level)) {
            wrapper.eq("level", level);
        }

        if (StringUtils.isNotBlank(startTime)) {
            wrapper.ge("gmt_create", startTime);
        }

        if (StringUtils.isNotBlank(endTime)) {
            wrapper.le("gmt_create", endTime);
        }

        baseMapper.selectPage(teacherPage, wrapper);
    }

    @Override
    public boolean deleteTeacherById(String id) {
        int result = baseMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public boolean createTeacher(EduTeacher eduTeacher) {
        int result = baseMapper.insert(eduTeacher);
        return result > 0;
    }

    @Override
    public boolean updateTeacherById(EduTeacher eduTeacher) {
        int result = baseMapper.updateById(eduTeacher);
        return result > 0;
    }
}
