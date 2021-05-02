//package com.mall.commodity_center.rocketmq;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class TestStreamConsumer {
//
//    @StreamListener(Sink.INPUT)
//    public void receive(String massageBody){
//        log.info("消息时：{}",massageBody);
//    }
//}
