package com.mall.commodity_center.service;

import com.mall.commodity_center.dao.commodity_center.CommodityInfoMapper;
import com.mall.commodity_center.domain.dto.massaging.OrderMinusMessageDTO;
import com.mall.commodity_center.domain.entity.commodity_center.CommodityInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CommodityInfoService {
    private final CommodityInfoMapper commodityInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    public void minusStock(OrderMinusMessageDTO massage) {
        log.info("in CommodityInfoService.minusStock");
        log.info("消息内容：{}", massage);
        //信息的异常处理
        if (!(massage.getCommodityId() != null && massage.getUserId() != null && massage.getOrderId() != null)) {
            throw new IllegalArgumentException("返回格式不正确");
        }
//
        Integer commodityId = massage.getCommodityId();
        Integer userId = massage.getUserId();
        CommodityInfo commodityInfo = this.commodityInfoMapper.selectByPrimaryKey(commodityId);
        if (Objects.equals(commodityInfo, null)) {
            throw new IllegalArgumentException("查无该商品");
        }
        commodityInfo.setCommodityRemainder(commodityInfo.getCommodityRemainder() - 1);
        log.info("更新后的商品:{}", commodityInfo);
        this.commodityInfoMapper.updateByPrimaryKeySelective(commodityInfo);
    }
}
