package com.fastcampus.payment.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {

    @Getter
    private Long paymentId;

    @Getter
    private String requestMembershipId;

    @Getter
    private int requestPrice;

    @Getter
    private String franchiseId;

    @Getter
    private String franchiseFeeRate;

    @Getter
    private int paymentStatus;

    @Getter
    private Date approvedAt;

    public static Payment generatePayment (
            PaymentId paymentId,
            RequestMembershipId requestMembershipId,
            RequestPrice requestPrice,
            FranchiseId franchiseId,
            FranchiseFeeRate franchiseFeeRate,
            PaymentStatus paymentStatus,
            ApprovedAt approvedAt
    ) {
        return new Payment(
                paymentId.getPaymentId(),
                requestMembershipId.getRequestMembershipId(),
                requestPrice.getRequestPrice(),
                franchiseId.getFranchiseId(),
                franchiseFeeRate.getFranchiseFeeRate(),
                paymentStatus.getPaymentStatus(),
                approvedAt.getApprovedAt()
        );
    }

    @Value
    public static class PaymentId {
        Long paymentId;

        public PaymentId(Long paymentId) {
            this.paymentId = paymentId;
        }
    }

    @Value
    public static class RequestMembershipId {
        String requestMembershipId;

        public RequestMembershipId(String requestMembershipId) {
            this.requestMembershipId = requestMembershipId;
        }
    }

    @Value
    public static class RequestPrice {
        int requestPrice;

        public RequestPrice(int requestPrice) {
            this.requestPrice = requestPrice;
        }
    }

    @Value
    public static class FranchiseId {
        String franchiseId;

        public FranchiseId(String franchiseId) {
            this.franchiseId = franchiseId;
        }
    }

    @Value
    public static class FranchiseFeeRate {
        String franchiseFeeRate;

        public FranchiseFeeRate(String franchiseFeeRate) {
            this.franchiseFeeRate = franchiseFeeRate;
        }
    }

    @Value
    public static class PaymentStatus {
        int paymentStatus;

        public PaymentStatus(int paymentStatus) {
            this.paymentStatus = paymentStatus;
        }
    }

    @Value
    public static class ApprovedAt {
        Date approvedAt;

        public ApprovedAt(Date approvedAt) {
            this.approvedAt = approvedAt;
        }
    }

}
