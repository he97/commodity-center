package com.mall.commodity_center.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableDiscoveryClient
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {
    private final DiscoveryClient discoveryClient;

    @GetMapping("/client")
    public List<String> findClient() {
        return this.discoveryClient.getServices();

    }
}
