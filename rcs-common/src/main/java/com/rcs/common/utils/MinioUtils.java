package com.rcs.common.utils;

import com.rcs.common.properties.MinioProperties;
import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    public void createMinioBucket(MinioClient minioClient, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {//创建一个新的bucket
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }else {
            log.info("Bucket already exists");
        }
    }

    /**
     * 上传单个文件（如果上传成功需要要返回对应的文件地址以供数据库存储）
     * @param minioClient
     * @param bucketName
     * @param filepath
     * @param objectName
     * @return
     * @throws IOException
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    public String uploadFile(MinioClient minioClient, String bucketName, String filepath, String objectName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        //上传文件
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName) //这里是存储在minio上的对象名
                        .filename(filepath)//文件本地存储路径
                        .build());

        //返回文件访问路径
        return getFiletUrl(bucketName, objectName);
    }

    /**
     * 删除单个文件（如果删除过程中出现异常则会抛出）
     * @param minioClient
     * @param bucketName
     * @param objectName
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    public void removeFile(MinioClient minioClient, String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        //删除文件
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build());
    }

    //获取文件类型
    public String getFileTypeName(String filePath){
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    //构建文件专属存储名
    public String getFileObjectName(String filePath){//这里的filePath是文件本地存储路径
        return UUID.randomUUID().toString() + "." + getFileTypeName(filePath);
    }

    //获取文件存储名
    public String getFileObjectNameFromFileUrl(String fileUrl){//这里的fileUrl是文件minio存储路径
        return fileUrl.substring(fileUrl.lastIndexOf("/")+1);
    }

    //获取文件所属bucket
    public String getBucketNameFromFilePath(String filePath){
        return getFileTypeName(filePath);
    }


    //获取文件存储地址
    public String getFiletUrl(String bucketName, String objectName) {
        return minioProperties.getEndpoint() + bucketName + "/" + bucketName + "/" + objectName;
    }


}
