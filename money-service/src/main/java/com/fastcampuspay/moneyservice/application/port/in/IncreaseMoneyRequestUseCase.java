package com.fastcampuspay.moneyservice.application.port.in;

import com.fastcampuspay.moneyservice.domain.MoneyChangingRequest;
import com.fastcampuspay.common.UseCase;

@UseCase
public interface IncreaseMoneyRequestUseCase {

    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
    MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command);

    void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command);
}
