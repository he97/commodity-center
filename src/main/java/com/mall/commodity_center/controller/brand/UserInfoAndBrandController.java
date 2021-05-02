package com.mall.commodity_center.controller.brand;

import com.mall.commodity_center.domain.dto.user.UserInfoAndBrandDTO;
import com.mall.commodity_center.service.UserInfoAndBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/userAndBrand")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInfoAndBrandController {
    private final UserInfoAndBrandService userInfoAndBrandService;
    private final DiscoveryClient discoveryClient;

    @GetMapping("/{id}")
    private UserInfoAndBrandDTO shareInfo(@PathVariable Integer id) {
        return userInfoAndBrandService.findUserInfoAndBrand(id);
    }
}
