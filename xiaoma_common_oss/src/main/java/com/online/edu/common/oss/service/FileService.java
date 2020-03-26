package com.online.edu.common.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: changyong
 * @Date: create in 14:56 2020/3/15
 * @Description:
 */
public interface FileService {

    /**
     * 上传文件到阿里云
     *
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
