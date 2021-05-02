package com.mall.commodity_center.domain.entity.commodity_center;

import java.util.Date;
import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "commodity_info_log")
public class CommodityInfoLog {
    @Id
    @Column(name = "log_id")
    @GeneratedValue(generator = "JDBC")
    private String logId;

    @Column(name = "commodity_id")
    private String commodityId;

    private String log;

    @Column(name = "transaction_id")
    private String transactionId;

    private Date time;

    @Column(name = "buyer_id")
    private String buyerId;
}