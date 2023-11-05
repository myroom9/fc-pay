package com.fastcampuspay.banking.adapter.in.axon.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFirmbankingRequestCommand {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    private int firmbankingStatus;

}
