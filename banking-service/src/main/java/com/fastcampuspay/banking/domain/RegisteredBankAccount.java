package com.fastcampuspay.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredBankAccount {

    @Getter
    private final String registeredBankAccountId;

    @Getter
    private final String membershipId;

    @Getter
    private final String bankName; // enum

    @Getter
    private final String bankAccountNumber;

    @Getter
    private final boolean linkedStatusIsValid;

    @Getter
    private final String aggregateIdentifier;

    // 오염이 되면 안되는 클래스, 고객 정보. 핵심 도메인
    public static RegisteredBankAccount generateRegisteredBankAccount (
        RegisteredBankAccount.RegisteredBankAccountId registeredBankAccountId,
        RegisteredBankAccount.MembershipId membershipId,
        RegisteredBankAccount.BankName bankName,
        RegisteredBankAccount.BankAccountNumber bankAccountNumber,
        RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid,
        RegisteredBankAccount.AggregateIdentifier aggregateIdentifier
    ) {
        return new RegisteredBankAccount(
                    registeredBankAccountId.registeredBankAccountId,
                    membershipId.membershipId,
                    bankName.bankName,
                    bankAccountNumber.bankAccountNumber,
                    linkedStatusIsValid.linkedStatusIsValid,
                    aggregateIdentifier.aggregateIdentifier
                );
    }

    @Value
    public static class RegisteredBankAccountId {

        String registeredBankAccountId;

        public RegisteredBankAccountId(String value) {
            this.registeredBankAccountId = value;
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
    public static class BankName {

        String bankName;

        public BankName(String bankName) {
            this.bankName = bankName;
        }
    }

    @Value
    public static class BankAccountNumber {

        String bankAccountNumber;

        public BankAccountNumber(String bankAccountNumber) {
            this.bankAccountNumber = bankAccountNumber;
        }
    }

    @Value
    public static class LinkedStatusIsValid {

        boolean linkedStatusIsValid;

        public LinkedStatusIsValid(boolean linkedStatusIsValid) {
            this.linkedStatusIsValid = linkedStatusIsValid;
        }
    }

    @Value
    public static class AggregateIdentifier {

        String aggregateIdentifier;

        public AggregateIdentifier(String aggregateIdentifier) {
            this.aggregateIdentifier = aggregateIdentifier;
        }
    }
}
