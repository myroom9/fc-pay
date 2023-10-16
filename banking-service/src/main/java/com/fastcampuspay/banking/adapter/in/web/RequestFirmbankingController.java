package com.fastcampuspay.banking.adapter.in.web;


import com.fastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingUseCase;
import com.fastcampuspay.banking.domain.FirmbankingRequest;
import common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {

    private final RequestFirmbankingUseCase requestFirmbankingUseCase;

    @PostMapping("/banking/firmbanking/request")
    public FirmbankingRequest registerMembership(@RequestBody RequestFirmbankingRequest request) {
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .toBankAccountNumber(request.getToBankAccountNumber())
                .toBankName(request.getToBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .fromBankName(request.getFromBankName())
                .moneyAmount(request.getMoneyAmount())
                .build();


        return requestFirmbankingUseCase.requestFirmbanking(command);
    }

}
