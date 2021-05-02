package com.mall.commodity_center.service;

import com.mall.commodity_center.dao.commodity_center.CommodityInfoLogMapper;
import com.mall.commodity_center.dao.commodity_center.CommodityInfoMapper;
import com.mall.commodity_center.dao.commodity_center.CommodityMapper;
import com.mall.commodity_center.domain.dto.commodity.AlterTransactionDto;
import com.mall.commodity_center.domain.entity.commodity_center.Commodity;
import com.mall.commodity_center.domain.entity.commodity_center.CommodityInfo;
import com.mall.commodity_center.domain.entity.commodity_center.CommodityInfoLog;
import com.mall.commodity_center.domain.entity.transaction.CartCommoditiesDTO;
import com.mall.commodity_center.domain.entity.transaction.TransactionMessageDto;
import com.mall.commodity_center.domain.enums.CommodityEnum;
import com.mall.commodity_center.domain.enums.TransactionCommodityMessageStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class HandleTransactionService {
    private final CommodityMapper commodityMapper;
    private final CommodityInfoMapper commodityInfoMapper;
    private final CommodityInfoLogMapper commodityInfoLogMapper;

    @Transactional(rollbackFor = Exception.class)
    public void underCarriageCommodity(TransactionMessageDto messageDTO) {
        String transactionId = messageDTO.getTransactionId();
        log.info("messageDTO in underCarriageCommodity:{}",messageDTO );
        if(!messageDTO.getStatus().equals(TransactionCommodityMessageStatusEnum.HAD_BOUGHT.toString())){
            throw  new IllegalArgumentException("购买失败");
        }
        List<CartCommoditiesDTO> commodities = messageDTO.getCommodities();
        for (CartCommoditiesDTO cartCommodity :
                commodities) {
            try {
                CommodityInfo commodityInfo = this.commodityInfoMapper.selectByPrimaryKey(cartCommodity.getCommodityId());
                if(Objects.nonNull(commodityInfo)){
                    if(cartCommodity.getCount() > commodityInfo.getCommodityRemainder()){
                        throw new IllegalStateException("余额不够");
                    }
                    commodityInfo.setCommodityRemainder(commodityInfo.getCommodityRemainder() - cartCommodity.getCount());
                    if(commodityInfo.getCommodityRemainder() == 0){
                        Commodity commodity = this.commodityMapper.selectByPrimaryKey(cartCommodity.getCommodityId());
                        commodity.setCommodityStatus(CommodityEnum.UNDER_CARRIAGE.name());
                        this.commodityMapper.updateByPrimaryKey(commodity);
                    }
                    this.commodityInfoMapper.updateByPrimaryKey(commodityInfo);
                    this.commodityInfoLogMapper.insertSelective(
                            CommodityInfoLog.builder()
                                    .commodityId(cartCommodity.getCommodityId())
                                    .transactionId(transactionId)
                                    .log("订单ok了")
                                    .logId(UUID.randomUUID().toString())
                                    .buyerId(messageDTO.getBuyerId())
                                    .build()
                    );

                }
            }catch (Exception e){
                log.info("发生了我没想到的错误");
                e.printStackTrace();
            }
        }
    }

    /**
     * 同意取消订单，把商品余额加上
     * @param alterTransactionDto
     */
    public void cancelTransaction(AlterTransactionDto alterTransactionDto) {
        if(alterTransactionDto.getToStatus().equals(TransactionCommodityMessageStatusEnum.ADMIN_AGREE_CONFIRM.name())
            || alterTransactionDto.getToStatus().equals(TransactionCommodityMessageStatusEnum.USER_AGREE_CANCEL.name())){
//            同意退货
            CommodityInfo commodityInfo = this.commodityInfoMapper.selectByPrimaryKey(alterTransactionDto.getCommodityId());
            commodityInfo.setCommodityRemainder(commodityInfo.getCommodityRemainder() + alterTransactionDto.getCount());
            this.commodityInfoMapper.updateByPrimaryKey(commodityInfo);
        }
        this.commodityInfoLogMapper.insertSelective(
                CommodityInfoLog.builder()
                        .logId(UUID.randomUUID().toString())
                        .log(alterTransactionDto.getHandleRole() + "将订单订单->" +alterTransactionDto.getToStatus())
                        .transactionId(alterTransactionDto.getTransactionId())
                        .commodityId(alterTransactionDto.getCommodityId())
                        .transactionId(alterTransactionDto.getTransactionId())
                        .build()
        );
    }
}
