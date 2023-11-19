package com.fastcampuspay.banking.adapter.in.axon.aggregate;

import com.fastcampuspay.banking.adapter.in.axon.command.CreateRegisteredBankAccountCommand;
import com.fastcampuspay.banking.adapter.in.axon.event.CreateRegisteredBankAccountEvent;
import com.fastcampuspay.banking.adapter.out.external.bank.BankAccount;
import com.fastcampuspay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.fastcampuspay.banking.application.port.out.RequestBankAccountInfoPort;
import com.fastcampuspay.common.event.CheckRegisteredBankAccountCommand;
import com.fastcampuspay.common.event.CheckedRegisteredBankAccountEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class RegisteredBankAccountAggregate {

    @AggregateIdentifier
    private String id;

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;

    @CommandHandler
    public RegisteredBankAccountAggregate(CreateRegisteredBankAccountCommand command) {
        System.out.println("CreateRegisteredBankAccountCommand handled");
        apply(new CreateRegisteredBankAccountEvent(command.getMembershipId(), command.getBankName(), command.getBankAccountNumber()));
    }

    @CommandHandler
    public void handle(@NotNull CheckRegisteredBankAccountCommand command, RequestBankAccountInfoPort bankAccountInfoPort) {
        System.out.println("CheckRegisteredBankAccountCommand handled");

        // command를 통해, 이 어그리거트(registeredBankAccount)가 정상인지를 확인해야한다
        id = command.getAggregateIdentifier();

        // registeredBankAccount가 정상인지 확인한다.
        BankAccount account = bankAccountInfoPort.getBankAccountInfo(
                new GetBankAccountRequest(
                        command.getBankName(),
                        command.getBankAccountNumber()
                ));

        boolean isValid = account.isValid();
        String firmbankingUUID = UUID.randomUUID().toString();

        // CheckedRegisteredBankAccountEvent
        CheckedRegisteredBankAccountEvent checkedRegisteredBankAccountEvent = new CheckedRegisteredBankAccountEvent(
                command.getRechargeRequestId(),
                command.getCheckRegisteredBankAccountId(),
                command.getMembershipId(),
                isValid,
                command.getAmount(),
                firmbankingUUID,
                account.getBankName(),
                account.getBankAccountNumber()
        );
        System.out.println("CheckRegisteredBankAccountCommand handler checkedRegisteredBankAccountEvent = " + checkedRegisteredBankAccountEvent);
        apply(checkedRegisteredBankAccountEvent);
    }

    @EventSourcingHandler
    public void on(CreateRegisteredBankAccountEvent event) {
        System.out.println("CreateRegisteredBankAccountEvent handled");
        this.id = UUID.randomUUID().toString();
        this.membershipId = event.getMembershipId();
        this.bankName = event.getBankName();
        this.bankAccountNumber = event.getBankAccountNumber();
    }

    public RegisteredBankAccountAggregate() {
    }
}
