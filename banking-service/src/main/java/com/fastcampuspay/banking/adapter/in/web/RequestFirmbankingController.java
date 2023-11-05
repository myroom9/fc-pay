package com.fastcampuspay.banking.adapter.in.web;


import com.fastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingUseCase;
import com.fastcampuspay.banking.application.port.in.UpdateFirmbankingCommand;
import com.fastcampuspay.banking.application.port.in.UpdateFirmbankingUseCase;
import com.fastcampuspay.banking.domain.FirmbankingRequest;
import com.fastcampuspay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {

    private final RequestFirmbankingUseCase requestFirmbankingUseCase;
    private final UpdateFirmbankingUseCase updateFirmbankingUseCase;

    @PostMapping("/banking/firmbanking/request")
    public FirmbankingRequest registerFirmbanking(@RequestBody RequestFirmbankingRequest request) {
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .toBankAccountNumber(request.getToBankAccountNumber())
                .toBankName(request.getToBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .fromBankName(request.getFromBankName())
                .moneyAmount(request.getMoneyAmount())
                .build();


        return requestFirmbankingUseCase.requestFirmbanking(command);
    }

    @PostMapping("/banking/firmbanking/request-eda")
    public void registerFirmbankingByEvent(@RequestBody RequestFirmbankingRequest request) {
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .toBankAccountNumber(request.getToBankAccountNumber())
                .toBankName(request.getToBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .fromBankName(request.getFromBankName())
                .moneyAmount(request.getMoneyAmount())
                .build();


        requestFirmbankingUseCase.requestFirmbankingByEvent(command);
    }

    @PutMapping("/banking/firmbanking/update-eda")
    public void updateFirmbankingByEvent(@RequestBody UpdateFirmbankingRequest request) {

        UpdateFirmbankingCommand command = UpdateFirmbankingCommand.builder()
                .firmbankingAggregateIdentifier(request.getFirmbankingRequestAggregateIdentifier())
                .firmbankingStatus(request.getStatus())
                .build();

        updateFirmbankingUseCase.updateFirmbankingByEvent(command);
    }
}
