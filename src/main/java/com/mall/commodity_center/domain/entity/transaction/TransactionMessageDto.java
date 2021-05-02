package com.mall.commodity_center.domain.entity.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionMessageDto {

    private List<CartCommoditiesDTO> commodities;

    private String status;

    private String buyerId;

    private String transactionId;
}
