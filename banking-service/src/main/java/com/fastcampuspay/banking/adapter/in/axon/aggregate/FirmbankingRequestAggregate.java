package com.fastcampuspay.banking.adapter.in.axon.aggregate;

import com.fastcampuspay.banking.adapter.in.axon.command.CreateFirmbankingRequestCommand;
import com.fastcampuspay.banking.adapter.in.axon.command.UpdateFirmbankingRequestCommand;
import com.fastcampuspay.banking.adapter.in.axon.event.FirmbankingRequestCreatedEvent;
import com.fastcampuspay.banking.adapter.in.axon.event.FirmbankingRequestUpdatedEvent;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Data
public class FirmbankingRequestAggregate {

    @AggregateIdentifier
    private String id;

    private String fromBankName;
    private String fromBankAccountNumber;

    private String toBankName;
    private String toBankAccountNumber;

    private int moneyAmount;
    private int firmbankingStatus;

    @CommandHandler
    public FirmbankingRequestAggregate(CreateFirmbankingRequestCommand command) {

        System.out.println("CreateFirmbankingRequestCommand constructor called");

        apply(new FirmbankingRequestCreatedEvent(command.getFromBankName(), command.getFromBankAccountNumber(), command.getToBankName(), command.getToBankAccountNumber(), command.getMoneyAmount()));
    }

    @CommandHandler
    public String handle(UpdateFirmbankingRequestCommand command) {
        System.out.println("UpdateFirmbankingRequestCommand handler called");

        id = command.getAggregateIdentifier();
        apply(new FirmbankingRequestUpdatedEvent(command.getFirmbankingStatus()));

        return id;
    }

    @EventSourcingHandler
    public void on(FirmbankingRequestCreatedEvent event) {
        System.out.println("FirmbankingRequestCreatedEvent handler called");

        id = UUID.randomUUID().toString();
        fromBankName = event.getFromBankName();
        fromBankAccountNumber = event.getFromBankAccountName();
        toBankName = event.getToBankName();
        toBankAccountNumber = event.getToBankAccountName();
    }

    @EventSourcingHandler
    public void on(FirmbankingRequestUpdatedEvent event) {
        System.out.println("FirmbankingRequestUpdatedEvent handler called");

        firmbankingStatus = event.getFirmbankingStatus();
    }

    public FirmbankingRequestAggregate() {
    }
}
