package com.mall.commodity_center.domain.dto.commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class UploadCommodityDto {

    private String brandName;

    private String commodityName;
    /*
    仓库id
     */
    private String commodityCategory;

    private String commodityInformation;

    private Integer commodityRemainder;

    private List<String> commodityPictureUrl;

    private Float price;
}
