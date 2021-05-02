package com.mall.commodity_center.domain.entity.commodity_center;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "brand")
public class Brand {
    @Id
    @Column(name = "brand_id")
    @GeneratedValue(generator = "JDBC")
    private String brandId;

    @Column(name = "brand_name")
    private String brandName;
}