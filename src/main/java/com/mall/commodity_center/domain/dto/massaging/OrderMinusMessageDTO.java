package com.mall.commodity_center.domain.dto.massaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMinusMessageDTO {
    private Integer orderId;

    private Integer userId;

    private Integer commodityId;
}
