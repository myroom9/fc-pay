package com.fastcampuspay.banking.application.port.in;

import com.fastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class GetRegisteredBankAccountCommand extends SelfValidating<GetRegisteredBankAccountCommand> {

    @NotNull
    private final String membershipId;

    public GetRegisteredBankAccountCommand(String membershipId) {
        this.membershipId = membershipId;
        this.validateSelf();
    }
}
