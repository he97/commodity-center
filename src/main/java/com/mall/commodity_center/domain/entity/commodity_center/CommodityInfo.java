package com.mall.commodity_center.domain.entity.commodity_center;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "commodity_info")
public class CommodityInfo {
    @Id
    @Column(name = "commodity_id")
    @GeneratedValue(generator = "JDBC")
    private String commodityId;

    @Column(name = "commodity_information")
    private String commodityInformation;

    @Column(name = "commodity_remainder")
    private Integer commodityRemainder;

    @Column(name = "commodity_picture_url")
    private String commodityPictureUrl;
}