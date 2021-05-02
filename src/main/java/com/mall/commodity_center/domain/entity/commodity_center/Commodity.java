package com.mall.commodity_center.domain.entity.commodity_center;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "commodity")
public class Commodity {
    @Id
    @Column(name = "commodity_id")
    @GeneratedValue(generator = "JDBC")
    private String commodityId;

    @Column(name = "brand_id")
    private String brandId;

    @Column(name = "commodity_name")
    private String commodityName;

    @Column(name = "commodity_category")
    private String commodityCategory;

    private Float price;

    @Column(name = "owner_id")
    private String ownerId;

    private Boolean cloud;

    @Column(name = "commodity_status")
    private String commodityStatus;
}