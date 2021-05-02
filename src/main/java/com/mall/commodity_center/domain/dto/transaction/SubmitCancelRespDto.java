package com.mall.commodity_center.domain.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SubmitCancelRespDto {

    private String status;

    private String message;
}
