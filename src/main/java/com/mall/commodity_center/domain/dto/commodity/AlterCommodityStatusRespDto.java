package com.mall.commodity_center.domain.dto.commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor@Builder

public class AlterCommodityStatusRespDto {

    private String message;

    private String status;
}

