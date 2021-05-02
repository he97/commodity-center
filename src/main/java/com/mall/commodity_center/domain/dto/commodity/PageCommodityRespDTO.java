package com.mall.commodity_center.domain.dto.commodity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PageCommodityRespDTO {
    private String commodityId;

    private String brandId;

    private String commodityName;

    private String commodityCategory;

    private String commodityInformation;

    private Integer commodityRemainder;

    private String commodityFirstPictureUrl;

    private Boolean cloud;

    private Float price;
}
