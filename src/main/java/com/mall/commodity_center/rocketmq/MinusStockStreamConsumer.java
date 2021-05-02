package com.mall.commodity_center.rocketmq;

import com.mall.commodity_center.domain.dto.commodity.AlterTransactionDto;
import com.mall.commodity_center.domain.entity.transaction.TransactionMessageDto;
import com.mall.commodity_center.service.HandleTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MinusStockStreamConsumer {
    private final HandleTransactionService handleTransactionService;

    @StreamListener(MySink.MY_INPUT)
    public void receive(TransactionMessageDto message) {
        try {
            this.handleTransactionService.underCarriageCommodity(message);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出错误了");
        }
    }

    @StreamListener(MySink.ALTER_TRANSACTION)
    @Transactional(rollbackFor = Exception.class)
    public void test(AlterTransactionDto alterTransactionDto){
        log.info("alterTransactionDto:{}",alterTransactionDto);
        this.handleTransactionService.cancelTransaction(alterTransactionDto);
    }
}
