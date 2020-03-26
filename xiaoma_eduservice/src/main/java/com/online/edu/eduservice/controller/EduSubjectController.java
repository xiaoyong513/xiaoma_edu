package com.online.edu.eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduSubject;
import com.online.edu.eduservice.entity.vo.SubjectNestedVO;
import com.online.edu.eduservice.entity.vo.SubjectVo;
import com.online.edu.eduservice.service.EduSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author xiaoma
 * @since 2020-03-20
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    // 通过上传excel文件获取文件内容
    @ApiOperation(value = "通过上传excel文件获取文件内容")
    @PostMapping("/import")
    public R importExcelSubject(@RequestParam("file") MultipartFile file) {
        List<String> list = subjectService.importExcelSubject(file);
        if (CollectionUtils.isEmpty(list)) {
            return R.ok();
        } else {
            return R.error().message("部分数据导入失败").data("msgList", list);
        }
    }

    // 获取课程列表
    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("/list")
    public R getSubjectList() {
        List<SubjectNestedVO> subjectNestedVoList = subjectService.getSubjectNestedVoList();
        return R.ok().data("items", subjectNestedVoList);
    }

    // 获取课程列表
    @ApiOperation(value = "根据父节点获取课程列表")
    @GetMapping("/list/{parent-id}")
    public R getSubjectListByParentId(@PathVariable("parent-id") String parentId) {
        List<SubjectVo> subjectVoList = subjectService.getSubjectListByParentId(parentId);
        return R.ok().data("items", subjectVoList);
    }


    @ApiOperation(value = "根据id删除课程")
    @DeleteMapping("/{id}")
    public R deleteSubjectById(@PathVariable("id") String id) {
        boolean result = subjectService.deleteSubjectById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("该课程存在二级目录");
        }
    }

    @ApiOperation(value = "添加分类")
    @PostMapping()
    public R createSubject(@RequestBody EduSubject eduSubject) {
        boolean result = subjectService.createSubject(eduSubject);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("该课程已经存在");
        }
    }

}

