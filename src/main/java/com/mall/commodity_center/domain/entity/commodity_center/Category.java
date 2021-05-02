package com.mall.commodity_center.domain.entity.commodity_center;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "category")
public class Category {
    @Id
    @Column(name = "categoey_id")
    private String categoeyId;

    @Column(name = "category_name")
    private String categoryName;
}