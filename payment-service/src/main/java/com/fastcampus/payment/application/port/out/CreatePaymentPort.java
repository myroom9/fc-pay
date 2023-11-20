package com.fastcampus.payment.application.port.out;

import com.fastcampus.payment.domain.Payment;

public interface CreatePaymentPort {
    Payment createPayment(String membershipId, String requestPrice, String franchiseId, String franchiseFeeRate);
}
