package com.fastcampus.payment.adapter.out.persistence;

import com.fastcampus.payment.application.port.out.CreatePaymentPort;
import com.fastcampus.payment.domain.Payment;
import com.fastcampuspay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements CreatePaymentPort {

    private final SpringDataPaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public Payment createPayment(String membershipId, String requestPrice, String franchiseId, String franchiseFeeRate) {
        PaymentJpaEntity jpaEntity = paymentRepository.save(
                new PaymentJpaEntity(
                        membershipId,
                        Integer.parseInt(requestPrice),
                        franchiseId,
                        franchiseFeeRate,
                        0, // 0: 승인, 1: 실패, 2: 정산 완료
                        null
                )
        );

        return paymentMapper.mapToDomainEntity(jpaEntity);
    }
}
