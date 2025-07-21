package com.rcs.server.controller;

import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.pojo.Result;
import com.rcs.server.service.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/vendor/management")
public class VendorManageController {

    @Autowired
    VendorService vendorService;

    /**
     * 供应商基础信息分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result pageVendorInfo(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("查询第{}页供应商信息",page);
        PageBean pageBean = vendorService.queryVendorInfo(page,pageSize);
        return Result.success(pageBean);
    }


    @PostMapping("/certificate/upload")
    public Result uploadCertificateFile(Integer id, String filePath) {
        //要为了哪个服务商进行文件上传
        log.info("上传文件为：{}",filePath);
        //如果成功，需要重新将链接返回前端，失败则被全局抛出异常
        String fileUrl = vendorService.uploadCertificateFile(id, filePath);
        //TODO 异常处理还未做
        return Result.success(fileUrl);
    }

//    @DeleteMapping("/certificate/{id}")
//    public Result removeCertificateFile(@PathVariable Integer id) {
//
//    }





}
