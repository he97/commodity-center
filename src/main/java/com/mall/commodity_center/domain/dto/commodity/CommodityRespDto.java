package com.mall.commodity_center.domain.dto.commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommodityRespDto {

    private String commodityId;

    private String brandId;

    private String brandName;

    private String commodityName;

    private String categoryName;

    private String commodityCategory;

    private String commodityInformation;

    private Integer commodityRemainder;

    private List<String> commodityPictureUrl;

    private String ownerId;

    private Float price;

    private Boolean cloud;

    private String commodityStatus;
}
