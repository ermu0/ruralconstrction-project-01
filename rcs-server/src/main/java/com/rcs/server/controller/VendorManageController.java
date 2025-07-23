package com.rcs.server.controller;

import com.rcs.server.domain.entity.Vendor;
import com.rcs.server.domain.pojo.PageBean;
import com.rcs.server.domain.pojo.Result;
import com.rcs.server.service.VendorManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vendor/management")
public class VendorManageController {

    @Autowired
    VendorManageService vendorManageService;

    /**
     * 分页查询所有服务商信息
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping
    public Result pageVendorInfo(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("查询第{}页供应商信息",page);
        PageBean pageBean = vendorManageService.queryVendorInfo(page,pageSize);
        return Result.success(pageBean);
    }

    /**
     * 根据手机号查询服务商
     * @param companyPhone
     * @return
     */
    @GetMapping("/{companyPhone}")
    public Result pageVendorInfo(@PathVariable String companyPhone) {
        log.info("正在查询联系方式为：{}的供应商信息",companyPhone);
        Vendor vendor = vendorManageService.queryVendorInfo(companyPhone);
        return Result.success(vendor);
    }

    /**
     * 根据键值删除服务商
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result removeVendorInfo(@PathVariable Integer id) {
        log.info("正在删除键值为：{}的服务商信息",id);
        vendorManageService.removeVendor(id);
        return Result.success("删除成功");
    }


    /**
     * 服务商信息更新
     * @param vendor
     * @return
     */
    @PutMapping("/{id}")
    public Result updateVendorInfo(@RequestBody Vendor vendor){
        log.info("正在修改服务商信息：{}", vendor);
        vendorManageService.updateVendorInfo(vendor);
        return Result.success("保存成功");
    }



}
