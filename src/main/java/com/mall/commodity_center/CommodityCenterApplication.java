package com.mall.commodity_center;

import com.mall.commodity_center.domain.enums.EnumToDescription;
import com.mall.commodity_center.rocketmq.MySink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.mall.commodity_center.dao")
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding({MySink.class})
public class CommodityCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommodityCenterApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public EnumToDescription enumToDescription(){
        return new EnumToDescription();
    }
}
