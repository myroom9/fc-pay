package com.fastcampuspay.moneyservice.adapter.axon.saga;

import com.fastcampuspay.common.event.*;
import com.fastcampuspay.moneyservice.adapter.axon.event.RechargingRequestCreatedEvent;
import com.fastcampuspay.moneyservice.adapter.out.persistence.MemberMoneyJpaEntity;
import com.fastcampuspay.moneyservice.application.port.out.IncreaseMoneyPort;
import com.fastcampuspay.moneyservice.domain.MemberMoney;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@NoArgsConstructor
public class MoneyRechargeSaga {

    @NonNull
    private transient CommandGateway commandGateway;

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "rechargingRequestId")
    public void handle(RechargingRequestCreatedEvent event) {
        System.out.println("RechargingRequestCreatedEvent Start saga");

        String checkRegisteredBankAccountId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("checkRegisteredBankAccountId", checkRegisteredBankAccountId);


        // "충전 요청"이 시작 되었다.

        // 뱅킹의 계좌 등록 여부 확인하기. (RegisteredBankAccount)
        // CheckRegisteredBankAccountCommand -> Check Bank Account
        // axon server -> banking service함


        commandGateway.send(new CheckRegisteredBankAccountCommand(
                        event.getRegisteredBankAccountAggregateIdentifier(),
                        event.getRechargingRequestId(),
                        event.getMembershipId(),
                        checkRegisteredBankAccountId,
                        event.getBankName(),
                        event.getBankAccountNumber(),
                        event.getAmount()))
                .whenComplete(
                        (result, throwable) -> {
                            if (throwable != null) {
                                throwable.printStackTrace();
                                System.out.println("CheckRegisteredBankAccountCommand Command failed");
                            } else {
                                System.out.println("CheckRegisteredBankAccountCommand Command succeeded");
                            }
                        }
                );
    }

    @SagaEventHandler(associationProperty = "checkRegisteredBankAccountId")
    public void handle(CheckedRegisteredBankAccountEvent event) {
        System.out.println("CheckedRegisteredBankAccountEvent saga: " + event.toString());
        boolean status = event.isChecked();
        if (status) {
            System.out.println("CheckedRegisteredBankAccountEvent event success");
        } else {
            System.out.println("CheckedRegisteredBankAccountEvent event Failed");
        }

        String requestFirmbankingId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("requestFirmbankingId", requestFirmbankingId);

        // 2. 송금 요청
        commandGateway.send(new RequestFirmbankingCommand(
                requestFirmbankingId,
                event.getFirmbankingRequestAggregateIdentifier()
                , event.getRechargingRequestId()
                , event.getMembershipId()
                , "fastcampus"
                , "123456789"
                , event.getFromBankName()
                , event.getFromBankAccountNumber()
                , event.getAmount()
        )).whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                        System.out.println("RequestFirmbankingCommand Command failed");
                    } else {
                        System.out.println("RequestFirmbankingCommand Command success");
                    }
                }
        );
    }

    @SagaEventHandler(associationProperty = "requestFirmbankingId")
    public void handle(RequestFirmbankingFinishedEvent event, IncreaseMoneyPort increaseMoneyPort) {
        System.out.println("RequestFirmbankingFinishedEvent saga: " + event.toString());
        boolean status = event.getStatus() == 0;
        if (status) {
            System.out.println("RequestFirmbankingFinishedEvent event success");
        } else {
            System.out.println("RequestFirmbankingFinishedEvent event Failed");
        }

        // DB Update 명령.
        MemberMoneyJpaEntity resultEntity =
                increaseMoneyPort.increaseMoney(
                        new MemberMoney.MembershipId(event.getMembershipId())
                        , event.getMoneyAmount()
                );

        if (resultEntity == null) {
            // 실패 시, 롤백 이벤트
            String rollbackFirmbankingId = UUID.randomUUID().toString();
            SagaLifecycle.associateWith("rollbackFirmbankingId", rollbackFirmbankingId);
            commandGateway.send(new RollbackFirmbankingRequestCommand(
                    rollbackFirmbankingId
                    ,event.getRequestFirmbankingAggregateIdentifier()
                    , event.getRechargingRequestId()
                    , event.getMembershipId()
                    , event.getToBankName()
                    , event.getToBankAccountNumber()
                    , event.getMoneyAmount()
            )).whenComplete(
                    (result, throwable) -> {
                        if (throwable != null) {
                            throwable.printStackTrace();
                            System.out.println("RollbackFirmbankingRequestCommand Command failed");
                        } else {
                            System.out.println("Saga success : "+ result.toString());
                            SagaLifecycle.end();
                        }
                    }
            );
        } else {
            // 성공 시, saga 종료.
            SagaLifecycle.end();
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "rollbackFirmbankingId")
    public void handle(RollbackFirmbankingFinishedEvent event) {
        System.out.println("RollbackFirmbankingFinishedEvent saga: " + event.toString());
    }

}
