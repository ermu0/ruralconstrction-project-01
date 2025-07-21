package com.rcs.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rcs.common.utils.MinioUtils;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.entity.Vendor;
import com.rcs.server.mapper.VendorMapper;
import com.rcs.server.service.VendorService;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Slf4j
@Service
public class VendorServiceImpl extends ServiceImpl<VendorMapper, Vendor> implements VendorService {

    @Autowired
    VendorMapper vendorMapper;

    @Autowired
    MinioUtils minioUtils;

    @Autowired
    MinioClient minioClient;

    /**
     * 供应商信息分页查询具体操作
     * @param pageNow
     * @param pageSize
     * @return
     */
    @Override
    public PageBean queryVendorInfo(Integer pageNow, Integer pageSize) {
        Page<Vendor> page = Page.of(pageNow,pageSize);
        IPage<Vendor> vendorIPage = vendorMapper.selectVendorInfo(page);
        return new PageBean(vendorIPage.getTotal(),vendorIPage.getRecords());
    }

    /**
     * 供应商资质证书上传具体操作
     * @param id
     * @param filePath
     * @return
     */
    @Override
    public String uploadCertificateFile(Integer id, String filePath) {//本来应该在前端做一个文件是否存在的判断，但是前端多半因为懒不想做，只能将就了
        //先根据主键查看数据库表此字段是否为空
        LambdaQueryWrapper<Vendor> queryWrapper = new LambdaQueryWrapper<Vendor>()
                .eq(Vendor::getId, id)
                .isNotNull(Vendor::getCertificate);
        //不为空则需要先根据存储件名删除minio服务端存储的该文件
        if (vendorMapper.selectCount(queryWrapper) > 0){
            log.info("文件需要先进行删除操作");
            try {
                //首先获取该证书的url地址
                Vendor vendor = vendorMapper.selectOne(queryWrapper);
                String fileUrl = vendor.getCertificate();
                //接着获取文件存储桶名
                String bucketName = minioUtils.getBucketNameFromFileUrl(fileUrl);
                //然后获取文件存储名
                String objectName = minioUtils.getFileObjectNameFromFileUrl(fileUrl);
                log.info("待删除文件为：{}，所属桶为：{}",objectName,bucketName);
                //再然后根据桶名与文件存储的对象名再minio服务端上进行删除
                minioUtils.removeFile(minioClient,bucketName,objectName);
                log.info("文件{}已删除",objectName);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //不管为不为空都要进行uploadOrderFile这个Service实现方法中的操作
        //重新利用本地路径中文件名获取对应的文件后缀
        String fileUrl = "";//这是重新构建的文件url
        try {
            //首先要判断该文件类型，然后通过类型匹配对应的bucket
            String bucketName = minioUtils.getBucketNameFromFilePath(filePath);
            //利用客户端判断并创建bucket
            minioUtils.createMinioBucket(minioClient,bucketName);
            //构建对应的文件存储名
            String objectName = minioUtils.getFileObjectName(filePath);
            //上传文件返回文件存储url
            fileUrl = minioUtils.uploadFile(minioClient, bucketName, filePath, objectName);
            log.info("文件{}上传成功",objectName);
            //构建更新条件
            LambdaUpdateWrapper<Vendor> updateWrapper = new LambdaUpdateWrapper<Vendor>()
                    .set(Vendor::getCertificate,fileUrl)
                    .set(Vendor::getUpdateTime,LocalDateTime.now())
                    .eq(Vendor::getId,id);
            //更新表
            //TODO 本来还应该做一个异常判断，就是判断是否更新成功
            vendorMapper.update(updateWrapper);
        }catch(Exception e){
            e.printStackTrace();
        }

        //最后返回新的fileUrl
        return fileUrl;
    }


}
