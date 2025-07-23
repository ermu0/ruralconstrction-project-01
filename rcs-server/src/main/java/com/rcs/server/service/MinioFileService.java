package com.rcs.server.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface MinioFileService {

    /**
     * 上传文件
     * @param file
     * @return
     */
    String uploadFile(MultipartFile file);
}
