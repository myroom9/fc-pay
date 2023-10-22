package com.fastcampuspay.moneyservice.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyChangingRequest {

    @Getter
    private final String moneyChangingRequestId;

    @Getter
    private final String targetMembershipId;

    @Getter
    private final int changingType; // 0 : 증액, 1 : 감액

    @Getter
    private final int changingMoneyAmount;

    @Getter
    private final int changingMoneyStatus;

    @Getter
    private final UUID uuid;

    @Getter
    private final Date createdAt;

    public static MoneyChangingRequest generateMoneyChangingRequest(
            MoneyChangingRequestId moneyChangingRequestId,
            TargetMembershipId targetMembershipId,
            MoneyChangingType moneyChangingType,
            ChangingMoneyAmount changingMoneyAmount,
            MoneyChangingStatus moneyChangingStatus,

            Uuid uuid
    ) {
        return new MoneyChangingRequest(
                moneyChangingRequestId.getMoneyChangingRequestId(),
                targetMembershipId.getTargetMembershipId(),
                moneyChangingType.getMoneyChangingType(),
                changingMoneyAmount.getChangingMoneyAmount(),
                moneyChangingStatus.getChangingMoneyStatus(),
                uuid.getUuid(),
                new Date()
        );
    }

    @Value
    public static class MoneyChangingRequestId {
        String moneyChangingRequestId;

        public MoneyChangingRequestId(String moneyChangingRequestId) {
            this.moneyChangingRequestId = moneyChangingRequestId;
        }
    }

    @Value
    public static class TargetMembershipId {
        String targetMembershipId;

        public TargetMembershipId(String targetMembershipId) {
            this.targetMembershipId = targetMembershipId;
        }
    }

    @Value
    public static class MoneyChangingType {
        int moneyChangingType;

        public MoneyChangingType(int moneyChangingType) {
            this.moneyChangingType = moneyChangingType;
        }
    }

    @Value
    public static class ChangingMoneyAmount {
        int changingMoneyAmount;

        public ChangingMoneyAmount(int changingMoneyAmount) {
            this.changingMoneyAmount = changingMoneyAmount;
        }
    }

    @Value
    public static class MoneyChangingStatus {
        int changingMoneyStatus;

        public MoneyChangingStatus(int changingMoneyStatus) {
            this.changingMoneyStatus = changingMoneyStatus;
        }
    }

    @Value
    public static class Uuid {
        UUID uuid;

        public Uuid(UUID uuid) {
            this.uuid = uuid;
        }
    }

}
