package com.mall.commodity_center.domain.dto.commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlterTransactionDto {

    private String commodityId;

    private Integer count;

    private String transactionId;

    private String toStatus;

    private String handleRole;
}
