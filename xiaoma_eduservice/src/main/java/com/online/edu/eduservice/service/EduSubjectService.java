package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.vo.SubjectNestedVO;
import com.online.edu.eduservice.entity.vo.SubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author xiaoma
 * @since 2020-03-20
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 从excel中导入课程信息
     * @param file
     * @return
     */
    List<String> importExcelSubject(MultipartFile file);

    /**
     * 获取层级列表
     * @return
     */
    List<SubjectNestedVO> getSubjectNestedVoList();

    /**
     * 根据id删除课程信息
     * @param id
     * @return
     */
    boolean deleteSubjectById(String id);

    boolean createSubject(EduSubject eduSubject);

    /**
     * 根据父节点id查询课程列表
     * @param parentId
     * @return
     */
    List<SubjectVo> getSubjectListByParentId(String parentId);
}
