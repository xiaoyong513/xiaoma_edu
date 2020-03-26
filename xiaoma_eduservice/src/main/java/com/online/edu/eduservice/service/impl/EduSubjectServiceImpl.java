package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.common.constants.ResultCodeEnum;
import com.online.edu.common.exception.XiaomaException;
import com.online.edu.eduservice.entity.EduSubject;
import com.online.edu.eduservice.entity.vo.SubjectNestedVO;
import com.online.edu.eduservice.entity.vo.SubjectVo;
import com.online.edu.eduservice.mapper.EduSubjectMapper;
import com.online.edu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author xiaoma
 * @since 2020-03-20
 */
@Service
@Slf4j
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public List<String> importExcelSubject(MultipartFile file) {
        try {
            // 1 读取文件
            InputStream inputStream = file.getInputStream();
            // 2 创建workbook
            Workbook workbook = new HSSFWorkbook(inputStream);
            // 3 获取sheet
            Sheet sheet = workbook.getSheetAt(0);
            // 存储错误信息
            List<String> msg = new ArrayList<>();
            // 4 获取行
            // 循环遍历行 从第二行开始
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    log.error("[上传课程excel] 表格第{}行数据为空,请输入数据", i + 1);
                    msg.add(String.format("表格第%s行数据为空", i + 1));
                    continue;
                }
                // 5 获取列
                Cell cellOne = row.getCell(0);
                if (cellOne == null) {
                    log.error("[上传课程excel] 表格第{}行{}列数据为空,请输入数据", i + 1, 1);
                    msg.add(String.format("表格第%s行%s列数据为空", i + 1, 1));
                    continue;
                }
                // 6获取值
                String cellOneValue = cellOne.getStringCellValue();
                // 一级分类
                String parentId = null;
                // 去重 判断数据是否存在
                EduSubject existOneSubject = this.existOneSubject(cellOneValue);
                if (existOneSubject == null) {
                    // 7添加到数据库
                    EduSubject eduSubject = new EduSubject();
                    eduSubject.setTitle(cellOneValue);
                    eduSubject.setParentId("0");
                    eduSubject.setSort(0);
                    baseMapper.insert(eduSubject);
                    parentId = eduSubject.getId();
                } else {
                    parentId = existOneSubject.getId();
                }
                Cell cellTwo = row.getCell(1);
                if (cellTwo == null) {
                    log.error("[上传课程excel] 表格第{}行{}列数据为空,请输入数据", i + 1, 2);
                    msg.add(String.format("表格第%s行%s列数据为空", i + 1, 2));
                    continue;
                }
                // 6获取值
                String cellTwoValue = cellTwo.getStringCellValue();
                // 添加一级分类和二级分类 体现出层级关系
                EduSubject existTwoSubject = this.existTwoSubject(cellTwoValue, parentId);
                if (existTwoSubject == null) {
                    // 7添加到数据库
                    EduSubject eduSubject = new EduSubject();
                    eduSubject.setTitle(cellTwoValue);
                    eduSubject.setParentId(parentId);
                    eduSubject.setSort(0);
                    baseMapper.insert(eduSubject);
                }
            }
            return msg;
        } catch (Exception e) {
            log.error("[上传课程excel] 失败,异常原因:", e);
            throw new XiaomaException(ResultCodeEnum.FILE_UPLOAD_ERROR.getCode(), ResultCodeEnum.FILE_UPLOAD_ERROR.getMessage());
        }
    }

    @Override
    public List<SubjectNestedVO> getSubjectNestedVoList() {
        List<SubjectNestedVO> subjectNestedVOList = new ArrayList<>();
        // 获取一级分类
        List<EduSubject> subjectNestedList = getSubjectNestedList();
        // 获取二级分类
        List<EduSubject> subjectList = getSubjectList();
        // 拼接
        if (CollectionUtils.isEmpty(subjectNestedList)) {
            log.info("[获取课程] 数据库中暂时还没有一级课程信息");
            return subjectNestedVOList;
        }
        for (EduSubject eduSubject : subjectNestedList) {
            SubjectNestedVO subjectNestedVO = new SubjectNestedVO();
            BeanUtils.copyProperties(eduSubject, subjectNestedVO);
            if (CollectionUtils.isEmpty(subjectList)) {
                log.info("[获取课程] 一级课程{}暂时还没有二级课程", eduSubject.getTitle());
                continue;
            }
            List<SubjectVo> subjectVoList = new ArrayList<>();
            Iterator<EduSubject> iterator = subjectList.iterator();
            while (iterator.hasNext()) {
                EduSubject subject = iterator.next();
                if (subject.getParentId().equals(eduSubject.getId())) {
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(subject, subjectVo);
                    subjectVoList.add(subjectVo);
                    iterator.remove();
                }
            }
            subjectNestedVO.setChildren(subjectVoList);
            subjectNestedVOList.add(subjectNestedVO);
        }
        return subjectNestedVOList;
    }

    @Override
    public boolean deleteSubjectById(String id) {
        // 判断一级分类下面是否有二级分类 如果有 不删除
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            log.error("[删除课程] 删除失败,因为该课程存在二级分类");
            return false;
        } else {
            int result = baseMapper.deleteById(id);
            log.info("[删除课程] 删除成功");
            return result > 0;
        }
    }

    @Override
    public boolean createSubject(EduSubject eduSubject) {
        EduSubject existSubject;
        if (StringUtils.isBlank(eduSubject.getTitle())) {
            log.error("[添加课程] 失败, 课程名称不能为空");
            throw new XiaomaException(ResultCodeEnum.CREATE_SUBJECT_TITLE_NAME_IS_NULL_ERROR.getCode(), "课程名称不能为空");
        }
        if (StringUtils.isBlank(eduSubject.getParentId()) || Objects.equals("0", eduSubject.getParentId())) {
            existSubject = existOneSubject(eduSubject.getTitle());
            eduSubject.setParentId("0");
        } else {
            existSubject = existTwoSubject(eduSubject.getTitle(), eduSubject.getParentId());
        }
        if (existSubject != null) {
            log.info("[添加课程] 失败, 因为课程名称:{}已经存在", eduSubject.getTitle());
            return false;
        }
        int insert = baseMapper.insert(eduSubject);
        return insert > 0;
    }

    @Override
    public List<SubjectVo> getSubjectListByParentId(String parentId) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        queryWrapper.orderByDesc("sort", "id");
        List<EduSubject> eduSubjects = baseMapper.selectList(queryWrapper);
        List<SubjectVo> subjectVoList = eduSubjects.stream().map(e -> new SubjectVo(e)).collect(Collectors.toList());
        return subjectVoList;
    }

    private List<EduSubject> getSubjectList() {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("parent_id", "0");
        queryWrapper.orderByDesc("sort", "id");
        List<EduSubject> eduSubjects = baseMapper.selectList(queryWrapper);
        return eduSubjects;
    }

    private List<EduSubject> getSubjectNestedList() {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", "0");
        queryWrapper.orderByDesc("sort", "id");
        List<EduSubject> eduSubjects = baseMapper.selectList(queryWrapper);
        return eduSubjects;
    }

    // 判断数据表中是否存在一级分类
    private EduSubject existOneSubject(String title) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", "0");
        EduSubject eduSubject = baseMapper.selectOne(queryWrapper);
        return eduSubject;
    }

    // 判断数据表中是否存在二级分类
    private EduSubject existTwoSubject(String title, String parentId) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", parentId);
        EduSubject eduSubject = baseMapper.selectOne(queryWrapper);
        return eduSubject;
    }
}
