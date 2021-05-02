//package com.mall.commodity_center.rocketmq;
//
//import com.mall.commodity_center.dao.commodity_center.CommodityInfoMapper;
//import com.mall.commodity_center.dao.commodity_center.CommodityMapper;
//import com.mall.commodity_center.domain.dto.massaging.OrderMinusMassageDTO;
//import com.mall.commodity_center.domain.entity.commodity_center.CommodityInfo;
//import com.mall.commodity_center.service.CommodityInfoService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.checkerframework.checker.units.qual.A;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//import org.springframework.stereotype.Service;
//
//import java.util.Objects;
//
//@Service
//@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "minus-stock")
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@Slf4j
//public class CommodityCountMinusListener implements RocketMQListener<OrderMinusMassageDTO> {
//    private final CommodityInfoService commodityInfoService;
//
//    @Override
//    @StreamListener(Sink.INPUT)
//    public void onMessage(OrderMinusMassageDTO message) {
//        this.commodityInfoService.minusStock(message);
//
//    }
//}
