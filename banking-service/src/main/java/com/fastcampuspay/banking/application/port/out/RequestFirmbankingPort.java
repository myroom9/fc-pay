package com.fastcampuspay.banking.application.port.out;

import com.fastcampuspay.banking.adapter.out.external.bank.BankAccount;
import com.fastcampuspay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.fastcampuspay.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import com.fastcampuspay.banking.domain.FirmbankingRequest;

public interface RequestFirmbankingPort {
    FirmbankingRequestJpaEntity createFirmbankingRequest(
            FirmbankingRequest.ToBankName toBankName,
            FirmbankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmbankingRequest.FromBankName fromBankName,
            FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmbankingRequest.MoneyAmount moneyAmount,
            FirmbankingRequest.FirmbankingStatus firmbankingStatus,
            FirmbankingRequest.FirmbankingAggregateIdentifier firmbankingAggregateIdentifier
    );

    FirmbankingRequestJpaEntity modifyFirmbankingRequest(
            FirmbankingRequestJpaEntity entity
    );

    FirmbankingRequestJpaEntity getFirmbankingRequest(
            FirmbankingRequest.FirmbankingAggregateIdentifier firmbankingAggregateIdentifier
    );
}
