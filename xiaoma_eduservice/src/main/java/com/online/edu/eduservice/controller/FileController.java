package com.online.edu.eduservice.controller;

import com.online.edu.common.R;
import com.online.edu.eduservice.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: changyong
 * @Date: create in 14:52 2020/3/15
 * @Description:
 */

@Api("阿里云文件管理")
@RestController
@RequestMapping("/eduservice/sso")
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;

    // 上传图片
    @ApiOperation("上传文件")
    @PostMapping
    public R upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file,
            @ApiParam(name = "host", value = "文件路径", required = false)
            @RequestParam("host") String host) {
        String uploadUrl = fileService.upload(file, host);
        return R.ok().message("文件上传成功").data("url", uploadUrl);
    }
}
