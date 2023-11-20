package com.fastcampus.payment.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPaymentCommand {

    private String membershipId;
    private String requestPrice;
    private String franchiseId;
    private String franchiseFeeRate;
    // private int paymentStatus;
    // private Date approvedAt;

}
