package com.fastcampuspay.moneyservice.adapter.axon.command;

import com.fastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class IncreaseMemberMoneyCommand extends SelfValidating<IncreaseMemberMoneyCommand> {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    @NotNull
    private String membershipId;

    @NotNull
    private int amount;
}
