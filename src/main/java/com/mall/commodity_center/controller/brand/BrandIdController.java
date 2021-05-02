package com.mall.commodity_center.controller.brand;

import com.mall.commodity_center.domain.entity.commodity_center.Brand;
import com.mall.commodity_center.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brandId")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BrandIdController {
    private final BrandService brandService;

//    @CheckLogin
    @GetMapping("/{brandId}")
    public Brand findById(@PathVariable String brandId) {
        log.info("被请求了");
//        return brandService.findByBrabdId(brandId);
        brandService.methodUpdatePicture();
        return null;
    }

    @GetMapping("/objectQuery")
    public Brand findByObject(Brand brand) {
        log.info("参数brand:{}", brand);
        return brandService.findByBrandId(brand.getBrandId());
    }
    @GetMapping("/withName/{name}")
    public Brand getBrandIdByName(@PathVariable String name){
        return this.brandService.getBrandIdByName(name);
    }

}
