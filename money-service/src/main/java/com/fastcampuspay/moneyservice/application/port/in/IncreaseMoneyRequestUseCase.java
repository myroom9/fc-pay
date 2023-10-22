package com.fastcampuspay.moneyservice.application.port.in;

import com.fastcampuspay.moneyservice.domain.MoneyChangingRequest;
import common.UseCase;

@UseCase
public interface IncreaseMoneyRequestUseCase {

    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);

}
