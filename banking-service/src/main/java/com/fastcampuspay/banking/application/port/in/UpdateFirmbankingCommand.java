package com.fastcampuspay.banking.application.port.in;

import com.fastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UpdateFirmbankingCommand extends SelfValidating<UpdateFirmbankingCommand> {

    @NotNull
    private final String firmbankingAggregateIdentifier;

    @NotNull
    private final int firmbankingStatus;

    public UpdateFirmbankingCommand(String firmbankingAggregateIdentifier, int firmbankingStatus) {
        this.firmbankingAggregateIdentifier = firmbankingAggregateIdentifier;
        this.firmbankingStatus = firmbankingStatus;

        this.validateSelf();
    }
}
