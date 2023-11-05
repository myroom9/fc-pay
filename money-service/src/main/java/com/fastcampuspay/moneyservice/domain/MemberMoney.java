package com.fastcampuspay.moneyservice.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {

    @Getter
    private final String memberMoneyId;

    @Getter
    private final String membershipId;

    @Getter
    private final int moneyBalance;

    public static MemberMoney generateMemberMoney(
            MemberMoneyId memberMoneyId,
            MembershipId membershipId,
            MoneyBalance moneyBalance
    ) {
        return new MemberMoney(
                memberMoneyId.getMemberMoneyId(),
                membershipId.getMembershipId(),
                moneyBalance.getMoneyBalance()
        );
    }


    @Value
    public static class MemberMoneyId {
        String memberMoneyId;

        public MemberMoneyId(String memberMoneyId) {
            this.memberMoneyId = memberMoneyId;
        }
    }

    @Value
    public static class MembershipId {
        String membershipId;

        public MembershipId(String membershipId) {
            this.membershipId = membershipId;
        }
    }

    @Value
    public static class MoneyBalance {
        int moneyBalance;

        public MoneyBalance(int moneyBalance) {
            this.moneyBalance = moneyBalance;
        }
    }

    @Value
    public static class MoneyAggregateIdentifier {
        String aggregateIdentifier;

        public MoneyAggregateIdentifier(String aggregateIdentifier) {
            this.aggregateIdentifier = aggregateIdentifier;
        }
    }
}

