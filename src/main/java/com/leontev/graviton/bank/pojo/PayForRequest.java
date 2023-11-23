package com.leontev.graviton.courses.pojo;

import lombok.Data;

@Data
public class PayForRequest {
    private long invoiceCenterId;
    private String payerTelegramId;
}
