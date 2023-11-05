package com.fastcampuspay.banking.application.port.in;

import com.fastcampuspay.banking.domain.FirmbankingRequest;
import com.fastcampuspay.common.UseCase;

@UseCase
public interface RequestFirmbankingUseCase {

    FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command);

    void requestFirmbankingByEvent(RequestFirmbankingCommand command);
}