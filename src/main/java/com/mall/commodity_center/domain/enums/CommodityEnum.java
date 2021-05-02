package com.mall.commodity_center.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommodityEnum {
    /*
    正在售卖
     */
    ON_SALE,
    /*
    没被批准
     */
    REJECTED,
    /*
    已经被购买了
     */
    HAD_BOUGHT,
    /**
     * 下架了
     */
    UNDER_CARRIAGE,
    /**
     * 等待审核
     */
    WAIT_CHECK,
    /**
     * 等待用户评审
     */
    WAIT_CANCEL_CHECK,
}
