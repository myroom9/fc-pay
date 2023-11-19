package com.fastcampuspay.moneyservice.adapter.axon.agreegate;

import com.fastcampuspay.moneyservice.adapter.axon.command.IncreaseMemberMoneyCommand;
import com.fastcampuspay.moneyservice.adapter.axon.command.MemberMoneyCreatedCommand;
import com.fastcampuspay.moneyservice.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import com.fastcampuspay.moneyservice.adapter.axon.event.IncreaseMemberMoneyEvent;
import com.fastcampuspay.moneyservice.adapter.axon.event.MemberMoneyCreatedEvent;
import com.fastcampuspay.moneyservice.adapter.axon.event.RechargingRequestCreatedEvent;
import com.fastcampuspay.moneyservice.application.port.out.GetRegisteredBankAccountPort;
import com.fastcampuspay.moneyservice.application.port.out.RegisteredBankAccountAggregateIdentifier;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Data
public class MemberMoneyAggregate {

    @AggregateIdentifier
    private String id;

    private Long membershipId;

    private int balance;

    @CommandHandler
    public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
        System.out.println("MemberMoneyCreatedCOmmand Handler");

        apply(new MemberMoneyCreatedEvent(command.getMembershipId()));
    }

    @CommandHandler
    public String handle(@NotNull IncreaseMemberMoneyCommand command) {
        System.out.println("IncreaseMemberMoneyCommand Handler");
        id = command.getAggregateIdentifier();

        apply(new IncreaseMemberMoneyEvent(id, command.getMembershipId(), command.getAmount()));
        return id;
    }

    @CommandHandler
    public void handler(RechargingMoneyRequestCreateCommand command, GetRegisteredBankAccountPort getRegisteredBankAccountPort) {
        System.out.println("RechargingMoneyRequestCreateCommand Handler");

        id = command.getAggregateIdentifier();

        // saga start
        // new RechargingRequestCreatedEvent
        // banking 정보가 필요함
        RegisteredBankAccountAggregateIdentifier registeredBankAccountIdentifier =
                getRegisteredBankAccountPort.getRegisteredBankAccount(command.getMembershipId());

        // saga start
        apply(new RechargingRequestCreatedEvent(
                command.getRechargingRequestId(),
                command.getMembershipId(),
                command.getAmount(),
                registeredBankAccountIdentifier.getAggregateIdentifier(),
                registeredBankAccountIdentifier.getBankName(),
                registeredBankAccountIdentifier.getBankAccountNumber()
        ));
    }

    @EventSourcingHandler
    public void on(MemberMoneyCreatedEvent event) {
        System.out.println("MemberMoneyCreatedEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = Long.parseLong(event.getMembershipId());
        balance = 0;
    }

    @EventSourcingHandler
    public void on(IncreaseMemberMoneyEvent event) {
        System.out.println("IncreaseMemberMoneyEvent Sourcing Handler");
        id = event.getAggregateIdentifier();
        membershipId = Long.parseLong(event.getTargetMembershipId());
        balance = event.getAmount();
    }

    public MemberMoneyAggregate() {
    }
}
