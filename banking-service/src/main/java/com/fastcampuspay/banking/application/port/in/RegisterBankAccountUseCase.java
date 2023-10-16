package com.fastcampuspay.banking.application.port.in;

import com.fastcampuspay.banking.domain.RegisteredBankAccount;
import common.UseCase;

@UseCase
public interface RegisterBankAccountUseCase {

    RegisteredBankAccount registerMBankAccount(RegisterBankAccountCommand command);

}
