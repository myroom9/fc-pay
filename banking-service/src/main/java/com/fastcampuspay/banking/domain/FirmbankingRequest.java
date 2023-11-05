package com.fastcampuspay.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FirmbankingRequest {


    @Getter
    private final String firmbankingRequestId;

    @Getter
    private final String fromBankName;

    @Getter
    private final String fromBankAccountNumber;

    @Getter
    private final String toBankName;

    @Getter
    private final String toBankAccountNumber;

    @Getter
    private final int moneyAmount;

    @Getter
    private final int firmbankingStatus;

    @Getter
    private final UUID uuid;

    @Getter
    private final String aggregateIdentifier;

    public  static FirmbankingRequest generateFirmbankingRequest (
            FirmbankingRequest.FirmbankingRequestId firmbankingRequestId,
            FromBankName frombankName,
            FromBankAccountNumber frombankAccountNumber,
            FirmbankingRequest.ToBankName toBankName,
            FirmbankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmbankingRequest.MoneyAmount moneyAmount,
            FirmbankingRequest.FirmbankingStatus firmbankingStatus,
            UUID uuid,
            FirmbankingAggregateIdentifier firmbankingAggregateIdentifier
    ) {
        return new FirmbankingRequest(
                firmbankingRequestId.getFirmbankingRequestId(),
                frombankName.getFromBankName(),
                frombankAccountNumber.getFromBankAccountNumber(),
                toBankName.getToBankName(),
                toBankAccountNumber.getToBankAccountNumber(),
                moneyAmount.getMoneyAmount(),
                firmbankingStatus.getFirmbankingStatus(),
                uuid,
                firmbankingAggregateIdentifier.getAggregateIdentifier()
        );
    }

    @Value
    public static class FirmbankingRequestId {
        String firmbankingRequestId;

        public FirmbankingRequestId(String firmbankingRequestId) {
            this.firmbankingRequestId = firmbankingRequestId;
        }
    }

    @Value
    public static class FromBankName {
        String fromBankName;

        public FromBankName(String fromBankName) {
            this.fromBankName = fromBankName;
        }
    }

    @Value
    public static class FromBankAccountNumber {
        String fromBankAccountNumber;

        public FromBankAccountNumber(String fromBankAccountNumber) {
            this.fromBankAccountNumber = fromBankAccountNumber;
        }
    }

    @Value
    public static class ToBankName {
        String toBankName;

        public ToBankName(String toBankName) {
            this.toBankName = toBankName;
        }
    }

    @Value
    public static class ToBankAccountNumber {
        String toBankAccountNumber;

        public ToBankAccountNumber(String toBankAccountNumber) {
            this.toBankAccountNumber = toBankAccountNumber;
        }
    }

    @Value
    public static class MoneyAmount {
        int moneyAmount;

        public MoneyAmount(int moneyAmount) {
            this.moneyAmount = moneyAmount;
        }
    }

    @Value
    public static class FirmbankingStatus {
        int firmbankingStatus;

        public FirmbankingStatus(int firmbankingStatus) {
            this.firmbankingStatus = firmbankingStatus;
        }
    }

    @Value
    public static class FirmbankingAggregateIdentifier {
        String aggregateIdentifier;

        public FirmbankingAggregateIdentifier(String value) {
            this.aggregateIdentifier = value;
        }
    }
}
