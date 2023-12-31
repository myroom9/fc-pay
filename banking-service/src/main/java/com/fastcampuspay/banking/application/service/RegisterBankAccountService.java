package com.fastcampuspay.banking.application.service;

import com.fastcampuspay.banking.adapter.in.axon.command.CreateRegisteredBankAccountCommand;
import com.fastcampuspay.banking.adapter.out.external.bank.BankAccount;
import com.fastcampuspay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.fastcampuspay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.fastcampuspay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.fastcampuspay.banking.application.port.in.GetRegisteredBankAccountCommand;
import com.fastcampuspay.banking.application.port.in.GetRegisteredBankAccountUseCase;
import com.fastcampuspay.banking.application.port.in.RegisterBankAccountCommand;
import com.fastcampuspay.banking.application.port.in.RegisterBankAccountUseCase;
import com.fastcampuspay.banking.application.port.out.*;
import com.fastcampuspay.banking.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterBankAccountService implements RegisterBankAccountUseCase, GetRegisteredBankAccountUseCase {

    private final GetMembershipPort getMembershipPort;

    private final RegisterBankAccountPort registerBankAccountPort;
    private final RequestBankAccountInfoPort requestBankAccountInfoPort;
    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;
    private final RegisteredBankAccountMapper mapper;

    private final CommandGateway commandGateway;

    @Override
    public RegisteredBankAccount registerMBankAccount(RegisterBankAccountCommand command) {
        // 은행 계좌를 등록해야되는 서비스

        // call membership svc, 정상인지 확인
        // call external bank svc, 정산인지 확인
        MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getMembershipId());
        if (!membershipStatus.isValid()) {
            return null;
        }


        // (멤버 서비스도 확인?) 여기서는 임시 skip

        // 1. 등록된 계좌인지 확인한다
        // 외부은행에 이 계좌가 정상인지?
        // biz logic -> external system
        // port -> adapter -> external system

        BankAccount bankAccountInfo = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));
        boolean accountInfoValid = bankAccountInfo.isValid();

        if (accountInfoValid) {
            RegisteredBankAccountJpaEntity savedAccountInfo = registerBankAccountPort.createRegisteredBankAccount(
                    new RegisteredBankAccount.MembershipId(command.getMembershipId() + ""),
                    new RegisteredBankAccount.BankName(command.getBankName()),
                    new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                    new RegisteredBankAccount.LinkedStatusIsValid(command.isValid()),
                    new RegisteredBankAccount.AggregateIdentifier("")
            );
            return mapper.mapToDomainEntity(savedAccountInfo);
        }

        // 2. 등록가능한 계좌라면, 등록한다. 성공하면, 등록에 성공한 등록 정보를 리턴
        // 2-1. 등록가능하지 않은 계좌라면 에러를 리턴

        return null;
    }

    @Override
    public void registerMBankAccountByEvent(RegisterBankAccountCommand command) {
        commandGateway.send(new CreateRegisteredBankAccountCommand(command.getMembershipId(), command.getBankName(), command.getBankAccountNumber()))
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        System.out.println("throwable = " + throwable);
                        throw new RuntimeException(throwable);
                    }
                    System.out.println("result = " + result);

                    registerBankAccountPort.createRegisteredBankAccount(
                            new RegisteredBankAccount.MembershipId(command.getMembershipId() + ""),
                            new RegisteredBankAccount.BankName(command.getBankName()),
                            new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                            new RegisteredBankAccount.LinkedStatusIsValid(command.isValid()),
                            new RegisteredBankAccount.AggregateIdentifier(result.toString())
                    );
                });
    }

    @Override
    public RegisteredBankAccount getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
        return mapper.mapToDomainEntity(getRegisteredBankAccountPort.getRegisteredBankAccount(command));
    }
}
