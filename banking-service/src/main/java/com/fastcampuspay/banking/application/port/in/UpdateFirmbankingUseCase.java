package com.fastcampuspay.banking.application.port.in;

import com.fastcampuspay.common.UseCase;

@UseCase
public interface UpdateFirmbankingUseCase {
    void updateFirmbankingByEvent(UpdateFirmbankingCommand command);
}