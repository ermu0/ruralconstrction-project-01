package com.rcs.server.service.impl;

import com.rcs.common.utils.MinioUtils;
import com.rcs.server.service.MinioFileService;
import io.minio.MinioClient;
import io.minio.MinioProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class MinioFileServiceImpl implements MinioFileService {

    @Autowired
    MinioClient minioClient;

    @Autowired
    MinioUtils minioUtils;

    /**
     * 上传文件具体操作
     * @param file
     * @return
     */
    @Override
    public String uploadFile(@RequestPart("file") MultipartFile file) {
        try {
            //bucket是预先创建的，接下来需要做的是创建文件夹，然后往文件夹里面存储对应文件
            //但是又由于minio中的bucket没有文件夹的说法，只会根据文件的objectName中存在的斜杠来展示出对应的虚拟文件夹
            //所以只需要在构建文件专属名时加上前缀就行了

            //首先获取桶名
            String bucketName = minioUtils.getBucketName();
            //其次构建存储名
            String objectName = minioUtils.getFileObjectName(file);
            //调用客户端进行上传操作
            String fileUrl = minioUtils.uploadFile(minioClient,bucketName,file,objectName);
            //创建对应的url并返回
            return fileUrl;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("上传失败");
        }
    }



}
