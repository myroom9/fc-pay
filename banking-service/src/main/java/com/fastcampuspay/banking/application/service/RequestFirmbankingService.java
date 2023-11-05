package com.fastcampuspay.banking.application.service;

import com.fastcampuspay.banking.adapter.in.axon.command.CreateFirmbankingRequestCommand;
import com.fastcampuspay.banking.adapter.in.axon.command.UpdateFirmbankingRequestCommand;
import com.fastcampuspay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.fastcampuspay.banking.adapter.out.external.bank.FirmbankingResult;
import com.fastcampuspay.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import com.fastcampuspay.banking.adapter.out.persistence.FirmbankingRequestMapper;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingUseCase;
import com.fastcampuspay.banking.application.port.in.UpdateFirmbankingCommand;
import com.fastcampuspay.banking.application.port.in.UpdateFirmbankingUseCase;
import com.fastcampuspay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.fastcampuspay.banking.application.port.out.RequestFirmbankingPort;
import com.fastcampuspay.banking.domain.FirmbankingRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase, UpdateFirmbankingUseCase {

    private final RequestFirmbankingPort requestFirmbankingPort;
    private final FirmbankingRequestMapper mapper;
    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;

    private final CommandGateway commandGateway;

    @Override
    public FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command) {
        // a -> b 계좌
        // 1. a, b 계좌

        // 1. 요청에 대해 정보를 먼저 write . "요청" 상태로
        FirmbankingRequestJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.ToBankName(command.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.FromBankName(command.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(0),
                null
        );

        // 2. 외부 은행에 펌뱅킹 요청
        FirmbankingResult result = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber()
        ));

        UUID uuid = UUID.randomUUID();
        requestedEntity.setUuid(uuid);
        // 3. 결과에 따라 1번에서 작성했던 firmbankingRequets 정보를 update
        if (result.getResultCode() == 0) {
            requestedEntity.setFirmbankingStatus(0);
        } else {
            requestedEntity.setFirmbankingStatus(1);
        }

        // 4. 결과 return
        return mapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity), uuid);
    }

    @Override
    public void requestFirmbankingByEvent(RequestFirmbankingCommand request) {
        // command -> event sourcing
        CreateFirmbankingRequestCommand command = CreateFirmbankingRequestCommand.builder().
                fromBankName(request.getFromBankName()).
                fromBankAccountNumber(request.getFromBankAccountNumber()).
                toBankName(request.getToBankName()).
                toBankAccountNumber(request.getToBankAccountNumber()).
                moneyAmount(request.getMoneyAmount()).
                build();

        commandGateway.send(command)
                .whenComplete((s, throwable) -> {
                    if (throwable != null) {
                        System.out.println("throwable = " + throwable);
                        throw new RuntimeException(throwable);
                    }

                    // 1. 요청에 대해 정보를 먼저 write . "요청" 상태로
                    FirmbankingRequestJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                            new FirmbankingRequest.ToBankName(command.getToBankName()),
                            new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                            new FirmbankingRequest.FromBankName(command.getFromBankName()),
                            new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                            new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                            new FirmbankingRequest.FirmbankingStatus(0),
                            new FirmbankingRequest.FirmbankingAggregateIdentifier(s.toString())
                    );

                    // 2. 외부 은행에 펌뱅킹 요청
                    FirmbankingResult firmbankingResult = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                            command.getFromBankName(),
                            command.getFromBankAccountNumber(),
                            command.getToBankName(),
                            command.getToBankAccountNumber()
                    ));

                    UUID uuid = UUID.randomUUID();
                    requestedEntity.setUuid(uuid);
                    // 3. 결과에 따라 1번에서 작성했던 firmbankingRequets 정보를 update
                    if (firmbankingResult.getResultCode() == 0) {
                        requestedEntity.setFirmbankingStatus(0);
                    } else {
                        requestedEntity.setFirmbankingStatus(1);
                    }

                    // 4. 결과 return
                    requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity);

                    System.out.println("result = " + s);
                });
    }

    @Override
    public void updateFirmbankingByEvent(UpdateFirmbankingCommand command) {

        UpdateFirmbankingRequestCommand updateFirmbankingRequestCommand =
                new UpdateFirmbankingRequestCommand(command.getFirmbankingAggregateIdentifier(), command.getFirmbankingStatus());

        commandGateway.send(updateFirmbankingRequestCommand)
                .whenComplete(
                        (s, throwable) -> {
                            if (throwable != null) {
                                System.out.println("throwable = " + throwable);
                                throw new RuntimeException(throwable);
                            }
                            System.out.println("result = " + s);

                            FirmbankingRequestJpaEntity entity = requestFirmbankingPort.getFirmbankingRequest(
                                    new FirmbankingRequest.FirmbankingAggregateIdentifier(command.getFirmbankingAggregateIdentifier()));

                            // status의 변경으로 인한 외부 은행과의 커뮤니케이션 가정하고 진행

                            entity.setFirmbankingStatus(command.getFirmbankingStatus());

                            requestFirmbankingPort.modifyFirmbankingRequest(entity);
                        }
                );
    }
}
