package com.mall.commodity_center.service;

import com.mall.commodity_center.dao.commodity_center.BrandMapper;
import com.mall.commodity_center.domain.dto.user.UserDTO;
import com.mall.commodity_center.domain.dto.user.UserInfoAndBrandDTO;
import com.mall.commodity_center.domain.entity.commodity_center.Brand;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Console;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserInfoAndBrandService {
    private final BrandMapper brandMapper;
    private final RestTemplate restTemplate;

    public UserInfoAndBrandDTO findUserInfoAndBrand(Integer brandId) {
        Brand brand = brandMapper.selectByPrimaryKey(brandId);
        String BrandId = brand.getBrandId();

        UserDTO userDTO = restTemplate.getForObject(
                "http://user-center/users/{id}",
                UserDTO.class, 1
        );
//        System.out.println(userDTO);
//        System.out.println("rnm");
        UserInfoAndBrandDTO userInfoAndBrandDTO = new UserInfoAndBrandDTO();
        BeanUtils.copyProperties(userDTO, userInfoAndBrandDTO);
//        System.out.println("第一次copy"+userInfoAndBrandDTO);

        BeanUtils.copyProperties(brand, userInfoAndBrandDTO);
//        System.out.println("第二次copy"/+userInfoAndBrandDTO);
        return userInfoAndBrandDTO;
    }
}
