package com.rcs.server.controller;

import com.rcs.server.domain.pojo.Result;
import com.rcs.server.service.MinioFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/file")
public class MinioFileController {

    @Autowired
    MinioFileService minioFileService;

    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file){
        log.info("file:{} is uploading", file.getOriginalFilename());
        String fileUrl = minioFileService.uploadFile(file);
        return Result.success(fileUrl);
    }

/**这个功能暂时搁置下**/
//    @DeleteMapping("/remove")
//    public Result removeFile(String fileUrl){
//        log.info("fileUrl:{} is removing", fileUrl);
//
//        return Result.success("删除成功");
//    }

}
