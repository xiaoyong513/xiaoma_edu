package com.online.edu.eduservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.online.edu.common.constants.ResultCodeEnum;
import com.online.edu.common.exception.XiaomaException;
import com.online.edu.eduservice.service.FileService;
import com.online.edu.eduservice.util.ConstantPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author: changyong
 * @Date: create in 14:57 2020/3/15
 * @Description:
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file, String host) {
        try {
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = ConstantPropertiesUtil.ENDPOINT;
            // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
            String accessKeyId = ConstantPropertiesUtil.KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;
            String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
            String fileHost = ConstantPropertiesUtil.FILE_HOST;
            if (StringUtils.isNotBlank(host)) {
                fileHost = host;
            }

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            InputStream inputStream = file.getInputStream();
            String date = new DateTime().toString("yyyy/MM/dd");
            String original = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = uuid + fileType;
            String fileUrl = fileHost + "/" + date + "/" + newName;
            //String fileUrl = Paths.get(fileHost, date, newName).toString();
            // 上传文件流。
            ossClient.putObject(bucketName, fileUrl, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();
            //获取url地址s
            String uploadUrl = "https://" + bucketName + "." + endpoint + "/" + fileUrl;
            return uploadUrl;
        } catch (IOException e) {
            log.error("[上传文件] 上传{}文件失败,原因: IO异常", file.getOriginalFilename(), e);
            throw new XiaomaException(ResultCodeEnum.FILE_UPLOAD_ERROR.getCode(), "上传文件到OSS失败");
        }
    }
}
