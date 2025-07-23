package com.rcs.common.utils;

import com.rcs.common.properties.MinioProperties;
import io.minio.*;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Component
public class MinioUtils {

    @Autowired
    MinioProperties minioProperties;

    /**
     * 为minio创建一个bucket，并判断该bucket是否存在
     * @param minioClient
     * @param bucketName
     * @return
     */
    public void createMinioBucket(MinioClient minioClient, String bucketName)  {
        try{
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {//创建一个新的bucket
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("Bucket Created");
            }else {
                log.info("Bucket {} already exists.", bucketName);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("bucket创建失败");
        }

    }

    /**
     * 上传单个文件（如果上传成功需要要返回对应的文件地址以供数据库存储）
     * @param minioClient
     * @param bucketName
     * @param file
     * @param objectName
     * @return
     */
    public String uploadFile(MinioClient minioClient, String bucketName, MultipartFile file, String objectName) {        //上传文件
        try{
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1) //三个参数含义是：字节流、字节大小、分片大小（-1代表默认策略）
                            .contentType(file.getContentType()) //代表文件类型，主要为了用户在调用时能够正确识别并处理
                            .build()
            );
            log.info("Uploaded file successfully");
            //返回文件访问路径
            return getFiletUrl(bucketName, objectName);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("文件上传失败");
        }
    }

    /**
     * 删除单个文件（如果删除过程中出现异常则会抛出）
     * @param minioClient
     * @param bucketName
     * @param objectName
     */
    public void removeFile(MinioClient minioClient, String bucketName, String objectName) {
        try{
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("文件删除失败");
        }
    }


    //构建文件专属存储名
    public String getFileObjectName(MultipartFile file){
        //获取文件原始名称
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        String ext = fileName.substring(fileName.lastIndexOf("."));
        //构建唯一标识
        String uuid = UUID.randomUUID().toString();
        //构建日期前缀，方便文件归档
        String datePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        //构建文件专属存储名（日期前缀+唯一标识+后缀）
        return String.join("/", datePrefix, uuid + (ext != null ? "." + ext : ""));
    }

    //获取文件存储的桶名
    public String getBucketName(){
        return minioProperties.getBucketName();
    }

    //获取文件存储地址
    public String getFiletUrl(String bucketName, String objectName) {
        return String.join("/", minioProperties.getBucketName(), bucketName, objectName);
    }

}
