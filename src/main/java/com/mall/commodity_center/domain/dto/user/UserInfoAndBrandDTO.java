package com.mall.commodity_center.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoAndBrandDTO {
    private Integer brandId;

    private String brandName;

    private Integer userId;

    private Byte userValid;

    private String userPassword;

}
