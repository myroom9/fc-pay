package com.fastcampuspay.moneyservice.adapter.in.web;


import com.fastcampuspay.moneyservice.application.port.in.IncreaseMoneyRequestCommand;
import com.fastcampuspay.moneyservice.application.port.in.IncreaseMoneyRequestUseCase;
import com.fastcampuspay.moneyservice.domain.MoneyChangingRequest;
import com.fastcampuspay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;

    @PostMapping("/money/increase")
    MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {


        /*RegisteredBankAccount registeredBankAccount = registerBankAccountUseCase.registerMBankAccount(command);

        if (ObjectUtils.isEmpty(registeredBankAccount)) {
            return null;
        }

        return registeredBankAccount;*/

        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();
        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);

        // TODO: 임시처리
        MoneyChangingResultDetail moneyChangingResultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                0,
                moneyChangingRequest.getChangingMoneyAmount()
        );

        return moneyChangingResultDetail;
    }


    @PostMapping("/money/increase-async")
    MoneyChangingResultDetail increaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request) {

        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();
        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequestAsync(command);

        // TODO: 임시처리
        MoneyChangingResultDetail moneyChangingResultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                0,
                moneyChangingRequest.getChangingMoneyAmount()
        );

        return moneyChangingResultDetail;
    }

}
