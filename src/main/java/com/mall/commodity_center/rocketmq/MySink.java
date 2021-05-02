package com.mall.commodity_center.rocketmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySink {

    String MY_INPUT = "my-input";

    String ALTER_TRANSACTION = "alter-transaction";

    @Input(MY_INPUT)
    SubscribableChannel myInput();

    @Input(ALTER_TRANSACTION)
    SubscribableChannel alterTransaction();

}
