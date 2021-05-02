package com.mall.commodity_center.domain.dto.commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class CommodityInfoLogDto {
    private String logId;

    private String commodityId;

    private String log;

    private String transactionId;

    private Date time;

    private String buyerId;
}
