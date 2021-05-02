package com.mall.commodity_center.domain.dto.commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author he97
 */
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class UploadCommodityRespDto {

    private String status;

    private String message;
}
