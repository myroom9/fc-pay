package com.fastcampus.payment.application.port.in;

import com.fastcampus.payment.domain.Payment;

public interface RequestPaymentUseCase {

    Payment requestPayment(RequestPaymentCommand command);

}
